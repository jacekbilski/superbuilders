package tech.bilski.superbuilders;

import java.util.Random;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class InnerClassesTests {

  @Test
  @Disabled
  void innerClass() {
    Long l = new Random().nextLong();
//    InnerClass innerClass = new InnerClassBuilder().withL(l).build();
//    assertThat(innerClass).hasFieldOrPropertyWithValue("l", l);
  }

  @SuperBuilder
  public class InnerClass {
    private Long l;

    public InnerClass(Long l) {
      this.l = l;
    }
  }
}
