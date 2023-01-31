package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week1.IPhoto;
import model.week1.SepiaColorTransformation;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that applies a sepia filter to the currently selected layer in a layered
 * photo model.
 */
public class SepiaCommand extends AbstAction {

  /**
   * Initializes a new sepia command.
   *
   * @param model the model that the command manipulates.
   */
  public SepiaCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new sepia command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public SepiaCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = implementFilter(new SepiaColorTransformation(), input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "sepia";
  }

  @Override
  public ActionDetails applyAction() {
    IPhoto grayPhoto = model.applyFilter(new SepiaColorTransformation());
    model.loadPhoto(grayPhoto);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
