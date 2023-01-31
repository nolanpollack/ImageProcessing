package model.week3;

import java.awt.image.BufferedImage;
import model.week2.ILayerModel;

/**
 * Represents a model with added functionality to render the visible layered image..
 */
public interface ILayerModel2 extends ILayerModel {

  /**
   * Gets the topmost visible photo as a BufferedImage.
   *
   * @return a BufferedImage representing the topmost visible layer.
   */
  public BufferedImage getTopmostBuffered();
}
