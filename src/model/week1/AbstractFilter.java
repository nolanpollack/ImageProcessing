package model.week1;

import controller.ImageUtil;
import model.week1.ImageCreator.PhotoType;

/**
 * Abstract class for the filters that use kernels to alter the image.
 */
public abstract class AbstractFilter extends SuperAbstractFilter {

  /**
   * Creates a new version of the given photo based upon the given kernel that represents a filter.
   *
   * @param image  an IPhoto of the desired photo to be altered.
   * @param kernel an odd-sized, square 2D-array of integers.
   * @param type   a PhotoType to tell what type of photo to create.
   * @return the new altered IPhoto of the original photo with the kernel filter
   * @throws IllegalArgumentException when the image or kernel is null, when the kernel is not a
   *                                  2D-array with odd dimensions of equal length.
   */
  protected IPhoto apply(IPhoto image, double[][] kernel, PhotoType type)
      throws IllegalArgumentException {
    ImageUtil.checkNull(image, "image");
    ImageUtil.checkNull(kernel, "kernel");

    if (kernel.length % 2 != 1 || kernel.length != kernel[0].length) {
      throw new IllegalArgumentException(
          "Kernel must be a 2D array with odd dimensions of equal length");
    }
    return createAlteredPhoto(type, image, kernel);
  }

  @Override
  protected int[] kernelOperation(double[][] kernel, IPhoto image, int row, int column) {
    int newPixelR = 0;
    int newPixelG = 0;
    int newPixelB = 0;
    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel.length; j++) {
        int pixelRow = i + row - ((kernel.length - 1) / 2);
        int pixelColumn = j + column - ((kernel.length - 1) / 2);
        if (!(pixelRow < 0
            || pixelRow >= image.getHeight()
            || pixelColumn < 0
            || pixelColumn >= image.getWidth())) {
          newPixelR +=
              image.getPixel(pixelRow, pixelColumn).getRValue() * kernel[i][j];
          newPixelG += image.getPixel(pixelRow, pixelColumn).getGValue() * kernel[i][j];
          newPixelB += image.getPixel(pixelRow, pixelColumn).getBValue() * kernel[i][j];
        }
      }
    }
    return new int[]{newPixelR, newPixelG, newPixelB};
  }
}
