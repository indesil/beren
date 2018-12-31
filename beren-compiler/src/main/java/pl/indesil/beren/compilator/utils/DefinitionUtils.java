package pl.indesil.beren.compilator.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import pl.indesil.beren.annotations.Field;
import pl.indesil.beren.compilator.parser.RawFieldDefinition;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class DefinitionUtils {

    public static RawFieldDefinition createRawFieldDefinition(Field field, String methodName, ProcessingFacade processingFacade) {
        String pattern = field.pattern();
        try{
            RawFieldDefinition rawFieldDefinition = new RawFieldDefinition()
                    .withName(field.name())
                    .withNames(field.names())
                    .withType(getClassValue(field))
                    .withPattern(isNotEmpty(pattern) ? Pattern.compile(pattern) : null)
                    .withOperation(field.operation())
                    .withMessage(field.message());
            if(isDefinitionValid(rawFieldDefinition)) {
               return rawFieldDefinition;
            }
            processingFacade.error(ErrorMessages.INVALID_SELECTORS_NUMBER, rawFieldDefinition, methodName);
        } catch (PatternSyntaxException e) {
            processingFacade.error(ErrorMessages.INVALID_PATTERN, pattern, e.getMessage());
        }

        return null;
    }

    private static boolean isDefinitionValid(RawFieldDefinition rawFieldDefinition) {
        String type = CodeUtils.isNotVoidType(rawFieldDefinition.getType()) ? rawFieldDefinition.getType().toString() : null;
        String name = isNotEmpty(rawFieldDefinition.getName()) ? rawFieldDefinition.getName() : null;
        String[] names = ArrayUtils.isNotEmpty(rawFieldDefinition.getNames())
                && StringUtils.isNoneEmpty(rawFieldDefinition.getNames()) ? rawFieldDefinition.getNames() : null;
        Pattern pattern = nonNull(rawFieldDefinition.getPattern()) ? rawFieldDefinition.getPattern() : null;
        return Stream.of(name, names, pattern, type)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).size() == 1;
    }

    private static TypeMirror getClassValue(Field field) {
        try {
            field.type();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }
}
