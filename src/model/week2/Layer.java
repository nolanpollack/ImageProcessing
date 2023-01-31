package model.week2;

import controller.ImageUtil;
import model.week1.IFilter;
import model.week1.IPhoto;

/**
 * Represents a layer in a layered photo.
 */
public class Layer implements ILayer {

  /**
   * The image in this layer.
   */
  private IPhoto image;

  /**
   * Whether or not this layer is visible.
   */
  private boolean visibility;

  /**
   * THe name of this layer.
   */
  private final String name;

  /**
   * Initializes a layer when it's first created. Image is set to null when created and visibility
   * is set to true.
   *
   * @param name the name of this layer.
   */
  public Layer(String name) {
    ImageUtil.checkNull(name, "name");
    this.name = name;
    this.image = null;
    this.visibility = true;
  }

  /**
   * Initializes a layer with a name, image, and visibility.
   *
   * @param name       name of the layer.
   * @param image      image for the layer.
   * @param visibility visibility of the layer.
   */
  public Layer(String name, IPhoto image, boolean visibility) {
    ImageUtil.checkNull(name, "name");
    this.name = name;
    this.image = image;
    this.visibility = visibility;
  }

  @Override
  public IPhoto getPhoto() {
    if (image == null) {
      return null;
    }
    return image.clone();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean getVisibility() {
    return this.visibility;
  }

  @Override
  public void changeVisibility(boolean changing) {
    this.visibility = changing;
  }

  @Override
  public void loadPhoto(IPhoto image) {
    this.image = image;
  }

  @Override
  public IPhoto applyFilter(IFilter filter) {
    ImageUtil.checkNull(filter, "filter");
    return filter.apply(this.image);
  }

  @Override
  public ILayer clone() {
    return new Layer(this.getName(), this.getPhoto(), this.getVisibility());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof ILayer) {
      ILayer other = (ILayer) (obj);
      return other.getVisibility() == this.getVisibility()
          && other.getName() == this.getName()
          && checkPhotos(other);
    }
    return false;
  }

  /**
   * Checks two layers to see if they have the same photo.
   *
   * @param other the second layer being compared.
   * @return true of both layers have the same photo.
   */
  private boolean checkPhotos(ILayer other) {
    if (other.getPhoto() == null && this.getPhoto() == null) {
      return true;
    }
    return other.getPhoto().equals(this.getPhoto());
  }

  @Override
  public int hashCode() {
    if (this.getVisibility()) {
      return 100 + this.getName().hashCode() + this.getPhoto().hashCode();
    } else {
      return 200 + this.getName().hashCode() + this.getPhoto().getHeight()
          * this.getPhoto().getWidth();
    }
  }
}
