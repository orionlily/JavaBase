package com.orion.base;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;

/**
 * join() 的作用：让“主线程”等待“子线程”结束之后才能继续运行
 *
 * join内部就是wait实现的，打断的话需要区分好事锁了哪个对象
 *
 * @author Administrator
 */
public class Join {

    public static void main(String[] args) {

        JoinThread joinThread = new JoinThread("join");
        joinThread.start();

        Thread.currentThread().interrupt();

        try {
            joinThread.join();
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("Join interrupted : " + e.getMessage());
        }

        SmallTool.printTimeAndThread("main thread is finished");
        /**
         * 运行结果：时间戳
         1642494783925	|	11	|	join	|	JoinThread is running 3s task
         1642494786969	|	1	|	main	|	main thread is finished

         加上 Thread.currentThread().interrupt();，结果为：
         1644899026663	|	1	|	main	|	Join interrupted : null
         1644899026664	|	1	|	main	|	main thread is finished
         1644899026667	|	11	|	join	|	JoinThread is running 3s task
         */
    }

    static class JoinThread extends Thread {
        public JoinThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            SmallTool.printTimeAndThread("JoinThread is running 3s task");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("sleep interrupted : " + e.getMessage());
            }
        }
    }
}
