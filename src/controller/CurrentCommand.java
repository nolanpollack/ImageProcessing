package controller;

import java.util.List;
import model.week2.ILayerModel;

/**
 * Represents a command that changes the current selected layer in a layered photo model.
 */
public class CurrentCommand extends AbstPhotoCommand {

  /**
   * Initializes a new current command.
   *
   * @param model the model that the command manipulates.
   */
  public CurrentCommand(ILayerModel model) {
    super(model);
  }

  @Override
  public StatusResult run(List<String> input) {
    model.changeCurrent(inputWithoutCommand(input));
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "current";
  }
}
