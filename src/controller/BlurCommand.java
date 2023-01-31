package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week1.BlurFilter;
import model.week1.IPhoto;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command called to apply a blur filter to a layer in the model.
 */
public class BlurCommand extends AbstAction {

  /**
   * Initializes a BlurCommand.
   *
   * @param model the model the blur filter is being applied to.
   */
  public BlurCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a BlurCommand with a view.
   *
   * @param model model of the program.
   * @param view  view of the program.
   */
  public BlurCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = implementFilter(new BlurFilter(), input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "blur";
  }

  @Override
  public ActionDetails applyAction() {
    IPhoto blurPhoto = model.applyFilter(new BlurFilter());
    model.loadPhoto(blurPhoto);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
