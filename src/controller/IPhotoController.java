package controller;

import java.util.List;
import model.week2.ILayer;

/**
 * Represents a controller for a photo processing application.
 */
public interface IPhotoController {

  /**
   * Runs the script for this IController from a given file.
   *
   * @param filename the file the script is being read from.
   */
  public void scriptWFile(String filename);

  /**
   * Runs the script from a given readable.
   *
   * @param rd the readable containing the script with commands for the controller.
   */
  public void scriptInteractive(Readable rd);

  /**
   * Imports a layered photo as a List of ILayers. Each layer is named the filename of the photo.
   *
   * @param fileLoc the name of the folder storing all of the photos in the layered photo. Each
   *                photo must have the same size and there must also be a text file in the folder
   *                containing the locations of each photo.
   * @return a list of the ILayers in a layered photo.
   */
  public List<ILayer> importLayeredPhoto(String fileLoc);

  /**
   * Exports a layered photo from the model. A folder is created in the default directory and for
   * each layer, an image is exported to the folder with the same name as the layer. Finally, a text
   * file is added containing the locations of each photo in the layer. The name of the text file is
   * the same as the layered photo.
   *
   * @param name   the name of the layered photo being saved.
   * @param format the format of the photos being exported.
   */
  public void exportLayeredPhoto(String name, String format);

  /**
   * Returns a list of the available commands in the controller.
   *
   * @return a list containing the commands that can be parsed as strings.
   */
  public List<String> availableCommands();
}
