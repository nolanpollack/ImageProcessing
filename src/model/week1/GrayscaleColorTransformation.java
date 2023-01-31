package model.week1;

import model.week1.ImageCreator.PhotoType;

/**
 * Represents a grayscale color transformation.
 */
public class GrayscaleColorTransformation extends AbstractColorTransformation {

  /**
   * The matrix for the grayscale color transformation.
   */
  private final double[][] matrix;

  /**
   * Constructs a new GrayscaleColorTransformation and initializes the matrix values.
   */
  public GrayscaleColorTransformation() {
    double[] values = {.2126, .7152, .0722};
    matrix = new double[][]{values, values, values};
  }

  @Override
  public IPhoto apply(IPhoto image) {
    return super.apply(image, matrix, PhotoType.SIMPLE_PHOTO);
  }

  @Override
  public String toString() {
    return "grayscale filter";
  }
}
