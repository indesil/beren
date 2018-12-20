package pl.jlabs.beren.compilator.methods.internal;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import pl.jlabs.beren.annotations.BreakingStrategy;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.definitions.FieldDefinition;
import pl.jlabs.beren.compilator.definitions.ValidationDefinition;
import pl.jlabs.beren.compilator.methods.TypeMetadata;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.regex.Pattern;

import static pl.jlabs.beren.compilator.definitions.PlaceHolders.PARAM_NAME;

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

    public MethodSpec createInternalValidationMethod(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        return strategyCodeGenerator
                .createInternalValidationMethod(validationDefinition)
                .addCode(generateMethodCode(validationDefinition, typeMetadata))
                .build();
    }

    public CodeBlock createInternalMethodCall(ValidationDefinition validationDefinition) {
        return strategyCodeGenerator.createInternalMethodCall(validationDefinition);
    }

    private CodeBlock generateMethodCode(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        return CodeBlock.builder()
                .add(createNullableCodeBlockCodeBlock(validationDefinition))
                .add(createValidationBlocks(validationDefinition, typeMetadata))
                .build();
    }

    private CodeBlock createNullableCodeBlockCodeBlock(ValidationDefinition validationDefinition) {
        ExecutableElement methodToImplement = validationDefinition.getMethodToImplement();
        String paramName = validationDefinition.getParameter().getSimpleName().toString();
        String violationMessage = formatViolationMessage(validationDefinition.getNullableMessage(), paramName);
        return CodeBlock.builder()
                .beginControlFlow("if($L == null)", paramName)
                .add(validationDefinition.isNullable() ? voidReturnBlock() : strategyCodeGenerator.createInvalidValueBlock(violationMessage))
                .endControlFlow()
                .build();
    }

    private String formatViolationMessage(String message, String paramName) {
        return PARAM_NAME_PATTERN.matcher(message).replaceAll(paramName);
    }

    private CodeBlock voidReturnBlock() {
        return CodeBlock.builder().addStatement("return").build();
    }

    private CodeBlock createValidationBlocks(ValidationDefinition validationDefinition, TypeMetadata typeMetadata) {
        CodeBlock.Builder builder = CodeBlock.builder();
        //    @Validate({
        //            @Field(name = "source", operation = "neitherOf(SarumanGifts, MordorGmbH)"),
        //            @Field(name = "requestId", operation = "biggerThan(0)", message = "requestId should be bigger than 0!"),
        //            @Field(name = "orders", operation = "validateOrders")
        //    })

        for (FieldDefinition fieldDefinition : validationDefinition.getFieldDefinitions()) {
            getFieldsGetters(fieldDefinition, validationDefinition);
        }

        return builder.build();
    }

    private List<String> getFieldsGetters(FieldDefinition fieldDefinition, ValidationDefinition validationDefinition){
        ElementFilter.fieldsIn(validationDefinition.getParameter().getEnclosedElements());

        return null;
    }
}
