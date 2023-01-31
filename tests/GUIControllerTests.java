import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.ExtendedImageUtil;
import controller.GUIController;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionEvent;
import model.week1.CheckerboardImage;
import model.week1.IPhoto;
import model.week1.ImageCreator.PhotoType;
import model.week1.OGColor;
import model.week3.ILayerModel2;
import model.week3.LayerModelImpl2;
import org.junit.Before;
import org.junit.Test;
import view.FrameViewImpl;
import view.IFrameView;

/**
 * Tests functionality of GUIController. 
 */
public class GUIControllerTests {

  private ILayerModel2 model;
  private ILayerModel2 model2;
  private IFrameView view;
  private IFrameView view2;
  private GUIController controller;
  private GUIController controller2;

  @Before
  public void init() {
    model = new LayerModelImpl2();
    view = new FrameViewImpl();
    model2 = new LayerModelImpl2();
    view2 = new FrameViewImpl();
  }

  @Test
  public void testActionPerformedBlur() {
    ActionEvent blur = new ActionEvent(view, 1, "Blur");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    controller = new GUIController(model, view);
    controller.actionPerformed(blur);
    int r1 = (int) (255 * (1.0 / 4.0));
    int b1 = (int) (255 * (1.0 / 8.0));
    int r2 = (int) (255 * (1.0 / 8.0));
    int b2 = (int) (255 * (1.0 / 4.0));
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(r1, 0, b1), new OGColor(r2, 0, b2), 1);
    assertTrue(expected.equals(model.getTopMostVisibleLayer().getPhoto()));
  }

  @Test
  public void testActionPerformedSharpen() {
    ActionEvent sharpen = new ActionEvent(view, 1, "Sharpen");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    controller = new GUIController(model, view);
    controller.actionPerformed(sharpen);
    int r1 = (int) (255 * (1.0));
    int b1 = (int) (255 * (1.0 / 4.0));
    int r2 = (int) (255 * (1.0 / 4.0));
    int b2 = (int) (255 * (1.0));
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(r1, 0, b1), new OGColor(r2, 0, b2), 1);
    assertTrue(expected.equals(model.getTopMostVisibleLayer().getPhoto()));
  }

  @Test
  public void testActionPerformedSepia() {
    ActionEvent sepia = new ActionEvent(view, 1, "Sepia");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    controller = new GUIController(model, view);
    controller.actionPerformed(sepia);
    int r2 = (int) (255 * .189);
    int g2 = (int) (255 * .168);
    int b2 = (int) (255 * .131);
    int r1 = (int) (255 * .393);
    int g1 = (int) (255 * .349);
    int b1 = (int) (255 * .272);
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(r1, g1, b1), new OGColor(r2, g2, b2), 1);
    IPhoto actual = model.getTopMostVisibleLayer().getPhoto();
    assertTrue(expected.equals(actual));
  }

  @Test
  public void testActionPerformedGrayscale() {
    ActionEvent grayscale = new ActionEvent(view, 1, "Grayscale");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    controller = new GUIController(model, view);
    controller.actionPerformed(grayscale);
    int r2 = (int) (255 * .0722);
    int g2 = (int) (255 * .0722);
    int b2 = (int) (255 * .0722);
    int r1 = (int) (255 * .2126);
    int g1 = (int) (255 * .2126);
    int b1 = (int) (255 * .2126);
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(r1, g1, b1), new OGColor(r2, g2, b2), 1);
    IPhoto actual = model.getTopMostVisibleLayer().getPhoto();
    assertTrue(expected.equals(actual));
  }

  @Test
  public void testActionPerformedSave() {
    ActionEvent save = new ActionEvent(view, 1, "Save");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    IPhoto photo2 = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 255, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo2);
    controller = new GUIController(model, view);
    //call the directory testSaveAction
    controller.actionPerformed(save);
    controller2 = new GUIController(model2, view2);
    ActionEvent open = new ActionEvent(view, 1, "Open");
    controller2.actionPerformed(open);

    assertTrue(model.getAllLayers().get(0).getPhoto().equals(model2.getAllLayers().get(0)
        .getPhoto()));
    assertTrue(model.getAllLayers().get(1).getPhoto().equals(model2.getAllLayers().get(1)
        .getPhoto()));
  }

  @Test
  public void testActionPerformedOpen() {
    ActionEvent save = new ActionEvent(view, 1, "Save");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto photo = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo);
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    IPhoto photo2 = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 255, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(photo2);
    controller = new GUIController(model, view);
    //call the directory testSaveAction
    controller.actionPerformed(save);
    controller2 = new GUIController(model2, view2);
    ActionEvent open = new ActionEvent(view, 1, "Open");
    controller2.actionPerformed(open);

    assertTrue(model.getAllLayers().get(0).getPhoto().equals(model2.getAllLayers().get(0)
        .getPhoto()));
    assertTrue(model.getAllLayers().get(1).getPhoto().equals(model2.getAllLayers().get(1)
        .getPhoto()));
  }

  @Test
  public void testActionPerformedCheckerboard() {
    ActionEvent checker = new ActionEvent(view, 1, "Checkerboard");
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    controller = new GUIController(model, view);
    //give checkerboard: 1, 1, 255, 0, 0, 0, 0, 255, 1
    controller.actionPerformed(checker);
    IPhoto photo = new CheckerboardImage(1, 1, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    assertTrue(photo.equals(model.getLayer("layer1").getPhoto()));
  }

  @Test
  public void testActionPerformedCreate() {
    ActionEvent create = new ActionEvent(view, 1, "Create");
    controller = new GUIController(model, view);
    //give layer name layer1
    controller.actionPerformed(create);
    assertTrue(model.getAllLayers().size() == 1);
    assertTrue(model.getAllLayers().get(0).getName().equals("layer1"));
  }

  //TODO: test current selection

  @Test
  public void testActionPerformedExecuteScript() {
    ActionEvent script = new ActionEvent(view, 1, "Execute script");
    controller = new GUIController(model, view);
    //give txt.file script1 from this project
    controller.actionPerformed(script);
    int r2 = (int) (255 * .0722);
    int g2 = (int) (255 * .0722);
    int b2 = (int) (255 * .0722);
    int r1 = (int) (255 * .2126);
    int g1 = (int) (255 * .2126);
    int b1 = (int) (255 * .2126);
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(r1, g1, b1), new OGColor(r2, g2, b2), 1);
    IPhoto actual = model.getTopMostVisibleLayer().getPhoto();
    assertTrue(expected.equals(actual));
    assertTrue(model.getAllLayers().size() == 2);
    assertTrue(model.getAllLayers().get(0).getName().equals("first"));
    assertTrue(model.getAllLayers().get(1).getName().equals("second"));
  }

  @Test
  public void testActionPerformedExportPNG() {
    ActionEvent export = new ActionEvent(view, 1, "Export");
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    model.loadPhoto(expected);
    controller = new GUIController(model, view);
    controller.actionPerformed(export);
    //export photo to testExport1 png
    IPhoto photo = ExtendedImageUtil.readPhoto("testExport1", PhotoType.SIMPLE_PHOTO,
        "png");
    assertTrue(photo.equals(model.getAllLayers().get(1).getPhoto()));
  }

  @Test
  public void testActionPerformedExportPPM() {
    ActionEvent export = new ActionEvent(view, 1, "Export");
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    model.loadPhoto(expected);
    controller = new GUIController(model, view);
    controller.actionPerformed(export);
    //export photo to testExport1 ppm
    IPhoto photo = ExtendedImageUtil.readPhoto("testExport1", PhotoType.SIMPLE_PHOTO,
        "ppm");
    assertTrue(photo.equals(model.getAllLayers().get(1).getPhoto()));
  }

  @Test
  public void testActionPerformedInvisible() {
    ActionEvent invis = new ActionEvent(view, 1, "Make invisible");
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    assertTrue(model.getLayer("layer2").getVisibility());
    controller = new GUIController(model, view);
    controller.actionPerformed(invis);
    assertFalse(model.getLayer("layer2").getVisibility());
  }

  @Test
  public void testActionPerformedVisible() {
    ActionEvent vis = new ActionEvent(view, 1, "Make visible");
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    model.changeVisibility(false);
    assertFalse(model.getLayer("layer2").getVisibility());
    controller = new GUIController(model, view);
    controller.actionPerformed(vis);
    assertTrue(model.getLayer("layer2").getVisibility());
  }

  @Test
  public void testActionPerformedLoad() {
    ActionEvent load = new ActionEvent(view, 1, "Load");
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    controller = new GUIController(model, view);
    //load testExport1
    controller.actionPerformed(load);
    assertTrue(model.getLayer("layer2").getPhoto().equals(expected));
  }

  @Test
  public void testActionPerformedReset() {
    ActionEvent reset = new ActionEvent(view, 1, "Reset");
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    model.loadPhoto(expected);
    controller = new GUIController(model, view);
    controller.actionPerformed(reset);
    assertEquals(0, model.getAllLayers().size());
  }

  @Test
  public void testActionPerformedMosaic() {
    ActionEvent mosaic = new ActionEvent(view, 1, "Mosaic");
    IPhoto expected = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.createLayer("layer1");
    model.createLayer("layer2");
    model.changeCurrent("layer2");
    model.loadPhoto(expected);
    controller = new GUIController(model, view);
    controller.actionPerformed(mosaic);
    //mosaic with 100 seeds
    IPhoto actual = model.getAllLayers().get(1).getPhoto();
    assertTrue(actual.equals(expected));
  }

  @Test
  public void testValueChangedCurrent() {
    //choose layer2
    model.createLayer("layer1");
    model.createLayer("layer2");
    assertEquals(model.getCurrent(), null);
    controller = new GUIController(model, view);
    ListSelectionEvent changing = new ListSelectionEvent(view, 1, 1,
        true);
    controller.valueChanged(changing);
  }

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
  public void handleReloadEventNoTopmost() {
    controller = new GUIController(model, view);
    assertTrue(bufferedImagesEqual(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB),
        controller.handleReloadEvent()));
  }

  @Test
  public void handleReloadEventWithTopmost() {
    model.createLayer("layer1");
    model.changeCurrent("layer1");
    IPhoto checker = new CheckerboardImage(1, 2, 255,
        new OGColor(255, 0, 0), new OGColor(0, 0, 255), 1);
    model.loadPhoto(checker);
    controller = new GUIController(model, view);
    BufferedImage expected = ExtendedImageUtil
        .asBuffered(model.getTopMostVisibleLayer().getPhoto());
    assertTrue(bufferedImagesEqual(expected,
        controller.handleReloadEvent()));
  }

}
