package com.orion.synchronize;

/**
 * synchronize 锁遇到异常的时候会释放锁
 *
 * @author Administrator
 */
public class ExcepReleaseLock {
    public static void main(String[] args) {
        Runnable r = () -> {
            synchronized (ExcepReleaseLock.class) {
                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        throw new RuntimeException(Thread.currentThread() + " throw exception");
                    }
                    System.out.println(Thread.currentThread() + "----" + i);
                }
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(r, "t" + i).start();
        }
    }
    /**
     * 运行结果：3个线程都是打印到2，遇到3抛异常，就释放锁了
     * Exception in thread "t1" Exception in thread "t0" Exception in thread "t2" java.lang.RuntimeException: Thread[t1,5,main] throw exception
     at com.orion.synchronize.ExcepReleaseLock.lambda$main$0(ExcepReleaseLock.java:14)
     at java.lang.Thread.run(Thread.java:748)
     java.lang.RuntimeException: Thread[t0,5,main] throw exception
     at com.orion.synchronize.ExcepReleaseLock.lambda$main$0(ExcepReleaseLock.java:14)
     at java.lang.Thread.run(Thread.java:748)
     java.lang.RuntimeException: Thread[t2,5,main] throw exception
     at com.orion.synchronize.ExcepReleaseLock.lambda$main$0(ExcepReleaseLock.java:14)
     at java.lang.Thread.run(Thread.java:748)
     Thread[t0,5,main]----0
     Thread[t0,5,main]----1
     Thread[t0,5,main]----2
     Thread[t1,5,main]----0
     Thread[t1,5,main]----1
     Thread[t1,5,main]----2
     Thread[t2,5,main]----0
     Thread[t2,5,main]----1
     Thread[t2,5,main]----2
     */
}
