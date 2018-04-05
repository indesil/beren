package pl.indesil.tools.beren.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class ValidationResult {
    private List<ClassViolationInfo> classViolationInfo = new ArrayList<>();

    public boolean isValidationSuccessful() {
        return classViolationInfo.isEmpty();
    }
}
