package com.orion.lock;

import com.orion.SmallTool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 带条件的锁 condition
 * <p>
 * condition使用await、signal/signalAll必须先拿到锁
 *
 * @author Administrator
 */
public class ConditionLock {

    private static int cnt = 0;

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        Runnable r = () -> {
            reentrantLock.lock();
            try {
                SmallTool.printTimeAndThread("获得锁，执行计算");
                for (int i = 0; i < 1_000_000; i++) {
                    if (i == 500_000) {
                        SmallTool.printTimeAndThread("正在 await，让出锁");
                        //等待队列，main + 前九个子线程
                        if (reentrantLock.getWaitQueueLength(condition) == 10) {
                            SmallTool.printTimeAndThread("我来唤醒 main");
                            condition.signal();
                        }
                        condition.await();
                        SmallTool.printTimeAndThread("醒来，继续计算");
                    }
                    cnt++;
                }
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("被打断抛出异常：" + e.getMessage());
            } finally {
                SmallTool.printTimeAndThread("执行完毕，释放锁");
                //子线程都回到同步队列，main在最后，当最后的子线程获得锁，它出列，queue只剩下main
                if (reentrantLock.getQueueLength() == 1) {
                    SmallTool.printTimeAndThread("又一次唤醒 main 线程");
                    condition.signal();
                }
                reentrantLock.unlock();
            }
        };

        try {
            reentrantLock.lock();
            SmallTool.printTimeAndThread("进行 await，让锁给子线程");

            for (int i = 0; i < 10; i++) {
                Thread thread = new Thread(r, "t" + i);
                thread.start();
            }

            condition.await();
            SmallTool.printTimeAndThread("子线程计算到一半，让 main 中途醒来，cnt = " + cnt);
            SmallTool.printTimeAndThread("继续让出锁，唤醒 所有子线程 继续进行计算");
            condition.signalAll();
            condition.await();
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("被打断抛出异常：" + e.getMessage());
        } finally {
            SmallTool.printTimeAndThread("释放锁，最终得到cnt = " + cnt);
            reentrantLock.unlock();
        }
    }
}
/**
 * 运行结果：
 1642484544411	|	1	|	main	|	进行 await，让锁给子线程
 1642484544413	|	14	|	t1	|	获得锁，执行计算
 1642484544420	|	14	|	t1	|	正在 await，让出锁
 1642484544421	|	13	|	t0	|	获得锁，执行计算
 1642484544422	|	13	|	t0	|	正在 await，让出锁
 1642484544422	|	15	|	t2	|	获得锁，执行计算
 1642484544433	|	15	|	t2	|	正在 await，让出锁
 1642484544433	|	16	|	t3	|	获得锁，执行计算
 1642484544434	|	16	|	t3	|	正在 await，让出锁
 1642484544434	|	17	|	t4	|	获得锁，执行计算
 1642484544435	|	17	|	t4	|	正在 await，让出锁
 1642484544435	|	18	|	t5	|	获得锁，执行计算
 1642484544437	|	18	|	t5	|	正在 await，让出锁
 1642484544437	|	19	|	t6	|	获得锁，执行计算
 1642484544438	|	19	|	t6	|	正在 await，让出锁
 1642484544438	|	20	|	t7	|	获得锁，执行计算
 1642484544439	|	20	|	t7	|	正在 await，让出锁
 1642484544439	|	21	|	t8	|	获得锁，执行计算
 1642484544440	|	21	|	t8	|	正在 await，让出锁
 1642484544441	|	22	|	t9	|	获得锁，执行计算
 1642484544442	|	22	|	t9	|	正在 await，让出锁
 1642484544442	|	22	|	t9	|	我来唤醒 main
 1642484544442	|	1	|	main	|	子线程计算到一半，让 main 中途醒来，cnt = 5_000_000
 1642484544442	|	1	|	main	|	继续让出锁，唤醒 所有子线程 继续进行计算
 1642484544442	|	14	|	t1	|	醒来，继续计算
 1642484544443	|	14	|	t1	|	执行完毕，释放锁
 1642484544443	|	13	|	t0	|	醒来，继续计算
 1642484544449	|	13	|	t0	|	执行完毕，释放锁
 1642484544450	|	15	|	t2	|	醒来，继续计算
 1642484544453	|	15	|	t2	|	执行完毕，释放锁
 1642484544454	|	16	|	t3	|	醒来，继续计算
 1642484544455	|	16	|	t3	|	执行完毕，释放锁
 1642484544455	|	17	|	t4	|	醒来，继续计算
 1642484544457	|	17	|	t4	|	执行完毕，释放锁
 1642484544458	|	18	|	t5	|	醒来，继续计算
 1642484544459	|	18	|	t5	|	执行完毕，释放锁
 1642484544459	|	19	|	t6	|	醒来，继续计算
 1642484544461	|	19	|	t6	|	执行完毕，释放锁
 1642484544461	|	20	|	t7	|	醒来，继续计算
 1642484544462	|	20	|	t7	|	执行完毕，释放锁
 1642484544462	|	21	|	t8	|	醒来，继续计算
 1642484544463	|	21	|	t8	|	执行完毕，释放锁
 1642484544463	|	21	|	t8	|	又一次唤醒 main 线程
 1642484544463	|	22	|	t9	|	醒来，继续计算
 1642484544464	|	22	|	t9	|	执行完毕，释放锁
 1642484544465	|	22	|	t9	|	又一次唤醒 main 线程
 1642484544465	|	1	|	main	|	释放锁，最终得到cnt = 10_000_000

 */

