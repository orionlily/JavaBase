package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock的重入性质，如果释放次数少于重入次数，即解锁不完全，下一次线程就拿不到锁。
 *
 * @author Administrator
 */
public class ReentrantQuality {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Runnable runnable = () -> {
            lock.lock();
                    SmallTool.printTimeAndThread("第1次获得锁");
            lock.lock();
            SmallTool.printTimeAndThread("第2次获得锁");
            try {

                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                SmallTool.printTimeAndThread("第1次释放锁");

                //如果注释掉，释放锁少于获取锁，下一线程将获取不到锁，执行不了代码
                lock.unlock();
                SmallTool.printTimeAndThread("第2次释放锁");
            }
        };
        Thread t1 = new Thread(runnable,"t1");
        Thread t2 = new Thread(runnable,"t2");

        t1.start();
        t2.start();
    }
    /**
     * 运行结果：
     1642320205339	|	11	|	t1	|	第1次获得锁
     1642320205339	|	11	|	t1	|	第2次获得锁
     1642320207353	|	11	|	t1	|	第1次释放锁
     1642320207353	|	11	|	t1	|	第2次释放锁

     1642320207353	|	12	|	t2	|	第1次获得锁
     1642320207353	|	12	|	t2	|	第2次获得锁
     1642320209364	|	12	|	t2	|	第1次释放锁
     1642320209364	|	12	|	t2	|	第2次释放锁
     *
     */
}
