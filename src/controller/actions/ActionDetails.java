package controller.actions;

import controller.ITriplet;
import controller.ImageUtil;

/**
 * Class used to be returned after an action holding details of the action.
 */
public class ActionDetails implements ITriplet<ActionStatus, String, String> {

  private final ActionStatus status;
  private final String name;
  private final String type;

  /**
   * Initializes a new ActionDetails.
   *
   * @param status the ActionStatus of the action.
   * @param name   name of the file if a file is being accessed.
   * @param type   file type of the file if a file is being accessed.
   */
  public ActionDetails(ActionStatus status, String name, String type) {
    ImageUtil.checkNull(status, "status");
    this.status = status;
    this.name = name;
    this.type = type;
  }

  @Override
  public ActionStatus getFirst() {
    return status;
  }

  @Override
  public String getSecond() {
    return name;
  }

  @Override
  public String getThird() {
    return type;
  }
}
