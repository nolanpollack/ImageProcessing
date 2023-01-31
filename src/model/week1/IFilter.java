package model.week1;

/**
 * A function object representing a filter to be applied to an image.
 */
public interface IFilter {

  /**
   * Applies this IFilter to an IPhoto.
   *
   * @param image the IPhoto being used to generate a new photo.
   * @return the new IPhoto with the applied filter.
   */
  public IPhoto apply(IPhoto image);
}
