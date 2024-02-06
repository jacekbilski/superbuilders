package tech.bilski.superbuilders;

import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import tech.bilski.superbuilders.model.Field;

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
    List<Field> fields = new ArrayList<>();

    for (Element enclosedElement : element.getEnclosedElements()) {
      switch (enclosedElement.getKind()) {
        case FIELD: {
          Field field = new Field(enclosedElement);
          System.out.println("XYZABC " + field);
          fields.add(field);
          break;
        }
        default: {
          System.out.println("XYZABC Found something: " + enclosedElement.getKind() + " by the name of \""
              + enclosedElement.getSimpleName() + "\": " + enclosedElement);
        }
      }
    }
    String builderName = className + "Builder";
    StringBuilder source = new StringBuilder().append("package ").append(packageName).append(";\n")
        .append("public class ").append(builderName).append("{\n");
    for (Field field : fields) {
      source.append("private ").append(field.getType()).append(" ").append(field.getName()).append(";\n");
      source.append("public ").append(builderName).append(" with").append(field.nameAsSuffix())
          .append("(").append(field.getType())
          .append(" ").append(field.getName()).append("){\nthis.").append(field.getName())
          .append("=").append(field.getName()).append(";\nreturn this;\n}\n");
    }
    source.append("public ").append(fqn).append(" build() {return new ").append(fqn).append("(")
        .append(fields.stream().map(Field::getName).collect(Collectors.joining(",")))
        .append(");}\n");
    source.append("}");

    Builder builder = new Builder();
    builder.className = packageName + "." + builderName;
    builder.source = source.toString();
    return builder;
  }

  private static class Builder {
    String className;
    String source;
  }
}
