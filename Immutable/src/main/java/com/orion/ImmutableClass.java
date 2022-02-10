package com.orion;

import java.util.Arrays;

/**
 * 不可变对象遵循：
 * 需要遵循以下几个原则：
 * <p>
 * 1）immutable对象的状态在创建之后就不能发生改变，任何对它的改变都应该产生一个新的对象。
 * <p>
 * 2）Immutable类的所有的成员都应该是private final的。通过这种方式保证成员变量不可改变。
 * 但只做到这一步还不够，因为如果成员变量是对象，它保存的只是引用，有可能在外部改变其引用指向的值，所以第5点弥补这个不足
 * <p>
 * 3）对象必须被正确的创建，比如：对象引用在对象创建过程中不能泄露。
 * <p>
 * 4）只提供读取成员变量的get方法，不提供改变成员变量的set方法，避免通过其他接口改变成员变量的值，破坏不可变特性。
 * <p>
 * 5）类应该是final的，保证类不被继承，如果类可以被继承会破坏类的不可变性机制，只要继承类覆盖父类的方法
 * 并且继承类可以改变成员变量值，那么一旦子类以父类的形式出现时，不能保证当前类是否可变。
 * <p>
 * 6）如果类中包含mutable类对象，那么返回给客户端的时候，返回该对象的一个深拷贝，而不是该对象本身（该条可以归为第一条中的一个特例）
 *
 * @author Administrator
 */
public class ImmutableClass {
    private final int[] ary;
    private final String[] strAry;

    public ImmutableClass(int[] array, String[] strAry) {
        this.ary = Arrays.copyOf(array, array.length);
        this.strAry = strAry.clone();
    }

    public int[] getAry() {
        return ary;
    }

    public String[] getStrAry() {
        return strAry.clone();
    }

    /**
     * 如下可见，外部传入的数组对象，作为参数给MutableClass进行构造初始化，然后改变外部的数组对象，然而类对象里的属性并不改变了
     * 所以是不可变的，类构造初始化时，弄成深拷贝，直接是独立的一份数据，就像string类中那样：
     * public String(char value[]) {
     * this.value = Arrays.copyOf(value, value.length);
     * }
     * 它并不是直接将传入的数组赋值给value属性，而是copy一份
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] imAry = {77, 88, 99};
        String[] imStrAry = {"aa", "bb", "cc"};
        ImmutableClass immutableClass = new ImmutableClass(imAry, imStrAry);
        System.out.println("arg imAry : " + Arrays.toString(imAry) + " , imStrAry : " + Arrays.toString(imStrAry));
        System.out.println("immutableClass imAry :" + Arrays.toString(immutableClass.getAry()) + " , imStrAry : "
                + Arrays.toString(immutableClass.getStrAry()));

        imAry[2] = 111;
        imStrAry[2] = "dd";
        System.out.println("arg imAry and imStrAry change index 2 , arg imAry : " + Arrays.toString(imAry) + " , imStrAry : " + Arrays.toString(imStrAry));
        System.out.println("after ary imAry and imStrAry change , immutableClass imAry :" + Arrays.toString(immutableClass.getAry()) + " , imStrAry : "
                + Arrays.toString(immutableClass.getStrAry()));

        immutableClass.getAry()[2] = 222;
        String[] tempStrAry = immutableClass.getStrAry();
        System.out.println("immutableClass.getStrAry() == immutableClass.strAry ? " + (tempStrAry == immutableClass.strAry));
        System.out.println("get immutableClass imAry and imStrAry and change , immutableClass imAry :" + Arrays.toString(immutableClass.getAry()) + " , imStrAry : "
                + Arrays.toString(immutableClass.getStrAry()));

        /**
         * 运行结果：
         arg imAry : [77, 88, 99] , imStrAry : [aa, bb, cc]
         immutableClass imAry :[77, 88, 99] , imStrAry : [aa, bb, cc]
         arg imAry and imStrAry change index 2 , arg imAry : [77, 88, 111] , imStrAry : [aa, bb, dd]
         after ary imAry and imStrAry change , immutableClass imAry :[77, 88, 99] , imStrAry : [aa, bb, cc]
         immutableClass.getStrAry() == immutableClass.strAry ? false
         get immutableClass imAry and imStrAry and change , immutableClass imAry :[77, 88, 222] , imStrAry : [aa, bb, cc]
         *
         * 以上可以得出，因为immutableClass初始化调用构造函数的时候，用的是拷贝技术，而不是直接赋值的方式，所以传参和内部属性时分离的，
         * 这相当于是深拷贝了，都是独立的一份，传入的数组在外边改变了，并不影响immutableClass类的属性，但在get方法中，分别对于2个参数，
         * 一个直接方法，一个调用clone来返回，在外面修改了，直接返回的也跟随变了，clone的没变。
         *
         * 同时证明直接对于数组的clone，其实就是一个深拷贝，对于ShallowAndDeepCopy项目中ShallowDeepCopy的ShallowStudent测试那样，
         * 作为其他类的子属性其实只是浅拷贝，直接使用数组的clone是深拷贝。
         */
    }
}
