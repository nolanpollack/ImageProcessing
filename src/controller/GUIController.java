package controller;

import static controller.actions.ActionStatus.OPEN;
import static controller.actions.ActionStatus.SAVE;
import static controller.actions.ActionStatus.SCRIPT;

import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import controller.actions.IAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a controller with support for a GUI.
 */
public class GUIController extends LayerPhotoController implements IGUIController, ActionListener,
    ListSelectionListener {

  /**
   * The view for this program.
   */
  private final IFrameView frameView;

  /**
   * The model for this program.
   */
  private final ILayerModel2 model;

  /**
   * A map containing actions this controller supports with string tags.
   */
  protected Map<String, IAction> actionMap;

  /**
   * Initializes a new GUIController. Registers this as an EventListener in the view and sets the
   * actions.
   *
   * @param model the model the controller applies actions to.
   * @param view  the view that displays information and contains functionality to call commands.
   */
  public GUIController(ILayerModel2 model, IFrameView view) {
    super(model, view);
    this.model = model;
    this.frameView = view;
    frameView.registerViewEventListener(this);
    frameView.registerViewListEventListener(this);
    actionMap = setActions();
  }

  /**
   * Sets the map of possible actions.
   *
   * @return a Map containing all of the IActions and a String with the name of the action.
   */
  protected Map<String, IAction> setActions() {
    Map<String, IAction> result = new HashMap<>();
    result.put("Blur", new BlurCommand(model, frameView));
    result.put("Sharpen", new SharpenCommand(model, frameView));
    result.put("Grayscale", new GrayscaleCommand(model, frameView));
    result.put("Sepia", new SepiaCommand(model, frameView));
    result.put("Save", new SaveCommand(model, frameView));
    result.put("Open", new OpenCommand(model, frameView));
    result.put("Checkerboard", new CheckerBoardCommand(model, frameView));
    result.put("Export", new ExportCommand(model, frameView));
    result.put("Load", new LoadCommand(model, frameView));
    result.put("Reset", new ResetCommand(model, frameView));
    result.put("Remove", new RemoveCommand(model, frameView));
    result.put("Create", new CreateCommand(model, frameView));
    result.put("Make visible", new VisibleCommand(model, frameView));
    result.put("Make invisible", new InvisibleCommand(model, frameView));
    result.put("Mosaic", new MosaicCommand(model, frameView));
    result.put("Execute script", new ExecuteScriptCommand(model, frameView));
    return result;
  }

  @Override
  public void run() {
    //Run overrides the run in the LayerPhotoController that GuiController extends. However, the
    // program starts when the view is initialized, not when run is called, so run only exists to
    // stop the program from doing two things at once.
  }

  @Override
  public BufferedImage handleReloadEvent() {
    try {
      return model.getTopmostBuffered();
    } catch (IllegalArgumentException e) {
      //Returns a blank BufferedImage if the model cannot retrieve topmost layer. Usually happens
      // when there is no image.
      return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    IAction action = actionMap.get(e.getActionCommand());
    try {
      ActionDetails status = action.applyAction();
      if (status.getFirst() == ActionStatus.RELOAD) {
        frameView.reloadImage(this.handleReloadEvent());
      } else if (status.getFirst() == SAVE) {
        frameView.reloadImage(this.handleReloadEvent());
        this.exportLayeredPhoto(status.getSecond(), "png");
      } else if (status.getFirst() == OPEN) {
        frameView.reloadImage(this.handleReloadEvent());
        model.changeMultiLayeredImage(this.importLayeredPhoto(status.getSecond()));
        frameView.updateSelection(model.getAllLayers());
        frameView.reloadImage(this.handleReloadEvent());
      } else if (status.getFirst() == SCRIPT) {
        scriptWFile(status.getSecond());
        frameView.updateSelection(model.getAllLayers());
        frameView.reloadImage(this.handleReloadEvent());
      }
    } catch (IllegalArgumentException exception) {
      frameView.renderErrorMessage(exception.getMessage());
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // This gets us the string value that's currently selected
    String selected = frameView.getChosenCurrent();
    model.changeCurrent(selected);
  }
}
