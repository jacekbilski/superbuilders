package tech.bilski.superbuilders;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.ModifierKind;

public class SuperBuilderProcessor extends AbstractAnnotationProcessor<SuperBuilder, CtClass<?>> {

  private static final Logger log = LoggerFactory.getLogger(SuperBuilderProcessor.class);

  @Override
  public void process(SuperBuilder annotation, CtClass<?> element) {
    log.warn("Processing element{}", element);
    CtClass builder = getFactory().createClass(element, "JavaBeanBuilder");
    builder.setModifiers(Set.of(ModifierKind.STATIC, ModifierKind.PUBLIC));
    element.addNestedType(builder);
  }
}
