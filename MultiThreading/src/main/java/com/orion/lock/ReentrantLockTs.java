package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock使用
 *
 * @author Administrator
 */
public class ReentrantLockTs {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(1000);
                    SmallTool.printTimeAndThread("finish work");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
            thread.start();
        }
    }
    /**
     * 运行结果：（逐秒运行，证明锁住）
     1642231552471	|	11	|	Thread-0	|	finish work
     1642231553486	|	12	|	Thread-1	|	finish work
     1642231554502	|	13	|	Thread-2	|	finish work
     1642231555516	|	15	|	Thread-4	|	finish work
     1642231556524	|	14	|	Thread-3	|	finish work
     *
     */
}