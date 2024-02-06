package tech.bilski.superbuilders.model;

import javax.lang.model.element.Element;

public class Field {

  private final String name;
  private final String type;

  public Field(Element element) {
    name = element.getSimpleName().toString();
    type = element.asType().toString();
  }

  public String nameAsSuffix() {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String toString() {
    return "Field, type: '" + type + "', name: '" + name + "'";
  }
}
