package model.week1;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week1.ImageCreator.PhotoType;
import model.week1.PixelCreator.PixelType;

/**
 * An image of a "checkerboard" (square or non-square) with alternating tiles.
 */
public class CheckerboardImage implements IPhoto {

  /**
   * Delegate to perform IPhoto operations with this implementation for simpler methods.
   */
  private final IPhoto delegate;

  /**
   * Constructs a new CheckerboardImage.
   *
   * @param numRows    the number of rows the checkerboard will have.
   * @param numColumns the number of columns the checkerboard will have.
   * @param maxValue   the max rgb value a pixel can have in this image.
   * @param color1     the first color of a square on the checkerboard.
   * @param color2     the second color of a square on the checkerboard.
   * @param sizeOfTile the size of a square tile on the checkerboard.
   * @throws IllegalArgumentException if the num of rows/columns is not greater than 0, the size of
   *                                  the tile is not at least 1, the given colors are null.
   */
  public CheckerboardImage(
      int numRows, int numColumns, int maxValue, IColor color1, IColor color2, int sizeOfTile) {
    if (numRows < 0) {
      throw new IllegalArgumentException("Not a valid number of rows");
    }
    if (numColumns < 0) {
      throw new IllegalArgumentException("Not a valid number of columns");
    }
    if (sizeOfTile < 1) {
      throw new IllegalArgumentException("Size cannot be zero or negative");
    }
    ImageUtil.checkNull(color1, "first color");
    ImageUtil.checkNull(color2, "second color");
    List<IColor> newColors = clampColors(color1, color2, maxValue);
    IPixel[][] pixels =
        createPixelSequence(numRows, numColumns, newColors.get(0), newColors.get(1), sizeOfTile);
    this.delegate = ImageCreator.create(PhotoType.SIMPLE_PHOTO, pixels, maxValue);
  }

  private List<IColor> clampColors(IColor color1, IColor color2, int maxValue) {
    List<IColor> colors = new ArrayList<>(Arrays.asList(color1, color2));
    List<IColor> newColors = new ArrayList<>();
    for (IColor color : colors) {
      List<Integer> values =
          new ArrayList<>(Arrays.asList(color.getR(), color.getG(), color.getB()));
      List<Integer> newValues = new ArrayList<>();
      for (Integer value : values) {
        if (value > maxValue) {
          newValues.add(maxValue);
        } else if (value < 0) {
          newValues.add(0);
        } else {
          newValues.add(value);
        }
      }
      newColors.add(new OGColor(values.get(0), values.get(1), values.get(2)));
    }
    return newColors;
  }

  /**
   * Creates a new pixel sequence for a checkerboard image.
   *
   * @param numRows    the number of rows of tiles in the image.
   * @param numColumns the number of columns of tiles in the image.
   * @param color1     the first tile color in the checkerboard.
   * @param color2     the second tile color in the checkerboard.
   * @param sizeOfTile the size of a tile in the checkerboard.
   * @return a pixel sequence representing the pixels in a checkerboard image.
   */
  private IPixel[][] createPixelSequence(
      int numRows, int numColumns, IColor color1, IColor color2, int sizeOfTile) {
    IPixel[][] finalPixels = new IPixel[numRows * sizeOfTile][numColumns * sizeOfTile];
    boolean alternatingColor = false;
    boolean columnAlternating = false;
    for (int i = 0; i < numRows * sizeOfTile; i = i + sizeOfTile) {
      alternatingColor = !columnAlternating;
      for (int j = 0; j < numColumns * sizeOfTile; j = j + sizeOfTile) {
        if (alternatingColor) {
          for (int h = 0; h < sizeOfTile; h++) {
            for (int k = 0; k < sizeOfTile; k++) {
              finalPixels[i + h][j + k] =
                  PixelCreator.createWColor(
                      PixelType.SIMPLE_PIXEL, color2.getR(), color2.getG(), color2.getB());
            }
          }
          alternatingColor = false;
        } else {
          for (int v = 0; v < sizeOfTile; v++) {
            for (int w = 0; w < sizeOfTile; w++) {
              finalPixels[i + v][j + w] =
                  PixelCreator.createWColor(
                      PixelType.SIMPLE_PIXEL, color1.getR(), color1.getG(), color1.getB());
            }
          }
          alternatingColor = true;
        }
      }
      columnAlternating = !columnAlternating;
    }
    return finalPixels;
  }

  @Override
  public IPixel[][] getPixelSequence() {
    return this.delegate.getPixelSequence();
  }

  @Override
  public IPixel getPixel(int row, int column) {
    return this.delegate.getPixel(row, column);
  }

  @Override
  public int getMaxRGBValue() {
    return this.delegate.getMaxRGBValue();
  }

  @Override
  public IPhoto clone() {
    return this.delegate.clone();
  }

  @Override
  public int getHeight() {
    return this.delegate.getHeight();
  }

  @Override
  public int getWidth() {
    return this.delegate.getWidth();
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
