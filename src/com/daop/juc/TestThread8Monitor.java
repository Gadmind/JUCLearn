package com.daop.juc;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 线程8锁
 * @DATE: 2020-09-11
 * @AUTHOR: Administrator
 * 1、两个普通同步方法，两个线程，标准打印                      打印？// one two
 * 2、新增Thread.sleep()给getOne()                         打印？// one two
 * 3、新增普通方法getThree()                                打印？// three one two
 * 4、两个普通同步方法两个number对象                          打印？// two one
 * 5、修改getOne()为静态同步方法                             打印？// two one
 * 6、修改两个方法均为静态同步方法 一个Number对象               打印？// one two
 * 7、一个为静态同步方法，一个为非静态同步方法，两个number对象    打印？// two one
 * 8、两个静态同步方法，两个Number对象                        打印？// one two
 * <p>
 * 关键：
 * 非静态方法情况下锁是this 静态方法情况下锁是当前类的class对象
 * 某一时刻内只能有一个线程持有锁，无论几个方法
 **/
public class TestThread8Monitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(() -> {
            number.getOne();
        }).start();

        new Thread(() -> {
            number2.getTwo();
        }).start();
       /* new Thread(() -> {
            number.getThree();
        }).start();*/
    }
}

class Number {
    public synchronized void getOne() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

   /* public void getThree() {
        System.out.println("three");
    }*/
}
