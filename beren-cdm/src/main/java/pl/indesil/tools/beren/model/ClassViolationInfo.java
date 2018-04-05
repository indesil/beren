package pl.indesil.tools.beren.model;

import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@Builder
@EqualsAndHashCode
public class ClassViolationInfo {
    private String classSimpleName;
    private String classFullName;
    private List<FieldViolationInfo> singleFieldsViolations;
    private List<GroupViolationInfo> groupsViolations;
}
