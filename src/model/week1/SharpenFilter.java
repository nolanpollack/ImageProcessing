package model.week1;

import model.week1.ImageCreator.PhotoType;

/**
 * Represents a sharpen filter.
 */
public class SharpenFilter extends AbstractFilter {

  /**
   * The kernel for the sharpen filter.
   */
  private final double[][] transformation;

  /**
   * Constructs a new sharpen filter.
   */
  public SharpenFilter() {
    double[] row1 = {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0};
    double[] row2 = {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0};
    double[] row3 = {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0};
    this.transformation = new double[][]{row1, row2, row3, row2, row1};
  }

  @Override
  public IPhoto apply(IPhoto image) {
    return super.apply(image, transformation, PhotoType.SIMPLE_PHOTO);
  }

  @Override
  public String toString() {
    return "sharpen filter";
  }
}
