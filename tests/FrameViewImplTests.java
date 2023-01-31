import static org.junit.Assert.assertEquals;

import controller.GUIController;
import java.awt.image.BufferedImage;
import mocks.MockFrameView;
import model.week3.ILayerModel2;
import model.week3.LayerModelImpl2;
import org.junit.Test;
import view.FrameViewImpl;
import view.IFrameView;

/**
 * Tests functionality of FrameViewImpl.
 */
public class FrameViewImplTests {

  private ILayerModel2 model = new LayerModelImpl2();
  private IFrameView view = new FrameViewImpl();
  private MockFrameView mockView;

  @Test
  public void testRegisterViewEventListener() {
    //I created a mock JButton that has the addActionListener method, and all it does
    //is append to a log what the action listener is
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.registerViewEventListener(new GUIController(model, view));
    assertEquals("GUI Controller\nGUI Controller\nGUI Controller\n", log.toString());
  }

  @Test
  public void testRegisterViewListEventListener() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.registerViewListEventListener(new GUIController(model, view));
    assertEquals("GUI Controller\n", log.toString());
  }

  @Test
  public void getChosenCurrentNull() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.chooseCurrent(10);
    assertEquals(null, mockView.getChosenCurrent());
  }

  @Test
  public void getChosenCurrentNonNull() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.chooseCurrent(1);
    assertEquals("whatever", mockView.getChosenCurrent());
  }

  @Test
  public void testReloadImage() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.reloadImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
    assertEquals("icon set to class java.awt.image.BufferedImage", log.toString());
  }

  @Test
  public void testResetSelection() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.resetSelection();
    assertEquals("reset has occurred", log.toString());
  }

  @Test
  public void testRemoveElement() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.removeElement("whatever");
    assertEquals("Removing layer: whatever", log.toString());
  }

  @Test
  public void testAddElement() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.addElement("whatever");
    assertEquals("Adding element: whatever", log.toString());
  }

  @Test
  public void testGetPathOpen() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.getPath(true);
    assertEquals("will show open button", log.toString());
  }

  @Test
  public void testGetPathSave() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.getPath(false);
    assertEquals("will show save button", log.toString());
  }

  @Test
  public void testExportJPG() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("jpg");
    mockView.export();
    assertEquals("the file type that has been chosen is jpg", log.toString());
  }

  @Test
  public void testExportPPM() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("ppm");
    mockView.export();
    assertEquals("the file type that has been chosen is ppm", log.toString());
  }

  @Test
  public void testExportPNG() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("png");
    mockView.export();
    assertEquals("the file type that has been chosen is png", log.toString());
  }

  @Test
  public void testGetLoadedPhotoJPG() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("jpg");
    assertEquals("jpg", mockView.getLoadedPhoto());
  }

  @Test
  public void testGetLoadedPhotoPNG() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("png");
    assertEquals("png", mockView.getLoadedPhoto());
  }

  @Test
  public void testGetLoadedPhotoPPM() {
    StringBuilder log = new StringBuilder();
    mockView = new MockFrameView(log);
    mockView.choosePhotoType("ppm");
    assertEquals("ppm", mockView.getLoadedPhoto());
  }
}
