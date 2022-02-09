package com.orion.jucutil;

import com.orion.SmallTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号量
 *
 * Semaphore可以理解为信号量，用于控制资源能够被并发访问的线程数量，以保证多个线程能够合
 理的使用特定资源。Semaphore就相当于一个许可证，线程需要先通过acquire方法获取该许可证，
 该线程才能继续往下执行，否则只能在该方法出阻塞等待。当执行完业务功能后，需要通过
 release() 方法将许可证归还，以便其他线程能够获得许可证继续执行。
 Semaphore可以用于做流量控制，特别是公共资源有限的应用场景，比如数据库连接。假如有多个
 线程读取数据后，需要将数据保存在数据库中，而可用的最大数据库连接只有10个，这时候就需要
 使用Semaphore来控制能够并发访问到数据库连接资源的线程个数最多只有10个。在限制资源使用
 的应用场景下，Semaphore是特别合适的。
 *
 * @author Administrator
 */
public class SemaphoreTs {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //非公平
        Semaphore semaphore = new Semaphore(5);

        System.out.println("一共只有5支圆珠笔，有10个小朋友需要拿来使用");
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                SmallTool.printTimeAndThread("想获取圆珠笔");
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SmallTool.printTimeAndThread("拿到笔了，开始使用");
                SmallTool.sleepMillis(2000);
                SmallTool.printTimeAndThread("使用完成，归还圆珠笔");
                semaphore.release();
            });
        }
    }
}
/**
 * 运行结果：
 *
 一共只有5支圆珠笔，有10个小朋友需要拿来使用
 1644394796050	|	12	|	pool-1-thread-2	|	想获取圆珠笔
 1644394796050	|	13	|	pool-1-thread-3	|	想获取圆珠笔
 1644394796050	|	13	|	pool-1-thread-3	|	拿到笔了，开始使用
 1644394796050	|	11	|	pool-1-thread-1	|	想获取圆珠笔
 1644394796050	|	11	|	pool-1-thread-1	|	拿到笔了，开始使用
 1644394796050	|	15	|	pool-1-thread-5	|	想获取圆珠笔
 1644394796050	|	15	|	pool-1-thread-5	|	拿到笔了，开始使用
 1644394796050	|	14	|	pool-1-thread-4	|	想获取圆珠笔
 1644394796050	|	14	|	pool-1-thread-4	|	拿到笔了，开始使用
 1644394796050	|	16	|	pool-1-thread-6	|	想获取圆珠笔
 1644394796050	|	12	|	pool-1-thread-2	|	拿到笔了，开始使用
 1644394796051	|	17	|	pool-1-thread-7	|	想获取圆珠笔
 1644394796051	|	18	|	pool-1-thread-8	|	想获取圆珠笔
 1644394796052	|	19	|	pool-1-thread-9	|	想获取圆珠笔
 1644394796052	|	20	|	pool-1-thread-10	|	想获取圆珠笔
 1644394798066	|	13	|	pool-1-thread-3	|	使用完成，归还圆珠笔
 1644394798067	|	16	|	pool-1-thread-6	|	拿到笔了，开始使用
 1644394798067	|	11	|	pool-1-thread-1	|	使用完成，归还圆珠笔
 1644394798067	|	17	|	pool-1-thread-7	|	拿到笔了，开始使用
 1644394798067	|	15	|	pool-1-thread-5	|	使用完成，归还圆珠笔
 1644394798067	|	18	|	pool-1-thread-8	|	拿到笔了，开始使用
 1644394798067	|	14	|	pool-1-thread-4	|	使用完成，归还圆珠笔
 1644394798067	|	19	|	pool-1-thread-9	|	拿到笔了，开始使用
 1644394798067	|	12	|	pool-1-thread-2	|	使用完成，归还圆珠笔
 1644394798067	|	20	|	pool-1-thread-10	|	拿到笔了，开始使用
 1644394800082	|	16	|	pool-1-thread-6	|	使用完成，归还圆珠笔
 1644394800083	|	17	|	pool-1-thread-7	|	使用完成，归还圆珠笔
 1644394800084	|	19	|	pool-1-thread-9	|	使用完成，归还圆珠笔
 1644394800084	|	18	|	pool-1-thread-8	|	使用完成，归还圆珠笔
 1644394800084	|	20	|	pool-1-thread-10	|	使用完成，归还圆珠笔
 *
 */
