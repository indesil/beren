package pl.indesil.beren.compilator.parser.source;

import pl.indesil.beren.compilator.utils.ErrorMessages;
import pl.indesil.beren.compilator.utils.ProcessingFacade;
import pl.indesil.beren.annotations.SourceType;

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
        processingFacade.error(ErrorMessages.SOURCE_NOT_SUPPORTED, sourceType);
        return new VoidParser();
    }
}
