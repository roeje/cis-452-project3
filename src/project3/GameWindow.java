package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by roeje on 4/2/17.
 */
public class GameWindow extends JFrame {
    private static final int GRID_WIDTH = 5;
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

    private int numHoles;
    private int numActive;

    private final List<JButton> buttonList = new ArrayList<JButton>();

    public GameWindow() {

        labelStartError.setVisible(false);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelStartError.setVisible(false);
                try {
                    numHoles = Integer.parseInt(textFieldHoles.getText());
                    numActive = Integer.parseInt(textFieldMoles.getText());
                    initializeController(numHoles, numActive);
                    createGrid();

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

    private JButton getGridButton(int x, int y) {

        int index = x * GRID_WIDTH + y;
        return buttonList.get(index);
    }

    private JButton createButton(int row, int col) {
        final JButton button  = new JButton("row" + row+",col"+ col);
        button.setName(row + "," + col);
//        button.putClientProperty("row", row);
//        button.putClientProperty("col", col);
        button.addActionListener(new ActionListener() {

            /*Action for grid button click*/
            @Override
            public void actionPerformed(ActionEvent e) {
//                String name = ((JButton)e.getSource()).getName();
//                String[] tmp = name.split(",");
//                int row_num = Integer.parseInt(tmp[0]);
//                int col_num = Integer.parseInt(tmp[1]);
//                System.out.println("Button Clicked: " + row_num + "," + col_num);
                int gridStatus = gameEngine.data.getStatus(row * GRID_WIDTH + col);
                if (gridStatus == 0) {
                    System.out.println("Button is not active: " + row + "," + col);
                } else {
                    System.out.println("Button Clicked: " + row + "," + col);
                    gameEngine.data.setActive(row * GRID_WIDTH + col);
                    System.out.println(gameEngine.data.getStatus(row * GRID_WIDTH + col));
                }

            }
        });
        return button;
    }

    private void createGrid() {
        int row = 0;
        int col = 0;
        JPanel grid = new JPanel(new GridLayout(-1, GRID_WIDTH));
        for (int i = 0; i < numHoles; i++) {
            col = i % GRID_WIDTH;
            if (col == 0 && i != 0) {
                row++;
            }
            JButton button = createButton(row, col);
            buttonList.add(button);
            grid.add(button);
        }
        panelGameGrid.add(grid);

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





