package com.orion.interrupt;


import com.orion.SmallTool;

import java.util.concurrent.TimeUnit;

/**
 * 强制线程睡够指定秒数
 *
 * @author Administrator
 */

public class ForceSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            SmallTool.printTimeAndThread("开始睡眠");
            forceSleep(3);
            SmallTool.printTimeAndThread("结束睡眠");
        });
        thread.start();
        thread.interrupt();
    }

    public static void forceSleep(int second) {
        long startTime = System.currentTimeMillis();
        long sleepMills = TimeUnit.SECONDS.toMillis(second);
        while ((startTime + sleepMills) > System.currentTimeMillis()) {
            long sleepTime = startTime + sleepMills - System.currentTimeMillis();
            if (sleepTime <= 0) {
                break;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 运行结果：
         1641830113018	|	11	|	Thread-0	|	开始睡眠
         java.lang.InterruptedException: sleep interrupted
         at java.lang.Thread.sleep(Native Method)
         at com.orion.interrupt.ForceSleep.forceSleep(ForceSleep.java:37)
         at com.orion.interrupt.ForceSleep.lambda$main$0(ForceSleep.java:17)
         at java.lang.Thread.run(Thread.java:748)
         1641830116029	|	11	|	Thread-0	|	结束睡眠
         */
    }
}
