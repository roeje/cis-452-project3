package project3;

import java.util.Vector;

/**
 * Created by roeje on 4/2/17.
 */
public class GameBoard {

    private final int[] data;
    private int numHits;
    private int numMisses;
    private int totalClicks;

    public GameBoard(int size) {
        data = new int[size];
        numHits = 0;
        numMisses = 0;
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

    public int getNumMisses() {
        return numMisses;
    }

    public void incrementHits() {
        numHits++;
    }

    public void incrementMisses() {
        numMisses++;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void incrementClicks() {
        totalClicks++;
    }
}
