package pl.indesil.tools.beren.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@ToString
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class GroupViolationInfo {
    private String message;
    private Map<String, FieldViolationInfo> fieldsViolations = new HashMap<>();
}
