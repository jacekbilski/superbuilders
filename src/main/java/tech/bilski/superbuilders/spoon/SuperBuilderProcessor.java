package tech.bilski.superbuilders.spoon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtClass;
import tech.bilski.superbuilders.SuperBuilder;

public class SuperBuilderProcessor extends AbstractAnnotationProcessor<SuperBuilder, CtClass<?>> {

  private static final Logger log = LoggerFactory.getLogger(SuperBuilderProcessor.class);

  private int x;

  public SuperBuilderProcessor() {
    log.warn("Hello world from processor");
  }

  @Override
  public void process(SuperBuilder annotation, CtClass<?> element) {
    log.warn("Processing element{}", element);
//    CtField
//    element.addField(field);
  }
}
