package mocks;

import controller.ImageUtil;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Mock to test the buttons. 
 */
public class MockJButton extends JButton {

  private StringBuilder log;

  public MockJButton(StringBuilder sb) {
    ImageUtil.checkNull(sb, "log");
    this.log = sb;
  }

  @Override
  public void addActionListener(ActionListener l) {
    log.append(l.toString() + "\n");
  }
}
