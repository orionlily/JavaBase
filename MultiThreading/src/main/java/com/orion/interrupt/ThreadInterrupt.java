package com.orion.interrupt;

import com.orion.SmallTool;

import java.util.Random;

/**
 * @author Administrator
 */
public class ThreadInterrupt {
    public static void main(String[] args) {

        Thread carTwo = new Thread(() -> {
            SmallTool.printTimeAndThread("卡丁2号 准备过桥");
            SmallTool.printTimeAndThread("发现1号在过，开始等待");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                SmallTool.printTimeAndThread("卡丁2号 开始过桥");
            }
            SmallTool.printTimeAndThread("卡丁2号 过桥完毕");
        });


        Thread carOne = new Thread(() -> {
            SmallTool.printTimeAndThread("卡丁1号 开始过桥");
            int timeSpend = new Random().nextInt(500) + 1000;
            SmallTool.sleepMillis(timeSpend);
            SmallTool.printTimeAndThread("卡丁1号 过桥完毕 耗时:" + timeSpend);
            SmallTool.printTimeAndThread("卡丁2号的状态" + carTwo.getState());
            //如果不加这句，carTwo的InterruptedException打印不会触发，而且要等完5秒才打印卡丁2号过桥完毕
            //加上的话，立刻中断并继续执行下去
            carTwo.interrupt();
        });

        carOne.start();
        carTwo.start();
    }
    /**
     * 运行结果：
     * （注释carTwo.interrupt,，看前面的时间戳）
     1641822835997	|	12	|	Thread-1	|	卡丁1号 开始过桥
     1641822835999	|	11	|	Thread-0	|	卡丁2号 准备过桥
     1641822835999	|	11	|	Thread-0	|	发现1号在过，开始等待
     1641822837375	|	12	|	Thread-1	|	卡丁1号 过桥完毕 耗时:1375
     1641822837375	|	12	|	Thread-1	|	卡丁2号的状态TIMED_WAITING
     1641822846008	|	11	|	Thread-0	|	卡丁2号 过桥完毕
     *
     * 不注释
     1641824076546	|	12	|	Thread-1	|	卡丁1号 开始过桥
     1641824076548	|	11	|	Thread-0	|	卡丁2号 准备过桥
     1641824076548	|	11	|	Thread-0	|	发现1号在过，开始等待
     1641824077600	|	12	|	Thread-1	|	卡丁1号 过桥完毕 耗时:1050
     1641824077600	|	12	|	Thread-1	|	卡丁2号的状态TIMED_WAITING
     1641824077600	|	11	|	Thread-0	|	卡丁2号 开始过桥
     1641824077601	|	11	|	Thread-0	|	卡丁2号 过桥完毕
     *
     */
}
