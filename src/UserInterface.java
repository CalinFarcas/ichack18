import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static picture.PictureProcessing.saveZones;;

public class UserInterface extends JFrame{
  private JTextField pasteSourcePathOrTextField;
  private JButton firstButton;
  private JButton secondButton;
  private JPanel panel;

  private class ButtonPressListener implements ActionListener {

    private JTextField field;
    private int action;

    public ButtonPressListener(JTextField field) {
      this.field = field;
      this.action = 0;
    }

    public ButtonPressListener() {
      this.action =  1;
    }

      public void actionPerformed(ActionEvent e) {
        switch (action) {
          case 0: {
            saveZones(field.getText());
            break;
        }
          case 1: {
            //TODO song gen method
            break;
          }
          default: break;
      }
    }
  }

  public UserInterface() {
    setTitle("PICTURE THEMESONG GENERATOR!!!");
    setSize(600, 100);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new JPanel();
    getContentPane().add(panel);

    pasteSourcePathOrTextField = new JTextField();
    pasteSourcePathOrTextField.setColumns(20);
    panel.add(pasteSourcePathOrTextField);

    firstButton = new JButton();
    firstButton.setText("Generate Song");
    firstButton.setSize(100, 50);
    firstButton.addActionListener(new ButtonPressListener(pasteSourcePathOrTextField));
    panel.add(firstButton);

    secondButton = new JButton();
    secondButton.setText("Extra button???");
    secondButton.setSize(100, 50);
    secondButton.addActionListener(new ButtonPressListener());
    panel.add(secondButton);

    setVisible(true);
  }

  public static void main(String[] args) {
    UserInterface app = new UserInterface();
  }
}
