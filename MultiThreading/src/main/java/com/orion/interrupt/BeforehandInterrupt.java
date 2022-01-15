package com.orion.interrupt;

import com.orion.SmallTool;

/**
 * 下面的例子就像没有睡眠一样，为什么呢，因为进入睡眠时已经检测到中断标志位是true了，
 *  立马去到exception中进行处理，不再继续睡了
 *
 * @author Administrator
 */
public class BeforehandInterrupt {
    public static void main(String[] args) {

        Thread.currentThread().interrupt();

        try {
            SmallTool.printTimeAndThread("开始睡眠");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            SmallTool.printTimeAndThread("发生中断");
        }

        SmallTool.printTimeAndThread("结束睡眠");
        /**
         * 运行结果：看时间戳
         * 1641827782934	|	1	|	main	|	开始睡眠
           1641827782934	|	1	|	main	|	发生中断
           1641827782934	|	1	|	main	|	结束睡眠
         *
         */
    }
}
