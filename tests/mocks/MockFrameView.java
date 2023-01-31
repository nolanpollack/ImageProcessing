package mocks;

import controller.ImageUtil;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.event.ListSelectionListener;
import view.ExportPair;
import view.FrameViewImpl;

/**
 * Mock to test functionality of the FrameViewImpl. 
 */
public class MockFrameView extends FrameViewImpl {

  private StringBuilder log;
  private MockJButton button1;
  private MockJButton button2;
  private MockJButton button3;
  private MockJList list;
  private String photoType;

  /**
   * Initializes a new MockFrameView.
   *
   * @sb StringBuilder to test the view. 
   */
  public MockFrameView(StringBuilder sb) {
    ImageUtil.checkNull(sb, "log");
    this.log = sb;
    button1 = new MockJButton(this.log);
    button2 = new MockJButton(this.log);
    button3 = new MockJButton(this.log);
    list = new MockJList(this.log);
    photoType = null;
  }

  @Override
  public void registerViewEventListener(ActionListener e) {
    button1.addActionListener(e);
    button2.addActionListener(e);
    button3.addActionListener(e);
  }

  @Override
  public void registerViewListEventListener(ListSelectionListener e) {
    list.addListSelectionListener(e);
  }

  @Override
  public String getChosenCurrent() {
    if (this.list.getSelectedValue() != null) {
      return this.list.getSelectedValue();
    } else {
      //TODO: we want this to throw exception here no?
      return null;
    }
  }

  public void chooseCurrent(int index) {
    list.chooseCurrent(index);
  }

  @Override
  public void reloadImage(BufferedImage image) {
    log.append("icon set to " + image.getClass());
  }

  @Override
  public void resetSelection() {
    log.append("reset has occurred");
  }

  @Override
  public void removeElement(String removingLayer) {
    log.append("Removing layer: " + removingLayer);
  }

  @Override
  public void addElement(String removingLayer) {
    log.append("Adding element: " + removingLayer);
  }

  @Override
  public String getPath(boolean open) {
    if (open) {
      log.append("will show open button");
    } else {
      log.append("will show save button");
    }
    return null;
  }

  public void choosePhotoType(String type) {
    photoType = type;
  }

  @Override
  public ExportPair export() {
    log.append("the file type that has been chosen is " + photoType);
    return new ExportPair("default", photoType);
  }

  @Override
  public String getLoadedPhoto() {
    return photoType;
  }
}
