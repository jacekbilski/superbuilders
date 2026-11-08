package tech.bilski.builders;

import org.junit.jupiter.api.Test;
import tech.bilski.builders.fixture.TestClassBuilder;

class BasicTest {

	@Test
	void builderClassIsCreated() {
		var builder = new TestClassBuilder();
	}

}
