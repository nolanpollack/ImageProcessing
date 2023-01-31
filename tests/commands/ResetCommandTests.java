package commands;

import static org.junit.Assert.assertEquals;

import controller.IPhotoCommand;
import controller.IPhotoController;
import controller.LayerPhotoController;
import controller.ResetCommand;
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
 * Class that holds the tests for the functionality of the ResetCommand class and its exceptions.
 */
public class ResetCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand resetCommand = new ResetCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("reset"));
  private IPhotoView view = new ImageTextualView(System.out);
  private IPhotoController controller = new LayerPhotoController(model, view);

  @Test
  public void testResetCorrect() {
    StatusResult result = resetCommand.run(list);
    assertEquals(0, model.getAllLayers().size());
    assertEquals(null, model.getCurrent());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testResetMoreThan1() {
    list = new ArrayList<>(Arrays.asList("reset", "done"));
    StatusResult result = resetCommand.run(list);
    assertEquals(0, model.getAllLayers().size());
    assertEquals(null, model.getCurrent());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }
}
