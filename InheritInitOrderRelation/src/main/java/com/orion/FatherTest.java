package com.orion;

/**
 * 打印顺序：
 *
 * 静态代码块--代码块--构造函数(有参打印有参，无参打印无参)--其他
 *
 * @author Administrator
 */
public class FatherTest {

    private String name;

    public FatherTest() {
        System.out.println("--父类的无参构造函数--");
    }

    public FatherTest(String name) {
        this.name = name;
        System.out.println("--父类的有参构造函数--" + this.name);
    }

    static {
        System.out.println("--父类的静态代码块--");
    }

    {
        System.out.println("--父类的非静态代码块--");
    }

    public void speak() {
        System.out.println("--父类的方法--");
    }
// 加入一个main程序后

    public static void main(String[] args) {
        System.out.println("--父类主程序--");
        FatherTest father = new FatherTest("父亲的名字");
        father.speak();
    }
}
/**
 * 运行结果：
 *
 --父类的静态代码块--
 --父类主程序--
 --父类的非静态代码块--
 --父类的有参构造函数--父亲的名字
 --父类的方法--
 */