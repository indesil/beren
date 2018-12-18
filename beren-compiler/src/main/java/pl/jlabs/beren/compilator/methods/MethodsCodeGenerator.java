package pl.jlabs.beren.compilator.methods;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;
import java.util.List;

public class MethodsCodeGenerator {

    public static List<MethodSpec> generateMethodsCode() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "It's alive!")
                .build();

        return null;
    }
}
