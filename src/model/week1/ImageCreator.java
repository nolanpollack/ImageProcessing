package model.week1;

import controller.ImageUtil;

/**
 * Factory method for the creation of a IPhoto based upon a PhotoType enum.
 */
public class ImageCreator {

  /**
   * Creates a new IPhoto based upon the given PhotoType.
   *
   * @param type   the type of photo to be created.
   * @param pixels the pixel sequence to be used in the photo.
   * @param max    the max possible rgb value for a pixel in the photo.
   * @return a new IPhoto created with the given parameters.
   * @throws IllegalStateException    if an invalid type is given.
   * @throws IllegalArgumentException if a null type is given.
   */
  public static IPhoto create(PhotoType type, IPixel[][] pixels, int max) {
    ImageUtil.checkNull(type, "photo type");
    if (type == PhotoType.SIMPLE_PHOTO) {
      return new PhotoImpl(pixels, max);
    }
    throw new IllegalStateException("you shouldn't get here. Only one photo type");
  }

  /**
   * Represents the type of an IPhoto.
   */
  public enum PhotoType {
    SIMPLE_PHOTO;
  }
}
