package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.BlurCommand;
import controller.IPhotoCommand;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.PhotoImpl;
import model.week1.PixelImpl;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality of the BlurCommand class and its exceptions.
 */
public class BlurCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand blurCommand = new BlurCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("blur"));

  @Test
  public void testToString() {
    assertEquals("blur", blurCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      blurCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testBlurOnLayerWNullPhoto() {
    model.createLayer("goodbye");
    model.changeCurrent("goodbye");
    try {
      blurCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the image cannot be null", e.getMessage());
    }
  }

  @Test
  public void testBlurCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(10, 20, 30);
    pixels[0][1] = new PixelImpl(30, 2, 10);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    StatusResult result = blurCommand.run(list);
    int r1 = (int) (10 * (1.0 / 4.0));
    int r2 = (int) (30 * (1.0 / 8.0));
    int rOG1 = r1 + r2;
    int g1 = (int) (20 * (1.0 / 4.0));
    int g2 = (int) (2 * (1.0 / 8.0));
    int gOG1 = g1 + g2;
    int b1 = (int) (30 * (1.0 / 4.0));
    int b2 = (int) (10 * (1.0 / 8.0));
    int bOG1 = b1 + b2;

    int r3 = (int) (10 * (1.0 / 8.0));
    int r4 = (int) (30 * (1.0 / 4.0));
    int rOG2 = r3 + r4;
    int g3 = (int) (20 * (1.0 / 8.0));
    int g4 = (int) (2 * (1.0 / 4.0));
    int gOG2 = g3 + g4;
    int b3 = (int) (30 * (1.0 / 8.0));
    int b4 = (int) (10 * (1.0 / 4.0));
    int bOG2 = b3 + b4;

    IPixel[][] newPixels = new IPixel[1][2];
    newPixels[0][0] = new PixelImpl(rOG1, gOG1, bOG1);
    newPixels[0][1] = new PixelImpl(rOG2, gOG2, bOG2);
    IPhoto altered = new PhotoImpl(newPixels, 255);
    assertTrue(model.getCurrent().getPhoto().equals(altered));
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testBlurCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("blur", "goodbye"));
    StatusResult result = blurCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }
}
