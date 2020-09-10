package com.daop.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 原子性测试
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * i++ 的原子性问题 i++操作分为三步 读-改-写
 * <p>
 * 原子变量：jdk1.5之后java.util.concurrent 包提供了常用的原子变量
 * 1、volatile 保证内存可见性
 * 2、CAS（Compare—And—Swap） 算法保证数据的原子性；是硬件对于并发操作共享数据的支持；
 * 包含三个操作数：
 * ①内存值 V；
 * ②预估值 A；
 * ③更新值 B；
 * 当且仅当 V==A时，V=B，否则，将不做任何操作
 * </p>
 **/
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 100; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {
    //    private volatile int serialNumber = 0;
    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "------:" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }
}