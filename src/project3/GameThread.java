package project3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by roeje on 4/3/2017.
 *
 * Thread class that implements Runnable, defines logic for each game thread
 *
 * @author  Jesse Roe
 * @version 04/1/2017
 */
public class GameThread implements Runnable {
    private final int id;
    private Semaphore sem;
    private GameBoard data;

    /**
     * Constructor for GameThread, assign globals from passed values
     * <p>
     *
     * @throws none
     * @param id, sem, data
     * @return none
     */
    public GameThread(final int id, Semaphore sem, GameBoard data) {
        this.id = id;
        this.sem = sem;
        this.data = data;
    }

    /**
     * Logic method for random action on game board
     * <p>
     *
     * @throws InterruptedException
     * @param none
     * @return none
     */
    public void randomAction() throws InterruptedException {

        int count = 0;
        int maxWait = random(10, 80);
        int actionFail = random(1, 100);

        /*20% of the time, the mole doesn't appear*/
        if (actionFail > 20) {
            data.setActive(id);
            while (data.getStatus(id) != 0 && count <= maxWait) {
                try {
                    sleep(100);
                    count++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            /*User didn't click in time*/
            if (count > maxWait) {
                data.incrementMissedHits();
            }
            data.setInactive(id);
        } else {
            try {
                sleep(random(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Helper method for generating a random number
     * <p>
     *
     * @throws none
     * @param min, max
     * @return int
     */
    public int random(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    /**
     * Implement required run method, handles game board access with Semaphore
     * <p>
     *
     * @throws none
     * @param none
     * @return none
     */
    @Override
    public void run() {
        boolean access = false;

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);
        while(true) {
            try {

                /*Try to acquire semaphore*/
                access = sem.tryAcquire(1, TimeUnit.SECONDS);

                if (access) {
                    System.out.println("Accessing SEM from thread with id: " + id);
                    randomAction();

                } else {
                    System.out.println("Couldn't get the SEM: " + id);
                    continue;
                }

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);

            } finally {
                if (access) {
                    sem.release();
                    try {
                        sleep(random(500, 2000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
