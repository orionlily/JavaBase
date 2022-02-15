package com.orion.base;

import com.orion.SmallTool;

/**
 * yield() 的作用是让出cpu，但只从运行状态回到就绪状态，仍有机会争夺cpu
 *
 * @author Administrator
 */
public class Yield {

    public static void main(String[] args) {
        YieldThread yieldThread1 = new YieldThread("yield-orion");
        YieldThread yieldThread2 = new YieldThread("yield-lily");

        yieldThread1.start();
        yieldThread2.start();
    }
    /**
     * 运行结果：
     * 1642493230206	|	12	|	yield-lily	|	i = 0
     * 1642493230206	|	11	|	yield-orion	|	i = 0
     * 1642493230207	|	12	|	yield-lily	|	i = 1
     * 1642493230207	|	11	|	yield-orion	|	i = 1
     * 1642493230207	|	11	|	yield-orion	|	i = 2
     * 1642493230207	|	11	|	yield-orion	|	i = 3
     * 1642493230207	|	11	|	yield-orion	|	i = 4
     * 1642493230207	|	12	|	yield-lily	|	i = 2
     * 1642493230207	|	11	|	yield-orion	|	 thread yield
     * 1642493230207	|	12	|	yield-lily	|	i = 3
     * 1642493230207	|	12	|	yield-lily	|	i = 4
     * 1642493230207	|	12	|	yield-lily	|	 thread yield
     * 1642493230208	|	11	|	yield-orion	|	i = 5
     * 1642493230208	|	12	|	yield-lily	|	i = 5
     * 1642493230208	|	12	|	yield-lily	|	i = 6
     * 1642493230208	|	12	|	yield-lily	|	i = 7
     * 1642493230208	|	12	|	yield-lily	|	i = 8
     * 1642493230208	|	12	|	yield-lily	|	i = 9
     * 1642493230208	|	11	|	yield-orion	|	i = 6
     * 1642493230209	|	11	|	yield-orion	|	i = 7
     * 1642493230209	|	11	|	yield-orion	|	i = 8
     * 1642493230209	|	11	|	yield-orion	|	i = 9
     */

    static class YieldThread extends Thread {

        public YieldThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    SmallTool.printTimeAndThread(" thread yield");
                    Thread.yield();
                }
                SmallTool.printTimeAndThread("i = " + i);
            }
        }
    }
}
