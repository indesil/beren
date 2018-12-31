package pl.indesil.beren.compilator.parser;

import javax.lang.model.type.TypeMirror;
import java.util.List;

import static pl.indesil.beren.compilator.parser.GetterDefinition.ReturnType.SINGLE_FIELD;

public class GetterDefinition {
    private String getterCall;
    private ReturnType returnType = SINGLE_FIELD;
    private List<? extends TypeMirror> genericTypes;

    public String getGetterCall() {
        return getterCall;
    }

    public GetterDefinition withGetterCall(String name) {
        this.getterCall = name;
        return this;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public GetterDefinition withReturnType(ReturnType returnType) {
        this.returnType = returnType;
        return this;
    }

    public List<? extends TypeMirror> getGenericTypes() {
        return genericTypes;
    }

    public GetterDefinition withGenericTypes(List<? extends TypeMirror> genericTypes) {
        this.genericTypes = genericTypes;
        return this;
    }

    public enum ReturnType {
        SINGLE_FIELD, COLLECTION, MAP;
    }
}
