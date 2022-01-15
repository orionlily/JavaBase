package com.orion.volatileEx;

import java.util.concurrent.TimeUnit;

/**
 * 只有单个线程，这个程序一开始就是死循环，跳不出去执行不到flag设置为false，根本停不下来，无关有无volatile
 *
 * @author Administrator
 */
public class VolatileSingle {

    public static void main(String[] args) {
        SubClazz subClazz = new SubClazz();
        subClazz.longContinue();


        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这句是无效的，死循环根本停不下来，执行不到这一句
        subClazz.setFlag(false);
        System.out.println("is Flag : " + subClazz.isFlag());
        /**
         * 运行结果：
         long continue in thread :main
         long continue in thread :main
         long continue in thread :main
         。。。。
         */
    }
}

class SubClazz{
    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void longContinue(){
        while (flag){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("long continue in thread :" + Thread.currentThread().getName());
        }
        System.out.println("long continue end");
    }
}
