package tech.bilski.builders.processor;

import tech.bilski.builders.SuperBuilder;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.RecordComponentElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("tech.bilski.builders.SuperBuilder")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SuperBuilderProcessor extends AbstractProcessor {

	private Elements elements;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.elements = processingEnv.getElementUtils();
		this.messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element annotated : roundEnv.getElementsAnnotatedWith(SuperBuilder.class)) {
			if (annotated.getKind() != ElementKind.CLASS && annotated.getKind() != ElementKind.RECORD) {
				error(annotated, "@SuperBuilder can only be applied to classes and records");
				continue;
			}
			TypeElement type = (TypeElement) annotated;
			List<Property> properties = collectProperties(type);
			if (!hasMatchingConstructor(type, properties)) {
				error(type, "@SuperBuilder requires an all-args constructor matching all "
					+ properties.size() + " propert" + (properties.size() == 1 ? "y" : "ies")
					+ " in declaration order");
				continue;
			}
			try {
				generateBuilder(type, properties);
			} catch (IOException e) {
				error(type, "Failed to generate builder: " + e.getMessage());
			}
		}
		return true;
	}

	private List<Property> collectProperties(TypeElement type) {
		List<Property> properties = new ArrayList<>();
		if (type.getKind() == ElementKind.RECORD) {
			for (RecordComponentElement component : type.getRecordComponents()) {
				properties.add(new Property(component.getSimpleName().toString(),
					component.asType().toString()));
			}
		} else {
			for (VariableElement field : ElementFilter.fieldsIn(type.getEnclosedElements())) {
				if (!field.getModifiers().contains(Modifier.STATIC)) {
					properties.add(new Property(field.getSimpleName().toString(),
						field.asType().toString()));
				}
			}
		}
		return properties;
	}

	private boolean hasMatchingConstructor(TypeElement type, List<Property> properties) {
		for (ExecutableElement ctor : ElementFilter.constructorsIn(type.getEnclosedElements())) {
			if (ctor.getParameters().size() != properties.size()) {
				continue;
			}
			boolean matches = true;
			for (int i = 0; i < properties.size(); i++) {
				if (!ctor.getParameters().get(i).asType().toString()
					.equals(properties.get(i).type())) {
					matches = false;
					break;
				}
			}
			if (matches) {
				return true;
			}
		}
		return false;
	}

	private void generateBuilder(TypeElement type, List<Property> properties) throws IOException {
		PackageElement pkg = elements.getPackageOf(type);
		String packageName = pkg.getQualifiedName().toString();
		String simpleName = type.getSimpleName().toString();
		String builderName = simpleName + "Builder";
		String qualifiedBuilderName = packageName.isEmpty() ? builderName : packageName + "." + builderName;

		Filer filer = processingEnv.getFiler();
		JavaFileObject sourceFile = filer.createSourceFile(qualifiedBuilderName, type);
		try (Writer writer = sourceFile.openWriter()) {
			if (!packageName.isEmpty()) {
				writer.write("package " + packageName + ";\n\n");
			}
			writer.write("public final class " + builderName + " {\n\n");
			writeFields(writer, properties);
			writeConstructors(writer, builderName, properties);
			writeSetters(writer, builderName, properties);
			writeBuildMethod(writer, simpleName, properties);
			writeBuilderFactory(writer, builderName);
			writer.write("}\n");
		}
	}

	private void writeFields(Writer writer, List<Property> properties) throws IOException {
		for (Property property : properties) {
			writer.write("    private final " + property.type() + " " + property.name() + ";\n");
		}
		if (!properties.isEmpty()) {
			writer.write("\n");
		}
	}

	private void writeConstructors(Writer writer, String builderName, List<Property> properties) throws IOException {
		writer.write("    public " + builderName + "() {\n");
		writer.write("        this(");
		writeJoined(writer, properties.size(), i -> "null");
		writer.write(");\n");
		writer.write("    }\n\n");
		writer.write("    private " + builderName + "(");
		writeJoined(writer, properties.size(), i -> properties.get(i).type() + " " + properties.get(i).name());
		writer.write(") {\n");
		for (Property property : properties) {
			writer.write("        this." + property.name() + " = " + property.name() + ";\n");
		}
		writer.write("    }\n");
		if (!properties.isEmpty()) {
			writer.write("\n");
		}
	}

	private void writeSetters(Writer writer, String builderName, List<Property> properties) throws IOException {
		for (Property property : properties) {
			writer.write("    public " + builderName + " " + property.name() + "(" + property.type()
				+ " " + property.name() + ") {\n");
			writer.write("        return new " + builderName + "(");
			for (Property p : properties) {
				if (p != property) {
					writer.write("this." + p.name() + ", ");
				}
			}
			writer.write(property.name() + ");\n");
			writer.write("    }\n\n");
		}
	}

	private void writeBuildMethod(Writer writer, String simpleName, List<Property> properties) throws IOException {
		writer.write("    public " + simpleName + " build() {\n");
		writer.write("        return new " + simpleName + "(");
		writeJoined(writer, properties.size(), i -> properties.get(i).name());
		writer.write(");\n");
		writer.write("    }\n\n");
	}

	private void writeBuilderFactory(Writer writer, String builderName) throws IOException {
		writer.write("    public static " + builderName + " builder() {\n");
		writer.write("        return new " + builderName + "();\n");
		writer.write("    }\n");
	}

	private void writeJoined(Writer writer, int count, java.util.function.IntFunction<String> element) throws IOException {
		for (int i = 0; i < count; i++) {
			if (i > 0) {
				writer.write(", ");
			}
			writer.write(element.apply(i));
		}
	}

	private void error(Element element, String message) {
		messager.printMessage(Diagnostic.Kind.ERROR, message, element);
	}

	private record Property(String name, String type) {
	}
}
