package com.daop.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description:
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * 用于解决多线程安全的方式：
 * synchronized
 * ①同步代码块
 * ②同步方法
 * 1.5后
 * ③同步锁 Lock（是一个显式的锁）通过lock()方法上锁，和unlock()方法进行释放锁
 **/
public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}

class Ticket implements Runnable {
    private int tick = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();//加锁
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --tick);
                }
            } finally {
                lock.unlock();//释放锁
            }
        }
    }
}
