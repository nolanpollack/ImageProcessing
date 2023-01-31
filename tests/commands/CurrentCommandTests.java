package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.CurrentCommand;
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
 * Class that holds the tests for the functionality of the CurrentCommand class and its exceptions.
 */
public class CurrentCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand currentCommand = new CurrentCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("current", "hello"));

  @Test
  public void testToString() {
    assertEquals("current", currentCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("current"));
    StatusResult result = currentCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("current done now"));
    StatusResult result = currentCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testNameNotFound() {
    try {
      currentCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag hello does not exist", e.getMessage());
    }
  }

  @Test
  public void testCurrentCorrect() {
    model.createLayer("hello");
    StatusResult result = currentCommand.run(list);
    assertEquals("hello", model.getCurrent().getName());
    assertTrue(model.getCurrent().getVisibility());
    assertEquals(null, model.getCurrent().getPhoto());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

}
