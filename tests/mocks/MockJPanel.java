package mocks;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Mock to test functionality of the JPanels. 
 */
public class MockJPanel extends JPanel {

  private List<String> items;
  private StringBuilder log;

  public MockJPanel(StringBuilder log) {
    this.items = new ArrayList<>();
    this.log = log;
  }

  @Override
  public void removeAll() {
    items.clear();
    log.append(items);
  }
}
