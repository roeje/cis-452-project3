package project3;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * CIS 452 - Project 3: Concurrency in Game Design
 *
 * GameEngine: Controller class to handle logic/structures necessary for thread management
 *
 * @author  Jesse Roe
 * @version 04/1/2017
 */
public final class GameEngine {
    public GameBoard data;
    private ExecutorService exe;
    public Semaphore sem;
    private int numHoles;
    private int numActive;

    /**
     * Constructor for GameEngine, sets up global vars
     * <p>
     *
     * @throws none
     * @param numHoles, numActive
     * @return none
     */
    public GameEngine(int numHoles, int numActive) {

        /* Define variables based on input from GUI */
        this.numHoles = numHoles;
        this.numActive = numActive;
        data = new GameBoard(numHoles);
        exe = Executors.newFixedThreadPool(numHoles);
        sem = new Semaphore(numActive);
    }

    /**
     * Spawn off threads based on defined number of holes
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void start() {

        /*Trigger on user input and after the*/
        IntStream.range(0, numHoles).forEach(i -> exe.submit(new GameThread(i, sem, data)));

    }

    /**
     * Handle graceful shutdown of executor service and any sleeping threads
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    public void shutdown() {

        try {
            System.out.println("Attempting to shutdown executor");
            exe.shutdown();
            exe.awaitTermination(1, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("Tasks were interrupted");
        }
        finally {
            if (!exe.isTerminated()) {
                System.err.println("Canceling non-finished tasks");
            }
            exe.shutdownNow();
            System.out.println("Shutdown is finished");
        }
    }
}
