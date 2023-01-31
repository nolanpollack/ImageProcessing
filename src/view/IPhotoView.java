package view;

/**
 * A view for a layered photo utility.
 */
public interface IPhotoView {

  /**
   * Renders a message.
   *
   * @param message the message to be rendered.
   * @throws IllegalStateException if the message is illegal.
   */
  public void renderMessage(String message) throws IllegalStateException;
}
