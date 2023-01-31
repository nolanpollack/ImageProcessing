package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import view.IFrameView;

/**
 * Represents a command that executes a script for a photo processing utility.
 */
public class ExecuteScriptCommand extends AbstAction {

  /**
   * Initializes a new ExecuteScriptCommand.
   *
   * @param model the model the script is manipulating.
   * @param view  the view used to open the script.
   */
  protected ExecuteScriptCommand(ILayerModel model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    //this returns null since the original controller has a method that directly
    //executes the scripted file
    return null;
  }

  @Override
  public ActionDetails applyAction() {
    String name = frameView.getScriptFile();
    if (name != null) {
      return new ActionDetails(ActionStatus.SCRIPT, name, null);
    } else {
      return new ActionDetails(ActionStatus.All_GOOD, null, null);
    }
  }
}
