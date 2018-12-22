package pl.jlabs.beren.compilator.parser.source;

import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.parser.FieldValidationDefinition;
import pl.jlabs.beren.compilator.parser.RawFieldDefinition;
import pl.jlabs.beren.compilator.parser.ValidationDefinition;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static javax.tools.Diagnostic.Kind.ERROR;
import static pl.jlabs.beren.compilator.utils.DefinitionUtils.createRawFieldDefinition;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.VALIDATION_FIELDS_EMPTY;
import static pl.jlabs.beren.compilator.utils.MethodsUtil.getValidationMethodParam;

public class MethodAnnotationSourceParser implements SourceParser {
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;

    public MethodAnnotationSourceParser(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
    }

    @Override
    public ValidationDefinition parse(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        VariableElement validationMethodParam = getValidationMethodParam(methodToImplement);
        Validate validateAnnotation = methodToImplement.getAnnotation(Validate.class);
        return new ValidationDefinition()
                .withNullable(validateAnnotation.nullable())
                .withNullableMessageTemplate(validateAnnotation.nullableMessage())
                .withFieldsToValidate(createFieldsDefinitions(validateAnnotation.value(), methodToImplement, methodReferences))
                .withValidatedParamName(validationMethodParam.getSimpleName().toString());
    }

    private List<FieldValidationDefinition> createFieldsDefinitions(Field[] fields, ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        String methodName = methodToImplement.getSimpleName().toString();
        if(fields == null || fields.length == 0) {
            processingEnv.getMessager().printMessage(ERROR, format(VALIDATION_FIELDS_EMPTY, methodName));
            return emptyList();
        }

        List<FieldValidationDefinition> fieldValidationDefinitions = new ArrayList<>();
        for (Field field : fields) {
            RawFieldDefinition rawFieldDefinition = createRawFieldDefinition(field, methodName, processingEnv);
            if(rawFieldDefinition != null) {
                fieldValidationDefinitions.addAll(createDefinitionsForField(rawFieldDefinition, methodToImplement, methodReferences));
            } else {
                break;
            }
        }

        return fieldValidationDefinitions;
    }

    private List<FieldValidationDefinition> createDefinitionsForField(RawFieldDefinition rawFieldDefinition, ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences) {
        return null;
    }
}
