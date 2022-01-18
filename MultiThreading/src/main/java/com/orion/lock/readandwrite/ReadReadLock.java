package com.orion.lock.readandwrite;

import com.orion.SmallTool;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 的读读模式，各线程能共存使用读锁，提高读取数据的效率
 *
 * @author Administrator
 */
public class ReadReadLock {

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

        Runnable r = () -> {
            try {
                readLock.lock();
                SmallTool.printTimeAndThread("获得读锁");
                SmallTool.sleepMillis(3000);
            } finally {
                SmallTool.printTimeAndThread("释放读锁");
                readLock.unlock();
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(r , "t" + i).start();
        }
    }
    /**
     * 运行结果：可见他们几乎是同时获取到锁的，看时间戳
     *
     1642353088298	|	13	|	t2	|	获得读锁
     1642353088298	|	11	|	t0	|	获得读锁
     1642353088298	|	14	|	t3	|	获得读锁
     1642353088298	|	15	|	t4	|	获得读锁
     1642353088298	|	12	|	t1	|	获得读锁

     1642353091308	|	14	|	t3	|	释放读锁
     1642353091308	|	13	|	t2	|	释放读锁
     1642353091308	|	11	|	t0	|	释放读锁
     1642353091308	|	12	|	t1	|	释放读锁
     1642353091308	|	15	|	t4	|	释放读锁.
     *
     */

}
