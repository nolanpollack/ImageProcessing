package view;

import java.io.IOException;

/**
 * Represents a textual view for a layered photo utility.
 */
public class ImageTextualView implements IPhotoView {

  private final Appendable ap;

  public ImageTextualView() {
    this.ap = System.out;
  }

  public ImageTextualView(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    if (message == null) {
      try {
        ap.append("");
      } catch (IOException e) {
        throw new IllegalStateException("could not write to appendable");
      }
    } else {
      try {
        ap.append(message);
      } catch (IOException e) {
        throw new IllegalStateException("could not write to appendable");
      }
    }
  }
}
