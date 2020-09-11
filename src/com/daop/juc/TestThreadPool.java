package com.daop.juc;

/**
 * @BelongsProject: JUCLearn
 * @BelongsPackage: com.daop.juc
 * @Description: 线程池
 * @DATE: 2020-09-11
 * @AUTHOR: Administrator
 * 线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁的额外开销，从而提高了响应速度。
 * 体系结构：
 * java.util.concurrent.Executor：负责线程的使用与调度的根接口
 *      |--ExecutorService 子接口：线程池的主要接口
 *          |--AbstractExecutorService：抽象类同提供实现的默认方法
 *              |--ThreadPoolExecutor：实现类
 *          |--ScheduledThreadPoolExecutor 子接口：负责线程的调度
 *              |--ScheduledThreadPoolExecutor：继承了ThreadPoolExecutor，实现了ScheduleExecutorService
 **/
public class TestThreadPool {
    public static void main(String[] args) {

    }
}
