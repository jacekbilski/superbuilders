package tech.bilski.superbuilders;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaygroundTest {

  @Test
  void sth() {
    String msg = "abc";
    SomeClass someClass = new SomeClassBuilder().withMsg(msg).build();
    assertThat(someClass).hasFieldOrPropertyWithValue("msg", msg);
  }
}
