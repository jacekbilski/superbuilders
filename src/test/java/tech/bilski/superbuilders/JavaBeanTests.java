package tech.bilski.superbuilders;

import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaBeanTests {

  @Test
  void oneField_withConstructorParameter() {
    String str = UUID.randomUUID().toString();
    JavaBean javaBean = new JavaBeanBuilder().withStr(str).build();
    assertThat(javaBean).hasFieldOrPropertyWithValue("str", str);
  }
}
