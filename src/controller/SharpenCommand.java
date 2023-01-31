package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week1.IPhoto;
import model.week1.SharpenFilter;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that applies a sharpen filter to the currently selected layer in the layered
 * photo model.
 */
public class SharpenCommand extends AbstAction {

  /**
   * Initializes a new sharpen command.
   *
   * @param model the model that the command manipulates.
   */
  public SharpenCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new sharpen command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public SharpenCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = implementFilter(new SharpenFilter(), input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "sharpen";
  }

  @Override
  public ActionDetails applyAction() {
    IPhoto sepiaPhoto = model.applyFilter(new SharpenFilter());
    model.loadPhoto(sepiaPhoto);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
