package model.week1;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an image, which is described as a sequence of pixels (simplest form).
 */
public class PhotoImpl implements IPhoto {

  /**
   * A 2D array of pixels, representing the pixels in the image.
   */
  private final IPixel[][] pixels;

  /**
   * The maximum possible value of an rgb channel in a pixel in this image.
   */
  private final int max;

  /**
   * Constructs a new PhotoImpl.
   *
   * @param pixelSequence the array of pixels for this image. Cannot be null, and there must be an
   *                      equal amount of columns in each row of pixels.
   * @param max           the max possible value of an rgb channel in the new image.
   * @throws IllegalArgumentException if the pixelSequence given is null, the amount of columns in
   *                                  the array is inconsistent in each row, the max value is not
   *                                  greater than 0.
   */
  public PhotoImpl(IPixel[][] pixelSequence, int max) {
    ImageUtil.checkNull(pixelSequence, "pixel sequence");
    if (!PhotoImpl.validPixelArray(pixelSequence)) {
      throw new IllegalArgumentException(
          "There must be an equal amount of columns in each row of " + "pixels.");
    }
    if (max < 1) {
      throw new IllegalArgumentException("The max value must be greater than 0");
    }

    this.max = max;
    this.pixels = finalClampedPixels(pixelSequence);
  }

  /**
   * Checks to see if a 2D array of pixels is formed in a valid way. A 2D array of pixels is valid
   * if every row has the same number of columns.
   *
   * @param pixels the array of pixels being checked.
   * @return true if the array is valid and false if not.
   */
  private static boolean validPixelArray(IPixel[][] pixels) {
    boolean validArray = true;
    for (IPixel[] row : pixels) {
      if (row.length != pixels[0].length) {
        validArray = false;
      }
    }
    return validArray;
  }

  /**
   * Clamps a 2D array of pixels by ensuring no value in any pixel is greater than the max rgb
   * value.
   *
   * @param pixelSequence the 2D array of pixels to be clamped.
   * @return a new 2D array of pixels with no rgb value greater than the max possible rgb value.
   */
  private IPixel[][] finalClampedPixels(IPixel[][] pixelSequence) {
    IPixel[][] finalPixels = new IPixel[pixelSequence.length][pixelSequence[0].length];
    for (int i = 0; i < pixelSequence.length; i++) {
      for (int j = 0; j < pixelSequence[0].length; j++) {
        finalPixels[i][j] = this.clampPixel(pixelSequence[i][j]);
      }
    }
    return finalPixels;
  }

  /**
   * Clamps a pixel by creating a new IPixel where each rgb value is not greater than the maximum or
   * smaller than 0.
   *
   * @param pixel the pixel to be clamped.
   * @return a new pixel where the rgb values are not greater than {@code max} or smaller than 0.
   * @throws IllegalArgumentException if the pixel given is null.
   */
  private IPixel clampPixel(IPixel pixel) {
    ImageUtil.checkNull(pixel, "pixel");
    Integer[] values = {pixel.getRValue(), pixel.getGValue(), pixel.getBValue()};
    List<Integer> finalValues = new ArrayList<>();
    for (Integer value : values) {
      if (value > this.max) {
        finalValues.add(this.max);
      } else if (value < 0) {
        finalValues.add(0);
      } else {
        finalValues.add(value);
      }
    }
    return pixel.create(finalValues.get(0), finalValues.get(1), finalValues.get(2));
  }

  @Override
  public IPixel[][] getPixelSequence() {
    // makes a deep copy
    IPixel[][] answer = new IPixel[this.getHeight()][this.getWidth()];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        answer[i][j] = pixels[i][j].clone();
      }
    }
    return answer;
  }

  @Override
  public IPixel getPixel(int row, int column) {
    if (row >= getHeight() || row < 0) {
      throw new IllegalArgumentException("out of bounds row");
    }
    if (column >= getWidth() || row < 0) {
      throw new IllegalArgumentException("out of bounds column");
    }
    // deep copy
    return pixels[row][column].clone();
  }

  @Override
  public int getMaxRGBValue() {
    return max;
  }

  @Override
  public IPhoto clone() {
    return new PhotoImpl(getPixelSequence(), getMaxRGBValue());
  }

  @Override
  public int getHeight() {
    return pixels.length;
  }

  @Override
  public int getWidth() {
    return pixels[0].length;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof IPhoto) {
      IPhoto other = (IPhoto) (obj);
      return other.getHeight() == this.getHeight()
          && other.getWidth() == this.getWidth()
          && other.getMaxRGBValue() == this.getMaxRGBValue()
          && checkPixelSequence(other);
    }
    return false;
  }

  private boolean checkPixelSequence(IPhoto other) {
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
        if (!this.getPixel(i, j).equals(other.getPixel(i, j))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return 2 * this.getPixelSequence().length + getMaxRGBValue() + getHeight() * getWidth();
  }
}
