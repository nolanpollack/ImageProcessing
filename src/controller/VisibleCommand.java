package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that makes the currently selected layer in a layered photo model visible.
 */
public class VisibleCommand extends AbstAction {

  /**
   * Initializes a new visible command.
   *
   * @param model the model that the command manipulates.
   */
  public VisibleCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new visible command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public VisibleCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = alterVisibility(true, input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "visible";
  }

  @Override
  public ActionDetails applyAction() {
    model.changeVisibility(true);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
