package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.ExportPair;
import view.IFrameView;

/**
 * Represents a command that exports an image from a layered photo model.
 */
public class ExportCommand extends AbstAction {

  /**
   * Initializes a new export command.
   *
   * @param model the model that the command manipulates.
   */
  public ExportCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new export command with a view.
   *
   * @param model the model the command manipulates.
   * @param view  the view for the program.
   */
  public ExportCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    List<String> results = LayerPhotoController.splitFileName(inputWithoutCommand(input));
    String fileName = results.get(0);
    String format = results.get(1);
    ExtendedImageUtil.writePhoto(fileName, model.getTopMostVisibleLayer().getPhoto(),
        format, null);
    return new StatusResult(Status.SUCCESS, null, null);
  }

  @Override
  public String toString() {
    return "export";
  }

  @Override
  public ActionDetails applyAction() {
    ExportPair result = frameView.export();
    if (result != null) {
      ExtendedImageUtil.writePhoto(result.getKey(), model.getTopMostVisibleLayer().getPhoto(),
          result.getValue(), null);
    }
    return new ActionDetails(ActionStatus.All_GOOD, null, null);
  }
}
