package tech.bilski.superbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtClass;

public class SuperBuilderProcessor extends AbstractAnnotationProcessor<SuperBuilder, CtClass<?>> {

  private static final Logger log = LoggerFactory.getLogger(SuperBuilderProcessor.class);

  @Override
  public void process(SuperBuilder annotation, CtClass<?> element) {
    log.warn("Processing element{}", element);
  }
}
