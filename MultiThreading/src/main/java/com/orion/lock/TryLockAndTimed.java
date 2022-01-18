package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock()拿不到直接放弃
 *
 * @author Administrator
 */
public class TryLockAndTimed {

    static ReentrantLock tryLock = new ReentrantLock();
    static ReentrantLock tryTimedLock = new ReentrantLock();

    public static void main(String[] args) {
        //testTryLock();
        /**
         * 运行结果：
         =========  测试 tryLock() =========
         1642262960675	|	14	|	subTry2	|	拿到锁，执行业务代码
         1642262960675	|	13	|	subTry1	|	拿不到锁，放弃
         1642262989388	|	14	|	subTry2	|	执行完业务，解锁
         *
         */

        //testTryLockParam();
        /**
         * 运行结果：
         =========  测试 tryLock(long timeout, TimeUnit unit) =========
         1642263033519	|	13	|	subTryTimed1	|	拿到锁，执行业务代码
         1642263036529	|	14	|	subTryTimed2	|	3s都拿不到锁，放弃
         1642263058888	|	13	|	subTryTimed1	|	执行完业务，解锁
         */

        testTryLockParamInterrupt();
        /**
         * 运行结果：
         =========  测试 tryLock(long timeout, TimeUnit unit) interrupt =========
         1642263148803	|	13	|	subTryTimed3	|	拿到锁，执行业务代码
         1642263149004	|	14	|	subTryTimed4	|	被中断了,状态：RUNNABLE
         1642263174946	|	13	|	subTryTimed3	|	执行完业务，解锁
         */
    }

    private static void testTryLockParamInterrupt() {
        System.out.println("=========  测试 tryLock(long timeout, TimeUnit unit) interrupt =========");
        SubTryTimed subTryTimed3 = new SubTryTimed("subTryTimed3");
        SubTryTimed subTryTimed4 = new SubTryTimed("subTryTimed4");
        subTryTimed3.start();
        subTryTimed4.start();

        //不加sleep，让线程4直接获得中断标志，tryLock(long timeout, TimeUnit unit)直接获得判断终中断位为true，直接抛异常，走catch
        //加上在doAcquireNanos里面判断Thread.interrupted()抛异常，走catch，也有可能是不抛异常，直接拿不到锁
        SmallTool.sleepMillis(200);
        subTryTimed4.interrupt();
    }

    private static void testTryLockParam() {
        System.out.println("=========  测试 tryLock(long timeout, TimeUnit unit) =========");
        SubTryTimed subTryTimed1 = new SubTryTimed("subTryTimed1");
        SubTryTimed subTryTimed2 = new SubTryTimed("subTryTimed2");
        subTryTimed1.start();
        subTryTimed2.start();
    }

    private static void testTryLock() {
        System.out.println("=========  测试 tryLock() =========");
        SubTry subTry1 = new SubTry("subTry1");
        SubTry subTry2 = new SubTry("subTry2");
        subTry1.start();
        subTry2.start();
    }

    static class SubTry extends Thread {
        public SubTry(String name) {
            super(name);
        }

        @Override
        public void run() {
            if (tryLock.tryLock()) {
                SmallTool.printTimeAndThread("拿到锁，执行业务代码");
                //执行一段比较长的代码
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    new StringBuilder();
                }
            } else {
                SmallTool.printTimeAndThread("拿不到锁，放弃");
            }
            if (tryLock.isHeldByCurrentThread()) {
                SmallTool.printTimeAndThread("执行完业务，解锁");
                tryLock.unlock();
            }
        }
    }

    static class SubTryTimed extends Thread {
        public SubTryTimed(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                if (tryTimedLock.tryLock(3, TimeUnit.SECONDS)) {
                    SmallTool.printTimeAndThread("拿到锁，执行业务代码");
                    //执行一段比较长的代码
                    for (int i = 0; i < Integer.MAX_VALUE; i++) {
                        new StringBuilder();
                    }
                } else {
                    SmallTool.printTimeAndThread("3s都拿不到锁，放弃");
                }
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("被中断了,状态：" + currentThread().getState());
            } finally {
                if (tryTimedLock.isHeldByCurrentThread()) {
                    SmallTool.printTimeAndThread("执行完业务，解锁");
                    tryTimedLock.unlock();
                }
            }
        }
    }
}


