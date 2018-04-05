package pl.indesil.tools.beren.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ToString
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class FieldViolationInfo {
    private String fieldName;
    private String fieldPath;
    private String validationMessage;
    private Map<String, String> customValidatorsHolders = new HashMap<>();
}
