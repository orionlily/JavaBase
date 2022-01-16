package com.orion.synchronize;

import com.orion.SmallTool;

/**
 * synchronized 的可重入性,假设不具有重入性，是可能造成死锁的。
 *
 * @author Administrator
 */
public class ReentrantQuality {

    public static void main(String[] args) {
        SubRunnable runnable = new SubRunnable();
        Thread sub1 = new Thread(runnable,"sub1");
        Thread sub2 = new Thread(runnable,"sub2");
        sub1.start();
        sub2.start();
    }

    static class SubRunnable implements Runnable{

        @Override
        public void run() {
            methodA();
        }


        public synchronized void methodA(){
            for (int i = 0; i < 5; i++) {
                SmallTool.printTimeAndThread("methodA -- "+ i);
            }
            methodB();
        }

        public synchronized void methodB(){
            for (int i = 0; i < 5; i++) {
                SmallTool.printTimeAndThread("methodB ** "+ i);
            }
        }
    }
}
