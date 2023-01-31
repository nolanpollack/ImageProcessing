package model.week1;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.List;
import model.week2.ILayer;
import model.week2.Layer;

/**
 * Represents a model for an image processing software that contains images that have one layer.
 */
public class ModelImpl implements IModelImage {

  /**
   * A map of photos stored in the model, indexed by a string tag.
   */
  protected List<ILayer> photos;

  /**
   * Constructs a new model and initializes the map of photos.
   */
  public ModelImpl() {
    this.photos = new ArrayList<>();
  }

  public ModelImpl(List<ILayer> layers) {
    this.photos = layers;
  }

  @Override
  public void addPhoto(String tag, IPhoto image) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        throw new IllegalArgumentException("the tag is already in use");
      }
    }
    ILayer addOn = new Layer(tag);
    addOn.loadPhoto(image);
    photos.add(addOn);
  }

  @Override
  public void replace(String tag, IPhoto image) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        layer.loadPhoto(image);
        return;
      }
    }
    throw new IllegalArgumentException("the tag " + tag + " does not exist");
  }

  @Override
  public void removePhoto(String tag) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        photos.remove(layer);
        return;
      }
    }
    throw new IllegalArgumentException("The tag " + tag + " does not exist");
  }

  @Override
  public IPhoto applyFilter(String tag, IFilter filter) {
    ImageUtil.checkNull(tag, "tag");
    ImageUtil.checkNull(filter, "filter");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        return layer.applyFilter(filter);
      }
    }
    throw new IllegalArgumentException("The tag " + tag + " does not exist");
  }

  @Override
  public IPhoto getPhoto(String tag) {
    ImageUtil.checkNull(tag, "tag");
    for (ILayer layer : photos) {
      if (layer.getName().equals(tag)) {
        return layer.getPhoto();
      }
    }
    throw new IllegalArgumentException("The tag " + tag + " does not exist");
  }
}
