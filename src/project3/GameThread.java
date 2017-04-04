package project3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by roeje on 4/3/2017.
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



    @Override
    public void run() {
        boolean access = false;
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName);
        try {
            access = sem.tryAcquire(1, TimeUnit.SECONDS);

            if (access) {
                System.out.println("Accessing SEM from thread with id: " + id);
                sleep(5000);
                sem.release();

            } else {
                System.out.println("Couldn't get the SEM");
                sleep(5000);
                sem.release();
            }

        } catch (InterruptedException e) {
            throw new IllegalStateException(e);

        } finally {
            if (access) {
                sem.release();
            }
        }
    }
}
