package com.orion.state;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;

/**
 * 线程的状态，有六种：
 * NEW 、新建
 * RUNNABLE 、可运行
 * WAITING、等待
 * TIMED_WAITING、限时等待
 * BLOCKED、阻塞
 * TERMINAL、终止
 *
 * 通过以上实验，NEW和TERMINATED对于中断操作几乎是屏蔽的，RUNNABLE和BLOCKED类似，
 * 对于中断操作只是设置中断标志位并没有强制终止线程，对于线程的终止权利依然在程序手中。
 * WAITING/TIMED_WAITING状态下的线程对于中断操作是敏感的，他们会抛出异常并清空中断标志位。
 *
 * @author Administrator
 */
public class ThreadState {

    public static void main(String[] args) {
        System.out.println("============================测试 NEW RUNNABLE TERMINAL TIMED_WAITING============================");
        Sub a = new Sub("a");
        SmallTool.printTimeAndThread("新建了一个线程a，但还没start，状态：" + a.getState());//NEW

        a.start();
        SmallTool.printTimeAndThread("线程a已经start，状态是：" + a.getState());//RUNNABLE

        SmallTool.printTimeAndThread("待线程" + Thread.currentThread() + "睡20ms，让cpu给a线程，让它睡眠5s");
        SmallTool.sleepMillis(20);
        SmallTool.printTimeAndThread("查看a线程的状态：" + a.getState());//TIMED_WAITING
        SmallTool.sleepMillis(5000);
        SmallTool.printTimeAndThread("睡眠超过a的5s，查看a的状态：" + a.getState());//TERMINATED

        //**************************************************************************************************

         System.out.println("============================测试BLOCK============================");

         Block b = new Block("b");
         Block c = new Block("c");
         b.start();
         SmallTool.printTimeAndThread("b已经启动，c在20ms后启动，让b拿着synchronize锁，睡眠5s");
         SmallTool.sleepMillis(20);
         c.start();
         SmallTool.sleepMillis(20);
         SmallTool.printTimeAndThread("待c启动成功之后，查看状态，b=" + b.getState() + " , c=" + c.getState());//b=TIMED_WAITING , c=BLOCKED
         SmallTool.printTimeAndThread("c正在" + c.getState() + "，尝试打断它");
         c.interrupt();
         SmallTool.printTimeAndThread("在c被打断后，查看打断标志：" + c.isInterrupted() + "，状态：" + c.getState());//isInterrupted=false, RUNNABLE
         SmallTool.printTimeAndThread("c响应中断后不会再执行run()方法D的睡5s，立马往下执行异常代码");

         //*******************************************************************************************************
         SmallTool.sleepMillis(5 * 1000);

         Wait d = new Wait("d");
         SmallTool.sleepMillis(20);
         System.out.println("============================测试WAIT 和 打断它============================");
         d.start();
         SmallTool.sleepMillis(20);
         SmallTool.printTimeAndThread("d已经启动成功，内执行wait()，查看状态： " + d.getState()); //WAITING
         SmallTool.sleepMillis(20);
         SmallTool.printTimeAndThread("过20ms打断d，会抛出异常");
         d.interrupt();
    }
    /**
     * 运行结果：
     * ============================测试 NEW RUNNABLE TERMINAL TIMED_WAITING============================
     1642177805719	|	1	|	main	|	新建了一个线程a，但还没start，状态：NEW
     1642177805721	|	1	|	main	|	线程a已经start，状态是：RUNNABLE
     1642177805722	|	1	|	main	|	待线程Thread[main,5,main]睡20ms，让cpu给a线程，让它睡眠5s
     1642177805733	|	11	|	a	|	在run()方法中睡眠5s...
     1642177805762	|	1	|	main	|	查看a线程的状态：TIMED_WAITING
     1642177810800	|	1	|	main	|	睡眠超过a的5s，查看a的状态：TERMINATED


     ============================测试BLOCK============================
     1642177810867	|	1	|	main	|	b已经启动，c在20ms后启动，让b拿着synchronize锁，睡眠5s
     1642177810925	|	1	|	main	|	待c启动成功之后，查看状态，b=TIMED_WAITING , c=BLOCKED
     1642177810925	|	1	|	main	|	c正在BLOCKED，尝试打断它
     1642177810925	|	1	|	main	|	在c被打断后，查看打断标志：true，状态：BLOCKED
     1642177810925	|	1	|	main	|	c响应中断后不会再执行run()方法的睡5s，立马往下执行异常代码
     1642177815886	|	12	|	b	|	完成业务了，状态是：RUNNABLE
     1642177815886	|	13	|	c	|	在-- block --被打断并清除中断标志，标志：false,状态是：RUNNABLE
     1642177815886	|	13	|	c	|	完成业务了，状态是：RUNNABLE


     ============================测试WAIT 和 打断它============================
     1642177815998	|	1	|	main	|	d已经启动成功，内执行wait()，查看状态： WAITING
     1642177816029	|	1	|	main	|	过20ms打断d，会抛出异常
     1642177816029	|	14	|	d	|	在-- wait --被打断并清除中断标志,状态是：RUNNABLE
     *
     */
}

class Sub extends Thread {

    public Sub(String name) {
        super(name);
    }

    @Override
    public void run() {
        SmallTool.printTimeAndThread("在run()方法中睡眠5s...");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("在-- sub --被打断并清除中断标志,状态是：" + Thread.currentThread().getState());
        }
    }
}

class Block extends Thread {
    public Block(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (Block.class) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("在-- block --被打断并清除中断标志，标志："
                        + Thread.currentThread().isInterrupted() + ",状态是：" + Thread.currentThread().getState());
            } finally {
                SmallTool.printTimeAndThread("完成业务了，状态是：" + Thread.currentThread().getState());
            }
        }
    }
}

class Wait extends Thread {
    public Wait(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("在-- wait --被打断并清除中断标志,状态是：" + Thread.currentThread().getState());//WAITING
            }
        }
    }
}