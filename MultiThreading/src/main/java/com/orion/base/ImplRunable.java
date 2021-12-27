package com.orion.base;

/**
 * 实现runnable接口
 *
 * @author Administrator
 */
public class ImplRunable implements Runnable {

    private static final int TIMES = 1000;

    public void run() {
        for (int i = 0; i < TIMES; i++) {
            System.out.println("in the thread :" + Thread.currentThread() + "--" + i);
        }
    }

    public static void main(String[] args) {
        ImplRunable runable = new ImplRunable();
        Thread thread = new Thread(runable);
        thread.start();
        for (int i = 0; i < TIMES; i++) {
            System.out.println("in the main :" + Thread.currentThread() + "--" + i);
        }
    }
    /**
     * 运行结果：(不固定)
     *
     in the main :Thread[main,5,main]--424
     in the main :Thread[main,5,main]--425
     in the thread :Thread[Thread-0,5,main]--5
     in the main :Thread[main,5,main]--426
     in the main :Thread[main,5,main]--427
     in the thread :Thread[Thread-0,5,main]--6
     in the thread :Thread[Thread-0,5,main]--7
     in the thread :Thread[Thread-0,5,main]--8
     in the main :Thread[main,5,main]--428
     in the main :Thread[main,5,main]--429
     in the main :Thread[main,5,main]--430
     in the main :Thread[main,5,main]--431
     in the thread :Thread[Thread-0,5,main]--9
     in the main :Thread[main,5,main]--432
     *
     */
}
