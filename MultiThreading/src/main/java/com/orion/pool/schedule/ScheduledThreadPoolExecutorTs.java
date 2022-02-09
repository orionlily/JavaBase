package com.orion.pool.schedule;

import com.orion.SmallTool;

import java.util.concurrent.*;

/**
 * ScheduledExecutorService,任务线程池的4种方法测试
 *
 * @author Administrator
 */
public class ScheduledThreadPoolExecutorTs {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(3
                , SmallTool.getCustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread("放弃任务：" + r + "，所属线程池：" + executor));

        //单次执行
        //delayExecOneTime(executorService);

        //单次执行callable
        //delayExecCallableOneTime(executorService);

        /*
         * scheduleAtFixedRate:固定速率进行
         * 是以上一个任务开始的时间计时，period时间过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，
         * 则当前任务立即执行，如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行
         */

        //fixRateExecManyTimes(executorService);

        /*
         * scheduleWithFixedDelay:固定速率进行
         * 当达到延时时间initialDelay后，任务开始执行。上一个任务执行结束后到下一次任务执行，
         * 中间延时时间间隔为delay。以这种方式，周期性执行任务
         */
        scheduleWithFixedDelayExecManyTimes(executorService);

    }

    private static void delayExecOneTime(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行scheduled task");
        Runnable runnable = () -> SmallTool.printTimeAndThread("runnable task");
        executorService.schedule(runnable, 3, TimeUnit.SECONDS);
        /**
         * 运行结果： 可见延迟3秒执行
         *
         1644420209771	|	1	|	main	|	马上进行scheduled task
         1644420212784	|	11	|	pool-llc-thread-1	|	runnable task
         */
    }

    private static void delayExecCallableOneTime(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行callable scheduled task");
        Callable<String> callable = () -> "callable task";
        ScheduledFuture<String> schedule = executorService.schedule(callable, 2, TimeUnit.SECONDS);
        try {
            SmallTool.printTimeAndThread("callable schedule result : " + schedule.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /**
         * 运行结果： 可见延迟2秒执行
         *
         1644422152579	|	1	|	main	|	马上进行callable scheduled task
         1644422154588	|	1	|	main	|	callable schedule result : callable task
         */
    }

    private static void fixRateExecManyTimes(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行fix rate scheduled task");
        Runnable runnable = () -> SmallTool.printTimeAndThread("fix rate runnable task");
        executorService.scheduleAtFixedRate(runnable, 3, 2, TimeUnit.SECONDS);
        /**
         * 运行结果： 可见延迟3秒执行，之后每隔两秒执行一次
         *
         1644421306253	|	1	|	main	|	马上进行fix rate scheduled task
         1644421309255	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421311264	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644421313260	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421315267	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421317262	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421319257	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421321255	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421323264	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421325263	|	12	|	pool-llc-thread-2	|	fix rate runnable task
         1644421327264	|	14	|	pool-llc-thread-3	|	fix rate runnable task
         1644421329262	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644421331260	|	11	|	pool-llc-thread-1	|	fix rate runnable task
         1644421333261	|	14	|	pool-llc-thread-3	|	fix rate runnable task
         */
    }

    private static void scheduleWithFixedDelayExecManyTimes(ScheduledExecutorService executorService) {
        SmallTool.printTimeAndThread("马上进行scheduleWithFixedDelay的task");
        Runnable runnable = () -> SmallTool.printTimeAndThread("schedule with fixed delay runnable task");
        ScheduledFuture schedule = executorService.scheduleWithFixedDelay(runnable, 2,1, TimeUnit.SECONDS);
        try {
            SmallTool.printTimeAndThread("callable schedule result : " + schedule.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /**
         * 运行结果： 可见延迟2秒执行,之后每隔1秒执行一次
         *
         1644422818152	|	1	|	main	|	马上进行scheduleWithFixedDelay的task
         1644422820160	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422821163	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422822179	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422823194	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422824207	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         1644422825221	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422826221	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422827225	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422828241	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422829251	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422830261	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422831271	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422832286	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422833294	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422834303	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422835316	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422836328	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422837337	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         1644422838351	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422839361	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422840374	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422841387	|	12	|	pool-llc-thread-2	|	schedule with fixed delay runnable task
         1644422842401	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         1644422843408	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422844414	|	11	|	pool-llc-thread-1	|	schedule with fixed delay runnable task
         1644422845428	|	13	|	pool-llc-thread-3	|	schedule with fixed delay runnable task
         */
    }
}
