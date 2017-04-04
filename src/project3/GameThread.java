package project3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
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

    public void randomAction() throws InterruptedException {

        int count = 0;

        int maxWait = random(1, 8);

        data.setActive(id);
        while(data.getStatus(id) != 0 && count <= maxWait) {
            sleep(1000);
            count++;
        }
        data.setInactive(id);
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
//                    sleep(random(2000, 10000));
                }

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);

            } finally {
                if (access) {
                    sem.release();
                    try {
                        sleep(random(500, 2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
