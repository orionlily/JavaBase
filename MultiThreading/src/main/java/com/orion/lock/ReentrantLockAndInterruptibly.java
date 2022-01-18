package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock，lock与lockInterruptibly的响应interrupt的区别
 *
 * @author Administrator
 */
public class ReentrantLockAndInterruptibly {
    static class Service {
        private ReentrantLock lock = new ReentrantLock();

        public void serviceMethod() {
            //打断响应是sleep的interruptException
            //simpleLockHasException(lock);

            //打断对lock没效果
            //simpleLockNoException(lock);

            //lockInterruptibly会响应中断
            lockInterrupt(lock);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Service s = new Service();
        Runnable r = () -> s.serviceMethod();
        Thread t1 = new Thread(r, "t1");
        t1.start();
        Thread.sleep(50);
        Thread t2 = new Thread(r, "t2");
        t2.start();
        Thread.sleep(50);
        t2.interrupt(); //中断 t2 线程
    }

    public static void simpleLockHasException(ReentrantLock lock) {
        lock.lock();

        SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 获得锁");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("睡眠被打断 ************** " + e.getMessage());
        } finally {
            SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 释放锁");
            lock.unlock(); //释放锁
        }
    }
    /**
     * 运行结果：（sleep遇到interrupt立马被打断，响应被打断异常）
     1642230995824	|	11	|	t1	|	RUNNABLE ***** 获得锁
     1642231000832	|	11	|	t1	|	RUNNABLE ***** 释放锁
     1642231000833	|	12	|	t2	|	RUNNABLE ***** 获得锁
     1642231000833	|	12	|	t2	|	睡眠被打断 ************** sleep interrupted
     1642231000833	|	12	|	t2	|	RUNNABLE ***** 释放锁
     */

    public static void simpleLockNoException(ReentrantLock lock) {
        lock.lock(); //获得锁定,即使调用了线程的 interrupt()方法,也没有真正的中断线程

        SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 获得锁");
        try {
            //执行一段比较耗时的代码
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                new StringBuilder();
            }
        } finally {
            SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 释放锁");
            lock.unlock(); //释放锁
        }
    }
    /**
     * 运行结果：（interrupt对lock没有效果）
     1642231790072	|	11	|	t1	|	RUNNABLE ***** 获得锁
     1642231813403	|	11	|	t1	|	RUNNABLE ***** 释放锁
     1642231813404	|	12	|	t2	|	RUNNABLE ***** 获得锁
     1642231833466	|	12	|	t2	|	RUNNABLE ***** 释放锁
     */

    public static void lockInterrupt(ReentrantLock lock) {
        try {
            lock.lockInterruptibly(); //如果线程被中断了,不会获得锁,会产生异常
            SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 获得锁");
            //执行一段比较耗时的代码
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                new StringBuilder();
            }
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("睡眠被打断 ************** " + e.getMessage());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                SmallTool.printTimeAndThread(Thread.currentThread().getState() + " ***** 释放锁");
                lock.unlock(); //释放锁
            }
        }
    }
    /**
     * 运行结果：（lockInterruptibly响应了中断，对于IllegalMonitorStateException是unlock抛出的，因为都没拿到锁怎么解锁，
     * 加上判断是不是当前线程拿着锁就不会打印异常信息）
     *
     1642232278186	|	11	|	t1	|	RUNNABLE ***** 获得锁
     1642232278305	|	12	|	t2	|	睡眠被打断 ************** null
     Exception in thread "t2" java.lang.IllegalMonitorStateException
     1642232278305	|	12	|	t2	|	RUNNABLE ***** 释放锁
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at com.orion.lock.ReentrantLockAndInterruptibly.lockInterrupt(ReentrantLockAndInterruptibly.java:93)
     at com.orion.lock.ReentrantLockAndInterruptibly$Service.serviceMethod(ReentrantLockAndInterruptibly.java:21)
     at com.orion.lock.ReentrantLockAndInterruptibly.lambda$main$0(ReentrantLockAndInterruptibly.java:27)
     at java.lang.Thread.run(Thread.java:748)
     1642232300178	|	11	|	t1	|	RUNNABLE ***** 释放锁
     */
}