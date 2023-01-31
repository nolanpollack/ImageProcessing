package controller;

import static controller.LayerPhotoController.splitFileName;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week1.IPhoto;
import model.week1.ImageCreator.PhotoType;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that loads a photo as the current in a layered photo model.
 */
public class LoadCommand extends AbstAction {

  /**
   * Initializes a new load command.
   *
   * @param model the model that the command manipulates.
   */
  public LoadCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new load command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public LoadCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    List<String> results = splitFileName(inputWithoutCommand(input));
    String fileName = results.get(0);
    String format = results.get(1);
    IPhoto photo = ExtendedImageUtil.readPhoto(fileName, PhotoType.SIMPLE_PHOTO, format);
    model.loadPhoto(photo);
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "load";
  }

  @Override
  public ActionDetails applyAction() {
    String name = frameView.getLoadedPhoto();
    if (name != null) {
      List<String> result = splitFileName(name);
      String filename = result.get(0);
      String format = result.get(1);
      IPhoto photo = ExtendedImageUtil.readPhoto(filename, PhotoType.SIMPLE_PHOTO, format);
      model.loadPhoto(photo);
      return new ActionDetails(ActionStatus.RELOAD, null, null);
    } else {
      return new ActionDetails(ActionStatus.All_GOOD, null, null);
    }
  }
}
