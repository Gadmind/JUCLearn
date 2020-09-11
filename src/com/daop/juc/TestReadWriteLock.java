package com.daop.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 读写锁测试 是一种乐观锁
 * @DATE: 2020-09-11
 * @AUTHOR: Administrator
 * 写写/读写    需要"互斥"
 * 读读        不需要"互斥"
 **/
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo rw = new ReadWriteLockDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                rw.set((int) (Math.random() * 101));
            }, "Write" + i).start();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                rw.get();
            }).start();

        }
    }

}

class ReadWriteLockDemo {
    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void get() {
        lock.readLock().lock();//上锁
        try {
            System.out.println(Thread.currentThread().getName() + "：" + number);
        } finally {
            lock.readLock().unlock();//释放锁
        }
    }

    public void set(int number) {
        lock.writeLock().lock();//上锁
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            this.number = number;
        } finally {
            lock.writeLock().unlock();//释放锁
        }

    }
}
