package com.orion.jucutil;

import com.orion.SmallTool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch就像是个计数器，一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行
 *
 * @author Administrator
 */
public class CountDownLatchRace {
    private static CountDownLatch startSignal = new CountDownLatch(1);
    //用来表示裁判员需要维护的是6个运动员
    private static CountDownLatch endSignal = new CountDownLatch(6);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            executorService.execute(() -> {
                try {
                    SmallTool.printTimeAndThread(Thread.currentThread().getName() + " 运动员等待裁判员响");
                    startSignal.await();
                    SmallTool.printTimeAndThread(Thread.currentThread().getName() + "正在全力冲刺");
                    TimeUnit.SECONDS.sleep(1);
                    endSignal.countDown();
                    SmallTool.printTimeAndThread(Thread.currentThread().getName() + " 到达终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        SmallTool.printTimeAndThread("裁判员发号施令啦！！！");
        TimeUnit.SECONDS.sleep(1);
        startSignal.countDown();
        endSignal.await();
        SmallTool.printTimeAndThread("所有运动员到达终点，比赛结束！");
        executorService.shutdown();
    }
}
/**
 * 运行结果：
 *
 1644395513781	|	1	|	main	|	裁判员发号施令啦！！！
 1644395513787	|	11	|	pool-1-thread-1	|	pool-1-thread-1 运动员等待裁判员响
 1644395513788	|	12	|	pool-1-thread-2	|	pool-1-thread-2 运动员等待裁判员响
 1644395513788	|	13	|	pool-1-thread-3	|	pool-1-thread-3 运动员等待裁判员响
 1644395513788	|	14	|	pool-1-thread-4	|	pool-1-thread-4 运动员等待裁判员响
 1644395513788	|	15	|	pool-1-thread-5	|	pool-1-thread-5 运动员等待裁判员响
 1644395513788	|	16	|	pool-1-thread-6	|	pool-1-thread-6 运动员等待裁判员响
 1644395514784	|	11	|	pool-1-thread-1	|	pool-1-thread-1正在全力冲刺
 1644395514784	|	12	|	pool-1-thread-2	|	pool-1-thread-2正在全力冲刺
 1644395514784	|	15	|	pool-1-thread-5	|	pool-1-thread-5正在全力冲刺
 1644395514784	|	16	|	pool-1-thread-6	|	pool-1-thread-6正在全力冲刺
 1644395514784	|	13	|	pool-1-thread-3	|	pool-1-thread-3正在全力冲刺
 1644395514784	|	14	|	pool-1-thread-4	|	pool-1-thread-4正在全力冲刺
 1644395515799	|	13	|	pool-1-thread-3	|	pool-1-thread-3 到达终点
 1644395515799	|	14	|	pool-1-thread-4	|	pool-1-thread-4 到达终点
 1644395515799	|	16	|	pool-1-thread-6	|	pool-1-thread-6 到达终点
 1644395515799	|	15	|	pool-1-thread-5	|	pool-1-thread-5 到达终点
 1644395515799	|	12	|	pool-1-thread-2	|	pool-1-thread-2 到达终点
 1644395515800	|	11	|	pool-1-thread-1	|	pool-1-thread-1 到达终点
 1644395515800	|	1	|	main	|	所有运动员到达终点，比赛结束！
 *
 */