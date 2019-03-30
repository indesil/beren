package io.github.indesil.beren.utils;

public class BerenUtils {

    private static final String IMPLEMENTATION_SUFFIX = "_ImplBeren_";

    public static String createValidatorName(String interfaceName) {
        return interfaceName + IMPLEMENTATION_SUFFIX;
    }
}
