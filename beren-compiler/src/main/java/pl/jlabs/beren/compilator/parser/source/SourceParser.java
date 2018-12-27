package pl.jlabs.beren.compilator.parser.source;

import pl.jlabs.beren.compilator.parser.ValidationDefinition;

public interface SourceParser {
    ValidationDefinition parse(ParserContext parserContext);
}
