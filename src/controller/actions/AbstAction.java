package controller.actions;

import controller.AbstPhotoCommand;
import controller.ImageUtil;
import model.week2.ILayerModel;
import view.IFrameView;

/**
 * Represents an abstract action for a photo processing command.
 */
public abstract class AbstAction extends AbstPhotoCommand implements IAction {

  protected final IFrameView frameView;

  /**
   * Initializes a new AbstAction without a view.
   *
   * @param model the model commands are applied to.
   */
  protected AbstAction(ILayerModel model) {
    super(model);
    this.frameView = null;
  }

  /**
   * Initializes a new Abstract action with a view.
   *
   * @param model the model commands are applied to.
   * @param view  the view of the program.
   */
  protected AbstAction(ILayerModel model, IFrameView view) {
    super(model);
    ImageUtil.checkNull(model, "model");
    ImageUtil.checkNull(view, "view");
    frameView = view;
  }
}
