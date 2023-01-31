import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ExtendedImageUtil;
import java.awt.image.BufferedImage;
import model.week1.CheckerboardImage;
import model.week1.IPhoto;
import model.week1.OGColor;
import model.week3.ILayerModel2;
import model.week3.LayerModelImpl2;
import org.junit.Test;

/**
 * Tests functionality of LayerModelImpl2.
 */
public class LayerModelImpl2SpecificTests {

  private ILayerModel2 model = new LayerModelImpl2();

  private boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
      for (int x = 0; x < img1.getWidth(); x++) {
        for (int y = 0; y < img1.getHeight(); y++) {
          if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  @Test
  public void testGetTopmostBufferedNoTopmost() {
    try {
      model.createLayer("layer1");
      assertTrue(bufferedImagesEqual(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB),
          model.getTopmostBuffered()));
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("no valid layer to retrieve", e.getMessage());
    }
  }

  @Test
  public void handleReloadEventWithTopmost() {
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto checker = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(checker);
    BufferedImage expected = ExtendedImageUtil
        .asBuffered(model.getTopMostVisibleLayer().getPhoto());
    assertTrue(bufferedImagesEqual(expected,
        model.getTopmostBuffered()));
  }
}
