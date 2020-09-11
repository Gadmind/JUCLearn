# JUC

1. ### volatile关键字-内存可见性

   

2. ### 原子变量—CAS算法

   CAS（Compare-And-Swap）是一种硬件对并发的支持，针对多处理器操作而设计的处理器中的一种特殊指令，用于管理对共享数据的并发访问。

   CAS是一种无锁的非阻塞算法的实现

   CAS包含了三个操作数：

   1. 需要读写的内存值V
   2. 进行比较的值A
   3. 拟写入的新值B

   当且仅当V的值等于A时，CAS通过原子方式用新值B来更新V的值，否则不会进行任何操作。

3. ### ConcurrentHashMap锁分段机制

   ConcurrentHashMap同步类容器是Java5.0增加的一个线程安全的哈希表，对于多线程的操作，介于HashMap和HashTable之间。内部采用“锁分段”机制代替HashTable的独占锁，进而提高性能

   - **锁粒度**

     

   - **锁分段**

     ConcurrentHashMap，内部细分了若干个小的HashMap，称之为段（Segment）。默认情况下一个ConcurrentHashMap被进一步细分为16个段，即就是锁的并发度。

     

   - **其他**

     此包还提供了设计用于多线程上下文中的Collection实现：

     ConcurrentHashMap、ConcurrentSkipListMap、ConcurrentSkipListSet、CopyOnWriteArrayList和CopyOnWriteArraySet。

     - 当期望许多线程访问一个给定的collection时，ConcurrentHashMap通常优于同步的HashMap；ConcurrentSkipListMap通常优于同步的TreeMap
     - 当期望的读数和遍历远远大于列表的更新数时，CopyOnWriteArrayList优于同步的ArrayList。

4. ### CountDownLatch闭锁

   CountDownLatch（闭锁）一个同步辅助类，在完成一组正在其他线程中执行的操作之前允许一个或多个线程等待。

   闭锁可以延迟线程的进度直到其到达终止状态，闭锁可以用来确保某些活动直到其他活动都完成才继续执行：

   - 确保每个计算在其需要的所有资源都被初始化之后才继续执行
   - 确保某个服务在其所依赖的所有其他服务都已经启动之后才启动
   - 等待直到某个操作所有参与者都准备就绪再继续执行

   **闭锁：在完成某些运算时，只有其他所有线程的运算完成时，当前运算才继续执行**

   CountDownLatch最重要的方法是`countDown()`倒数和`await()`，前者主要是倒数一次，后者是等到倒数为0后继续操作，如果没有到达0，就只能阻塞等待

   ```java
   public class TestCountDownLatch {
       public static void main(String[] args) throws InterruptedException {
           int count = 10;
           //设置闭锁初始值，每个线程执行完-1
           final CountDownLatch latch = new CountDownLatch(count);
           LatchDemo ld = new LatchDemo(latch);
           long start = System.currentTimeMillis();
           for (int i = 0; i < count; i++) {
               new Thread(ld).start();
           }
   
           latch.await();//确保所有线程执行完毕，再执行main线程
   
           long end = System.currentTimeMillis();
           System.out.println("耗费时间：" + (end - start));
       }
   }
   
   class LatchDemo implements Runnable {
       private CountDownLatch latch;
   
       public LatchDemo(CountDownLatch latch) {
           this.latch = latch;
       }
   
       @Override
       public void run() {
           synchronized (this) {
               try {
                   for (int i = 0; i < 5000; i++) {
                       if (i % 2 == 0) {
                           System.out.println(i);
                       }
                   }
               } finally {
                   latch.countDown();//放在finally中确保每次都执行-1操作
               }
           }
       }
   }
   ```

   

5. ### 实现Callable接口

   

6. ### Lock同步锁

7. ### Condition控制线程通信

   Condition接口描述了可能会与锁有关联的条件变量。这些变量在用法上方与使用Object.wait访问的隐式监视器类似，但提供了更强大的功能，需要特别指出的是，单个Lock可能与多个Condition对象关联。为了避免兼容问题，Condition方法的名称与对应的Object版本中的不同。

   在Condition对象中，与wait、notify和notifyAll方法对应的分别是await、signal和signalAll。

   Condition实例实质上被绑定到一个锁上。要为特定的的Lock实例获得Condition实例，请使用其newCondition()方法。

8. ### 线程八锁

   

9. ### 线程按序交替

   

10. ### ReadWriteLock读写锁

11. ### 线程池

12. ### 线程调度

13. ### ForkJoinPool分支/框架 工作窃取

