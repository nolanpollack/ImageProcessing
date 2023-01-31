package model.week1;

/**
 * A simple representation of a color that holds the value of the RGB channels.
 */
public class OGColor implements IColor {

  /**
   * A red value.
   */
  private final int r;

  /**
   * A green value.
   */
  private final int g;

  /**
   * A blue value.
   */
  private final int b;

  /**
   * constructor for a color representation of the given RGB values (no bounds).
   *
   * @param r the red color value
   * @param g the green color value
   * @param b the blue color value
   */
  public OGColor(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getG() {
    return g;
  }

  @Override
  public int getB() {
    return b;
  }

  @Override
  public IColor clone() {
    return new OGColor(r, g, b);
  }
}
