package pl.jlabs.beren;

public class BerenUtils {

    private static final String IMPLEMENTATION_SUFFIX = "ImplBeren_";

    public static String createValidatorName(String interfaceName) {
        return interfaceName + IMPLEMENTATION_SUFFIX;
    }
}
