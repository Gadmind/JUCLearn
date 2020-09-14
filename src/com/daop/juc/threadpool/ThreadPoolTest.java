package com.daop.juc.threadpool;

import java.util.concurrent.*;

/**
 * @BelongsProject: JUC
 * @BelongsPackage: com.daop.juc.threadpool
 * @Description: 线程池原理及自定义线程池
 * @DATE: 2020-09-14
 * @AUTHOR: Administrator
 **/
public class ThreadPoolTest {
    public static void main(String[] args) {

        //最大线程池数=maximumPoolSize+阻塞队列容量
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
