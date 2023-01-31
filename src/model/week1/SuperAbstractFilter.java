package model.week1;

import model.week1.ImageCreator.PhotoType;

/**
 * Represents an abstract filter for any sort of filtering operation (filter or color
 * transformation).
 */
public abstract class SuperAbstractFilter implements IFilter {

  /**
   * Creates a new photo with the given filter operation applied.
   *
   * @param type   the type of photo being outputted.
   * @param image  the image the filter operation is being applied to.
   * @param kernel the kernel or matrix being used for the new photo.
   * @return a new photo with the given filter operation applied.
   */
  protected IPhoto createAlteredPhoto(PhotoType type, IPhoto image, double[][] kernel) {
    IPixel[][] result = new IPixel[image.getHeight()][image.getWidth()];
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        result[i][j] = (newPixel(i, j, image, kernel));
      }
    }
    return ImageCreator.create(type, result, image.getMaxRGBValue());
  }

  /**
   * Produces a list of new values for a pixel's rgb values.
   *
   * @param kernel the kernel or matrix being used for the filter.
   * @param image  the image the filter is being applied to.
   * @param row    the row of the pixel the filter is being applied to.
   * @param column the column of the pixel the filter is being applied to.
   * @return a list of integer values representing r g b values.
   */
  protected abstract int[] kernelOperation(
      double[][] kernel, IPhoto image, int row, int column);

  /**
   * Creates a new pixel with modified values.
   *
   * @param row    the row of the pixel being modified.
   * @param column the column of the pixel being modified.
   * @param image  the image the pixel is being drawn from.
   * @param matrix the matrix with the values needed to modify the pixel.
   * @return a new pixel with the modified values.
   */
  private IPixel newPixel(int row, int column, IPhoto image, double[][] matrix) {
    int[] newValues = kernelOperation(matrix, image, row, column);

    IPixel newPixel = image.getPixel(0, 0)
        .create(newValues[0], newValues[1], newValues[2]);
    return newPixel;
  }
}
