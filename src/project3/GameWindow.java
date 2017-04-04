package project3;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by roeje on 4/2/17.
 */
public class GameWindow extends JFrame {
    private static GameEngine gameEngine;
    private JPanel panelMain;
    private JLabel WindowHeader;
    private JLabel labelHoleField;
    private JTextField textFieldHoles;
    private JLabel labelMoleField;
    private JTextField textFieldMoles;
    private JPanel panelGameGrid;
    private JButton buttonStart;
    private JButton quitButton;
    private JLabel labelNumberHit;
    private JLabel labelNumberMissed;
    private JButton buttonTest1;
    private JLabel labelStartError;

    public GameWindow() {

        labelStartError.setVisible(false);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelStartError.setVisible(false);
                try {
                    int numHoles = Integer.parseInt(textFieldHoles.getText());
                    int numActive = Integer.parseInt(textFieldMoles.getText());
                    initializeController(numHoles, numActive);
                } catch (NumberFormatException error) {
                    labelStartError.setVisible(true);
                }

            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting Game");
                gameEngine.shutdown();
                System.exit(0);
            }
        });
        buttonTest1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                gameEngine.shutdown();
                System.out.println("Exiting Game");
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void initializeController(int numHoles, int numActive) {
        gameEngine = new GameEngine(numHoles, numActive);
        gameEngine.start();

    }

    public static void main(String[] args) {




        JFrame mainFrame = new JFrame("GameWindow");
        mainFrame.setContentPane(new GameWindow().panelMain);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}





