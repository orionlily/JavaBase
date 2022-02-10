package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 不释放锁导致的后果
 *
 * 可见被打断后，没有释放锁，state补位0，后面在finally新起的线程拿不到锁执行不下去,只有先unlock才能执行下去
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
                    reentrantLock.lock();
                    SmallTool.printTimeAndThread("r biz");
                    reentrantLock.unlock();
                }).start();
            }
        });

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
        });
        r.start();
        r1.start();


        SmallTool.sleepMillis(200);
        r.interrupt();
        SmallTool.printTimeAndThread("main will interrupt");
    }
    /**
     * 运行结果：
     1644473401209	|	1	|	main	|	main will interrupt
     1644473403007	|	14	|	Thread-1	|	r1 biz
     java.lang.InterruptedException
     1644473403007	|	14	|	Thread-1	|	r1 release lock
     1644473403010	|	16	|	Thread-2	|	r begin
     at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.reportInterruptAfterWait(AbstractQueuedSynchronizer.java:2014)
     at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2048)
     at com.orion.lock.ShowLockNotRelease.lambda$main$1(ShowLockNotRelease.java:23)
     at java.lang.Thread.run(Thread.java:748)

     */
}
