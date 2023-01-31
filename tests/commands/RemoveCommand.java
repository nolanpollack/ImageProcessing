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
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality of the RemoveCommand class and its exceptions.
 */
public class RemoveCommand {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand removeCommand = new controller.RemoveCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("remove", "hello"));

  @Test
  public void testToString() {
    assertEquals("remove", removeCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("remove"));
    StatusResult result = removeCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("remove done now"));
    StatusResult result = removeCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testNameNotInUse() {
    try {
      removeCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag hello does not exist", e.getMessage());
    }
  }

  @Test
  public void testRemoveCorrect() {
    model.createLayer("hello");
    assertEquals(1, model.getAllLayers().size());
    assertEquals("hello", model.getAllLayers().get(0).getName());
    assertTrue(model.getAllLayers().get(0).getVisibility());
    assertEquals(null, model.getAllLayers().get(0).getPhoto());

    StatusResult result = removeCommand.run(list);
    assertEquals(0, model.getAllLayers().size());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }
}
