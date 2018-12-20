package pl.jlabs.beren.compilator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import pl.jlabs.beren.compilator.configuration.BerenConfig;
import pl.jlabs.beren.compilator.methods.MethodsCodeGenerator;
import pl.jlabs.beren.compilator.methods.TypeMetadata;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ClassCodeGenerator {

    private ProcessingEnvironment processingEnv;
    private MethodsCodeGenerator methodsCodeGenerator;

    public ClassCodeGenerator(ProcessingEnvironment processingEnv, BerenConfig berenConfig) {
        this.processingEnv = processingEnv;
        this.methodsCodeGenerator = new MethodsCodeGenerator(processingEnv, berenConfig);
    }

    public void generateJavaClass(Element typeElement, TypeMetadata typeMetadata) throws IOException {
        String validatorClass = typeElement.toString();
        PackageElement elementPackage = processingEnv.getElementUtils().getPackageOf(typeElement);
        TypeSpec validatorJava = createJavaType(typeElement, methodsCodeGenerator.generateMethodsCode(typeMetadata));
        JavaFile javaFile = JavaFile.builder(elementPackage.toString(), validatorJava)
                .build();

        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(validatorClass + "Impl", typeElement);

        Writer out = sourceFile.openWriter();
        javaFile.writeTo(out);
        out.close();
    }

    private TypeSpec createJavaType(Element element, List<MethodSpec> methods) {
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
