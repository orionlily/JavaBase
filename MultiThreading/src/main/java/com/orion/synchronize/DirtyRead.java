package com.orion.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * 脏读问题
 *
 * 正常是name和age要么一起是默认值，要么一起是更新值，如果出现了一个默认值，一个更新值，就证明是出现了脏读，如果getValue和
 * setValue不进行同步控制加synchronize，就会出现脏读，所以都加上
 *
 * @author Administrator
 */
public class DirtyRead {
    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        Thread t1 = new Thread(() -> {
            subClass.setValue("orion", 12);
        }, "t1");

        t1.start();
        //等待t1设置好
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> {
            subClass.getValue();
        }, "t2");
        t2.start();
    }
    /**
     * 运行结果：
     * Thread[t1,5,main]--setter -- name : orion -- age : 12
       Thread[t2,5,main]--getter -- name : orion -- age : 12

     不加 synchronize出现以下结果：
     Thread[t2,5,main]--getter -- name : orion -- age : 0
     Thread[t1,5,main]--setter -- name : orion -- age : 12
     */


}

class SubClass {
    private String name = "default";
    private Integer age = 0;

    public  void getValue() {
        System.out.println(Thread.currentThread() + "--getter -- name : " + name + " -- age : " + age);
    }

    public synchronized void setValue(String name, Integer age) {
        this.name = name;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.age = age;
        System.out.println(Thread.currentThread() + "--setter -- name : " + name + " -- age : " + age);
    }
}
