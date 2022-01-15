package com.orion.atomic;

/**
 * 自定义cas，因为正常cas是采用硬件底层实现，这里使用synchronize实现
 *
 * @author Administrator
 */
public class CustomCas {

    private static int cnt = 0;

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    getAndAddInt(1);
                }
            }

            public int getAndAddInt(int delta) {
                int oldVal;
                int newVal;
                do {
                    oldVal = cnt;
                    newVal = cnt + delta;
                } while (!this.compareAndSwap(oldVal, newVal));
                return oldVal;
            }

            public synchronized boolean compareAndSwap(int oldVal, int newVal) {
                if (oldVal == cnt) {
                    cnt = newVal;
                    return true;
                }
                return false;
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable, "t" + 0);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("count result : " + cnt);
    }

}
