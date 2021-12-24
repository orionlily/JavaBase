package com.orion;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Administrator
 * @date 2021/12/19
 */
public class ValTrans {

    public void changeSimple(int a) {
        a = 152;
    }

    public void changeString(String a) {
        a = "change string";
    }

    public void changeStringNew(String a) {
        a = new String("change string new");
    }

    public void changeDate(Date d) {
        d.setDate(30);
    }

    public void changeDateNew(Date d) {
        d = new Date(2022, 9, 9);
    }

    public void changeAry(int[] a) {
        a[0] = 9;
    }


    public static void main(String[] args) {
        int a = 555;
        String s = "orion";

        String v = "lily";

        Date d = new Date();

        Date k = new Date();

        int[] ary = {1, 2, 3};


        ValTrans valTrans = new ValTrans();


        valTrans.changeSimple(a);
        //基本类型保存在栈中，方法中的参数和传入的参数都是在栈中并保存为555，方法结束方法的参数就被干掉，所以改变不了值，还是原来定义的值
        System.out.println(a + "--- 152");

        //方法的变量在栈中定义了，方法结束它就被干掉，进来的时候传参和方法参都指向同一个对象，后台方法参改变了指向，原来的没改变，所以还是原来的值
        valTrans.changeString(s);
        System.out.println(s + "--- change string");

        //同上原理
        valTrans.changeStringNew(v);
        System.out.println(v + "--- change string new");

        //传参和方法参指向同一个，方法参改变了某个属性，所以原来的也改变了
        valTrans.changeDate(d);
        System.out.println(d + "--- 30");

        //原来指向一致，后面方法参改变了指向，原来的没变
        valTrans.changeDateNew(k);
        System.out.println(k + "--- 2022-9-9");

        //改变了某个属性，数组就是对象，故变化了
        valTrans.changeAry(ary);
        System.out.println(Arrays.toString(ary) + "--- ary[0] 9");
    }

}
