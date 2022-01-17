package com.orion.volatileEx;

import java.util.concurrent.TimeUnit;

/**
 * volatile使共享变量发生变化立刻刷新主内存，其他线程也可见使自己的工作内存拷贝的变量失效，强制重新从主内存拿，即可见性，它还有
 * 一种功能是 禁止重排序
 *
 * @author Administrator
 */
public class VolatileSwitch {
    private static volatile  boolean turner = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (turner) {
                //里面不能有System.out.println，因为其源码有synchronized关键字，这样就刷新主存数据了
            }
            System.out.println("switch is off!");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("after 2s set turner is false");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turner = false;
        });

        t1.start();
        t2.start();
    }
    /**
     * 运行结果：
     * after 2s set turner is false
       switch is off!
     *
     */

}
