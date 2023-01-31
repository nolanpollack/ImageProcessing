import controller.GUIController;
import controller.IGUIController;
import controller.IPhotoController;
import controller.LayerPhotoController;
import java.io.InputStreamReader;
import model.week3.ILayerModel2;
import model.week3.LayerModelImpl2;
import view.FrameViewImpl;
import view.IFrameView;
import view.ImageTextualView;

/**
 * The main class for the code.
 */
public class Main {

  /**
   * Initializes the controller and model and starts the script.
   *
   * @param args script to initialize with a script, text for a text-based interactive view, or
   *             interactive for an interactive GUI.
   */
  public static void main(String[] args) {
    ILayerModel2 model = new LayerModelImpl2();

    if (args.length == 0) {
      IFrameView view = new FrameViewImpl();
      IGUIController controller = new GUIController(model, view);
      controller.run();
    } else {
      for (String arg : args) {
        String[] openArg = arg.split(" ");
        switch (openArg[0]) {
          case "script":
            if (openArg.length != 2) {
              throw new IllegalArgumentException("Illegal script argument");
            }
            ImageTextualView view = new ImageTextualView();
            IPhotoController controller = new LayerPhotoController(model, view);
            controller.scriptWFile(openArg[1]);
            break;
          case "text":
            ImageTextualView viewText = new ImageTextualView();
            IPhotoController controllerText = new LayerPhotoController(model, viewText);
            controllerText.scriptInteractive(new InputStreamReader(System.in));
            break;
          case "interactive":

            IFrameView viewFrame = new FrameViewImpl();
            IGUIController controllerFrame = new GUIController(model, viewFrame);
            controllerFrame.run();
            break;
          default:
            throw new IllegalArgumentException("Illegal command-line argument.");
        }

      }
    }
  }

}
