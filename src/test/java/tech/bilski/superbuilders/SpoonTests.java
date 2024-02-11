package tech.bilski.superbuilders;

import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtType;

import static spoon.testing.Assert.assertThat;

public class SpoonTests {

  @Test
  void firstOne() {
    SpoonAPI spoon = new Launcher();
    spoon.addInputResource("src/test/java/tech/bilski/superbuilders/JavaBean.java");
    spoon.run();

    CtType<SuperBuilderProcessor> type = spoon.getFactory().Type().get(JavaBean.class);
    assertThat(type.getField("str"))
        .withProcessor(new SuperBuilderProcessor())
        .isEqualTo("private java.lang.String str;");
  }
}
