package view;

import controller.ImageUtil;

/**
 * Represents a pair of Strings with a key and a value.
 */
public class ExportPair implements IPair<String, String> {

  private final String name;
  private final String value;

  /**
   * Initializes a new ExportPair.
   *
   * @param name  the key being stored.
   * @param value the value being stored.
   */
  public ExportPair(String name, String value) {
    ImageUtil.checkNull(name, "name");
    ImageUtil.checkNull(value, "value");
    this.name = name;
    this.value = value;
  }

  @Override
  public String getKey() {
    return name;
  }

  @Override
  public String getValue() {
    return value;
  }
}
