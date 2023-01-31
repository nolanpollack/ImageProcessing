package model.week3;

import controller.ExtendedImageUtil;
import java.awt.image.BufferedImage;
import model.week2.LayerModelImpl;

/**
 * Represents a Model for a photo processing application.
 */
public class LayerModelImpl2 extends LayerModelImpl implements ILayerModel2 {

  @Override
  public BufferedImage getTopmostBuffered() {
    if (getTopMostVisibleLayer().getPhoto() == null) {
      BufferedImage toReturn = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
      return toReturn;
    } else {
      return ExtendedImageUtil.asBuffered(getTopMostVisibleLayer().getPhoto());
    }
  }
}
