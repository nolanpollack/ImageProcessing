package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.IPhotoCommand;
import controller.SharpenCommand;
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
 * Class that holds the tests for the functionality of the SharpenCommand class and its exceptions.
 */
public class SharpenCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand sharpenCommand = new SharpenCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("sharpen"));

  @Test
  public void testToString() {
    assertEquals("sharpen", sharpenCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      sharpenCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testSharpenOnLayerWNullPhoto() {
    model.createLayer("goodbye");
    model.changeCurrent("goodbye");
    try {
      sharpenCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the image cannot be null", e.getMessage());
    }
  }

  @Test
  public void testSharpenCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(10, 20, 30);
    pixels[0][1] = new PixelImpl(30, 2, 10);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    StatusResult result = sharpenCommand.run(list);
    int r1 = (int) (10 * (1.0));
    int r2 = (int) (30 * (1.0 / 4.0));
    int rOG1 = r1 + r2;
    int g1 = (int) (20 * (1.0));
    int g2 = (int) (2 * (1.0 / 4.0));
    int gOG1 = g1 + g2;
    int b1 = (int) (30 * (1.0));
    int b2 = (int) (10 * (1.0 / 4.0));
    int bOG1 = b1 + b2;

    int r3 = (int) (10 * (1.0 / 4.0));
    int r4 = (int) (30 * (1.0));
    int rOG2 = r3 + r4;
    int g3 = (int) (20 * (1.0 / 4.0));
    int g4 = (int) (2 * (1.0));
    int gOG2 = g3 + g4;
    int b3 = (int) (30 * (1.0 / 4.0));
    int b4 = (int) (10 * (1.0));
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
  public void testSharpenCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("sharpen", "goodbye"));
    StatusResult result = sharpenCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }
}
