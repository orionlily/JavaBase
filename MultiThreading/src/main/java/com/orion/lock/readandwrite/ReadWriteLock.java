package com.orion.lock.readandwrite;

import com.orion.SmallTool;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 的读写模式，读写线程是互斥的，读线程使用锁的时候，写线程拿不到锁，相反亦如此
 * 通过以下的试验可得知。
 *
 * @author Administrator
 */
public class ReadWriteLock {

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        Thread read =  new Thread(() -> {
            try {
                readLock.lock();
                SmallTool.printTimeAndThread("获得读锁");
                SmallTool.sleepMillis(2000);
            } finally {
                SmallTool.printTimeAndThread("释放读锁");
                readLock.unlock();
            }
        });

        Thread write = new Thread(() -> {
            try {
                writeLock.lock();
                SmallTool.printTimeAndThread("获得写锁");
                SmallTool.sleepMillis(4000);
            } finally {
                SmallTool.printTimeAndThread("释放写锁");
                writeLock.unlock();
            }
        });

        read.start();
        SmallTool.sleepMillis(20);
        write.start();
        /**
         * 运行结果：
         1642412462950	|	11	|	Thread-0	|	获得读锁
         1642412465009	|	11	|	Thread-0	|	释放读锁
         1642412465009	|	12	|	Thread-1	|	获得写锁
         1642412469024	|	12	|	Thread-1	|	释放写锁
         */


       /* write.start();
        SmallTool.sleepMillis(20);
        read.start();*/
        /**
         * 运行结果：
         1642412338219	|	12	|	Thread-1	|	获得写锁
         1642412342228	|	12	|	Thread-1	|	释放写锁
         1642412342229	|	11	|	Thread-0	|	获得读锁
         1642412344238	|	11	|	Thread-0	|	释放读锁
         */
    }
}
