package pl.jlabs.beren.model;

import java.util.HashMap;
import java.util.Map;

public class OperationContext {
    private Object validationObject;
    private Map<String, String> operationPlaceHolders = new HashMap<>();

    public Object getValidationObject() {
        return validationObject;
    }

    public <T> T getValidationObject(Class<T> expectedType) {
        Class<?> validationObjectClass = validationObject.getClass();
        if(validationObjectClass.equals(expectedType)) {
            return (T) validationObject;
        }
        throw new IllegalArgumentException(validationObjectClass + " is not type of " + expectedType);
    }

    public void addPlaceHolder(String placeHolder, String value) {
        operationPlaceHolders.put(placeHolder, value);
    }

    public void setValidationObject(Object validationObject) {
        this.validationObject = validationObject;
    }
}
