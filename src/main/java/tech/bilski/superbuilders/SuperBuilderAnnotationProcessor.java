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
    System.out.println("XYZABC SuperBuilderAnnotationProcessor.process()");
    for (TypeElement annotation : annotations) {
      System.out.println("XYZABC About to process annotation: " + annotation);
      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
      for (Element element : annotatedElements) {
        System.out.println("XYZABC About to process element: " + element);
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
    String className = element.asType().toString();
    System.out.println("className: " + className);
    for (Element enclosedElement : element.getEnclosedElements()) {
      System.out.println("XYZABC Element has " + enclosedElement.getKind() + ": " + enclosedElement);
    }

    Builder builder = new Builder();
    builder.className = className + "Builder";
    builder.source = "package tech.bilski.superbuilders;\npublic class JavaBeanBuilder{private String str;\npublic JavaBeanBuilder withStr(String str){this.str=str;return this;}\npublic JavaBean build() {return new JavaBean(str);}}";
    return builder;
  }

  private static class Builder {
    String className;
    String source;
  }
}
