package tech.bilski.superbuilders;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;

public class SuperBuilderProcessor extends AbstractAnnotationProcessor<SuperBuilder, CtClass<?>> {

  private static final Logger log = LoggerFactory.getLogger(SuperBuilderProcessor.class);

  @Override
  public void process(SuperBuilder annotation, CtClass<?> element) {
    log.warn("Processing element{}", element);
//    var className = element.getSimpleName();
//    CtClass builder = getFactory().Core().createClass();
//    builder.setSimpleName("JavaBeanBuilder");
//    builder.addModifier(ModifierKind.STATIC);
//    element.addNestedType(builder);

    CtTypeReference<Integer> intType = getFactory().Code().createCtTypeReference(Integer.class);
    CtField intField = getFactory().createField(element, Set.of(ModifierKind.PRIVATE), intType, "x");
    element.addField(intField);
  }
}
