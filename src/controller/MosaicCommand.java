package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.util.List;
import javax.swing.JOptionPane;
import model.week1.BlurFilter;
import model.week1.IPhoto;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import model.week3.MosaicFilter;
import view.IFrameView;

/**
 * Represents a command called to apply a mosaic filter to a layer in the model.
 */
public class MosaicCommand extends AbstAction {

  /**
   * Initializes a MosaicCommand.
   *
   * @param model the model the blur filter is being applied to.
   */
  public MosaicCommand(ILayerModel model) {
    super(model);
  }

  /**
   * Initializes a new mosaic command.
   *
   * @param model the model that the command manipulates.
   * @param view  the view that interacts with the controller.
   */
  public MosaicCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
  }

  @Override
  public StatusResult run(List<String> input) {
    Status status = implementFilter(new BlurFilter(), input);
    return new StatusResult(status, null, null);
  }

  @Override
  public String toString() {
    return "mosaic";
  }

  @Override
  public ActionDetails applyAction() {
    String seeds = JOptionPane.showInputDialog("Enter number of seeds in mosaic");
    try {
      IPhoto mosaicPhoto = model.applyFilter(new MosaicFilter(Integer.parseInt(seeds)));
      model.loadPhoto(mosaicPhoto);
      return new ActionDetails(ActionStatus.RELOAD, null, null);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Seeds must be a number");
    }
  }
}
