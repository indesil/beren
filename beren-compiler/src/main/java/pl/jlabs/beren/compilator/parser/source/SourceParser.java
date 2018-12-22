package pl.jlabs.beren.compilator.parser.source;

import pl.jlabs.beren.compilator.parser.ValidationDefinition;

import javax.lang.model.element.ExecutableElement;
import java.util.Map;

public interface SourceParser {
    ValidationDefinition parse(ExecutableElement methodToImplement, Map<String, ExecutableElement> methodReferences);
}
