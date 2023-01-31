package model.week1;

import model.week1.ImageCreator.PhotoType;

/**
 * Represents a blur filter.
 */
public class BlurFilter extends AbstractFilter {

  /**
   * The kernel for the blur filter transformation.
   */
  private final double[][] kernel;

  /**
   * Constructs a new blur filter.
   */
  public BlurFilter() {
    double[] row1 = {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0};
    double[] row2 = {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0};
    this.kernel = new double[][]{row1, row2, row1};
  }

  @Override
  public IPhoto apply(IPhoto image) {
    return super.apply(image, kernel, PhotoType.SIMPLE_PHOTO);
  }

  @Override
  public String toString() {
    return "blur filter";
  }
}
