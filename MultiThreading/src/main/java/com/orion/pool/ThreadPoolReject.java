package com.orion.pool;

import com.orion.SmallTool;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池的拒绝策略
 * <p>
 * 重写自定义的任务拒绝策略，可以从结果中看出，线程池处理不过来，对于多出的任务直接丢弃掉
 * <p>
 * 线程池能处理的最大任务数是queueSize + maxPoolSize
 *
 * @author Administrator
 */
public class ThreadPoolReject {

    private static ThreadPoolExecutor the = new ThreadPoolExecutor(3, 5, 10
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new CustomThreadFactory()
            , (r, executor) -> SmallTool.printTimeAndThread(" 正在放弃任务：" + r + " , 所属executor : " + executor)
    );

    public static void main(String[] args) {
        Runnable r = () -> {
            int sleepSec = new Random().nextInt(5) + 1;
            SmallTool.printTimeAndThread("睡眠秒数：" + sleepSec);
            SmallTool.sleepMillis(sleepSec * 1000);
        };


        for (int i = 0; i < 100; i++) {
            the.execute(r);
        }

        the.shutdown();

    }

    static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger atomicInteger = new AtomicInteger(0);

        private ThreadGroup group = new ThreadGroup("llc-group");

        private String prefix = "llc-thread-pool-";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(group, r, prefix + atomicInteger.incrementAndGet());
        }
    }
}
/**
 * 运行结果：
 * 没有自定义的拒绝策略，只会打印一次异常
 * Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task com.orion.pool.ThreadPoolReject$$Lambda$1/1595212853@77f03bb1 rejected from java.util.concurrent.ThreadPoolExecutor@7a92922[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
 * at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
 * at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
 * at com.orion.pool.ThreadPoolReject.main(ThreadPoolReject.java:34)
 * 1644248707487	|	13	|	llc-thread-pool-1	|	睡眠秒数：1
 * 1644248707488	|	14	|	llc-thread-pool-2	|	睡眠秒数：3
 * 1644248707489	|	15	|	llc-thread-pool-3	|	睡眠秒数：5
 * 1644248707489	|	16	|	llc-thread-pool-4	|	睡眠秒数：3
 * 1644248707490	|	17	|	llc-thread-pool-5	|	睡眠秒数：1
 * 1644248708502	|	13	|	llc
 * <p>
 * <p>
 * <p>
 * <p>
 * 自定义的拒绝策略
 * 1644248818763	|	11	|	llc-thread-pool-1	|	睡眠秒数：1
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818763	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818764	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818764	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818764	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818764	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818764	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818765	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818765	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818765	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818765	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818765	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818771	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818771	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818771	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818767	|	15	|	llc-thread-pool-5	|	睡眠秒数：4
 * 1644248818766	|	14	|	llc-thread-pool-4	|	睡眠秒数：2
 * 1644248818766	|	13	|	llc-thread-pool-3	|	睡眠秒数：1
 * 1644248818766	|	12	|	llc-thread-pool-2	|	睡眠秒数：5
 * 1644248818771	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818773	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818774	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818774	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818774	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818774	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818774	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818775	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818775	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818787	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818787	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818787	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818788	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818789	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818789	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818789	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818789	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818790	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818790	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818790	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818803	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818803	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818803	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818803	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818804	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818805	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818819	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818819	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818820	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818821	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818834	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818834	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818834	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248818835	|	1	|	main	|	 正在放弃任务：com.orion.pool.ThreadPoolReject$$Lambda$2/558638686@58372a00 , 所属executor : java.util.concurrent.ThreadPoolExecutor@4dd8dc3[Running, pool size = 5, active threads = 5, queued tasks = 5, completed tasks = 0]
 * 1644248819767	|	11	|	llc-thread-pool-1	|	睡眠秒数：1
 * 1644248819783	|	13	|	llc-thread-pool-3	|	睡眠秒数：3
 * 1644248820769	|	11	|	llc-thread-pool-1	|	睡眠秒数：5
 * 1644248820784	|	14	|	llc-thread-pool-4	|	睡眠秒数：2
 * 1644248822779	|	15	|	llc-thread-pool-5	|	睡眠秒数：1
 */
