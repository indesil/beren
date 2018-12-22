package pl.jlabs.beren.compilator.parser.source;

import pl.jlabs.beren.annotations.SourceType;
import pl.jlabs.beren.compilator.configuration.BerenConfig;

import javax.annotation.processing.ProcessingEnvironment;

import static java.lang.String.format;
import static javax.tools.Diagnostic.Kind.ERROR;
import static pl.jlabs.beren.compilator.utils.ErrorMessages.SOURCE_NOT_SUPPORTED;

public class SourceParserFactory {
    private ProcessingEnvironment processingEnv;
    private BerenConfig berenConfig;

    public SourceParserFactory(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.berenConfig = berenConfig;
    }

    public SourceParser obtainParserForSource(SourceType sourceType) {
        if(sourceType.equals(SourceType.METHOD_DEFINITION)) {
            return new MethodAnnotationSourceParser(processingEnv, berenConfig);
        }
        //TO DO ADD OTHERS SOURCES
        processingEnv.getMessager().printMessage(ERROR, format(SOURCE_NOT_SUPPORTED, sourceType));
        return new VoidParser();
    }
}
