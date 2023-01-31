package model.week2;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.List;
import model.week1.IFilter;
import model.week1.IPhoto;
import model.week1.ModelImpl;

/**
 * Represents an implementation of a layered photo model.
 */
public class LayerModelImpl extends ModelImpl implements ILayerModel {

  /**
   * The currently selected layer in the model.
   */
  private ILayer current;

  /**
   * The required height for an image in the model, or 0 when there is no photo.
   */
  private int height;

  /**
   * The required width for an image in the model, or 0 when there is no photo.
   */
  private int width;

  /**
   * Initializes a LayerModelImpl. The currently selected layer is set to null by default and height
   * and width are set to 0.
   */
  public LayerModelImpl() {
    super();
    this.current = null;
    this.height = 0;
    this.width = 0;
  }

  /**
   * Creates a new LayerModelImpl with a given set of layers.
   *
   * @param layers a list of layers to be used as the list of layers in the model.
   */
  public LayerModelImpl(List<ILayer> layers) {
    super(layers);
    updateHeightAndWidth();
    checkValidLayers();
    this.current = null;
  }

  /**
   * Updates this model's height and width to the height and width of a photo in a layer in the
   * model.
   */
  private void updateHeightAndWidth() {
    for (ILayer layer : photos) {
      if (layer.getPhoto() != null) {
        this.height = layer.getPhoto().getHeight();
        this.width = layer.getPhoto().getHeight();
        return;
      }
    }
  }

  /**
   * Checks all layers to confirm they have the same height and width.
   */
  private void checkValidLayers() {
    for (ILayer layer : photos) {
      if (layer.getPhoto() != null) {
        if (layer.getPhoto().getHeight() != this.height
            || layer.getPhoto().getWidth() != this.width) {
          throw new IllegalArgumentException("the photos in the given collection are not all the "
              + "same height and width");
        }
      }
    }
  }

  @Override
  public boolean getVisibility(String tag) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        return layer.getVisibility();
      }
    }
    throw new IllegalArgumentException("The tag " + tag + " does not exist");
  }

  @Override
  public void changeCurrent(String current) {
    //TODO: I dont think we need to check for null here
    //ImageUtil.checkNull(current, "current's tag");
    if (current == null) {
      this.current = null;
      return;
    }
    for (ILayer layer : photos) {
      if (layer.getName().equals(current)) {
        this.current = layer;
        return;
      }
    }
    throw new IllegalArgumentException("The tag " + current + " does not exist");
  }

  @Override
  public ILayer getCurrent() {
    if (current == null) {
      return null;
    }
    return this.current.clone();
  }

  @Override
  public void reset() {
    this.height = 0;
    this.width = 0;
    this.current = null;
    this.photos = new ArrayList<>();
  }

  @Override
  public ILayer getLayer(String tag) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        return layer.clone();
      }
    }
    throw new IllegalArgumentException("The tag " + tag + " does not exist");
  }

  @Override
  public List<ILayer> getAllLayers() {
    List<ILayer> answer = new ArrayList<>();
    for (ILayer layer : photos) {
      answer.add(layer.clone());
    }
    return answer;
  }

  @Override
  public void createLayer(String name) {
    this.addPhoto(name, null);
  }

  @Override
  public void changeMultiLayeredImage(List<ILayer> newCollection) {
    ImageUtil.checkNull(newCollection, "new multilayered image");
    this.photos = newCollection;
    this.current = null;
  }

  @Override
  public void loadPhoto(IPhoto photo) {
    ImageUtil.checkNull(photo, "photo");
    if (height == 0 && width == 0) {
      this.height = photo.getHeight();
      this.width = photo.getWidth();
      checkCurrent();
      String tag = current.getName();
      this.replace(tag, photo);
    } else if (height != photo.getHeight() || width != photo.getWidth()) {
      throw new IllegalArgumentException("cannot layer this photo");
    } else {
      checkCurrent();
      String tag = current.getName();
      this.replace(tag, photo);
    }
  }

  /**
   * Checks to see if a layer is selected. If not, current is null and this method throws an
   * exception.
   */
  private void checkCurrent() {
    if (this.current == null) {
      throw new IllegalArgumentException("you have not selected a current layer");
    }
  }

  @Override
  public IPhoto applyFilter(IFilter filter) {
    checkCurrent();
    ImageUtil.checkNull(filter, "filter");
    return current.applyFilter(filter);
  }

  @Override
  public ILayer getTopMostVisibleLayer() {
    List<ILayer> layers = this.getAllLayers();
    for (int i = layers.size() - 1; i >= 0; i = i - 1) {
      if (layers.get(i).getPhoto() != null && layers.get(i).getVisibility()) {
        return layers.get(i).clone();
      }
    }
    throw new IllegalArgumentException("no valid layer to retrieve");
  }

  @Override
  public void changeVisibility(boolean changing) {
    checkCurrent();
    this.current.changeVisibility(changing);
  }

  @Override
  public boolean getVisibility() {
    checkCurrent();
    return this.current.getVisibility();
  }

  @Override
  public void addPhoto(String tag, IPhoto image) {
    if (image != null) {
      if (height == 0 && width == 0) {
        super.addPhoto(tag, image);
        this.height = image.getHeight();
        this.width = image.getWidth();
        return;
      } else if (image.getHeight() != this.height || image.getWidth() != this.width) {
        throw new IllegalArgumentException(
            "this photo does not match the height and width of this collection");
      }
      super.addPhoto(tag, image);
    } else {
      super.addPhoto(tag, image);
    }
  }
}
