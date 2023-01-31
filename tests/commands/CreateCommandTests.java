package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.CreateCommand;
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
 * Class that holds the tests for the functionality of the CreateCommand class and its exceptions.
 */
public class CreateCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand createCommand = new CreateCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("create", "hello"));

  @Test
  public void testToString() {
    assertEquals("create", createCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("create"));
    StatusResult result = createCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("create done now"));
    StatusResult result = createCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testNameAlreadyInUse() {
    model.createLayer("hello");
    try {
      createCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag is already in use", e.getMessage());
    }
  }

  @Test
  public void testCreateCorrect() {
    StatusResult result = createCommand.run(list);
    assertEquals(1, model.getAllLayers().size());
    assertEquals("hello", model.getAllLayers().get(0).getName());
    assertTrue(model.getAllLayers().get(0).getVisibility());
    assertEquals(null, model.getAllLayers().get(0).getPhoto());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }
}
