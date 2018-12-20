package pl.jlabs.beren.compilator.definitions;

import pl.jlabs.beren.compilator.utils.CodeUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

public class ValidationDefinition {
    private ExecutableElement methodToImplement;
    private String methodName;
    private VariableElement parameter;
    //dodac parameter meta data zeby miec wszystkie gettery dla danego parametru
    //processingEnv.getElementUtils().getAllMembers((TypeElement) processingEnv.getTypeUtils().asElement(validationDefinition.getParameter().asType()))
    //ElementFilter.methodsIn().filterGetters()
    private boolean nullable;
    private String nullableMessage;
    private List<FieldDefinition> fieldDefinitions;

    public ValidationDefinition withMethodToImplement(ExecutableElement methodToImplement) {
        this.methodToImplement = methodToImplement;
        this.methodName = methodToImplement.getSimpleName().toString();
        this.parameter = CodeUtils.getValidationMethodParam(methodToImplement);
        return this;
    }

    public ValidationDefinition withNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public ValidationDefinition withNullableMessage(String nullableMessage) {
        this.nullableMessage = nullableMessage;
        return this;
    }

    public ValidationDefinition withFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        return this;
    }

    public ExecutableElement getMethodToImplement() {
        return methodToImplement;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isNullable() {
        return nullable;
    }

    public String getNullableMessage() {
        return nullableMessage;
    }

    public VariableElement getParameter() {
        return parameter;
    }

    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }
}
