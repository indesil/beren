package pl.jlabs.beren.compilator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import pl.jlabs.beren.compilator.parser.ValidatorDefinition;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import static pl.jlabs.beren.compilator.methods.MethodsCodeGenerator.generateMethodsCode;

public class ClassCodeGenerator {

    public static void generateJavaClass(Element typeElement, ValidatorDefinition validatorDefinition, ProcessingEnvironment processingEnv) throws IOException {
        String validatorClass = typeElement.toString();
        PackageElement elementPackage = processingEnv.getElementUtils().getPackageOf(typeElement);
        TypeSpec validatorJava = createJavaType(typeElement, generateMethodsCode(validatorDefinition));

        JavaFile javaFile = JavaFile.builder(elementPackage.toString(), validatorJava)
                .addStaticImport(Arrays.class, "asList")
                .build();
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(validatorClass + "Impl", typeElement);

        Writer out = sourceFile.openWriter();
        javaFile.writeTo(out);
        out.close();
    }

    private static TypeSpec createJavaType(Element element, List<MethodSpec> methods) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(element.getSimpleName().toString() + "Impl")
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methods);

        if(element.getKind().equals(ElementKind.INTERFACE)) {
            builder.addSuperinterface(TypeName.get(element.asType()));
        } else {
            builder.superclass(TypeName.get(element.asType()));
        }

        return builder.build();
    }
}
