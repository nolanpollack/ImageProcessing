package controller;

import controller.actions.AbstAction;
import controller.actions.ActionDetails;
import controller.actions.ActionStatus;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import model.week1.CheckerboardImage;
import model.week1.IColor;
import model.week1.IPhoto;
import model.week1.OGColor;
import model.week2.ILayerModel;
import model.week3.ILayerModel2;
import view.IFrameView;

/**
 * Represents a command that creates a checkerboard image in a layered photo model.
 */
public class CheckerBoardCommand extends AbstAction {

  /**
   * The amount of rows in the checkerboard.
   */
  private int row;
  /**
   * The amount of columns in the checkerboard.
   */
  private int column;
  /**
   * The first color of a tile in the checkerboard.
   */
  private IColor color1;
  /**
   * The color of alternating tiles in the checkerboard.
   */
  private IColor color2;
  /**
   * The max RGB value for a pixel in the checkerboard image.
   */
  private int max;
  /**
   * The size of one tile in the checkerboard in pixels.
   */
  private int size;

  /**
   * Initializes a new checkerboard command and sets all values to a default value.
   *
   * @param model the model the command is applied to.
   */
  public CheckerBoardCommand(ILayerModel model) {
    super(model);
    this.row = 0;
    this.column = 0;
    this.color1 = null;
    this.color2 = null;
    this.max = 0;
    this.size = 0;
  }

  /**
   * Initializes a new checkerboard command and sets all values to a default value.
   *
   * @param model the model the command is applied to.
   * @view the view calling the command. 
   */
  public CheckerBoardCommand(ILayerModel2 model, IFrameView view) {
    super(model, view);
    this.row = 0;
    this.column = 0;
    this.color1 = null;
    this.color2 = null;
    this.max = 0;
    this.size = 0;
  }

  @Override
  public StatusResult run(List<String> input) {
    if (input.size() == 11) {
      try {
        input.remove(0);
        getRow(input);
        return new StatusResult(Status.SUCCESS, null, null);
      } catch (IllegalArgumentException e) {
        return new StatusResult(Status.INVALID_INPUT, null, null);
      }
    } else {
      return new StatusResult(Status.INVALID_INPUT, null, null);
    }
  }

  /**
   * Gets the size of the checkerboard being created.
   *
   * @param input the list of inputs used to call the command.
   */
  private void getSize(List<String> input) {
    if (getNextInt(input.get(9))) {
      this.size = Integer.parseInt(input.get(9));
      IPhoto board = new CheckerboardImage(row, column, max, color1, color2, size);
      model.loadPhoto(board);
    } else {
      throw new IllegalArgumentException("invalid size");
    }
  }

  /**
   * Gets the max RGB value of a pixel in the checkerboard being created.
   *
   * @param input the list of inputs used to call the command.
   */
  private void getMax(List<String> input) {
    if (getNextInt(input.get(2))) {
      this.max = Integer.parseInt(input.get(2));
      getColors(input);
    } else {
      throw new IllegalArgumentException("invalid max");
    }
  }

  /**
   * Gets the colors of the checkerboard being created.
   *
   * @param input the list of inputs used to call the command.
   */
  private void getColors(List<String> input) {
    if (getNextInt(input.get(3))
        && getNextInt(input.get(4))
        && getNextInt(input.get(5))
        && getNextInt(input.get(6))
        && getNextInt(input.get(7))
        && getNextInt(input.get(8))) {
      this.color1 =
          new OGColor(
              Integer.parseInt(input.get(3)),
              Integer.parseInt(input.get(4)),
              Integer.parseInt(input.get(5)));
      this.color2 =
          new OGColor(
              Integer.parseInt(input.get(6)),
              Integer.parseInt(input.get(7)),
              Integer.parseInt(input.get(8)));
      getSize(input);
    } else {
      throw new IllegalArgumentException("invalid color inputs");
    }
  }

  /**
   * Gets the number of columns in the checkerboard being created.
   *
   * @param input the list of inputs used to call the command.
   */
  private void getColumn(List<String> input) {
    if (getNextInt(input.get(1))) {
      this.column = Integer.parseInt(input.get(1));
      getMax(input);
    } else {
      throw new IllegalArgumentException("invalid column input");
    }
  }

  /**
   * Gets the number of rows in the checkerboard being created.
   *
   * @param input the list of inputs used to call the command.
   */
  private void getRow(List<String> input) {
    if (getNextInt(input.get(0))) {
      row = Integer.parseInt(input.get(0));
      getColumn(input);
    } else {
      throw new IllegalArgumentException("invalid row input");
    }
  }

  /**
   * Gets the next int in the command used to call this.
   *
   * @param input the command used to call this checkerboardcommand.
   */
  private boolean getNextInt(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Gets the next int in the command used to call this.
   *
   * @param input the command used to call this checkerboardcommand.
   */
  private int parseInt(String input, String message) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid " + message);
    }
  }

  @Override
  public String toString() {
    return "checkerboard";
  }

  @Override
  public ActionDetails applyAction() {
    //TODO is this too tied to the view because it uses JOptionPane? Would it be better to make an
    // ask user function or something? Kind of like the color.
    //Asks client for number of rows.
    row = parseInt(JOptionPane.showInputDialog("Number of rows"), "number of rows");
    //Asks client for number columns.
    column = parseInt(JOptionPane.showInputDialog("Number of columns"), "number of columns");
    //Gets a color from the view.
    Color col1 = frameView.getColor();
    color1 = new OGColor(col1.getRed(), col1.getGreen(), col1.getBlue());
    Color col2 = frameView.getColor();
    color2 = new OGColor(col2.getRed(), col2.getGreen(), col2.getBlue());
    //Asks client for size of a tile in pixels.
    size = parseInt(JOptionPane.showInputDialog("Size of a tile in pixels"), "size");
    IPhoto photo = new CheckerboardImage(row, column, 255, color1, color2, size);
    model.loadPhoto(photo);
    return new ActionDetails(ActionStatus.RELOAD, null, null);
  }
}
