package com.orion;

import java.util.Arrays;

/**
 * @author Administrator
 * @date 2021/12/19
 */
public class FinalVariable {

    public static void main(String[] args) {
        final int i = 10;
        //i =11;//提示错误了，基本类型final的变量指向地址不能被改变

        final String a = "abc";
        //a = "bcd";//提示错误了，String类型final的变量指向地址不能被改变

        final int[] arr = {1, 2, 3, 45};
        //int[] a1 = {123};
        //arr = a1; 不能赋值给final
        arr[3] = 999;
        arr.toString(); //数组可以调用clone、toString等Object的方法，侧面反映它是对象了，指向地址不变，内容可变
        System.out.println(Arrays.toString(arr));

        ChildTestFuncToExtend childTestFuncToExtend = new ChildTestFuncToExtend();
        childTestFuncToExtend.notFinalFunc();
        childTestFuncToExtend.finalFunc();
    }
}

/**
 * 继承有final方法的非final类 测试
 */

class ParentTestFunc {
    //final方法不能被继承类重写的
    public final void finalFunc() {
        System.out.println("parent : final func");
    }

    public void notFinalFunc() {
        System.out.println("parent : not final func");
    }
}

class ChildTestFuncToExtend extends ParentTestFunc {
    @Override
    public void notFinalFunc() {
        System.out.println("child : not final func can be override");
    }
}

/**
 * 继承有final修饰的类 测试
 */

//final类不能被继承
final class ParentTestClass {

}

//报错了，不能继承final修饰的类
//class ChildTestClassToExtend extends ParentTestClass{}


