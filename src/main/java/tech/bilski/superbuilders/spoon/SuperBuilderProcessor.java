package tech.bilski.superbuilders.spoon;

import spoon.processing.AbstractManualProcessor;

public class SuperBuilderProcessor extends AbstractManualProcessor {

  private int x;

  @Override
  public void process() {
    System.out.println("Hello workd from processor");
  }
}
