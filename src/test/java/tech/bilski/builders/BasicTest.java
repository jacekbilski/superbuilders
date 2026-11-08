package tech.bilski.builders;

import org.junit.jupiter.api.Test;
import tech.bilski.builders.fixture.TestClass;
import tech.bilski.builders.fixture.TestClassBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class BasicTest {

	@Test
	void builderClassIsCreated() {
		var builder = new TestClassBuilder();
	}

	@Test
	void builderBuildsCorrectClass() {
		var builder = new TestClassBuilder();
		var instance = builder.build();
		assertThat(instance).isInstanceOf(TestClass.class);
	}
}
