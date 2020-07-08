package com.learn.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println(System.nanoTime());

        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MICROSECONDS.sleep(1337);

        long delay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("remaining delay:%sms\n", delay);

        executor.shutdown();
    }

}
