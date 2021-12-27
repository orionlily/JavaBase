package com.orion.base;

import java.util.concurrent.TimeUnit;

/**
 * join() 的作用：让“主线程”等待“子线程”结束之后才能继续运行
 * yield() 的作用是让出cpu，但只从运行状态回到就绪状态，仍有机会争夺cpu
 *
 * @author Administrator
 */
public class YieldAndJoin {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        System.out.println("main is begin : " + begin);
        YieldAndJoin yieldAndJoin = new YieldAndJoin();
        yieldAndJoin.testYield();
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("先睡个2s...zzZZ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JoinThread joinThread = new JoinThread("join");
        joinThread.start();
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main is end , cast : " + (System.currentTimeMillis() - begin) + "ms");
        /**
         * 没有join，main先结束，如有则main最后结束
         * 先睡个2s...zzZZ
         * main is end , cast : 2002ms
         * JoinThread is running ：Thread[join,5,main]
         */
    }


    public void testYield() {
        YieldThread yieldThread1 = new YieldThread("yield-orion");
        YieldThread yieldThread2 = new YieldThread("yield-lily");

        yieldThread1.start();
        yieldThread2.start();
    }

}

class YieldThread extends Thread {

    public YieldThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (i == 10) {
                System.out.println(Thread.currentThread() + " is yield");
                Thread.yield();
            }
            System.out.println(Thread.currentThread() + " ---" + i);
        }
        /**
         * 某一种结果：
         Thread[yield-orion,5,main] ---0
         Thread[yield-lily,5,main] ---0
         Thread[yield-lily,5,main] ---1
         Thread[yield-orion,5,main] ---1
         Thread[yield-orion,5,main] ---2
         Thread[yield-lily,5,main] ---2
         Thread[yield-orion,5,main] ---3
         Thread[yield-orion,5,main] ---4
         Thread[yield-orion,5,main] ---5
         Thread[yield-orion,5,main] ---6
         Thread[yield-orion,5,main] ---7
         Thread[yield-orion,5,main] ---8
         Thread[yield-orion,5,main] ---9
         Thread[yield-orion,5,main] is yield
         Thread[yield-lily,5,main] ---3
         Thread[yield-lily,5,main] ---4
         Thread[yield-lily,5,main] ---5
         Thread[yield-lily,5,main] ---6
         Thread[yield-lily,5,main] ---7
         Thread[yield-lily,5,main] ---8
         Thread[yield-lily,5,main] ---9
         Thread[yield-lily,5,main] is yield
         Thread[yield-orion,5,main] ---10
         Thread[yield-orion,5,main] ---11
         *
         */
    }
}

class JoinThread extends Thread {
    public JoinThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("JoinThread is running ：" + Thread.currentThread());
    }
}
