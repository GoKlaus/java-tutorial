package com.learn.thread;

import java.util.concurrent.*;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());

//        executor.shutdownNow(); //报错

//        executor.shutdown(); //不报错
        Integer result = future.get();

        System.out.println("future done? " + future.isDone());
        System.out.print("result: " + result);
    }

}
