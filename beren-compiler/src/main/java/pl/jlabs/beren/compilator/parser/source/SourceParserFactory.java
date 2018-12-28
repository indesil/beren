package pl.jlabs.beren.compilator.parser.source;

import pl.jlabs.beren.annotations.SourceType;
import pl.jlabs.beren.compilator.utils.ProcessingFacade;

import static pl.jlabs.beren.compilator.utils.ErrorMessages.SOURCE_NOT_SUPPORTED;

public class SourceParserFactory {
    private ProcessingFacade processingFacade;

    public SourceParserFactory(ProcessingFacade processingFacade) {
        this.processingFacade = processingFacade;
    }

    public SourceParser obtainParserForSource(SourceType sourceType) {
        if(sourceType.equals(SourceType.METHOD_DEFINITION)) {
            return new MethodAnnotationSourceParser(processingFacade);
        }
        //TO DO ADD OTHERS SOURCES
        processingFacade.error(SOURCE_NOT_SUPPORTED, sourceType);
        return new VoidParser();
    }
}
