package com.orion.base;

/**
 * 继承thread类
 *
 * @author Administrator
 */
public class ExtendThread extends Thread {

    private static final int TIMES = 1000;

    public static void main(String[] args) {
        ExtendThread extendThread = new ExtendThread();
        extendThread.start();
        for (int i = 0; i < TIMES; i++) {
            System.out.println("in the main :" + Thread.currentThread() + "--" + i);
        }
    }

    /**
     * 运行结果：(不固定)
     * in the main :Thread[main,5,main]--260
     in the main :Thread[main,5,main]--261
     in the main :Thread[main,5,main]--262
     in the main :Thread[main,5,main]--263
     in the main :Thread[main,5,main]--264
     in the thread :Thread[Thread-0,5,main]--0
     in the thread :Thread[Thread-0,5,main]--1
     in the main :Thread[main,5,main]--265
     in the thread :Thread[Thread-0,5,main]--2
     in the main :Thread[main,5,main]--266
     in the thread :Thread[Thread-0,5,main]--3
     in the main :Thread[main,5,main]--267
     in the thread :Thread[Thread-0,5,main]--4
     in the main :Thread[main,5,main]--268
     in the thread :Thread[Thread-0,5,main]--5
     in the main :Thread[main,5,main]--269
     in the thread :Thread[Thread-0,5,main]--6
     in the main :Thread[main,5,main]--270
     in the thread :Thread[Thread-0,5,main]--7
     in the main :Thread[main,5,main]--271
     in the thread :Thread[Thread-0,5,main]--8
     in the main :Thread[main,5,main]--272
     in the thread :Thread[Thread-0,5,main]--9
     in the main :Thread[main,5,main]--273
     in the thread :Thread[Thread-0,5,main]--10
     in the main :Thread[main,5,main]--274
     in the thread :Thread[Thread-0,5,main]--11
     in the main :Thread[main,5,main]--275
     in the thread :Thread[Thread-0,5,main]--12
     in the main :Thread[main,5,main]--276
     in the thread :Thread[Thread-0,5,main]--13
     in the main :Thread[main,5,main]--277
     in the main :Thread[main,5,main]--278
     in the main :Thread[main,5,main]--279
     in the main :Thread[main,5,main]--280
     in the main :Thread[main,5,main]--281
     in the thread :Thread[Thread-0,5,main]--14
     in the main :Thread[main,5,main]--282
     *
     */

    @Override
    public void run() {
        for (int i = 0; i < TIMES; i++) {
            System.out.println("in the thread :" + Thread.currentThread() + "--" + i);
        }
    }
}
