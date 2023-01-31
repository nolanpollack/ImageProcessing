package model.week1;

/**
 * Representation of a color that handles RGB values. The maximum value of the RGB values are not
 * documented in the color itself, but the photo that uses the color.
 */
public interface IColor {

  /**
   * Produces the value of the R channel for this color.
   *
   * @return the integer R channel value
   */
  public int getR();

  /**
   * Produces the value of the G channel for this color.
   *
   * @return the integer G channel value
   */
  public int getG();

  /**
   * Produces the value of the B channel for this color.
   *
   * @return the integer B channel value
   */
  public int getB();

  /**
   * Creates a new IColor.
   *
   * @return a new IColor
   */
  public IColor clone();


}
