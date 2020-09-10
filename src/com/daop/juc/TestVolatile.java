package com.daop.juc;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: Volatile关键字
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * Volatile关键字：当多个线程操作共享数据时，保证内存中的数据可见
 * 相较于synchronized是一种较为轻量的同步策略
 * 1、volatile不具备“互斥性”
 * 2、volatile不能保证变量的“原子性”
 * 内存可见性问题是，当多个线程操作共享数据时，彼此不可见
 **/
public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            if (td.isFlag()) {
                System.out.println("----------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
