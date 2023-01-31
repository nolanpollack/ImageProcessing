package model.week1;


/**
 * Represents the model for an ImageProcessing application.
 */
public interface IModelImage {

  /**
   * Adds a photo to the collection of images.
   *
   * @param tag   a tag to index the photo.
   * @param image the image being added.
   * @throws IllegalArgumentException if the parameters are null, the tag is already in use.
   */
  public void addPhoto(String tag, IPhoto image);

  /**
   * Replaces a photo in the collection of images.
   *
   * @param tag   the tag of the photo being replaced.
   * @param image the new image.
   * @throws IllegalArgumentException if the parameters are null, the tag does not exist in the
   *                                  map.
   */
  public void replace(String tag, IPhoto image);

  /**
   * Removes a photo from the collection of photos.
   *
   * @param tag the tag of the photo being replaced.
   * @throws IllegalArgumentException if the parameters are null, the tag does not exist in the
   *                                  map.
   */
  public void removePhoto(String tag);

  /**
   * Applies a filter to an image.
   *
   * @param tag    the image being indexed
   * @param filter the filter being applied
   * @return a new photo as the previous photo with the filter applied.
   * @throws IllegalArgumentException if the parameters are null, the tag does not exist in the
   *                                  map.
   */
  public IPhoto applyFilter(String tag, IFilter filter);

  /**
   * Retrieves a photo from the collection.
   *
   * @param tag the photo being retrieved
   * @return a photo
   * @throws IllegalArgumentException if the parameter is null, the tag does not exist in the map.
   */
  public IPhoto getPhoto(String tag);
}
