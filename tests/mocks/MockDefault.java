package mocks;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 * Mock to test functionality of the code. 
 */
public class MockDefault extends DefaultListModel<String> {

  private List<String> list;
  private StringBuilder log;

  public MockDefault(StringBuilder log) {
    this.list = new ArrayList<>();
    this.log = log;
  }

  @Override
  public boolean removeElement(Object obj) {
    try {
      list.remove((String) (obj));
      log.append("Object removed: " + (String) (obj));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public void addElement(String element) {
    list.add(element);
    log.append("added: " + element);
  }

  @Override
  public void removeAllElements() {
    list.clear();
    log.append(list);
  }
}
