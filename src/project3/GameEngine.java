package project3;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.util.concurrent.*;
import java.util.stream.IntStream;
import static java.lang.Thread.*;

/**
 * Created by roeje on 4/2/17.
 *
 * Controller class to handle logic necessary for interacting with the view, board model and thread management
 *
 */
public final class GameEngine {
    public GameBoard data;
    public ExecutorService exe;
    public Semaphore sem;
    int numHoles;
    int numActive;

    public GameEngine(int numHoles, int numActive) {
        this.numHoles = numHoles;
        this.numActive = numActive;
        data = new GameBoard(numHoles);
        exe = Executors.newFixedThreadPool(numHoles);
        sem = new Semaphore(numActive);
    }

//    Runnable gameThread = new Runnable() {
//        private int _id;
//
//        @Override
//        public void run() {
//            boolean access = false;
//            String threadName = Thread.currentThread().getName();
//            System.out.println(threadName);
//            try {
//                access = sem.tryAcquire(1, TimeUnit.SECONDS);
//
//                if (access) {
//                    System.out.println("Accessing SEM");
//                    sleep(5000);
//
//                } else {
//                    System.out.println("Couldn't get the SEM...");
//                    sleep(5000);
//                }
//
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//
//            } finally {
//                if (access) {
//                    sem.release();
//                }
//            }
//        }
//    };

    public void start() {

        // Trigger on user input and after the
        IntStream.range(0, numHoles).forEach(i -> exe.submit(new GameThread(i, sem, data)));

    }

    public void shutdown() {
        exe.shutdown();
    }
}
