package model.week1;

/**
 * Represents a pixel found in an IPhoto. The pixel contains an IColor that holds the values for the
 * red, green, and blue channels of the color. No opacity/transparency or any other features are
 * included for this interface.
 */
public interface IPixel {

  /**
   * Returns the clone version of the color found in the pixel.
   *
   * @return IColor the color of the pixel.
   */
  public IColor getColor();

  /**
   * Finds the red value of this IPixel.
   *
   * @return a number representing the value of the r channel of this pixel.
   */
  public int getRValue();

  /**
   * Finds the green value of this IPixel.
   *
   * @return a number representing the value of the g channel of this pixel.
   */
  public int getGValue();

  /**
   * Finds the blue value of this IPixel.
   *
   * @return a number representing the value of the b channel of this pixel.
   */
  public int getBValue();

  /**
   * Creates a new IPixel.
   *
   * @param r the r value of the new pixel.
   * @param g the g value of the new pixel.
   * @param b the b value of the new pixel.
   * @return a new IPixel created from the given values.
   */
  public IPixel create(int r, int g, int b);

  /**
   * Clones this IPixel.
   *
   * @return a new IPixel with the same values.
   */
  public IPixel clone();

}
