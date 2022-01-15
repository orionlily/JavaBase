package com.orion.volatileEx;

import java.util.concurrent.TimeUnit;

/**
 * volatile使共享变量发生变化立刻刷新主内存，其他线程也可见使自己的工作内存拷贝的变量失效，强制重新从主内存拿，即可见性，它还有
 * 一种功能是 禁止重排序
 *
 * volatile变量在t2、t1调用了它的变量，t1也能及时感知到该变量变化，从而暂停死循环
 *
 * @author Administrator
 */
public class VolatileSwitchOtherVal {

    public static void main(String[] args) {

        Ee t1 = new Ee();


        Thread t2 = new Thread(() -> {
            while (t1.isTurner()) {
            }
            System.out.println("switch is off!");
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


    static class  Ee extends Thread{
        volatile boolean turner = true;
        @Override
        public void run() {
            System.out.println("after 2s set turner is false");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turner = false;
        }

        public boolean isTurner() {
            return turner;
        }
    }
}
