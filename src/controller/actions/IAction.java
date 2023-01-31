package controller.actions;

import controller.IPhotoCommand;

/**
 * Represents an action for a photo processing view/command.
 */
public interface IAction extends IPhotoCommand {

  /**
   * Applies the action.
   *
   * @return an ActionDetails representing the state of the action.
   */
  public ActionDetails applyAction();

}
