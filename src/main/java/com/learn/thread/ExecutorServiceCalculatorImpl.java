package com.learn.thread;

import com.sun.jndi.ldap.pool.Pool;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.LongStream;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ExecutorServiceCalculatorImpl implements Calculator {

    private int parallism;

    private ExecutorService pool;

    public ExecutorServiceCalculatorImpl() {
        //获取 cpu 数量
        parallism = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(parallism);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] longs = LongStream.rangeClosed(1, 10000000).toArray();
        ExecutorServiceCalculatorImpl executor = new ExecutorServiceCalculatorImpl();
        Instant start = Instant.now();
        long l = executor.sumUp(longs);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + "ms");

        System.out.println(l);

        executor.pool.shutdown();
    }

    @Override
    public long sumUp(long[] num) throws ExecutionException, InterruptedException {
        List<Future<Long>> results = new ArrayList<>();

        int part = num.length / parallism;

        for (int i = 0; i < parallism; i++) {
            int from = i * part; //开始位置
            int to = (i == parallism - 1) ? num.length - 1 : (i + 1) * part - 1; //结束位置
            //扔给线程池计算
            results.add(pool.submit(new SumTask(num, from, to)));
        }
        long l = 0;
        for (Future<Long> result : results) {

            l += result.get();
        }

        return l;
    }

    private static class SumTask implements Callable<Long> {

        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Long call() throws Exception {
            long total = 0;
            for (int i = from; i <= to; i++) {
                total += numbers[i];
            }
            return total;
        }
    }
}
