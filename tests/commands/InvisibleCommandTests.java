package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import controller.IPhotoCommand;
import controller.InvisibleCommand;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality of the InvisibleCommand class and its
 * exceptions.
 */
public class InvisibleCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand invisibleCommand = new InvisibleCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("invisible"));

  @Test
  public void testToString() {
    assertEquals("invisible", invisibleCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      invisibleCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testVisibleCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    StatusResult result = invisibleCommand.run(list);

    assertFalse(model.getLayer("first").getVisibility());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testVisibleCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("invisible", "goodbye"));
    StatusResult result = invisibleCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

}
