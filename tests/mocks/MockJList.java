package mocks;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

/**
 * Mock to test the JLists. 
 */
public class MockJList extends JList<String> {

  private StringBuilder log;
  private List<String> items;
  private String current;

  /**
   * Initializes a new MockJList.
   * 
   * @sb the StringBuilder to test the list. 
   */
  public MockJList(StringBuilder sb) {
    ImageUtil.checkNull(sb, "log");
    this.log = sb;
    this.items = new ArrayList<>(Arrays.asList("hello", "whatever", "done"));
    this.current = null;
  }

  /**
   * Tests functionality of the chooseCurrent method. 
   * 
   * @index the index of the layer being chosen. 
   */
  public void chooseCurrent(int index) {
    if (index < 0 || index > 2) {
      this.current = null;
    } else {
      this.current = items.get(index);
    }
  }

  @Override
  public void addListSelectionListener(ListSelectionListener listener) {
    log.append(listener.toString() + "\n");
  }

  @Override
  public String getSelectedValue() {
    return current;
  }

  @Override
  public void remove(int index) {
    try {
      items.remove(index);
      log.append("Removed");
    } catch (ArrayIndexOutOfBoundsException e) {
      log.append("invalid index");
    }
  }
}
