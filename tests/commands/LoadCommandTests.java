package commands;

import static org.junit.Assert.assertEquals;

import controller.IPhotoCommand;
import controller.IPhotoController;
import controller.LayerPhotoController;
import controller.LoadCommand;
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
 * Class that holds the tests for the functionality of the LoadCommand class and its exceptions.
 */
public class LoadCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand loadCommand = new LoadCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("load", "done2.ppm"));
  private IPhotoView view = new ImageTextualView(System.out);
  private IPhotoController controller = new LayerPhotoController(model, view);

  @Test
  public void testToString() {
    assertEquals("load", loadCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("load"));
    StatusResult result = loadCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(), result.getFirst());
    assertEquals(
        new StatusResult(Status.INVALID_INPUT, null, null).getSecond(), result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(), result.getThird());
  }

  @Test
  public void testLoadCorrect() {
    model.createLayer("halo");
    model.changeCurrent("halo");
    StatusResult result = loadCommand.run(list);
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }
}
