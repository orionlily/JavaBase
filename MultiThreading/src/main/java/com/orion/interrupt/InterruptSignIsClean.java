package com.orion.interrupt;


import com.orion.SmallTool;

/**
 * 进入interruptException、调用Thread的静态方法Thread.interrupted()会清除线程的中断标志位
 * 调用线程的isInterrupted()则不会清除标志位的
 * <p>
 * 这两个方法都是测试线程是不是被中断了
 *
 * @author Administrator
 */
public class InterruptSignIsClean {
    public static void main(String[] args) {
        Thread carOne = new Thread(() -> {
            long startMills = System.currentTimeMillis();
            while (System.currentTimeMillis() - startMills < 3) {
                //if (Thread.currentThread().isInterrupted()) {
                if (Thread.interrupted()) {
                    SmallTool.printTimeAndThread("被中断了还开个毛???");
                } else {
                    SmallTool.printTimeAndThread("没被中断继续开");
                }
            }
        });

        carOne.start();

        SmallTool.sleepMillis(1);
        carOne.interrupt();
        /**
         * 运行结果：
         * （调用Thread.currentThread().isInterrupted()）
         * 1641827127303	|	11	|	Thread-0	|	没被中断继续开
         1641827127303	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127304	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827127305	|	11	|	Thread-0	|	被中断了还开个毛???
         *
         *
         * 调用Thread.interrupted()这个静态方法，会清楚中断标志位
         1641827423247	|	11	|	Thread-0	|	没被中断继续开
         1641827423247	|	11	|	Thread-0	|	没被中断继续开
         1641827423247	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	被中断了还开个毛???
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         1641827423248	|	11	|	Thread-0	|	没被中断继续开
         */
    }
}
