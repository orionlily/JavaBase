package com.orion.pool;

import com.orion.SmallTool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试invokeAll,传入是callable
 *
 * @author Administrator
 */
public class ThreadPoolExecutorInvokeTs {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(3, 5,
                5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), SmallTool.getCustomThreadFactory()
                , (r, executor) -> SmallTool.printTimeAndThread("放弃任务：" + r + "，所属线程池：" + executor));
        Callable<Integer> callable = () -> 999;
        Callable<Integer> callable2 = () -> 888;

        try {
            List<Future<Integer>> futures = executorService.invokeAll(Arrays.asList(callable, callable2));

            futures.forEach(item -> {
                try {
                    SmallTool.printTimeAndThread(item.get().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
