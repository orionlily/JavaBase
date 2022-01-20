package com.orion.base;

import com.orion.SmallTool;

/**
 * wait(long)带有 long 类型参数的 wait()等待,如果在参数指定的时间内没有被唤醒,超时后会自动唤醒
 *
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
