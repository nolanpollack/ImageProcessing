package model.week1;

import controller.ImageUtil;
import model.week1.ImageCreator.PhotoType;

/**
 * Represents an abstract color transformation function object.
 */
public abstract class AbstractColorTransformation extends SuperAbstractFilter {

  /**
   * Applies a color transformation to a given photo.
   *
   * @param image  the image the color transformation is being applied to.
   * @param matrix a matrix representing the transformations for each rgb value.
   * @param type   the type of image being created.
   * @return a new photo with the given transformation applied.
   * @throws IllegalArgumentException if the image or matrix is null, the matrix is not 3x3
   */
  protected IPhoto apply(IPhoto image, double[][] matrix, PhotoType type) {
    ImageUtil.checkNull(image, "image");
    ImageUtil.checkNull(type, "photo type");

    if (matrix.length != 3 || matrix[0].length != 3) {
      throw new IllegalArgumentException("Matrix must be a 2D array with 3x3 dimensions.");
    }

    return createAlteredPhoto(type, image, matrix);
  }

  @Override
  protected int[] kernelOperation(double[][] kernel, IPhoto image, int row, int column) {
    int newPixelR = getChannelValue(row, column, image, kernel[0]);
    int newPixelG = getChannelValue(row, column, image, kernel[1]);
    int newPixelB = getChannelValue(row, column, image, kernel[2]);

    return new int[]{newPixelR, newPixelG, newPixelB};
  }

  /**
   * Calculates the new value of an r g or b channel with the transformation kernel.
   *
   * @param row    the row of the pixel
   * @param column the column of the pixel
   * @param image  the image the pixel is from
   * @param values the multipliers for this channel.
   * @return the new value of this channel.
   */
  private int getChannelValue(int row, int column, IPhoto image, double[] values) {
    int value = 0;
    value += image.getPixel(row, column).getRValue() * values[0]
        + image.getPixel(row, column).getGValue() * values[1]
        + image.getPixel(row, column).getBValue() * values[2];
    return value;
  }
}
