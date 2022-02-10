package com.orion.threadlocal;

/**
 * 一个线程就算有多个threadLocal，threadLocal之间也不会使对方有影响
 *
 * @author Administrator
 */
public class MultiThreadLocalTs {
    private static ThreadLocal<String> local1 = new ThreadLocal<>();
    private static ThreadLocal<String> local2 = new ThreadLocal<>();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                local1.set("local1:" + Thread.currentThread().getName());
                local2.set("local2:" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "   " + local1.get()+ "   " +local2.get() + "   " + local1.get());
            }).start();
        }
    }
    /**
     * 运行结果：
     Thread-1   local1:Thread-1   local2:Thread-1   local1:Thread-1
     Thread-0   local1:Thread-0   local2:Thread-0   local1:Thread-0
     Thread-3   local1:Thread-3   local2:Thread-3   local1:Thread-3
     Thread-2   local1:Thread-2   local2:Thread-2   local1:Thread-2
     Thread-7   local1:Thread-7   local2:Thread-7   local1:Thread-7
     Thread-9   local1:Thread-9   local2:Thread-9   local1:Thread-9
     Thread-6   local1:Thread-6   local2:Thread-6   local1:Thread-6
     Thread-5   local1:Thread-5   local2:Thread-5   local1:Thread-5
     Thread-4   local1:Thread-4   local2:Thread-4   local1:Thread-4
     Thread-8   local1:Thread-8   local2:Thread-8   local1:Thread-8
     *
     */
}    