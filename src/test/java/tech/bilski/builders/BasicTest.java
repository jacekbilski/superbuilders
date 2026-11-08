package tech.bilski.builders;

import org.junit.jupiter.api.Test;
import tech.bilski.builders.fixture.TestClass;
import tech.bilski.builders.fixture.TestClassBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BasicTest {

	private final TestClassBuilder builder = TestClassBuilder.builder();

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

	@Test
	void settingFieldReturnsNewBuilderInstance() {
		var newBuilder = builder.field("value");
		assertThat(newBuilder).isNotSameAs(builder);
	}

	@Test
	void originalBuilderIsNotModifiedWhenFieldIsSet() {
		var fieldValue = UUID.randomUUID().toString();
		builder.field(fieldValue);
		var instance = builder.build();
		assertThat(instance.field).isNull();
	}
}
