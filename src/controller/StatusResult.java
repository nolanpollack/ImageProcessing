package controller;

/**
 * Data representation that, if necessary, will return the status of the running command, and a file
 * name and format type (only for save and open commands).
 */
public class StatusResult implements ITriplet<Status, String, String> {

  private final Status status;
  private final String filename;
  private final String formatType;

  /**
   * Initializes a new StatusResult.
   *
   * @param status     the status of the operation.
   * @param filename   the name of the file, without any filetype.
   * @param formatType the format type of the file.
   */
  public StatusResult(Status status, String filename, String formatType) {
    this.status = status;
    this.filename = filename;
    this.formatType = formatType;
  }

  @Override
  public Status getFirst() {
    return status;
  }

  @Override
  public String getSecond() {
    return filename;
  }

  @Override
  public String getThird() {
    return formatType;
  }
}
