package tech.bilski.superbuilders;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tech.bilski.superbuilders.InnerClassesTests$InnerClass.InnerClassBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class InnerClassesTests {

  @Test
  void innerClass() {
    Long l = new Random().nextLong();
    InnerClass innerClass = new InnerClassBuilder().withL(l).build();
    assertThat(innerClass).hasFieldOrPropertyWithValue("l", l);
  }

  @SuperBuilder
  public class InnerClass {
    private Long l;

    public InnerClass(Long l) {
      this.l = l;
    }
  }
}
