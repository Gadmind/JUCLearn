package com.daop.juc;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 测试生产者、消费者案例
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * 等待唤醒机制 线程.wait()  线程.notifyAll()
 **/
public class TestProducerAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer pro = new Producer(clerk);
        Consumer cus = new Consumer(clerk);
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
class Clerk {
    private int product = 0;

    /**
     * 进货
     */
    public synchronized void get() throws InterruptedException {
        //避免虚假唤醒问题，wait()方法应该总是在循环中使用
        while (product >= 1) {
            System.out.println("产品已满");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + ":" + ++product);
        this.notifyAll();
    }

    /**
     * 卖货
     */
    public synchronized void sale() throws InterruptedException {
        while (product <= 0) {

            System.out.println("缺货");
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + ":" + --product);
        this.notifyAll();
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
            try {
                Thread.sleep(200);
                clerk.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            try {
                clerk.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}