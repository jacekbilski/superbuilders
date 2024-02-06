package tech.bilski.superbuilders;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
@SupportedAnnotationTypes("tech.bilski.superbuilders.SuperBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SuperBuilderAnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    for (TypeElement annotation : annotations) {
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
      for (Element element : annotatedElements) {
        Builder builder = createBuilder(element);
        try {
          JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builder.className);
          PrintWriter out = new PrintWriter(builderFile.openWriter());
          out.write(builder.source);
          out.close();
        } catch (IOException e) {
          processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
              "Error writing source file: " + e.getMessage());
        }
      }
    }
    return true;
  }

  private Builder createBuilder(Element element) {
    String fqn = element.asType().toString();
    int lastDot = fqn.lastIndexOf('.');
    String className = fqn.substring(lastDot + 1);
    String packageName = fqn.substring(0, lastDot);
    System.out.println("fqn: " + fqn);
    for (Element enclosedElement : element.getEnclosedElements()) {
      System.out.println("XYZABC Element has " + enclosedElement.getKind() + ": " + enclosedElement);
    }
    String builderName = className + "Builder";

    Builder builder = new Builder();
    builder.className = packageName + "." + builderName;
    builder.source = "package "+packageName+";\n"
        + "public class " + builderName + "{\n"
        + "private String str;\n"
        + "public " + builderName + " withStr(String str){this.str=str;return this;}\n"
        + "public " + fqn + " build() {return new " + fqn + "(str);}}";
    return builder;
  }

  private static class Builder {
    String className;
    String source;
  }
}
