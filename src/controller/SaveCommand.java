package controller;

import static controller.actions.ActionStatus.All_GOOD;
import static controller.actions.ActionStatus.SAVE;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that saves an entire layered photo from a model.
 */
public class SaveCommand extends AbstAction {

  /**
   * Initializes a new save command.
   *
   * @param model the model that the command manipulates.
   */
  public SaveCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new save command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public SaveCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    List<String> results = LayerPhotoController.splitFileName(inputWithoutCommand(input));
    String fileName = results.get(0);
    String format = results.get(1);

    return new StatusResult(Status.EXPORT, fileName, format);
  }

  @Override
  public String toString() {
    return "save";
  }

  @Override
  public ActionDetails applyAction() {
    String path = frameView.getPath(false);
    if (path != null) {
      return new ActionDetails(SAVE, path, null);
    } else {
      return new ActionDetails(All_GOOD, null, null);
    }
  }
}
