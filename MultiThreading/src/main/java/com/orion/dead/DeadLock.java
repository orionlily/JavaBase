package com.orion.dead;

import com.orion.SmallTool;

/**
 * 死锁，线程之间都拿着对方需要的锁互不相让。
 *
 * @author Administrator
 */
public class DeadLock {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lockA){
                SmallTool.printTimeAndThread("handle A begin");
                SmallTool.sleepMillis(1000);
                SmallTool.printTimeAndThread("handle A end ,handle B begin");
                synchronized (lockB){
                    SmallTool.printTimeAndThread("handle B end");
                }
            }
        });


        Thread t2 = new Thread(() -> {
            synchronized (lockB){
                SmallTool.printTimeAndThread("handle B begin");
                SmallTool.sleepMillis(1000);
                SmallTool.printTimeAndThread("handle B end ,handle A begin");
                synchronized (lockA){
                    SmallTool.printTimeAndThread("handle A end");
                }
            }
        });

        t1.start();
        t2.start();
    }

}
