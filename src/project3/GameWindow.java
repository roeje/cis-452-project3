package project3;

import javax.swing.*;

/**
 * Created by roeje on 4/2/17.
 */
public class GameWindow {
    private JPanel panelMain;
    private JLabel WindowHeader;
    private JLabel labelHoleField;
    private JTextField textFieldHoles;
    private JLabel labelMoleField;
    private JTextField textFieldMoles;
    private JPanel panelGameGrid;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("GameWindow");
        mainFrame.setContentPane(new GameWindow().panelMain);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}





