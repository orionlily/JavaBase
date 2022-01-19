package com.orion.base;

import com.orion.SmallTool;

/**
 * @author Administrator
 * @date 2022/1/19
 */
public class WaitThread {

    public static void main(String[] args) {
        Object o = new Object();
        Thread t = new Thread(() -> {
            synchronized (o) {
                SmallTool.printTimeAndThread("马上wait");
                try {
                    o.wait();
                    //o.wait(50);
                } catch (InterruptedException e) {
                    SmallTool.printTimeAndThread("被中断了");
                }
            }
        });
        t.start();
        SmallTool.sleepMillis(20);
        t.interrupt();
    }
    /**
     * 运行结果：
     1642602803210	|	11	|	Thread-0	|	马上wait
     1642602803242	|	11	|	Thread-0	|	被中断了
     */
}
