package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that opens a saved layered photo and sets it as the layered photo in a
 * layered photo model.
 */
public class OpenCommand extends AbstAction {

  /**
   * Initializes a new open command.
   *
   * @param model the model that the command manipulates.
   */
  public OpenCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new open command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public OpenCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    return new StatusResult(Status.OPEN, inputWithoutCommand(input), null);
  }

  @Override
  public String toString() {
    return "open";
  }

  @Override
  public ActionDetails applyAction() {
    frameView.resetSelection();
    String openPath = frameView.getPath(true);
    if (openPath != null) {
      return new ActionDetails(ActionStatus.OPEN, openPath, null);
    } else {
      return new ActionDetails(ActionStatus.All_GOOD, null, null);
    }
  }
}
