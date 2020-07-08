package com.learn.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ExecutorDemo2 {
    public static void main(String[] args) throws Exception {
        List<Callable<String>> list = Arrays.asList(
            callable("demo1", 2),
                callable("demo2", 1),
                callable("demo3", 3)
        );

        ExecutorService executor = Executors.newWorkStealingPool();
        String s = executor.invokeAny(list);
        System.out.println("any");
        System.out.println(s);
        System.out.println("=====");
        List<Future<String>> futures = executor.invokeAll(list);
        for (Future<String> future : futures) {
            String s1 = future.get();
            System.out.println(s1);
        }

    }

    static Callable<String> callable(String str, Integer i) {
        return () -> {
            TimeUnit.SECONDS.sleep(i);
            return str;
        };
    }
}
