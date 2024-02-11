package tech.bilski.superbuilders;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtType;
import spoon.testing.Assert;


public class JavaBeanTests {

  @Test
  @Disabled
  void oneField_withConstructorParameter() {
//    String str = UUID.randomUUID().toString();
//    JavaBean javaBean = new JavaBean.JavaBeanBuilder().build();
//    Assertions.assertThat(javaBean).hasFieldOrPropertyWithValue("str", str);
  }

  @Test
  void classHasInnerBuilderClass() {
    SpoonAPI spoon = new Launcher();
    spoon.run();

    CtType<SuperBuilderProcessor> type = spoon.getFactory().Type().get(JavaBean.class);
    CtType<?> javaBeanBuilder = type.getNestedType("JavaBeanBuilder");
    System.out.println(javaBeanBuilder);
    Assert.assertThat(javaBeanBuilder)
        .isEqualTo("public static class JavaBeanBuilder {\n"
            + "    public JavaBeanBuilder() {\n"
            + "    }\n"
            + "}");
  }
}
