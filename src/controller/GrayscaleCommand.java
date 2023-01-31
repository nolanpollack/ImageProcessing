package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week1.GrayscaleColorTransformation;
import model.week1.IPhoto;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that applies a grayscale filter to a photo in a layered photo model.
 */
public class GrayscaleCommand extends AbstAction {

  /**
   * Initializes a new grayscale command.
   *
   * @param model the model that the command manipulates.
   */
  public GrayscaleCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new grayscale command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view used to call the command.
   */
  public GrayscaleCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = implementFilter(new GrayscaleColorTransformation(), input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "grayscale";
  }

  @Override
  public ActionDetails applyAction() {
    IPhoto sharPhoto = model.applyFilter(new GrayscaleColorTransformation());
    model.loadPhoto(sharPhoto);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
