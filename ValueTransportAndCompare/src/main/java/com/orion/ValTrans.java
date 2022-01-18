package com.orion;

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

    public void changeIntegerLess128(Integer less128) {
        less128 = 12;
    }

    public void changeIntegerGe128(Integer ge128) {
        ge128 = 258;
    }


    public static void main(String[] args) {
        ValTrans valTrans = new ValTrans();
        int a = 555;
        valTrans.changeSimple(a);
        //基本类型保存在栈中，方法中的参数和传入的参数都是在栈中并保存为555，方法结束方法的参数就被干掉，所以改变不了值，还是原来定义的值
        System.out.println(a + "--- 152");//555--- 152

        String s = "orion";
        //方法的变量在栈中定义了，方法结束它就被干掉，进来的时候传参和方法参都指向同一个对象，后台方法参改变了指向，原来的没改变，所以还是原来的值
        valTrans.changeString(s);
        System.out.println(s + "--- change string"); //orion--- change string

        String v = "lily";
        //同上原理
        valTrans.changeStringNew(v);
        System.out.println(v + "--- change string new");//lily--- change string new

        Date d = new Date();
        //传参和方法参指向同一个，方法参改变了某个属性，所以原来的也改变了
        valTrans.changeDate(d);
        System.out.println(d + "--- 30");//Sun Jan 30 00:43:49 CST 2022--- 30

        Date k = new Date();
        //原来指向一致，后面方法参改变了指向，原来的没变
        valTrans.changeDateNew(k);
        System.out.println(k + "--- 2022-9-9");//Wed Jan 19 00:43:49 CST 2022--- 2022-9-9

        int[] ary = {1, 2, 3};
        //改变了某个属性，数组就是对象，故变化了
        valTrans.changeAry(ary);


        Integer less128_1 = 5;
        valTrans.changeIntegerLess128(less128_1);
        System.out.println(less128_1 + "--- 12");//5--- 12

        Integer less128_2 = 6;
        valTrans.changeIntegerGe128(less128_2);
        System.out.println(less128_2 + "--- 258");//6--- 258

        Integer ge128_1 = 555;
        valTrans.changeIntegerLess128(ge128_1);
        System.out.println(ge128_1 + "--- 12");//555--- 12

        Integer ge128_2 = 666;
        valTrans.changeIntegerGe128(ge128_2);
        System.out.println(ge128_2 + "--- 258");//666--- 258

    }

}
