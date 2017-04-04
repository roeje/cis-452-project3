package project3;

import java.util.Vector;

/**
 * Created by roeje on 4/2/17.
 */
public class GameBoard {

    private final int[] data;

    public GameBoard(int size) {
        data = new int[size];
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
}
