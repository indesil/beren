package pl.jlabs.beren.compilator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import pl.jlabs.beren.compilator.methods.ClassMethods;
import pl.jlabs.beren.compilator.methods.MethodsCodeGenerator;

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

public class ClassCodeGenerator {

    public static void generateJavaClass(Element element, ClassMethods classMethods, ProcessingEnvironment processingEnv) throws IOException {
        String validatorClass = element.toString();
        PackageElement elementPackage = processingEnv.getElementUtils().getPackageOf(element);
        TypeSpec validatorJava = createJavaType(element, MethodsCodeGenerator.generateMethodsCode());
        JavaFile javaFile = JavaFile.builder(elementPackage.toString(), validatorJava)
                .build();

        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(validatorClass + "Impl", element);

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
