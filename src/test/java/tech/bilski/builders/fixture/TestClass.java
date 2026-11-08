package tech.bilski.builders.fixture;

import tech.bilski.builders.SuperBuilder;

@SuperBuilder
public class TestClass {
	public String field;

	public TestClass(String field) {
		this.field = field;
	}
}
