package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command used to reset all layers on a layered photo model.
 */
public class ResetCommand extends AbstAction {

  /**
   * Constructs this photo command.
   *
   * @param model the model being used by the controller.
   */
  public ResetCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new reset command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public ResetCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    if (input.size() != 1) {
      return new StatusResult(Status.INVALID_INPUT, null, null);
    }
    model.reset();
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "reset";
  }

  @Override
  public ActionDetails applyAction() {
    model.reset();
    frameView.resetSelection();
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
