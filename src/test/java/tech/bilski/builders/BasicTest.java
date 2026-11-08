package tech.bilski.builders;

import org.junit.jupiter.api.Test;
import tech.bilski.builders.fixture.TestClass;
import tech.bilski.builders.fixture.TestClassBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BasicTest {

	private final TestClassBuilder builder = new TestClassBuilder();

	@Test
	void builderClassIsCreated() {
	}

	@Test
	void builderBuildsCorrectClass() {
		var instance = builder.build();
		assertThat(instance).isInstanceOf(TestClass.class);
	}

	@Test
	void builderSupportsFields() {
		var fieldValue = UUID.randomUUID().toString();
		var instance = builder
			.field(fieldValue)
			.build();
		assertThat(instance.field).isEqualTo(fieldValue);
	}
}
