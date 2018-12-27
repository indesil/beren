package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.configuration.OperationConfig;
import pl.jlabs.beren.compilator.definitions.*;
import pl.jlabs.beren.compilator.methods.TypeMetadata;
import pl.jlabs.beren.compilator.utils.CodeUtils;
import pl.jlabs.beren.compilator.utils.OperationUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static javax.tools.Diagnostic.Kind.ERROR;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static pl.jlabs.beren.compilator.configuration.OperationConfig.OperationType.CLASS_METHOD;
import static pl.jlabs.beren.compilator.configuration.OperationConfig.OperationType.INTERNAL_VALIDATOR_METHOD;
import static pl.jlabs.beren.compilator.definitions.PlaceHolders.PARAM_NAME;
import static pl.jlabs.beren.compilator.definitions.PlaceHolders.THIS_;
import static pl.jlabs.beren.compilator.utils.CodeUtils.normalizeGetterName;
import static pl.jlabs.beren.compilator.utils.OperationUtils.extractMethodName;

public class InternalMethodGenerator {

    private Pattern PARAM_NAME_PATTERN = Pattern.compile(PARAM_NAME);
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;
    private StrategyCodeGenerator strategyCodeGenerator;

    public InternalMethodGenerator(ProcessingEnvironment processingEnv, BerenConfig berenConfig, BreakingStrategy breakingStrategy) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
        if(breakingStrategy.equals(BreakingStrategy.THROW_ON_FIRST)) {
            strategyCodeGenerator = new ThrowOnFirstCodeGenerator();
        } else {
            strategyCodeGenerator = new SummarizeCodeGenerator();
        }
    }

    public MethodSpec createValidationMethod(ValidationDefinition validationDefinition) {
        return strategyCodeGenerator.createValidationMethod(validationDefinition);
    }

    public MethodSpec createInternalValidationMethod(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        return strategyCodeGenerator
                .createInternalValidationMethod(validationDefinition)
                .addCode(generateMethodCode(validationDefinition, typeMetadata))
                .build();
    }

    private CodeBlock generateMethodCode(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        return CodeBlock.builder()
                .add(createNullableCodeBlock(validationDefinition))
                .add(createValidationBlocks(validationDefinition, typeMetadata))
                .build();
    }

    private CodeBlock createNullableCodeBlock(ValidationDefinition validationDefinition) {
        String paramName = validationDefinition.getValidationParameter().getParamName();
        String violationMessage = formatViolationMessage(validationDefinition.getNullableMessage(), paramName);
        return strategyCodeGenerator.createNullableCodeBlock(validationDefinition, violationMessage);
    }

    private String formatViolationMessage(String message, String paramName) {
        return PARAM_NAME_PATTERN.matcher(message).replaceAll(paramName);
    }

    private CodeBlock createValidationBlocks(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        CodeBlock.Builder builder = CodeBlock.builder();
        for (FieldDefinition fieldDefinition : validationDefinition.getFieldDefinitions()) {
            builder.add(createFieldValidationBlock(fieldDefinition, validationDefinition, typeMetadata));
        }

        return builder.build();
    }

    private CodeBlock createFieldValidationBlock(FieldDefinition fieldDefinition, ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        OperationDefinition operationDefinition = parseOperation(fieldDefinition.getOperation());
        OperationConfig mappingForOperation = findMappingForOperation(operationDefinition, typeMetadata);
        CodeBlock.Builder builder = CodeBlock.builder();

        for (GetterDefinition fieldToValidate : determineFieldsToValidate(fieldDefinition, validationDefinition)) {
            if(mappingForOperation.getOperationType().equals(INTERNAL_VALIDATOR_METHOD)) {
                builder.add(createInternalValidatorCall(operationDefinition, validationDefinition, fieldToValidate));
            } else if(mappingForOperation.getOperationType().equals(CLASS_METHOD)) {
                builder.add(createValidationByClassMethodBlock(mappingForOperation, fieldToValidate, fieldDefinition, validationDefinition));
            }
            else {
                builder.add(createValidationIfBlock(operationDefinition, mappingForOperation, fieldToValidate, fieldDefinition, validationDefinition));
            }
        }

        return builder.build();
    }

    private CodeBlock createInternalValidatorCall(OperationDefinition operationDefinition, ValidationDefinition validationDefinition, GetterDefinition fieldToValidate) {
        String paramName = validationDefinition.getValidationParameter().getParamName();
        String fieldGetter = createGetterCall(fieldToValidate, paramName);
        return CodeBlock.builder()
                .add(strategyCodeGenerator.createInternalMethodCall(operationDefinition.getNameRef(), fieldGetter))
                .build();
    }

    private CodeBlock createValidationByClassMethodBlock(OperationConfig mappingForOperation, GetterDefinition fieldToValidate, FieldDefinition fieldDefinition, ValidationDefinition validationDefinition) {
        String methodCall = mappingForOperation.getOperationCall();
        String violationMessage = createViolationMessage(fieldDefinition, fieldToValidate, mappingForOperation);
        String paramName = validationDefinition.getValidationParameter().getParamName();
        return CodeBlock.builder()
                .beginControlFlow("if(!$L($L))", methodCall, createGetterCall(fieldToValidate, paramName))
                .add(strategyCodeGenerator.createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }

    private CodeBlock createValidationIfBlock(OperationDefinition operationDefinition, OperationConfig mappingForOperation, GetterDefinition fieldToValidate, FieldDefinition fieldDefinition, ValidationDefinition validationDefinition) {
        //brak tutaj jakiej kolwiek walidacji na wypadek literówki czy czegos index out of boundry jezeli nie ma takiej klasy to bardzo zle
        //trzeba dodac walidacje do parsera zeby byla sprawa jasna
        Pair<String, String> packageAndClassName = extractPackageAndClassName(mappingForOperation.getOperationCall());
        ClassName operationCallClass = ClassName.get(packageAndClassName.getKey(), packageAndClassName.getValue());
        String methodCall = extractMethodName(mappingForOperation.getOperationCall());
        String violationMessage = createViolationMessage(fieldDefinition, fieldToValidate, mappingForOperation);
        String callParams = determineCallParams(operationDefinition, fieldToValidate, validationDefinition.getValidationParameter().getParamName());
        //poki co to zakladamy ze
        // zawsze this jest na pcozatku
        // robimy paste po prostu argumentow tak jak leci nie patrzac na to jak zostaly zdefiniowane
        //potem jak sie ddorobi parser z rpawdziwego zdazenia to bedzie poprawka
        return CodeBlock.builder()
                .beginControlFlow("if(!$T.$L($L))", operationCallClass, methodCall, callParams)
                .add(strategyCodeGenerator.createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }

    private String createGetterCall(GetterDefinition fieldToValidate, String variableName) {
        return variableName + "." + fieldToValidate.getVariableGetter() + "()";
    }

    private String determineCallParams(OperationDefinition operationDefinition, GetterDefinition fieldToValidate, String variableName) {
        String getterCall = createGetterCall(fieldToValidate, variableName);
        List<String> argsValues = operationDefinition.getArgsValues();
        if(!argsValues.isEmpty()) {
            return getterCall + "," + join(",", argsValues);
        }
        return getterCall;
    }

    private List<GetterDefinition> determineFieldsToValidate(FieldDefinition fieldDefinition, ValidationDefinition validationDefinition) {
        ValidationParamDefinition validationParameter = validationDefinition.getValidationParameter();
        if(StringUtils.isNoneEmpty(fieldDefinition.getName())) {
            String fieldNameNormalized = fieldDefinition.getName().toLowerCase();
            GetterDefinition getter = validationParameter.getMapOfVariables().get(fieldNameNormalized);
            return Collections.singletonList(getter);
        }

        if(fieldDefinition.getNames() != null && fieldDefinition.getNames().length > 0) {
            List<String> names = Stream.of(fieldDefinition.getNames()).map(String::toLowerCase).collect(toList());
            List<GetterDefinition> variablesOfType = validationParameter.getMapOfVariables().entrySet()
                    .stream()
                    .filter(entry -> names.contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(toList());
            if(variablesOfType.size() == names.size()) {
                return variablesOfType;
            } else {
                //ten message moglby pokazywac tylko te pola ktorych naprawde nie mozna zmatchowac
                String errorMessage = format("Not all fields %s could be matched to variable %s!", names, validationParameter);
                processingEnv.getMessager().printMessage(ERROR, errorMessage);
                return emptyList();
            }
        }

        if(isNotEmpty(fieldDefinition.getPattern())) {
            //tutaj juz jest lipa bo mamy splaszczone te names w mapie toLowerCase jak ktos tego nie wezmie pod uwage to lezy
            Pattern pattern = Pattern.compile(fieldDefinition.getPattern());
            List<GetterDefinition> variablesOfType = validationParameter.getMapOfVariables().entrySet()
                    .stream()
                    .filter(entry -> pattern.matcher(entry.getKey()).matches())
                    .map(Map.Entry::getValue)
                    .collect(toList());
            if (!variablesOfType.isEmpty()) {
                return variablesOfType;
            }
            String errorMessage = format("No variables from %s matched pattern %s", validationParameter, fieldDefinition.getPattern());
            processingEnv.getMessager().printMessage(ERROR, errorMessage);
            return emptyList();
        }

        if(CodeUtils.isNotVoidType(fieldDefinition.getType())) {
            List<GetterDefinition> variablesOfType = validationParameter.getMapOfVariables().entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().getVariableType().equals(fieldDefinition.getType()))
                    .map(entry -> entry.getValue())
                    .collect(toList());

            if(!variablesOfType.isEmpty()) {
                return variablesOfType;
            }
        }

        String errorMessage = format("Could not match %s to any variable type %s!", fieldDefinition, validationParameter);
        processingEnv.getMessager().printMessage(ERROR, errorMessage);
        return emptyList();
    }

    private OperationDefinition parseOperation(String operation) {
        return new OperationDefinition()
                .withNameRef(OperationUtils.extractOperationRef(operation))
                .withArgsValues(OperationUtils.parseParams(operation));
    }

    //no todbra takie zalozenie dla MVP 1
    // nie sprawdzamy zadnej informacji o tym co dokladnie przyjmuje metoda daltego tez nie interesuja nas przeladowania parametrami
    // ma sie kompilowac i to od usera zalezy czy bedzie
    //potem nalzezy dorobic ROZBUDOWANY system parsowania konfiguracji ktory bedzie pozwalał na przechowyanie
    //informacji o typach jakie operacja przyjmuje itp
    // obecnie to jest zbyt duzo roboty
    private OperationConfig findMappingForOperation(OperationDefinition operationDefinition, TypeMetadata typeMetadata) {
        Optional<Map.Entry<String, OperationConfig>> mappingForOperation = berenConfig.getOperationsMappings().entrySet()
                .stream()
                .filter(entry -> entry.getKey().startsWith(operationDefinition.getNameRef()))
                .findAny();

        //jeszcze ktos sie mogl pozluc zdefiniowana metoda przez siebie samego...
        if(!mappingForOperation.isPresent()) {
            Optional<ExecutableElement> customDefinedMethod = typeMetadata.getMethod(operationDefinition.getNameRef());
            if(!customDefinedMethod.isPresent()) {
                String message = format("Operation %s is not defined in configuration!", operationDefinition);
                processingEnv.getMessager().printMessage(ERROR, message);
                throw new IllegalArgumentException(message);
            }
            return createCustomDefinedOperation(customDefinedMethod.get(), operationDefinition);
        }

        return mappingForOperation.get().getValue();
    }

    //stop!!!!!
    //jezeli to nasz customowy Validate to nie moze byc traktowany jako zwykly if() !!! tylko jako call
    //jezeli ta metoda zwraca boolean to wtedy if() czyli juz trzeba patrzec na return type i traktowac to inaczej niz zwykyl call!
    //O.o.o.o.o.o.o
    //to sie robi coraz trudniejsze
    //ehhh

    //poki co niech jao arg przyjmuje jedynie this
    //if methos has Validate -> customValidator call dla internalMethod(this, validationResutls)
    // else boolean validator call dla (ths)
    private OperationConfig createCustomDefinedOperation(ExecutableElement executableElement, OperationDefinition operationDefinition) {
        return new OperationConfig()
                .withOperationCall(executableElement.getSimpleName().toString())
                .withArgs(asList(THIS_))
                .withDefaultMessage("NOT MESSAGE")
                .withOperationType(executableElement.getAnnotation(Validate.class) != null ? INTERNAL_VALIDATOR_METHOD : CLASS_METHOD);
    }

    private Pair<String, String> extractPackageAndClassName(String operationCall) {
        int lastDot = operationCall.lastIndexOf('.');
        String classWithPackage = operationCall.substring(0, lastDot);
        lastDot = classWithPackage.lastIndexOf('.');

        return Pair.of(classWithPackage.substring(0, lastDot), classWithPackage.substring(lastDot + 1));
    }

    private String createViolationMessage(FieldDefinition fieldDefinition, GetterDefinition fieldToValidate, OperationConfig mappingForOperation) {
        String paramName = normalizeGetterName(fieldToValidate.getVariableGetter());

        if(isNotEmpty(fieldDefinition.getMessage())) {
            return formatViolationMessage(fieldDefinition.getMessage(), paramName);
        }
        return formatViolationMessage(mappingForOperation.getDefaultMessage(), paramName);
    }
}
