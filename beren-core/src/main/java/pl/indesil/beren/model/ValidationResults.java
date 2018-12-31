package pl.indesil.beren.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResults {
    private List<String> violations = new ArrayList<>();

    public void addViolation(String violation) {
        violations.add(violation);
    }

    public boolean hasViolations() {
        return !violations.isEmpty();
    }

    public List<String> getViolations() {
        return violations;
    }
}
