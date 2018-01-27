import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static picture.PictureProcessing.saveZones;;

public class TestGui extends JFrame{
  private JTextField pasteSourcePathOrTextField;
  private JButton generateColourZonesButton;
  private JPanel panel;

  private class ButtonPressListener implements ActionListener {

    private JTextField field;

    public ButtonPressListener(JTextField field) {
      this.field = field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      saveZones(field.getText());
    }
  }

  public TestGui() {
    setTitle("test");
    setSize(500, 300);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new JPanel();
    getContentPane().add(panel);
    pasteSourcePathOrTextField = new JTextField();
    pasteSourcePathOrTextField.setColumns(20);
    panel.add(pasteSourcePathOrTextField);
    generateColourZonesButton = new JButton();
    generateColourZonesButton.setText("Generate Colour Zones");
    generateColourZonesButton.setSize(100, 50);
    generateColourZonesButton.addActionListener(new ButtonPressListener(pasteSourcePathOrTextField));
    panel.add(generateColourZonesButton);
    setVisible(true);
  }

  public static void main(String[] args) {
    TestGui app = new TestGui();
  }
}
