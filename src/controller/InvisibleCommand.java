package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that makes the current selected layer in a layered photo model invisible.
 */
public class InvisibleCommand extends AbstAction {

  /**
   * Initializes a new invisible command.
   *
   * @param model the model that the command manipulates.
   */
  public InvisibleCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new invisible command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view of the program.
   */
  public InvisibleCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = alterVisibility(false, input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "invisible";
  }

  @Override
  public ActionDetails applyAction() {
    model.changeVisibility(false);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
