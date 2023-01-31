package controller;

import java.util.List;
import model.week1.IFilter;
import model.week1.IPhoto;
import model.week2.ILayerModel;

/**
 * Represents an abstract commmand to be applied for an image processing controller.
 */
public abstract class AbstPhotoCommand implements IPhotoCommand {

  /**
   * The model being used in the controller for this command to call.
   */
  protected final ILayerModel model;


  /**
   * Constructs this photo command.
   *
   * @param model the model being used by the controller.
   */
  protected AbstPhotoCommand(ILayerModel model) {
    ImageUtil.checkNull(model, "model");
    this.model = model;
  }

  /**
   * Checks whether the command is equal and quits if q is parsed from a list of inputs.
   *
   * @param input the list of inputs from one command.
   * @return a Status representing whether the command was inputted correctly or whether the user
   *     has requested to quit.
   */
  protected Status getNextWord(List<String> input) {
    if (input.size() == 2) {
      return Status.SUCCESS;
    } else {
      return Status.INVALID_INPUT;
    }
  }

  /**
   * Converts a command from a script, separated by spaces, as one string without the first word.
   *
   * @param input the list of strings used in the command.
   * @return a string with every String from input except the first one, separated by spaces.
   */
  protected String inputWithoutCommand(List<String> input) {
    if (input == null || input.size() < 2) {
      throw new IllegalArgumentException("Illegal input");
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 1; i < input.size() - 1; i++) {
      builder.append(input.get(i) + " ");
    }
    builder.append(input.get(input.size() - 1));
    return builder.toString();
  }

  /**
   * Applies a filter to a layer in the model.
   *
   * @param filter the filter being applied to the model.
   * @param input  the command
   * @return a Status representing whether the given command was valid. For a filter command, it is
   *     only valid if the command has the name of the filter and nothing else.
   */
  protected Status implementFilter(IFilter filter, List<String> input) {
    if (input.size() == 1) {
      IPhoto photo = model.applyFilter(filter);
      model.loadPhoto(photo);
      return Status.SUCCESS;
    } else {
      return Status.INVALID_INPUT;
    }
  }

  /**
   * Alters the visibility of the current ILayer in the model.
   *
   * @param change whether the layer is being made visible or not.
   * @param input  the command called  separated as a list of strings.
   * @return A Status representing whether the input was formatted correctly.
   */
  protected Status alterVisibility(boolean change, List<String> input) {
    if (input.size() == 1) {
      model.changeVisibility(change);
      return Status.SUCCESS;
    } else {
      return Status.INVALID_INPUT;
    }
  }
}
