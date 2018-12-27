package pl.jlabs.beren.compilator.parser.source;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.Map;

public class ParserContext {
    private ExecutableElement methodToImplement;
    private Map<String, ExecutableElement> methodReferences;
    private VariableElement validationMethodParam;
    private List<ExecutableElement> validationParamFieldsGetters;

    public ExecutableElement getMethodToImplement() {
        return methodToImplement;
    }

    public String getMethodToImplementName() {
        return methodToImplement.getSimpleName().toString();
    }

    public ParserContext withMethodToImplement(ExecutableElement methodToImplement) {
        this.methodToImplement = methodToImplement;
        return this;
    }

    public Map<String, ExecutableElement> getMethodReferences() {
        return methodReferences;
    }

    public ParserContext withMethodReferences(Map<String, ExecutableElement> methodReferences) {
        this.methodReferences = methodReferences;
        return this;
    }

    public VariableElement getValidationMethodParam() {
        return validationMethodParam;
    }

    public String getValidationMethodParamName() {
        return validationMethodParam.getSimpleName().toString();
    }

    public ParserContext withValidationMethodParam(VariableElement validationMethodParam) {
        this.validationMethodParam = validationMethodParam;
        return this;
    }

    public List<ExecutableElement> getValidationParamFieldsGetters() {
        return validationParamFieldsGetters;
    }

    public ParserContext withValidationParamFieldsGetters(List<ExecutableElement> validationParamFieldsGetters) {
        this.validationParamFieldsGetters = validationParamFieldsGetters;
        return this;
    }
}
