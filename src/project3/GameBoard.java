package project3;

/**
 * CIS 452 - Project 3: Concurrency in Game Design
 *
 * GameBoard: Model class to store the game board array and various statistical data.
 *
 * @author  Jesse Roe
 * @version 04/1/2017
 */
public class GameBoard {

    private int[] data;
    private int numHits;
    private int numMissClicks;
    private int totalClicks;
    private int numMissedHits;

    /**
     * Constructor for GameBoard, sets up data based on passed board size.
     * <p>
     *
     * @throws none
     * @param size
     * @return none
     */
    public GameBoard(int size) {
        data = new int[size];
        numHits = 0;
        numMissClicks = 0;
        totalClicks = 0;
    }

    /**
     * Accessor for data
     * <p>
     *
     * @throws none
     * @param size
     * @return int[]
     */
    public int[] getData() {
        return data;
    }

    /**
     * Mutate for data at a specified index
     * <p>
     *
     * @throws none
     * @param index
     * @return none
     */
    public void setActive(int index) {
        data[index] = 1;
    }

    /**
     * Mutator for data at specified index
     * <p>
     *
     * @throws none
     * @param index
     * @return none
     */
    public void setInactive(int index) {
        data[index] = 0;
    }

    /**
     * Access for data at a specified index
     * <p>
     *
     * @throws none
     * @param index
     * @return int
     */
    public int getStatus(int index) {
        return data[index];
    }

    /**
     * Accessor for numHits
     * <p>
     *
     * @throws none
     * @param none
     * @return int
     */
    public int getNumHits() {
        return numHits;
    }

    /**
     * Accessor for numMissClicks
     * <p>
     *
     * @throws none
     * @param none
     * @return int
     */
    public int getNumMissClicks() {
        return numMissClicks;
    }

    /**
     * Increment numHits
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void incrementHits() {
        numHits++;
    }

    /**
     * Increment numMissClicks
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void incrementMisses() {
        numMissClicks++;
    }

    /**
     * Accessor for totalClicks
     * <p>
     *
     * @throws none
     * @param none
     * @return int
     */
    public int getTotalClicks() {
        return totalClicks;
    }

    /**
     * Increment totalClicks
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void incrementClicks() {
        totalClicks++;
    }

    /**
     * Accessor for numMissedHits
     * <p>
     *
     * @throws none
     * @param none
     * @return int
     */
    public int getNumMissedHits() {
        return numMissedHits;
    }

    /**
     * Mutate for numMissedHits
     * <p>
     *
     * @throws none
     * @param numMissedHits
     * @return none
     */
    public void setNumMissedHits(int numMissedHits) {
        this.numMissedHits = numMissedHits;
    }

    /**
     * Increment numMissedHits
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void incrementMissedHits() {
        numMissedHits++;
    }

}
