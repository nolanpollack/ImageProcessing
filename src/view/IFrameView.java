package view;


import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuListener;
import model.week2.ILayer;

/**
 * Represents a view using JFrame for this application.
 */
public interface IFrameView extends IPhotoView {

  /**
   * Registers a ListSelectionListener to each ViewListEvent.
   *
   * @param e the ListSelectionSelecter being added to the listOfLayers.
   */
  public void registerViewListEventListener(ListSelectionListener e);

  /**
   * Registers view event listeners for each button in the view.
   *
   * @param e the ActionListener for the button.
   */
  public void registerViewEventListener(ActionListener e);

  /**
   * Gives options to export an image.
   *
   * @return an ExportPair containing the file location and type for the photo being exported.
   */
  public ExportPair export();

  /**
   * Adds a layer to the view.
   *
   * @param name name of the layer being added.
   */
  public void addElement(String name);

  /**
   * Removes a layer from the view.
   *
   * @param removingLayer name of the layer being removed.
   */
  public void removeElement(String removingLayer);

  /**
   * Gets the loaded photo from the controller.
   *
   * @return the path of a photo being loaded as a string.
   */
  public String getLoadedPhoto();

  /**
   * Prompts user to give path when opening or saving a file.
   *
   * @param open true if opening, false if saving.
   * @return the path
   */
  public String getPath(boolean open);

  /**
   * Gets the chosen layer from the list of layers.
   *
   * @return a string with the name of the currently selected layer or null if there is none.
   */
  public String getChosenCurrent();

  /**
   * Prompts user for a file to open a script.
   *
   * @return a string with the absolute file path of the file containing the script.
   */
  public String getScriptFile();

  /**
   * Resets the layered photo so a new one can be used.
   */
  public void resetSelection();

  /**
   * Updates the current selected layer.
   *
   * @param layers the list of layers in the view.
   */
  public void updateSelection(List<ILayer> layers);

  /**
   * Reloads the visible image to a new BufferedImage.
   *
   * @param image the image to be displayed in the view.
   */
  public void reloadImage(BufferedImage image);

  /**
   * Displays a pop up warning the user that they have caused an error.
   *
   * @param message the message describing the error to the user.
   */
  public void renderErrorMessage(String message);

  /**
   * Gets a color from a JFrame color selector.
   *
   * @return a color selected by the user.
   */
  public Color getColor();

  /**
   * Registers a menu listener for the menus.
   *
   * @param e the MenuListener to be registered.
   */
  public void registerMenuListener(MenuListener e);
}
