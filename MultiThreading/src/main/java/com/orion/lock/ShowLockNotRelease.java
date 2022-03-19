package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 不释放锁导致的后果
 *
 * 可见被打断后，没有释放锁，state不为0，后面在finally新起的线程拿不到锁执行不下去,只有先unlock才能执行下去
 *
 * @author Administrator
 */
public class ShowLockNotRelease {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition c = reentrantLock.newCondition();

        Thread r = new Thread(() -> {
            reentrantLock.lock();
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //reentrantLock.unlock();
                new Thread(() -> {
                    SmallTool.printTimeAndThread("r begin");
                    //reentrantLock.lock(); //注释能使程序终结，lock的计数归零
                    SmallTool.printTimeAndThread("r biz");
                    reentrantLock.unlock();
                },"r2").start();
            }
        },"r");

        Thread r1 = new Thread(() -> {
            reentrantLock.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
                SmallTool.printTimeAndThread("r1 biz");
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("r1 to interrupt");
            }finally {
                SmallTool.printTimeAndThread("r1 release lock");
                reentrantLock.unlock();
            }
        },"r1");
        r.start();
        r1.start();


        SmallTool.sleepMillis(200);
        r.interrupt();
        SmallTool.printTimeAndThread("main will interrupt");
    }
    /**
     * 运行结果：
     1647659806410	|	1	|	main	|	main will interrupt
     java.lang.InterruptedException
     1647659808220	|	12	|	r1	|	r1 biz
     at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.reportInterruptAfterWait(AbstractQueuedSynchronizer.java:2014)
     1647659808221	|	12	|	r1	|	r1 release lock
     1647659808223	|	14	|	r2	|	r begin
     at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2048)
     1647659808223	|	14	|	r2	|	r biz
     at com.orion.lock.ShowLockNotRelease.lambda$main$1(ShowLockNotRelease.java:25)
     at java.lang.Thread.run(Thread.java:748)
     Exception in thread "r2" java.lang.IllegalMonitorStateException
     at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:151)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1261)
     at java.util.concurrent.locks.ReentrantLock.unlock(ReentrantLock.java:457)
     at com.orion.lock.ShowLockNotRelease.lambda$null$0(ShowLockNotRelease.java:34)
     at java.lang.Thread.run(Thread.java:748)

     Process finished with exit code 0

     */
}
