package pl.jlabs.beren.compilator.utils;

public interface ErrorMessages {
    String NON_ABSTRACT = "Validator method %s must be abstract to override!";
    String PARAMS_NUMBER = "Validator method %s must have 1 (and only 1) input parameter!";
    String THROW_ON_FIRST_NOT_VOID = "For THROW_ON_FIRST strategy validator method %s must return void!";
    String SUMMARIZE_BAD_RETURN = "For SUMMARIZE_ALL strategy validator method %s must return ValidationResults!";
    String INVALID_PATTERN = "Invalid pattern %s - %s";
    String INVALID_SELECTORS_NUMBER = "Invalid number of selectors %s for method %s! Must have one and only one selector! Choose only one of [name, names, type, pattern(has it compiled?)]!";

    String SOURCE_NOT_SUPPORTED = "Source %s is not supported in current version!";
    String VALIDATION_FIELDS_EMPTY = "Could not find any fields validation definition for method %s";
}
