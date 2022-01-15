package com.orion.state;

import com.orion.SmallTool;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试lockSupport的状态、使用unpark和interrupt之后的状态
 *
 * 可以知道lockSupport的park之后，线程是处于waiting状态的
 *
 * @author Administrator
 */
public class LockSupportState {

    public static void main(String[] args) {
        System.out.println("============================测试LockSupport是WAITING，以及unPark============================");
        LockSupportPark e = new LockSupportPark("e");

        e.start();
        SmallTool.sleepMillis(20);
        SmallTool.printTimeAndThread("让e被park住，查看状态，e：" + e.getState());
        LockSupport.unpark(e);
        SmallTool.sleepMillis(20);
        SmallTool.printTimeAndThread("之后main线程使用unpark唤醒它，查看状态，e：" + e.getState());

        SmallTool.sleepMillis(20);
        System.out.println("============================测试LockSupport是WAITING，以及打断============================");

        LockSupportPark f = new LockSupportPark("f");
        f.start();
        SmallTool.sleepMillis(20);
        SmallTool.printTimeAndThread("让f被park住，查看状态，f：" + f.getState());
        f.interrupt();
        SmallTool.sleepMillis(20);
        SmallTool.printTimeAndThread("之后main线程使用interrupt打断它，查看状态，f：" + f.getState());
    }
    /**
     * 运行结果：
     ============================测试LockSupport是WAITING，以及unPark============================
     1642177729984	|	11	|	e	|	e线程被park之前
     1642177730013	|	1	|	main	|	让e被park住，查看状态，e：WAITING
     1642177730013	|	11	|	e	|	e线程被park之后
     1642177730045	|	1	|	main	|	之后main线程使用unpark唤醒它，查看状态，e：TERMINATED


     ============================测试LockSupport是WAITING，以及打断============================
     1642177730077	|	12	|	f	|	f线程被park之前
     1642177730109	|	1	|	main	|	让f被park住，查看状态，f：WAITING
     1642177730109	|	12	|	f	|	f线程被park之后
     1642177730141	|	1	|	main	|	之后main线程使用interrupt打断它，查看状态，f：TERMINATED

     */
}


class LockSupportPark extends Thread {

    public LockSupportPark(String name) {
        super(name);
    }

    @Override
    public void run() {
        SmallTool.printTimeAndThread(currentThread().getName() + "线程被park之前");
        LockSupport.park(this);
        SmallTool.printTimeAndThread(currentThread().getName() + "线程被park之后");
    }
}
