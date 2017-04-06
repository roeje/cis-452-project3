package project3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CIS 452 - Project 3: Concurrency in Game Design
 *
 * GameWindow: View class that creates and updates the GUI, accepts user input, and displays the current state of the game board
 *
 * This class utilizes the Intellij UI builder library
 * so some of the UI elements are contained in a separate ".form" class
 *
 * @author  Jesse Roe
 * @version 04/1/2017
 */
public class GameWindow extends JFrame {

    /*Gui elements and global vars*/
    private static GameEngine gameEngine;
    private JPanel panelMain;
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
    private JLabel labelNumClicks;
    private JLabel labelMain;
    private JLabel labelMissedHits;
    private JTextField textFieldGameTime;
    private JLabel labelGameTime;
    private JLabel labelFieldWinner;
    private JTextField textFieldGridWidth;
    private JLabel labelGridWidth;

    private int numHoles;
    private int numActive;
    private int gameTimer;
    private int gridWidth;

    private List<JButton> buttonList = new ArrayList<JButton>();

    private final Image deathStar = ImageIO.read(getClass().getResource("/resources/death_star_100px.png"));
    private final Image warpHole = ImageIO.read(getClass().getResource("/resources/warp_hole_100px.png"));

    /**
     * Constructor for the GameWindow class
     * <p>
     *
     * @throws IOException
     * @return none
     */
    public GameWindow() throws IOException {

        labelStartError.setVisible(false);
        labelFieldWinner.setVisible(false);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelFieldWinner.setVisible(false);
                labelStartError.setVisible(false);
                try {
                    numHoles = Integer.parseInt(textFieldHoles.getText());
                    numActive = Integer.parseInt(textFieldMoles.getText());
                    gameTimer = Integer.parseInt(textFieldGameTime.getText());
                    gridWidth = Integer.parseInt(textFieldGridWidth.getText());
                    initializeController(numHoles, numActive);
                    createGrid();
                    startGameTimer();
                    startAutoRefresh();

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

    /**
     * Accessor for the buttonList vector of JButtons
     * <p>
     *
     * @param x, the row, y, the col
     * @return JButton
     */
    private JButton getGridButton(int x, int y) {

        /*Convert row and col to single int index for vector*/
        int index = x * gridWidth + y;
        return buttonList.get(index);
    }

    /**
     * Creates a single JButton and action listener based on a specified row and col from game grid
     * <p>
     *
     * @param row, col
     * @return JButton
     */
    private JButton createButton(int row, int col) {
        final JButton button = new JButton();
        button.setName(row + "," + col);
        button.setIcon(new ImageIcon(warpHole));
//        button.setSize(50, 50);
        button.setBackground(Color.black);
        button.addActionListener(new ActionListener() {

            /*Action for grid button click*/
            @Override
            public void actionPerformed(ActionEvent e) {
                int gridStatus = gameEngine.data.getStatus(row * gridWidth + col);
                gameEngine.data.incrementClicks();
                if (gridStatus == 0) {
                    System.out.println("Button is not active: " + row + "," + col);
                    gameEngine.data.incrementMisses();
                } else {
                    System.out.println("Button Clicked: " + row + "," + col);
                    gameEngine.data.setInactive(row * gridWidth + col);
                    gameEngine.data.incrementHits();
                    System.out.println(gameEngine.data.getStatus(row * gridWidth + col));
                }

            }
        });
        return button;
    }

    /**
     * Create game grid and buttons. Attach to game panel
     * <p>
     *
     * @param  none
     * @throws none
     * @return none
     */
    private void createGrid() {
        int row = 0;
        int col = 0;
        panelGameGrid.removeAll();
        buttonList = new ArrayList<JButton>();

        /*Create grid layout and attach each button*/
        JPanel grid = new JPanel(new GridLayout(-1, gridWidth));
        for (int i = 0; i < numHoles; i++) {
            col = i % gridWidth;
            if (col == 0 && i != 0) {
                row++;
            }
            JButton button = createButton(row, col);
            buttonList.add(button);
            grid.add(button);
        }
        panelGameGrid.add(grid);
    }

    /**
     * Method to refresh the button grid.
     * Grid is redrawn and stat labels are updated
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    private void refreshGridButtons() {

        /*Iterate over buttonList and update state of each button*/
        for(int i = 0; i < buttonList.size(); i++) {
            int status = gameEngine.data.getStatus(i);
            if(status == 0) {

                buttonList.get(i).setIcon(new ImageIcon(warpHole));
            } else {

                buttonList.get(i).setIcon(new ImageIcon(deathStar));
            }

            /*Update labels with stats from GameBoard*/
            labelNumberHit.setText("Number of hits: " + gameEngine.data.getNumHits());
            labelNumberMissed.setText("Number of misses: " + gameEngine.data.getNumMissClicks());
            labelNumClicks.setText("Number of clicks: " + gameEngine.data.getTotalClicks());
            labelMissedHits.setText("Missed Hits: " + gameEngine.data.getNumMissedHits());
        }
    }

    /**
     * Create controller instance with user defined values
     * <p>
     *
     * @throws none
     * @param numHoles, numActive
     * @return none
     */
    private void initializeController(int numHoles, int numActive) {
        gameEngine = new GameEngine(numHoles, numActive);
        gameEngine.start();
    }

    /**
     * Create timer for auto refresh, start timer and call refresh function every 0.1 second
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void startAutoRefresh() {
        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshGridButtons();
            }
        });
        timer.setDelay(100); // delay for 0.1 second
        timer.start();
    }

    /**
     * Create game timer, start, and call shutdown on ExeService when timer has expired
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void startGameTimer() {
        Timer timer = new Timer(gameTimer * 1000 , new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Timer Has Finished, Game Over");
                labelFieldWinner.setVisible(true);
                gameEngine.shutdown();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Main method for gui setup
     * <p>
     *
     * @throws IOException
     * @param args
     * @return none
     */
    public static void main(String[] args) throws IOException {
        JFrame mainFrame = new JFrame("GameWindow");
        mainFrame.setContentPane(new GameWindow().panelMain);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}





