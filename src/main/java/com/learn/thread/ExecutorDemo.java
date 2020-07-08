package com.learn.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");


        executor.invokeAll(callables)
                .stream()
                .map((future -> {
                    try {
                        String s = future.get();
                        return s;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }))
                .forEach(System.out::println);
    }

}
