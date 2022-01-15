package com.orion.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * 多线程售票-runnable方式,
 * 加锁会顺序把票号打出来，因为num--不是原子操作，会有多线程安全问题，故意加上sleep 甚至容易出现0号票的共享数据安全问题。
 *
 * @author Administrator
 */
public class TicketSellRunnable {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            int num = 100;

            @Override
            public void run() {
                while (true) {
                    synchronized (TicketSellRunnable.class) {
                        if (num > 0) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getName() + "正在售卖第" + num-- + "号票子");
                        }
                    }
                }
            }
        };


        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(runnable, "t" + i);
            thread.start();
        }
    }

}
