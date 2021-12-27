package com.orion.base;

import java.util.concurrent.TimeUnit;

/**
 * thread 方法测试
 *
 * @author Administrator
 */
public class ThreadMethod {
    private static final int TIMES = 1000;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < TIMES; i++) {
                if (i % 5 == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        System.out.println("睡眠10ms...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread temp = Thread.currentThread();
                System.out.println("in the thread :" + temp
                        + ", id:" + temp.getId()
                        + ", name:" + temp.getName()
                        + ", threadGroup:" + temp.getThreadGroup()
                        + "," + i + "---" + System.currentTimeMillis());
            }
        });
        thread.start();
    }
}
