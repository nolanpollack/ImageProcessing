package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
 * Class that holds the tests for the functionality of the GrayscaleCommand class and its
 * exceptions.
 */
public class GrayscaleCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand grayscaleCommand = new controller.GrayscaleCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("grayscale"));

  @Test
  public void testToString() {
    assertEquals("grayscale", grayscaleCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      grayscaleCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testGrayscaleOnLayerWNullPhoto() {
    model.createLayer("goodbye");
    model.changeCurrent("goodbye");
    try {
      grayscaleCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the image cannot be null", e.getMessage());
    }
  }

  @Test
  public void testGrayscaleCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(10, 20, 30);
    pixels[0][1] = new PixelImpl(30, 2, 10);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    StatusResult result = grayscaleCommand.run(list);
    int r1 = (int) (10 * .2126 + 20 * .7152 + 30 * .0722);
    int r2 = (int) (30 * .2126 + 2 * .7152 + 10 * .0722);
    int g1 = (int) (10 * .2126 + 20 * .7152 + 30 * .0722);
    int g2 = (int) (30 * .2126 + 2 * .7152 + 10 * .0722);
    int b1 = (int) (10 * .2126 + 20 * .7152 + 30 * .0722);
    int b2 = (int) (30 * .2126 + 2 * .7152 + 10 * .0722);

    IPixel[][] newPixels = new IPixel[1][2];
    newPixels[0][0] = new PixelImpl(r1, g1, b1);
    newPixels[0][1] = new PixelImpl(r2, g2, b2);
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
  public void testGrayscaleCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("grayscale", "goodbye"));
    StatusResult result = grayscaleCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

}
