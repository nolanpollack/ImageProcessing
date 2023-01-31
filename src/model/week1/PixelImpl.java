package model.week1;

import controller.ImageUtil;

/**
 * Represents a pixel in an image, with only an r, g, and b values for its color.
 */
public class PixelImpl implements IPixel {

  /**
   * Color of the pixel represented by an IColor.
   */
  private final IColor color;

  /**
   * Constructs a new PixelImpl given the r, g, b values of the color.
   *
   * @param r the r value of the pixel.
   * @param g the g value of the pixel.
   * @param b the b value of the pixel.
   */
  public PixelImpl(int r, int g, int b) {
    //these values are checked when creating the photo
    this.color = new OGColor(r, g, b);
  }

  /**
   * Constructs a new PixelImpl given the color.
   *
   * @param color the color of the new pixel.
   * @throws IllegalArgumentException if the color given is null
   */
  public PixelImpl(IColor color) {
    ImageUtil.checkNull(color, "color");
    this.color = color;
  }

  @Override
  public int getRValue() {
    return this.color.getR();
  }

  @Override
  public int getGValue() {
    return this.color.getG();
  }

  @Override
  public int getBValue() {
    return this.color.getB();
  }

  @Override
  public IColor getColor() {
    return color.clone();
  }

  @Override
  public IPixel create(int r, int g, int b) {
    return new PixelImpl(r, g, b);
  }

  @Override
  public IPixel clone() {
    return new PixelImpl(getColor());
  }

  @Override
  public boolean equals(Object obj) {
    ImageUtil.checkNull(obj, "given object");
    if (obj instanceof IPixel) {
      IPixel other = (IPixel) (obj);
      return this.getBValue() == other.getBValue()
          && this.getGValue() == other.getGValue()
          && this.getRValue() == other.getRValue();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getRValue() - getBValue() + getGValue() * 2;
  }
}
