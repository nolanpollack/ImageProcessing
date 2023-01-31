package controller;

import java.util.List;

/**
 * Represents a command function object to be used with a photo processing controller.
 */
public interface IPhotoCommand {

  /**
   * Runs this command by parsing a list of inputs.
   *
   * @param input the list of inputs to be parsed.
   * @return a StatusResult representing whether the method worked successfully, received a quit,
   *     or had invalid inputs. For some of the methods, a filename and format in string form needs
   *     to be passed to the controller. Otherwise, the second and third parts of the StatusResult
   *     will be null.
   */
  public StatusResult run(List<String> input);
}
