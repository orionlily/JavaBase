package com.orion.base;

import java.util.concurrent.TimeUnit;

/**
 * Java 中的线程分为用户线程与守护线程,守护线程是为其他线程提供服务的线程,如垃圾回收器(GC)就是一
 个典型的守护线程,守护线程不能单独运行, 当 JVM 中没有其他用户线程,只有守护线程时,守护线程会自动销毁, JVM 会退出

 interrupted中断线程. 注意调用 interrupt()方法仅仅是在当前线程打一个停止标志,并不是真正的停止线程

 * @author Administrator
 */
public class InterruptAndDaemon {

    public static void main(String[] args) {
        //TestDaemon();
        try {
            System.out.println("先睡个2s...zzZZ");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InterruptedThread interruptedThread = new InterruptedThread("interrupted");
        interruptedThread.start();
        for (int i = 0; i < 20; i++) {
            if (i == 10){
                interruptedThread.interrupt();
            }
            System.out.println("in main : " + i);
        }
    }

    private static void TestDaemon() {
        System.out.println("main is beginning...");
        Thread thread = new Thread(() -> {
            while (true){
                System.out.println("in the thread");
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println("main is ended...");
        /**
         * 运行结果(其中一种：main结束，守护线程也不复存在)
         main is beginning...
         main is ended...
         in the thread
         in the thread
         in the thread
         in the thread
         in the thread
         in the thread
         in the thread
         in the thread
         *
         *
         */
    }
}

class InterruptedThread extends Thread{

    public InterruptedThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true){
            if (this.isInterrupted()){
                System.out.println("InterruptedThread的中断标志为 true, 要退出了");
                //break; //中断循环, run()方法体执行完毕, 子线程运行完毕
                return; //直接结束当前 run()方法的执行
            }
            System.out.println("InterruptedThread --> " + Thread.currentThread());
        }
        /**
         * 其中一种：
         先睡个2s...zzZZ
         in main : 0
         in main : 1
         in main : 2
         in main : 3
         in main : 4
         in main : 5
         in main : 6
         in main : 7
         in main : 8
         in main : 9
         InterruptedThread --> Thread[interrupted,5,main]
         InterruptedThread的中断标志为 true, 要退出了
         in main : 10
         in main : 11
         in main : 12
         in main : 13
         in main : 14
         in main : 15
         in main : 16
         in main : 17
         in main : 18
         in main : 19
         */
    }
}
