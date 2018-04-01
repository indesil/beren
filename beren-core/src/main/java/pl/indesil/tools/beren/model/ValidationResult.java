package pl.indesil.tools.beren.model;

import lombok.*;

@ToString
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class ValidationResult {
    private String classSimpleName;
    private String classFullName;
    private String fieldName;
    private String errorMessage;

}
