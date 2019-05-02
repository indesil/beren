package io.github.indesil.beren.operations;

import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;

import java.util.List;
import java.util.regex.Pattern;

public class Operations {

    public static boolean oneOf(Object input, List<Object> expectedObjects) {
        return expectedObjects.contains(input);
    }

    public static boolean neitherOf(Object input, List<Object> expectedObjects) {
        return !expectedObjects.contains(input);
    }

    public static boolean email(CharSequence email, String additionalPattern, int flags) {
        if(email == null || email.length() == 0) {
            return true;
        }

        boolean isValid = EmailAddressValidator.isValid(email.toString());
        if(!isValid || additionalPattern == null) {
            return isValid;
        }

        return Pattern.compile(additionalPattern, flags).matcher(email).matches();
    }

    public static boolean pattern(CharSequence value, String pattern, int flags) {
        return value == null || Pattern.compile(pattern, flags).matcher(value).matches();
    }
}