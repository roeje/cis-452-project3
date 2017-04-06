package project3;

/**
 * CIS 452 - Project 3: Concurrency in Game Design
 *
 * Model class to store the game board array and various statistical data.
 *
 * @author  Jesse Roe
 * @version 04/1/2017
 */
public class GameBoard {

    private final int[] data;
    private int numHits;
    private int numMissClicks;
    private int totalClicks;
    private int numMissedHits;

    public GameBoard(int size) {
        data = new int[size];
        numHits = 0;
        numMissClicks = 0;
        totalClicks = 0;
    }

    public int[] getData() {
        return data;
    }

    public void setActive(int index) {
        data[index] = 1;
    }

    public void setInactive(int index) {
        data[index] = 0;
    }

    public int getStatus(int index) {
        return data[index];
    }

    public int getNumHits() {
        return numHits;
    }

    public int getNumMissClicks() {
        return numMissClicks;
    }

    public void incrementHits() {
        numHits++;
    }

    public void incrementMisses() {
        numMissClicks++;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void incrementClicks() {
        totalClicks++;
    }

    public int getNumMissedHits() {
        return numMissedHits;
    }

    public void setNumMissedHits(int numMissedHits) {
        this.numMissedHits = numMissedHits;
    }

    public void incrementMissedHits() {
        numMissedHits++;
    }

}
