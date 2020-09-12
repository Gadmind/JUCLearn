package com.daop.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @BelongsProject: JUC
 * @BelongsPackage: com.daop.juc
 * @Description: 调度线程池
 * @DATE: 2020-09-12 08:36
 * @AUTHOR: Daop
 **/
public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> result = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);
            System.out.println(result.get());
        }
        pool.shutdown();
    }
}
