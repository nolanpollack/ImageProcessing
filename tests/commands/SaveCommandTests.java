package commands;

import static org.junit.Assert.assertEquals;

import controller.IPhotoCommand;
import controller.IPhotoController;
import controller.LayerPhotoController;
import controller.SaveCommand;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week1.CheckerboardImage;
import model.week1.OGColor;
import model.week2.ILayer;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;
import view.IPhotoView;
import view.ImageTextualView;

/**
 * Class that holds the tests for the functionality of the SaveCommand class and its exceptions.
 */
public class SaveCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand saveCommand = new SaveCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("save", "hello"));
  private IPhotoView view = new ImageTextualView(System.out);
  private IPhotoController controller = new LayerPhotoController(model, view);

  @Test
  public void testToString() {
    assertEquals("save", saveCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("save"));
    StatusResult result = saveCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("save done now"));
    StatusResult result = saveCommand.run(list);
    assertEquals(new StatusResult(Status.EXPORT, "done now", null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.EXPORT, "done now", null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.EXPORT, "done now", null).getThird(),
        result.getThird());
  }

  @Test
  public void testSaveEmpty() {
    StatusResult result = saveCommand.run(list);
    List<ILayer> listResult = controller.importLayeredPhoto("hello");
    assertEquals(0, listResult.size());
    assertEquals(new StatusResult(Status.EXPORT, "hello", null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.EXPORT, "hello", null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.EXPORT, "hello", null).getThird(),
        result.getThird());
  }

  @Test
  public void testSaveCorrect() {
    model.addPhoto("hello", new CheckerboardImage(1, 2, 255,
        new OGColor(1, 2, 3), new OGColor(3, 2, 1), 1));
    list = new ArrayList<>(Arrays.asList("save", "woohoo"));
    StatusResult result = saveCommand.run(list);
    assertEquals(new StatusResult(Status.EXPORT, "woohoo", null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.EXPORT, "woohoo", null).getSecond(),
        result.getSecond());
  }
}