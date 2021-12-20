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
        System.out.println(a + "--- 152");

        valTrans.changeString(s);
        System.out.println(s + "--- change string");

        valTrans.changeStringNew(v);
        System.out.println(v + "--- change string new");

        valTrans.changeDate(d);
        System.out.println(d + "--- 30");

        valTrans.changeDateNew(k);
        System.out.println(k + "--- 2022-9-9");

        valTrans.changeAry(ary);
        System.out.println(Arrays.toString(ary) + "--- ary[0] 9");
    }

}
