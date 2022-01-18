package com.orion.lock.readandwrite;

import com.orion.SmallTool;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 的写写模式，各线程阻塞获锁
 *
 * @author Administrator
 */
public class WriteWriteLock {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        Runnable r = () -> {
            try {
                writeLock.lock();
                SmallTool.printTimeAndThread("获得写锁");
                SmallTool.sleepMillis(3000);
            } finally {
                SmallTool.printTimeAndThread("释放写锁");
                writeLock.unlock();
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(r , "t" + i).start();
        }
    }
    /**
     * 运行结果：(可见每个线程获得锁 都是间隔了 3S 的)
     1642411874789	|	11	|	t0	|	获得写锁
     1642411877799	|	11	|	t0	|	释放写锁

     1642411877799	|	12	|	t1	|	获得写锁
     1642411880807	|	12	|	t1	|	释放写锁

     1642411880807	|	13	|	t2	|	获得写锁
     1642411883815	|	13	|	t2	|	释放写锁

     1642411883815	|	14	|	t3	|	获得写锁
     1642411886820	|	14	|	t3	|	释放写锁

     1642411886820	|	15	|	t4	|	获得写锁
     1642411889877	|	15	|	t4	|	释放写锁
     */
}
