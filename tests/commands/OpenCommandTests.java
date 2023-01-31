package commands;

import static org.junit.Assert.assertEquals;

import controller.IPhotoCommand;
import controller.IPhotoController;
import controller.LayerPhotoController;
import controller.OpenCommand;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;
import view.IPhotoView;
import view.ImageTextualView;

/**
 * Class that holds the tests for the functionality of the OpenCommand class and its exceptions.
 */
public class OpenCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand openCommand = new OpenCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("open", "newyorkphotos"));
  private IPhotoView view = new ImageTextualView(System.out);
  private IPhotoController controller = new LayerPhotoController(model, view);

  @Test
  public void testToString() {
    assertEquals("open", openCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("open"));
    StatusResult result = openCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(), result.getFirst());
    assertEquals(
        new StatusResult(Status.INVALID_INPUT, null, null).getSecond(), result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(), result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("open done now"));
    StatusResult result = openCommand.run(list);
    assertEquals(new StatusResult(Status.OPEN, "done now", null).getFirst(), result.getFirst());
    assertEquals(new StatusResult(Status.OPEN, "done now", null).getSecond(), result.getSecond());
    assertEquals(new StatusResult(Status.OPEN, "done now", null).getThird(), result.getThird());
  }

  @Test
  public void testOpenEmpty() {
    StatusResult result = openCommand.run(list);
    assertEquals(
        new StatusResult(Status.OPEN, "newyorkphotos", null).getFirst(), result.getFirst());
    assertEquals(
        new StatusResult(Status.OPEN, "newyorkphotos", null).getSecond(), result.getSecond());
    assertEquals(
        new StatusResult(Status.OPEN, "newyorkphotos", null).getThird(), result.getThird());
    assertEquals(3, model.getAllLayers().size());
  }

  @Test
  public void testOpenCorrect() {
    list = new ArrayList<>(Arrays.asList("open", "woohoo"));
    StatusResult result = openCommand.run(list);
    assertEquals(new StatusResult(Status.OPEN, "woohoo", null).getFirst(), result.getFirst());
    assertEquals(new StatusResult(Status.OPEN, "woohoo", null).getSecond(), result.getSecond());
    assertEquals(new StatusResult(Status.OPEN, "woohoo", null).getThird(), result.getThird());
  }
}
