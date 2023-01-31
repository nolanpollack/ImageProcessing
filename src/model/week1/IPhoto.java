package model.week1;


/**
 * Represents a photo that has a sequence of IPixels as its data, and the maximum value of the color
 * channels that is allowed.
 */
public interface IPhoto {

  /**
   * Retrieves the entire image as an array of pixels.
   *
   * @return the array of pixels
   */
  public IPixel[][] getPixelSequence();

  /**
   * Gets an individual pixel from this image.
   *
   * @param row    the row of the pixel in the array.
   * @param column the column of the pixel in the array
   * @return a pixel from the given positions.
   */
  public IPixel getPixel(int row, int column);

  /**
   * Finds the max possible rgb value an image's pixels can contain.
   *
   * @return the max rgb value as an int.
   */
  public int getMaxRGBValue();

  /**
   * Creates a clone of this photo.
   *
   * @return a new IPhoto.
   */
  public IPhoto clone();

  /**
   * Determines the height of this photo.
   *
   * @return the height of this photo.
   */
  public int getHeight();

  /**
   * Determines the width of the photo by taking the length of a pixel row.
   *
   * @return the width of this photo
   */
  public int getWidth();


}
