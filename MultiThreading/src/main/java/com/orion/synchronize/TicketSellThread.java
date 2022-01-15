package com.orion.synchronize;

/**
 * 多线程卖票问题 thread实现
 * 加锁会顺序把票号打出来，因为num--不是原子操作，会有多线程安全问题。
 *
 * @author Administrator
 */
public class TicketSellThread {

    public static void main(String[] args) {
        TickerThread t1 = new TickerThread("orion");
        TickerThread t2 = new TickerThread("lily");
        t1.start();
        t2.start();
    }
}

class TickerThread extends Thread {

    private static int num = 100;

    public TickerThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (TicketSellThread.class) {
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + " 正在售卖第 " + (num--) + " 号票子");
                }
            }
        }
    }
}
