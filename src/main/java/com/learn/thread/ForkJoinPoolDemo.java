package com.learn.thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.stream.LongStream;

/**
 * description:
 *
 * @author klaus
 * @date 2020/7/8
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] longs = LongStream.rangeClosed(1, 10000000).toArray();
        Instant start = Instant.now();

        Calculator calculator = new ForLoopCalculator();

        long l = calculator.sumUp(longs);
        Instant end = Instant.now();

        System.out.println(Duration.between(start, end).toMillis() + "ms");

        System.out.println(l);
    }

}
interface Calculator{
    long sumUp(long[] num) throws ExecutionException, InterruptedException;
}

class ForLoopCalculator implements Calculator {

    @Override
    public long sumUp(long[] num) {
        long result = 0L;
        for (long l : num) {
            result += l;
        }
        return result;
    }
}
