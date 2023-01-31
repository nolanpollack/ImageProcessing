package model.week2;

import java.util.List;
import model.week1.IFilter;
import model.week1.IModelImage;
import model.week1.IPhoto;

/**
 * Represents an ILayerModel.
 */
public interface ILayerModel extends IModelImage {

  /**
   * Gets the layer from a given tag.
   *
   * @param tag the name of the layer.
   * @return a copy of the layer.
   */
  public ILayer getLayer(String tag);

  /**
   * Gets all layers from the model.
   *
   * @return a list of all the ILayers stored in this model.
   */
  public List<ILayer> getAllLayers();

  /**
   * Creates a new layer.
   *
   * @param name the name of the layer being created.
   */
  public void createLayer(String name);

  /**
   * Changes the layered image being stored in the model to a new list of layers.
   *
   * @param newCollection new list of layers to be set as the new layered image.
   */
  public void changeMultiLayeredImage(List<ILayer> newCollection);

  /**
   * Loads a photo into the current selected layer.
   *
   * @param photo being loaded.
   */
  public void loadPhoto(IPhoto photo);

  /**
   * Applies a filter to the current selected photo.
   *
   * @param filter the filter being applied.
   * @return a new IPhoto with the filter applied.
   */
  public IPhoto applyFilter(IFilter filter);

  /**
   * Gets the topmost visible layer in the layered photo.
   *
   * @return an ILayer representing the top visible layer in this layered photo.
   */
  public ILayer getTopMostVisibleLayer();

  /**
   * Changes the visibility of the currently selected layer.
   *
   * @param changing true for visible, false for invisible.
   */
  public void changeVisibility(boolean changing);

  /**
   * Gets the visibility of the current selected layer.
   *
   * @return true for visible, false for invisible.
   */
  public boolean getVisibility();

  /**
   * Gets the visibility for a layer in this model.
   *
   * @param tag the layer.
   * @return true if the layer is visible, false if not.
   */
  public boolean getVisibility(String tag);

  /**
   * Changes the current selected layer in this model.
   *
   * @param current the name of the layer being made current.
   */
  public void changeCurrent(String current);

  /**
   * Returns the currently selected layer.
   *
   * @return a copy of the current layer.
   */
  public ILayer getCurrent();

  /**
   * Resets the entire list of files, the current layer, and the height and width of the current
   * collection of layers.
   */
  public void reset();
}
