package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import javax.swing.JOptionPane;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that removes a layer from a layered photo model.
 */
public class RemoveCommand extends AbstAction {

  /**
   * Initializes a new remove command.
   *
   * @param model the model that the command manipulates.
   */
  public RemoveCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new remove command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public RemoveCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    model.removePhoto(inputWithoutCommand(input));
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "remove";
  }

  @Override
  public ActionDetails applyAction() {
    String removingLayer = JOptionPane.showInputDialog("Enter layer name to remove");
    if (removingLayer != null) {
      model.removePhoto(removingLayer);
      frameView.removeElement(removingLayer);
      return new ActionDetails(ActionStatus.RELOAD, null, null);
    }
    return new ActionDetails(ActionStatus.All_GOOD, null, null);
  }
}
