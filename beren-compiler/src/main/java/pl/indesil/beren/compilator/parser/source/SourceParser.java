package pl.indesil.beren.compilator.parser.source;

import pl.indesil.beren.compilator.parser.ValidationDefinition;

public interface SourceParser {
    ValidationDefinition parse(ParserContext parserContext);
}
