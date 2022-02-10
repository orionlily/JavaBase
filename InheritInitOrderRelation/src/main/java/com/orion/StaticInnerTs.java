package com.orion;

/**
 * 静态内部类的调用方式的测试
 *
 * @author Administrator
 */
public class StaticInnerTs {
 
    public static void main(String[] args) {
        OutsideClass.InsideClass.staticMethod("OutsideClass.InsideClass.staticMethod(methodName)");
        System.out.println("==========");

        new OutsideClass().outsideMethod("【一般类的非静态方法】 调用");
        System.out.println("==========");

        new OutsideClass.InsideClass();
        System.out.println("==========");

        new OutsideClass.InsideClass().insideMethod("【一般类的静态方法】 调用");
        System.out.println("==========");

        new StaticInnerTs().test();
        System.out.println("==========");

        //new OutsideClass.InsideClass().outsideMethod();//不能调用
    }
     
    public void test(){
        OutsideClass.InsideClass.staticMethod(new String("test()"));
        new OutsideClass.InsideClass().insideMethod("【一般类的一般方法】 调用");
    }
}
 
class OutsideClass{
    static class InsideClass{
         int anInt;
         
        // 静态内部类的构造方法
        public InsideClass(){
            System.out.println("静态内部类的【构造函数】");
        }
         
        public static void staticMethod(String methodName){
            System.out.println(methodName + "调用静态内部类 的 静态方法。——————可行！！！");
            new OutsideClass().outsideMethod("【静态内部类 的 静态方法】 调用");
            new OutsideClass.InsideClass().insideMethod("【静态内部类 的 静态方法】 调用");
        }
         
        public void insideMethod(String who){
            System.out.println(who + "静态内部类 中，非静态方法。——————可行！！！");
        }
    }
     
    public void outsideMethod(String who){
        System.out.println(who + "外部类 的 非静态方法。——————可行！！！");
    }
}

/**
 OutsideClass.InsideClass.staticMethod(methodName)调用静态内部类 的 静态方法。——————可行！！！
 【静态内部类 的 静态方法】 调用外部类 的 非静态方法。——————可行！！！
 静态内部类的【构造函数】
 【静态内部类 的 静态方法】 调用静态内部类 中，非静态方法。——————可行！！！
 ==========
 【一般类的非静态方法】 调用外部类 的 非静态方法。——————可行！！！
 ==========
 静态内部类的【构造函数】
 ==========
 静态内部类的【构造函数】
 【一般类的静态方法】 调用静态内部类 中，非静态方法。——————可行！！！
 ==========
 test()调用静态内部类 的 静态方法。——————可行！！！
 【静态内部类 的 静态方法】 调用外部类 的 非静态方法。——————可行！！！
 静态内部类的【构造函数】
 【静态内部类 的 静态方法】 调用静态内部类 中，非静态方法。——————可行！！！
 静态内部类的【构造函数】
 【一般类的一般方法】 调用静态内部类 中，非静态方法。——————可行！！！
 ==========
 *
 *
 */