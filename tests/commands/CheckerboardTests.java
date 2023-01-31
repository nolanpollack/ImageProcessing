package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.CheckerBoardCommand;
import controller.IPhotoCommand;
import controller.IPhotoController;
import controller.LayerPhotoController;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week1.CheckerboardImage;
import model.week1.IPhoto;
import model.week1.OGColor;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;
import view.IPhotoView;
import view.ImageTextualView;

/**
 * Class that holds the tests for the functionality of the CheckerboardCommand class and its
 * exceptions.
 */
public class CheckerboardTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand checkerBoardCommand = new CheckerBoardCommand(model);
  private List<String> list = new ArrayList<>(Arrays.asList("checkerboard", "1", "1", "255", "0",
      "0", "0", "255", "255", "255", "1"));
  private IPhotoView view = new ImageTextualView(System.out);
  private IPhotoController controller = new LayerPhotoController(model, view);

  @Test
  public void testCheckerCorrect() {
    model.createLayer("check");
    model.changeCurrent("check");
    IPhotoView view = new ImageTextualView(System.out);
    controller = new LayerPhotoController(model, view);
    StatusResult result = checkerBoardCommand.run(list);
    IPhoto photo = new CheckerboardImage(1, 1, 255,
        new OGColor(0, 0, 0), new OGColor(255, 255, 255), 1);
    IPhoto expected = model.getLayer("check").getPhoto();
    assertTrue(expected.equals(photo));
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testCheckerIncorrect() {
    model.createLayer("check");
    model.changeCurrent("check");
    IPhotoView view = new ImageTextualView(System.out);
    controller = new LayerPhotoController(model, view);
    list = new ArrayList<>(Arrays.asList("checkerboard bleh"));
    StatusResult result = checkerBoardCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }
}
