package com.daop.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: ABC交替打印 线程按序交替打印
 * @DATE: 2020-09-11
 * @AUTHOR: Administrator
 **/
public class TestAbcAlternate {

    public static void main(String[] args) {

        AlternateDemo ad = new AlternateDemo();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ad.loopC();
            }
        }, "C").start();
    }
}

class AlternateDemo {
    /**
     * 当前正在执行的标记
     */
    private int num = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA() {
        lock.lock();
        try {
            if (num != 1) {
                condition1.await();
            }
            System.out.print(Thread.currentThread().getName());
            num = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopB() {
        lock.lock();
        try {
            if (num != 2) {
                condition2.await();
            }
            System.out.print(Thread.currentThread().getName());
            num = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void loopC() {
        lock.lock();
        try {
            if (num != 3) {
                condition3.await();
            }
            System.out.print(Thread.currentThread().getName() + "\t");
            num = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
