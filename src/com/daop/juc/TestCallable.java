package com.daop.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 创建执行线程的方式三：实现Callable接口
 * @DATE: 2020-09-10
 * @AUTHOR: Administrator
 * 相较于实现Runnable接口的方法，Callable接口方法可以有返回值，并可以抛出异常
 * 执行Callable需要FutureTask实现类的支持，用于接收运算结果
 * FutureTask是Future接口的实现类,FutureTask也可以用于闭锁操作
 **/
public class TestCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadDemo2 td = new ThreadDemo2();
        //执行Callable需要FutureTask实现类的支持，用于接收运算结果

        FutureTask<Integer> result = new FutureTask<>(td);
        new Thread(result).start();

        //接收县城运算后的结果
        Integer sum = result.get();
        System.out.println(sum);
        System.out.println("-----------------------");
    }
}

class ThreadDemo2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 1000; i++) {
            sum += i;
        }
        return sum;
    }
}

class ThreadDemo1 implements Runnable {
    @Override
    public void run() {

    }
}
