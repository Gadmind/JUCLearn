package com.daop.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 闭锁测试
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * 闭锁：在完成某些运算时，只有其他所有线程的运算完成时，当前运算才继续执行
 **/
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        //设置闭锁初始值，每个线程执行完-1
        final CountDownLatch latch = new CountDownLatch(count);
        LatchDemo ld = new LatchDemo(latch);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            new Thread(ld).start();
        }

        latch.await();//确保所有线程执行完毕，再执行main线程

        long end = System.currentTimeMillis();
        System.out.println("耗费时间：" + (end - start));
    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            } finally {
                latch.countDown();//放在finally中确保每次都执行-1操作
            }
        }
    }
}
