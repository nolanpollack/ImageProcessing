package model.week1;

import model.week1.ImageCreator.PhotoType;

/**
 * Represents a sepia color transformation.
 */
public class SepiaColorTransformation extends AbstractColorTransformation {

  /**
   * The matrix for this color transformation.
   */
  private final double[][] matrix;

  /**
   * Constructs a new SepiaColorTransformation.
   */
  public SepiaColorTransformation() {
    double[] rValues = {.393, .769, .189};
    double[] gValues = {.349, .686, .168};
    double[] bValues = {.272, .534, .131};
    matrix = new double[][]{rValues, gValues, bValues};
  }

  @Override
  public IPhoto apply(IPhoto image) {
    return super.apply(image, matrix, PhotoType.SIMPLE_PHOTO);
  }

  @Override
  public String toString() {
    return "sepia filter";
  }

}
