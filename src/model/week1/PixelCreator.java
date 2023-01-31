package model.week1;

import controller.ImageUtil;

/**
 * Factory method for the creation of an IPixel based upon a PixelType enum.
 */
public class PixelCreator {

  /**
   * Creates an IPixel from a class associated with the given PixelType using the given r, g, and b
   * values.
   *
   * @param type PixelType enum declaring the type of desired pixel
   * @param r    the r value for the pixel's color
   * @param g    the g value for the pixel's color
   * @param b    the b value for the pixel's color
   * @return IPixel based upon the given enum
   * @throws IllegalStateException    if an invalid type is given
   * @throws IllegalArgumentException if the type given is null
   */
  public static IPixel createWColor(PixelType type, int r, int g, int b) {
    ImageUtil.checkNull(type, "pixel type");
    if (type == PixelType.SIMPLE_PIXEL) {
      return new PixelImpl(r, g, b);
    }
    throw new IllegalStateException("you shouldn't get here. Only one photo type");
  }

  /**
   * Represents the type of an IPixel.
   */
  public enum PixelType {
    SIMPLE_PIXEL;
  }
}
