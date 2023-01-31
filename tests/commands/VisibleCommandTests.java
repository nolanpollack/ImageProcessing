package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.IPhotoCommand;
import controller.Status;
import controller.StatusResult;
import controller.VisibleCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality of the VisibleCommand class and its exceptions.
 */
public class VisibleCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand visibleCommand = new VisibleCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("visible"));

  @Test
  public void testToString() {
    assertEquals("visible", visibleCommand.toString());
  }

  @Test
  public void testRunOnNoCurrentLayer() {
    model.createLayer("Hello");
    try {
      visibleCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("you have not selected a current layer", e.getMessage());
    }
  }

  @Test
  public void testVisibleCommandCorrect() {
    model.createLayer("first");
    model.changeCurrent("first");
    model.changeVisibility(false);
    assertFalse(model.getLayer("first").getVisibility());
    StatusResult result = visibleCommand.run(list);

    assertTrue(model.getLayer("first").getVisibility());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testVisibleCommandTooManyInputs() {
    list = new ArrayList<>(Arrays.asList("visible", "goodbye"));
    StatusResult result = visibleCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

}
