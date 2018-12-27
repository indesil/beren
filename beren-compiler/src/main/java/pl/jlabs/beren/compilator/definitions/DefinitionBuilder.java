package pl.jlabs.beren.compilator.definitions;

import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.compilator.utils.CodeUtils;
import pl.jlabs.beren.compilator.utils.MethodsUtil;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static pl.jlabs.beren.compilator.utils.CodeUtils.normalizeGetterName;

public class DefinitionBuilder {

    public static ValidationDefinition fromAnnotation(ExecutableElement methodElement, ProcessingEnvironment processingEnv) {
        Validate annotation = methodElement.getAnnotation(Validate.class);
        return new ValidationDefinition()
                .withNullable(annotation.nullable())
                .withNullableMessage(annotation.nullableMessage())
                .withFieldDefinitions(createFieldsDefinitions(annotation.value()))
                .withValidationParameter(createValidationParamDefinition(methodElement, processingEnv))
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

    private static ValidationParamDefinition createValidationParamDefinition(ExecutableElement methodToImplement, ProcessingEnvironment processingEnv) {
        VariableElement param = CodeUtils.getValidationMethodParam(methodToImplement);
        return new ValidationParamDefinition()
                .withParam(param)
                .withMapOfGetters(createVariableMapMap(param, processingEnv))
                .withParamName(param.getSimpleName().toString());
    }

    private static Map<String, GetterDefinition> createVariableMapMap(VariableElement param, ProcessingEnvironment processingEnv) {
        TypeElement paramElement = (TypeElement) processingEnv.getTypeUtils().asElement(param.asType());
        List<? extends Element> allMembers = processingEnv.getElementUtils().getAllMembers(paramElement);
        return MethodsUtil.extractMethods(allMembers, processingEnv)
                .stream()
                .filter(CodeUtils::isGetterMethod)
                .collect(toMap(method -> normalizeGetterName(method.getSimpleName().toString()), DefinitionBuilder::toVariableDefinition));
    }

    private static GetterDefinition toVariableDefinition(ExecutableElement getterMethod) {
        return new GetterDefinition()
                .withVariableGetter(getterMethod.getSimpleName().toString())
                .withVariableType(getterMethod.getReturnType());
    }
}
