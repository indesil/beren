package io.github.indesil.beren.compilator.parser.source;

import io.github.indesil.beren.compilator.parser.ValidationDefinition;

public interface SourceParser {
    ValidationDefinition parse(ParserContext parserContext);
}
