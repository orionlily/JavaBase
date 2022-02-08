package com.orion.pool;

import com.orion.SmallTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * forkJoin采用的是分而治之的方式，将大任务分解为多个小任务来处理
 *
 * @author Administrator
 */
public class ForkJoinPoolTs {

    private static final Integer THRESHOLD = 1000;

    public static void main(String[] args) {
        int begin = 1000;
        int end = 6000;

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(new CustomForkJoinTask(begin, end));
        try {
            long start = System.currentTimeMillis();
            SmallTool.printTimeAndThread("ForkJoin结果：" + submit.get() + " , 耗时ms：" + (System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //for循环方式
        loopSum(begin, end);

        //jdk8的parallel
        parallelSum(begin, end);
    }

    /**
     * 运行结果：
     1644333766793	|	1	|	main	|	ForkJoin结果：17503500 , 耗时ms：3
     1644333766793	|	1	|	main	|	for循环结果：17503500 , 耗时ms：0
     1644333766991	|	1	|	main	|	使用stream的parallel计算结果：17503500 , 耗时ms：197
     */

    static class CustomForkJoinTask extends RecursiveTask<Integer> {
        private Integer begin;
        private Integer end;

        public CustomForkJoinTask(Integer begin, Integer end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if (end - begin > THRESHOLD) {
                int cnt = (end - begin) / THRESHOLD;
                int mod = (end - begin) % THRESHOLD == 0 ? 0 : 1;
                cnt = cnt + mod;
                List<CustomForkJoinTask> taskAry = new ArrayList<>(cnt);

                int from = begin;
                for (int i = 0; i < cnt; i++) {
                    int to = from + THRESHOLD;
                    if (to > end) {
                        to = end;
                    }

                    CustomForkJoinTask temp = new CustomForkJoinTask(from, to);
                    taskAry.add(temp);
                    from = to + 1;
                    temp.fork();
                }
                for (CustomForkJoinTask task : taskAry) {
                    sum += task.join();
                }
            } else {
                for (int i = begin; i <= end; i++) {
                    sum += i;
                }
            }
            return sum;
        }
    }

    private static void loopSum(int begin, int end) {
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = begin; i <= end; i++) {
            sum += i;
        }
        SmallTool.printTimeAndThread("for循环结果：" + sum + " , 耗时ms：" + (System.currentTimeMillis() - start));
    }

    private static void parallelSum(int begin, int end) {
        long start = System.currentTimeMillis();
        int sum = IntStream.rangeClosed(begin, end).parallel().reduce(0, Integer::sum);
        SmallTool.printTimeAndThread("使用stream的parallel计算结果：" + sum + " , 耗时ms：" + (System.currentTimeMillis() - start));
    }
}
