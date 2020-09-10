package com.daop.juc;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 测试生产者、消费者案例
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 **/
public class TestProducerAndConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);
    }
}

/**
 * 店员
 */
class Clerk {
    private int product = 0;

    /**
     * 进货
     */
    public synchronized void get() {
        if (product >= 10) {
            System.out.println("产品已满");
        } else {
            System.out.println(Thread.currentThread().getName() + ":" + ++product);
        }
    }

    /**
     * 卖货
     */
    public synchronized void sale() {
        if (product <= 0) {
            System.out.println("缺货");
        } else {
            System.out.println(Thread.currentThread().getName() + ":" + --product);
        }
    }
}

class Producer implements Runnable {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}