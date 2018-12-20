package pl.jlabs.beren.compilator.definitions;

import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Validate;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DefinitionBuilder {
    public static ValidationDefinition fromAnnotation(ExecutableElement methodElement) {
        Validate annotation = methodElement.getAnnotation(Validate.class);
        return new ValidationDefinition()
                .withNullable(annotation.nullable())
                .withNullableMessage(annotation.nullableMessage())
                .withFieldDefinitions(createFieldsDefinitions(annotation.value()))
                .withMethodToImplement(methodElement);
    }

    private static List<FieldDefinition> createFieldsDefinitions(Field[] fields) {
        return Stream.of(fields).map(DefinitionBuilder::createFieldDefinition).collect(toList());
    }

    private static FieldDefinition createFieldDefinition(Field field) {
        return new FieldDefinition()
                .withName(field.name())
                .withNames(field.names())
                .withType(getClassValue(field))
                .withPattern(field.pattern())
                .withOperation(field.operation())
                .withMessage(field.message());
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
