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
    private final Semaphore sem;
    private final GameBoard data;

    public GameThread(final int id, final Semaphore sem, final GameBoard data) {
        this.id = id;
        this.sem = sem;
        this.data = data;
    }

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

    public int random(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    @Override
    public void run() {
        boolean access = false;

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);
        while(true) {
            try {
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
