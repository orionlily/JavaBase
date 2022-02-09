package com.orion.jucutil;

import com.orion.SmallTool;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger是一个用于线程间协作的工具类，用于两个线程间能够交换。它提供了一个交换的同步点，在这个同步点两个线程能够交换数据。
 * 具体交换数据是通过exchange方法来实现的，如果一个线程先执行exchange方法，那么它会同步等待另一个线程也执行exchange方法，
 * 这个时候两个线程就都达到了同步点，两个线程就可以交换数据。
 *
 * @author Administrator
 */
public class ExchangerTs {
    private static Exchanger<String> exchanger = new Exchanger();

    public static void main(String[] args) {
        //代表男生和女生
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(() -> {
            try {
                //男生对女生说的话
                String girl = exchanger.exchange("我其实暗恋你很久了......");
               SmallTool.printTimeAndThread("女孩儿说：" + girl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(() -> {
            try {
                SmallTool.printTimeAndThread("女生慢慢的从教室你走出来......");
                TimeUnit.SECONDS.sleep(3);
                //男生对女生说的话
                String boy = exchanger.exchange("我也很喜欢你......");
                SmallTool.printTimeAndThread("男孩儿说：" + boy);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

/**
 * 运行结果：

 1644395793077	|	12	|	pool-1-thread-2	|	女生慢慢的从教室你走出来......
 1644395796092	|	12	|	pool-1-thread-2	|	男孩儿说：我其实暗恋你很久了......
 1644395796092	|	11	|	pool-1-thread-1	|	女孩儿说：我也很喜欢你......
 *
 *
 */