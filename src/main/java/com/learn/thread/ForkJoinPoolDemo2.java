package com.learn.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ForkJoinPoolDemo2 implements Calculator {

    private ForkJoinPool pool;


    private static class SubTask extends RecursiveTask<Long> {

        private long[] numbers;
        private int from;
        private int to;

        public SubTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected Long compute() {
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
            } else {
                int middle = (from + to) / 2;
                SubTask taskLeft = new SubTask(numbers, from, middle);
                SubTask taskRight = new SubTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinPoolDemo2() {
        pool = new ForkJoinPool();
    }

    @Override
    public long sumUp(long[] num) throws ExecutionException, InterruptedException {
        Long result = pool.invoke(new SubTask(num, 0, num.length - 1));
        pool.shutdown();
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPoolDemo2 pool = new ForkJoinPoolDemo2();
        Instant start = Instant.now();
        long[] numbers = LongStream.rangeClosed(0, 10000000L).toArray();
        long result = pool.sumUp(numbers);

        Instant end = Instant.now();
        System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");

        System.out.println("结果为：" + result);

    }

}
