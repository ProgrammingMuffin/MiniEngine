package engine.services;

import java.util.concurrent.atomic.AtomicInteger;

public class StatService {

    public static void printFps(AtomicInteger frames) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("fps: " + frames.get());
            frames.set(0);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
