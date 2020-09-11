package com.daop.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 测试生产者、消费者案例
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * 等待唤醒机制 线程.wait()  线程.notifyAll()
 **/
public class TestProducerAndConsumerForLock {

    public static void main(String[] args) {
        ClerkForLock clerk = new ClerkForLock();
        ProducerForLock pro = new ProducerForLock(clerk);
        ConsumerForLock cus = new ConsumerForLock(clerk);
        //虚假唤醒
        new Thread(pro, "生产者").start();
        new Thread(cus, "消费者").start();

        new Thread(pro, "生产者2").start();
        new Thread(cus, "消费者2").start();
    }
}

/**
 * 店员
 */
class ClerkForLock {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 进货
     */
    public void get() throws InterruptedException {
        lock.lock();
        //避免虚假唤醒问题，wait()方法应该总是在循环中使用
        try {
            while (product >= 1) {
                System.out.println("产品已满");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 卖货
     */
    public void sale() throws InterruptedException {
        lock.lock();
        try {
            while (product <= 0) {

                System.out.println("缺货");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

class ProducerForLock implements Runnable {
    private ClerkForLock clerk;

    public ProducerForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
                clerk.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConsumerForLock implements Runnable {
    private ClerkForLock clerk;

    public ConsumerForLock(ClerkForLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                clerk.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}