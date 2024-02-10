package tech.bilski.superbuilders;

import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtType;
import tech.bilski.superbuilders.spoon.SuperBuilderProcessor;

import static spoon.testing.Assert.assertThat;


public class SpoonTests {

  @Test
  void firstOne() {
    var spoon = new Launcher();
    spoon.addInputResource("src/main/java/tech/bilski/superbuilders/spoon/SuperBuilderProcessor.java");
    spoon.run();

    CtType<SuperBuilderProcessor> type = spoon.getFactory().Type().get(SuperBuilderProcessor.class);
    assertThat(type.getField("x")).withProcessor(new SuperBuilderProcessor()).isEqualTo("private int x;");
  }

}
