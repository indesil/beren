package pl.jlabs.beren;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;

import static java.lang.String.format;

public class Beren {

    public static <T> T loadValidator(Class<T> validatorInterfaceClass) {
        return loadValidator(validatorInterfaceClass, new Object[0]);
    }

    public static <T> T loadValidator(Class<T> validatorInterfaceClass, Object...constructorArgs) {
        try {
            Class<T> validatorClass = loadValidatorClass(validatorInterfaceClass);
            return ConstructorUtils.invokeConstructor(validatorClass, constructorArgs);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to load validator class", e);
        }
    }

    private static <T> Class<T> loadValidatorClass(Class<T> validatorInterfaceClass) throws ClassNotFoundException {
        String validatorClassName = BerenUtils.createValidatorName(validatorInterfaceClass.getName());
        Class<?> validatorImplClass = getClassLoader().loadClass(validatorClassName);
        if(validatorImplClass.isAssignableFrom(validatorImplClass)) {
            return (Class<T>) validatorImplClass;
        }
        String errorMessage = format("Class %s does not implement %s", validatorImplClass.getSimpleName(),
                validatorInterfaceClass.getSimpleName());
        throw new IllegalArgumentException(errorMessage);
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null) {
            classLoader = Beren.class.getClassLoader();
        }

        return classLoader;
    }
}
