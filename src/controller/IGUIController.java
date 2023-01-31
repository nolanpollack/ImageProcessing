package controller;

import java.awt.image.BufferedImage;

/**
 * A controller that handles a GUIView.
 */
public interface IGUIController extends IPhotoController {

  /**
   * Runs the controller.
   */
  public void run();

  /**
   * Handles a reload event from the view by reloading the topmost visible image when it needs to be
   * refreshed.
   *
   * @return a BufferedImage representing the topmost visible image in the model.
   */
  public BufferedImage handleReloadEvent();

}
