import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static picture.PictureProcessing.saveZones;;

public class UserInterface extends JFrame{
  private JTextField pasteSourcePathOrTextField;
  private JButton generateColourZonesButton;
  private JButton generateSongButton;
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

    generateColourZonesButton = new JButton();
    generateColourZonesButton.setText("Generate Colour Zones");
    generateColourZonesButton.setSize(100, 50);
    generateColourZonesButton.addActionListener(new ButtonPressListener(pasteSourcePathOrTextField));
    panel.add(generateColourZonesButton);

    generateSongButton = new JButton();
    generateSongButton.setText("Generate Song");
    generateSongButton.setSize(100, 50);
    generateSongButton.addActionListener(new ButtonPressListener());
    panel.add(generateSongButton);

    setVisible(true);
  }

  public static void main(String[] args) {
    UserInterface app = new UserInterface();
  }
}
