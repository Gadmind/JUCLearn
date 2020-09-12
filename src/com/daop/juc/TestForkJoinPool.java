package com.daop.juc;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @BelongsProject: JUC
 * @BelongsPackage: com.daop.juc
 * @Description:
 * @DATE: 2020-09-12 08:45
 * @AUTHOR: Daop
 * <p>
 * Fork/Join框架：
 * 就是在必要的情况下，将一个大任务，进项拆分（fork）成若干个小任务（拆到不可再拆时），再将一个个小的任务运算的结果进行join汇总
 **/
public class TestForkJoinPool {
    /**
     * 通过ForkJoin多线程
     */
    public static void main(String[] args) {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 50000000000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start, end).toMillis());
    }

    /**
     * 普通循环
     */
    @Test
    public void test1() {
        Instant start = Instant.now();
        long sum = 0L;
        for (long i = 0; i <= 50000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start, end).toMillis());
    }

    /**
     * 使用Java8
     */
    @Test
    public void test2() {
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0L, 50000000000L).parallel().reduce(0L, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start, end).toMillis());
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {
    private static final long serialVersionUID = -634360173679175871L;
    private long start;
    private long end;
    private static final long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            //进行拆分，同时压入线程队列
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
