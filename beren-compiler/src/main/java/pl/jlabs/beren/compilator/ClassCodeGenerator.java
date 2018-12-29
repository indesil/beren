package pl.jlabs.beren.compilator;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import pl.jlabs.beren.BerenUtils;
import pl.jlabs.beren.compilator.parser.ValidatorDefinition;
import pl.jlabs.beren.compilator.utils.ProcessingFacade;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import static pl.jlabs.beren.compilator.methods.MethodsCodeGenerator.generateMethodsCode;

class ClassCodeGenerator {

    private ProcessingFacade processingFacade;

    ClassCodeGenerator(ProcessingFacade processingFacade) {
        this.processingFacade = processingFacade;
    }

    void generateJavaClass(Element typeElement, ValidatorDefinition validatorDefinition) {
        String javaClassName = BerenUtils.createValidatorName(typeElement.getSimpleName().toString());
        JavaFileObject sourceFile = processingFacade.createJavaSourceFileForType(javaClassName, typeElement);
        if(sourceFile != null) {
            fillSourceFileWithCode(javaClassName, typeElement, validatorDefinition, sourceFile);
        }
    }

    private void fillSourceFileWithCode(String javaClassName, Element typeElement, ValidatorDefinition validatorDefinition, JavaFileObject sourceFile) {
        TypeSpec validatorJava = createJavaType(javaClassName, typeElement, generateMethodsCode(validatorDefinition));
        String packageName = processingFacade.getPackageOfType(typeElement).toString();
        JavaFile javaFile = JavaFile.builder(packageName, validatorJava)
                .addStaticImport(Arrays.class, "asList")
                .build();
        writeAndClose(javaFile, sourceFile);
    }

    private TypeSpec createJavaType(String javaClassName,Element element, List<MethodSpec> methods) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(javaClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methods);

        if(element.getKind().equals(ElementKind.INTERFACE)) {
            builder.addSuperinterface(TypeName.get(element.asType()));
        } else {
            builder.superclass(TypeName.get(element.asType()));
        }

        return builder.build();
    }

    private void writeAndClose(JavaFile javaFile, JavaFileObject sourceFile) {
        Writer out = null;
        try {
            out = sourceFile.openWriter();
            javaFile.writeTo(out);
        } catch (IOException e) {
            processingFacade.error("Error while writting to java file %s",  e.getMessage());
        } finally {
            silentClose(out);
        }
    }

    private void silentClose(Writer out) {
        try {
            if(out != null) {
                out.close();
            }
        } catch (IOException e) {
            processingFacade.error("Error while closing writer %s",  e.getMessage());
        }
    }
}
