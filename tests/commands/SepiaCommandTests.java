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
 * Class that holds the tests for the functionality of the SepiaCommand class and its exceptions.
 */
public class SepiaCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand sepiaCommand = new controller.SepiaCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("sepia"));

  @Test
  public void testToString() {
    assertEquals("sepia", sepiaCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      sepiaCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testSepiaOnLayerWNullPhoto() {
    model.createLayer("goodbye");
    model.changeCurrent("goodbye");
    try {
      sepiaCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the image cannot be null", e.getMessage());
    }
  }

  @Test
  public void testSepiaCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(10, 20, 30);
    pixels[0][1] = new PixelImpl(30, 2, 10);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    StatusResult result = sepiaCommand.run(list);
    int r1 = (int) (10 * .393 + 20 * .769 + 30 * .189);
    int r2 = (int) (30 * .393 + 2 * .769 + 10 * .189);
    int g1 = (int) (10 * .349 + 20 * .686 + 30 * .168);
    int g2 = (int) (30 * .349 + 2 * .686 + 10 * .168);
    int b1 = (int) (10 * .272 + 20 * .534 + 30 * .131);
    int b2 = (int) (30 * .272 + 2 * .534 + 10 * .131);

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
  public void testSepiaCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("sepia", "goodbye"));
    StatusResult result = sepiaCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }
}
