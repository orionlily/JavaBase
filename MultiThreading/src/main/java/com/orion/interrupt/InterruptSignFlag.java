package com.orion.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 中断可以理解为线程的一个标志位，它表示了一个运行中的线程是否被其他线程进行了中断操作。
 中断好比其他线程对该线程打了一个招呼。其他线程可以调用该线程的interrupt()方法对其进行中断
 操作，同时该线程可以调用 isInterrupted（）来感知其他线程对其自身的中断操作，从而做出响
 应。

 清除中断标志位：
 ①可以调用Thread的静态方法 interrupted（）对当前线程进行中断操作，该方法会清除中断标志位。
 ②需要注意的是，当抛出InterruptedException时候，会清除中断标志位，也就是说在调用isInterrupted会返回false。


 开启了两个线程分别为sleepThread和BusyThread, sleepThread睡眠1s，BusyThread执行死循环。然
 后分别对着两个线程进行中断操作，可以看出sleepThread抛出InterruptedException后清除标志位，
 而busyThread就不会清除标志位。

 另外，同样可以通过中断的方式实现线程间的简单交互， while (sleepThread.isInterrupted()) 表示在
 Main中会持续监测sleepThread，一旦sleepThread的中断标志位清零，即sleepThread.isInterrupted()
 返回为false时才会继续Main线程才会继续往下执行。因此，中断操作可以看做线程间一种简便的交
 互方式。一般在结束线程时通过中断标志位或者标志位的方式可以有机会去清理资源，相对于武断
 而直接的结束线程，这种方式要优雅和安全。
 *
 * @author Administrator
 */
public class InterruptSignFlag {
    public static void main(String[] args) {
        Thread sleepThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("sleep thread 睡觉被打断了，中断标志位被清除了，sleep thread is interrupted ? "
                         + Thread.currentThread().isInterrupted());
            }
        });

        Thread busyThread = new Thread(() -> {
            while (true){
            }
        });

        sleepThread.start();
        busyThread.start();

        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("sleep thread is interrupted ? " + sleepThread.isInterrupted());
        System.out.println("busy thread is interrupted ? " + busyThread.isInterrupted());

        /**
         * 运行结果：
         sleep thread is interrupted ? true
         busy thread is interrupted ? true
         sleep thread 睡觉被打断了，中断标志位被清除了，sleep thread is interrupted ? false

         busyThread没有停下来，只是给了个标志，不是立马停下来
         */
    }
}
