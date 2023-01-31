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
 * Represents a command that creates a new layer in a model.
 */
public class CreateCommand extends AbstAction {

  /**
   * Initializes a new create command object.
   *
   * @param model the photo processing model that the command is calling.
   */
  public CreateCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new create command object with a view.
   *
   * @param model the model the command is applied to.
   * @param view  the view displaying the program.
   */
  public CreateCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    model.createLayer(inputWithoutCommand(input));
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "create";
  }

  @Override
  public ActionDetails applyAction() {
    String name = JOptionPane.showInputDialog("Enter layer name");
    if (name != null) {
      model.createLayer(name);
      frameView.addElement(name);
    }
    return new ActionDetails(ActionStatus.All_GOOD, null, null);
  }
}
