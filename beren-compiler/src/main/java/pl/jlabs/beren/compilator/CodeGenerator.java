package pl.jlabs.beren.compilator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

public class CodeGenerator {
    public static void generateCode(Element element, ProcessingEnvironment processingEnv) throws IOException {
        String validatorClass = element.toString();
        PackageElement elementPackage = processingEnv.getElementUtils().getPackageOf(element);

        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "It's alive!")
                .build();

        TypeSpec validatorJava = TypeSpec.classBuilder(element.getSimpleName().toString() + "Impl")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder(elementPackage.toString(), validatorJava)
                .build();

        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(validatorClass + "Impl", element);

        Writer out = sourceFile.openWriter();
        javaFile.writeTo(out);
        out.close();
    }

    private static PackageElement getPackage(Element type) {
        while (type.getKind() != ElementKind.PACKAGE) {
            type = type.getEnclosingElement();
        }
        return (PackageElement) type;
    }

    private static String strippedTypeName(String type, String packageName) {
        return type.substring(packageName.isEmpty() ? 0 : packageName.length() + 1);
    }
}
