package com.orion;

/**
 * 有继承的打印顺序：
 * 注意静态代码块只打印一次，代码块会打印多次
 *
 * 父类静态代码块-子类静态代码块-父类代码块-父类无参构造函数-子类代码块--子类构造函数(有参打印有参，无参打印无参)-其他
 *
 * @author Administrator
 */
public class SonTest extends FatherTest {
    private String name;
    static {
        System.out.println("--子类的静态代码块--");
    }

    {
        System.out.println("--子类的非静态代码块--");
    }

    SonTest() {
        System.out.println("--子类的无参构造函数--");
    }

    SonTest(String name) {
        this.name = name;
        System.out.println("--子类的有参构造函数--" + this.name);
    }

    @Override
    public void speak() {
        System.out.println("--子类Override了父类的方法--");
    }

    // 然后再加入一个main函数
    public static void main(String[] args) {
        System.out.println("--子类主程序--");
        FatherTest father = new FatherTest("父亲的名字");
        father.speak();
        System.out.println("==============");
        SonTest son1 = new SonTest("儿子1的名字");
        son1.speak();
        System.out.println("==============");
        SonTest son2 = new SonTest("儿子2的名字");
        son2.speak();
    }
    /**
     * 运行结果：
     *
     --父类的静态代码块--
     --子类的静态代码块--
     --子类主程序--
     --父类的非静态代码块--
     --父类的有参构造函数--父亲的名字
     --父类的方法--
     ==============
     --父类的非静态代码块--
     --父类的无参构造函数--
     --子类的非静态代码块--
     --子类的有参构造函数--儿子1的名字
     --子类Override了父类的方法--
     ==============
     --父类的非静态代码块--
     --父类的无参构造函数--
     --子类的非静态代码块--
     --子类的有参构造函数--儿子2的名字
     --子类Override了父类的方法--
     */
}