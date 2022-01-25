package com.orion.threadlocal;

import com.orion.SmallTool;

/**
 * threadLocal
 * 每个线程都拥有一份共享变量的本地副本, 每个线程对应一个副本, 同时对共享变量的操作也改为对属于自己的副本的操作,
 * 这样每个线程处理自己的本地变量, 形成数据隔离.
 *
 * ThreadLocal和Synchronized都是为了解决多线程中共享变量的访问冲突问题

 不同的是:

 Synchronized通过线程等待, 牺牲时间来解决访问冲突
 ThreadLocal通过每个线程单独一份存储空间来存储共享变量的副本, 牺牲空间来解决冲突相比于Synchronized,

 ThreadLocal具有线程隔离的效果:
 只有在线程内才能获取到对应的值, 线程外则不能访问到想要的值.
 threadLocal最适合的是共享变量在线程间隔离, 而在本线程内方法或类间共享的场景.
 *
 * @author Administrator
 */
public class ThreadLocalTs {
    private static final int ticketNum = 20;
    private static final int seller = 5;
    private static ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> ticketNum / 5);


    public static void main(String[] args) {
        for (int i = 0; i < seller; i++) {
            Thread temp = new SellThread("s" + i);
            temp.start();
        }
    }

    static class SellThread extends Thread {
        public SellThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int numForMe = tl.get();
            while (numForMe > 0) {
                SmallTool.printTimeAndThread("卖 " + numForMe + " 号票子");
                tl.set(numForMe--);
            }
            tl.remove();
        }
    }
    /**
     * 运行结果：
     1642941006686	|	17	|	s4	|	卖 4 号票子
     1642941006686	|	13	|	s0	|	卖 4 号票子
     1642941006686	|	15	|	s2	|	卖 4 号票子
     1642941006686	|	13	|	s0	|	卖 3 号票子
     1642941006686	|	14	|	s1	|	卖 4 号票子
     1642941006686	|	16	|	s3	|	卖 4 号票子
     1642941006686	|	14	|	s1	|	卖 3 号票子
     1642941006686	|	13	|	s0	|	卖 2 号票子
     1642941006686	|	14	|	s1	|	卖 2 号票子
     1642941006686	|	15	|	s2	|	卖 3 号票子
     1642941006686	|	14	|	s1	|	卖 1 号票子
     1642941006686	|	17	|	s4	|	卖 3 号票子
     1642941006687	|	17	|	s4	|	卖 2 号票子
     1642941006686	|	15	|	s2	|	卖 2 号票子
     1642941006686	|	13	|	s0	|	卖 1 号票子
     1642941006686	|	16	|	s3	|	卖 3 号票子
     1642941006687	|	15	|	s2	|	卖 1 号票子
     1642941006687	|	16	|	s3	|	卖 2 号票子
     1642941006687	|	17	|	s4	|	卖 1 号票子
     1642941006687	|	16	|	s3	|	卖 1 号票子
     */
}
