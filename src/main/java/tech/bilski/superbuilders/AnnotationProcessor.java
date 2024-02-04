package tech.bilski.superbuilders;

import com.google.auto.service.AutoService;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes("tech.bilski.superbuilders.SuperBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    System.out.println("XYZABC AnnotationProcessor.process()");
    return annotations.stream()
        .peek(typeElement -> System.out.println("XYZABC Found typeElement " + typeElement.toString()))
        .anyMatch(typeElement -> typeElement.getQualifiedName().contentEquals("tech.bilski.superbuilders.SuperBuilder"));
  }
}
