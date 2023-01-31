package model.week2;

import model.week1.IFilter;
import model.week1.IPhoto;

/**
 * Represents a layer in a layered photo.
 */
public interface ILayer {

  /**
   * Returns the image from the given layer.
   *
   * @return IPhoto that is loaded in this layer.
   */
  public IPhoto getPhoto();

  /**
   * Returns the given name of the layer.
   *
   * @return String name of layer.
   */
  public String getName();

  /**
   * Returns whether this layer has their visibility "turned" on or off.
   *
   * @return a boolean representing the visibility of this layer.
   */
  public boolean getVisibility();

  /**
   * Sets the visibility of this layer.
   *
   * @param changing true for visible, false for invisible.
   */
  public void changeVisibility(boolean changing);

  /**
   * Loads a photo into this layer.
   *
   * @param image the image being loaded.
   */
  public void loadPhoto(IPhoto image);

  /**
   * Applies a filter to the photo in this layer and returns it as a new photo.
   *
   * @param filter the filter being applied.
   * @return a new IPhoto containing the applied filter.
   */
  public IPhoto applyFilter(IFilter filter);

  /**
   * Clones this layer.
   *
   * @return a new ILayer with no fields containing the same references.
   */
  public ILayer clone();
}
