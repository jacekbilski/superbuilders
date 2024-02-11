package tech.bilski.superbuilders;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtType;

import static spoon.testing.Assert.assertThat;

public class JavaBeanTests {

  @Test
  @Disabled
  void oneField_withConstructorParameter() {
//    String str = UUID.randomUUID().toString();
//    JavaBean javaBean = new JavaBeanBuilder().withStr(str).build();
//    assertThat(javaBean).hasFieldOrPropertyWithValue("str", str);
  }

  @Test
  void classHasInnerBuilderClass() {
    SpoonAPI spoon = new Launcher();
    spoon.run();

    CtType<SuperBuilderProcessor> type = spoon.getFactory().Type().get(JavaBean.class);
//    CtType<?> javaBeanBuilder = type.getNestedType("JavaBeanBuilder");
//    assertThat(javaBeanBuilder)
//        .withProcessor(new SuperBuilderProcessor())
//        .isEqualTo("private static class JavaBeanBuilder");
    CtField<?> x = type.getField("x");
    assertThat(x)
        .withProcessor(new SuperBuilderProcessor())
        .isEqualTo("private java.lang.Integer x;");
  }
}
