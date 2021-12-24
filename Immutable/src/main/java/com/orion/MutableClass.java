package com.orion;

import java.util.Arrays;

/**
 * 不可变对象遵循：
 * 需要遵循以下几个原则：

 1）immutable对象的状态在创建之后就不能发生改变，任何对它的改变都应该产生一个新的对象。

 2）Immutable类的所有的成员都应该是private final的。通过这种方式保证成员变量不可改变。
    但只做到这一步还不够，因为如果成员变量是对象，它保存的只是引用，有可能在外部改变其引用指向的值，所以第5点弥补这个不足

 3）对象必须被正确的创建，比如：对象引用在对象创建过程中不能泄露。

 4）只提供读取成员变量的get方法，不提供改变成员变量的set方法，避免通过其他接口改变成员变量的值，破坏不可变特性。

 5）类应该是final的，保证类不被继承，如果类可以被继承会破坏类的不可变性机制，只要继承类覆盖父类的方法
    并且继承类可以改变成员变量值，那么一旦子类以父类的形式出现时，不能保证当前类是否可变。

 6）如果类中包含mutable类对象，那么返回给客户端的时候，返回该对象的一个深拷贝，而不是该对象本身（该条可以归为第一条中的一个特例）
 *
 *
 * @author Administrator
 */
public class MutableClass {

    private final int[] ary;
    public MutableClass(int[] array) {
        this.ary = array;
    }

    public int[] getAry() {
        return ary;
    }

    /**
     * 如下可见，外部传入的数组对象，作为参数给MutableClass进行构造初始化，然后改变外部的数组对象，然而类对象里的属性也被改变了
     * 所以并不是不可变的，类内部属性和传参数组都是指向堆中的同一个地方，它们只是不同标识名字而已，堆的数据改变，两个都受影响。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] ary = {1,2,3};
        System.out.println("arg ary init : " + Arrays.toString(ary));
        MutableClass mutableClass = new MutableClass(ary);
        System.out.println("mutableClass ary :" + Arrays.toString(mutableClass.getAry()));

        ary[2] = 9 ;
        System.out.println("arg ary change index 2 : " + Arrays.toString(ary));

        System.out.println("after ary change , mutableClass ary :" + Arrays.toString(mutableClass.getAry()));

        /**
         * 运行结果：
           arg ary init : [1, 2, 3]
           mutableClass ary :[1, 2, 3]
           arg ary change index 2 : [1, 2, 9]
           after ary change , mutableClass ary :[1, 2, 9]
         */
    }

}
