package com.orion;

import java.util.Arrays;

/**
 * 1. 浅拷贝
 * 浅拷贝是按位拷贝对象，它会创建一个新对象，这个对象有着原始对象属性值的一份精确拷贝。如果属性是基本类型，
 * 拷贝的就是基本类型的值；如果属性是内存地址（引用类型），拷贝的就是内存地址 ，因此如果其中一个对象改变了这个地址，
 * 就会影响到另一个对象。即默认拷贝构造函数只是对对象进行浅拷贝复制(逐个成员依次拷贝)，即只复制对象空间而不复制资源。
 * <p>
 * 2. 浅拷贝特点
 * (1) 对于基本数据类型的成员对象，因为基础数据类型是值传递的，所以是直接将属性值赋值给新的对象。基础类型的拷贝，
 * 其中一个对象修改该值，不会影响另外一个。
 * (2) 对于引用类型，比如数组或者类对象，因为引用类型是引用传递，所以浅拷贝只是把内存地址赋值给了成员变量，
 * 它们指向了同一内存空间。改变其中一个，会对另外一个也产生影响。
 *
 * 2. 深拷贝
 * (1) 对于基本数据类型的成员对象，因为基础数据类型是值传递的，所以是直接将属性值赋值给新的对象。基础类型的拷贝，其中一个对象修改该值，不会影响另外一个（和浅拷贝一样）。
 * (2) 对于引用类型，比如数组或者类对象，深拷贝会新建一个对象空间，然后拷贝里面的内容，所以它们指向了不同的内存空间。改变其中一个，不会对另外一个也产生影响。
 * (3) 对于有多层对象的，每个对象都需要实现 Cloneable 并重写 clone() 方法，进而实现了对象的串行层层拷贝。
 * (4) 深拷贝相比于浅拷贝速度较慢并且花销较大。
 *
 * @author Administrator
 */
public class ShallowDeepCopy {
    /**
     * 之所以定义那么多class，因为需要重写clone方法，原本的Object对象的clone方法是protected的，不能直接调用的。
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowStudent ss1 = new ShallowStudent("lily", 12, 155, new int[]{50, 60, 70});
        //这是浅拷贝
        ShallowStudent ss2 = (ShallowStudent) ss1.clone();
        System.out.println("ss1: " + ss1 + ",detail: " + ss1.showShallowStudentDetail() + ",ss1 hashcode: " + ss1.hashCode());
        System.out.println("ss2: " + ss2 + ",detail: " + ss2.showShallowStudentDetail() + ",ss2 hashcode: " + ss2.hashCode());
        System.out.println("ss1.height == ss2.height? " + (ss1.getHeight() == ss2.getHeight()));
        System.out.println("ss1.age == ss2.age? " + (ss1.getAge() == ss2.getAge()));
        System.out.println("ss1.name == ss2.name? " + (ss1.getName() == ss2.getName()));
        System.out.println("ss1.cmeScores == ss2.cmeScores? " + (ss1.getCmeScores() == ss2.getCmeScores()));

        System.out.println("change ss1 age = 15 , name = orion , height = 162 , cmeScores[0] = 22:");
        ss1.setAge(15);
        ss1.setName("orion");
        ss1.setHeight(162);
        ss1.getCmeScores()[0] = 22;
        System.out.println("ss1: " + ss1 + ",detail: " + ss1.showShallowStudentDetail() + ",ss1 hashcode: " + ss1.hashCode());
        System.out.println("ss2: " + ss2 + ",detail: " + ss2.showShallowStudentDetail() + ",ss2 hashcode: " + ss2.hashCode());
        /**
         *
         * 结果如下：
         ss1: com.orion.ShallowStudent@28d93b30,detail: ShallowStudent{name='lily', cmeScores=[50, 60, 70], age=12, height=155},ss1 hashcode: 685325104
         ss2: com.orion.ShallowStudent@1b6d3586,detail: ShallowStudent{name='lily', cmeScores=[50, 60, 70], age=12, height=155},ss2 hashcode: 460141958
         ss1.height == ss2.height? true
         ss1.age == ss2.age? true
         ss1.name == ss2.name? true
         ss1.cmeScores == ss2.cmeScores? true
         change ss1 age = 15 , name = orion , height = 162 , cmeScores[0] = 22:
         ss1: com.orion.ShallowStudent@28d93b30,detail: ShallowStudent{name='orion', cmeScores=[22, 60, 70], age=15, height=162},ss1 hashcode: 685325104
         ss2: com.orion.ShallowStudent@1b6d3586,detail: ShallowStudent{name='lily', cmeScores=[22, 60, 70], age=12, height=155},ss2 hashcode: 460141958
         *
         * 结论：ShallowStudent实现了cloneable接口，重写了clone方法，创建1个对象，另一个clone，它们在栈中是分配两个地址的，
         * 都是指向同一个堆地址，height是int基本类型，其他都是对象类型，height直接比较value没问题，而改变了ss1的name为什么ss2没有改变呢，
         * 因为string是不可变类，它会再生一个string，而ss2改变了指向，所以ss1不变，ss2变，age也是integer不可变类也是同样道理,
         * 还有ss1和ss2的name、age用==来比较的话，结果是true，证明它们指向的地址是一样的，
         * 而数组对象cmeScores是可变对象，ss1和ss2指向同一个地址，改变了它，两个都是会发生变化的。
         *
         * 所以，我们来验证深浅拷贝，需要一个可变对象，用不可变对象是验证不出来的。
         */

        System.out.println("=====================不使用数组，使用2个自定义对象,可变对象参数不实现cloneable接口=====================");

        GrandFather gf1 = new GrandFather(70, "grandFather", new Father(40, "father"));
        GrandFather gf2 = (GrandFather) gf1.clone();
        System.out.println("gf1: " + gf1 + ",detail: " + gf1.showGrandFatherDetail() + ",gf1 hashcode: " + gf1.hashCode());
        System.out.println("gf2: " + gf2 + ",detail: " + gf2.showGrandFatherDetail() + ",gf2 hashcode: " + gf2.hashCode());
        System.out.println("clone gf2 change gfName = fakeGf , gfAge = 80 , father set fName = fakeF and fAge = 50");

        gf2.setGfName("fakeGf");
        gf2.setGfAge(80);
        gf2.getFather().setfAge(50);
        gf2.getFather().setfName("fakeF");

        System.out.println("gf1: " + gf1 + ",detail: " + gf1.showGrandFatherDetail() + ",gf1 hashcode: " + gf1.hashCode());
        System.out.println("gf2: " + gf2 + ",detail: " + gf2.showGrandFatherDetail() + ",gf2 hashcode: " + gf2.hashCode());

        /**
         * 运行结果：
         gf1: com.orion.GrandFather@4554617c,detail: GrandFather{gfAge=70, gfName='grandFather', father=Father{fAge=40, fName='father'}},gf1 hashcode: 1163157884
         gf2: com.orion.GrandFather@74a14482,detail: GrandFather{gfAge=70, gfName='grandFather', father=Father{fAge=40, fName='father'}},gf2 hashcode: 1956725890
         clone gf2 change gfName = fakeGf , gfAge = 80 , father set fName = fakeF and fAge = 50
         gf1: com.orion.GrandFather@4554617c,detail: GrandFather{gfAge=70, gfName='grandFather', father=Father{fAge=50, fName='fakeF'}},gf1 hashcode: 1163157884
         gf2: com.orion.GrandFather@74a14482,detail: GrandFather{gfAge=80, gfName='fakeGf', father=Father{fAge=50, fName='fakeF'}},gf2 hashcode: 1956725890
         *
         * 结论：
         * GrandFather有实现cloneable接口并且重写clone方法，而它的属性Father对象是没有实现cloneable接口的，
         * 克隆体gf2改变了father属性的子属性时，gf1也被改变了，结果可证，这也是一个浅拷贝。
         *
         */
        System.out.println("=====================不使用数组，使用2个自定义对象,可变对象参数实现cloneable接口=====================");

        GrandFather gf3 = new GrandFather(99, "grandFather", new Father(44, "father"), new Son(22, "son"));
        GrandFather gf4 = (GrandFather) gf3.clone();
        System.out.println("gf3: " + gf3 + ",detail: " + gf3.showGrandFatherDetail() + ",gf3 hashcode: " + gf3.hashCode());
        System.out.println("gf4: " + gf4 + ",detail: " + gf4.showGrandFatherDetail() + ",gf4 hashcode: " + gf4.hashCode());
        System.out.println("clone gf4 change gfName = trueGF , gfAge = 88 , father set fName = trueF and fAge = 55 , son set sName= trueS and sAge = 33");

        gf4.setGfName("trueGF");
        gf4.setGfAge(88);
        gf4.getFather().setfAge(55);
        gf4.getFather().setfName("trueF");
        gf4.getSon().setsAge(33);
        gf4.getSon().setsName("trueS");

        System.out.println("gf3: " + gf3 + ",detail: " + gf3.showGrandFatherDetail() + ",gf3 hashcode: " + gf3.hashCode());
        System.out.println("gf4: " + gf4 + ",detail: " + gf4.showGrandFatherDetail() + ",gf4 hashcode: " + gf4.hashCode());
        /**
         * 运行结果：
         gf3: com.orion.GrandFather@1540e19d,detail: GrandFather{gfAge=99, gfName='grandFather', father=Father{fAge=44, fName='father'}, son=Son{sAge=22, sName='son'}},gf3 hashcode: 356573597
         gf4: com.orion.GrandFather@677327b6,detail: GrandFather{gfAge=99, gfName='grandFather', father=Father{fAge=44, fName='father'}, son=Son{sAge=22, sName='son'}},gf4 hashcode: 1735600054
         clone gf4 change gfName = trueGF , gfAge = 88 , father set fName = trueF and fAge = 55 , son set sName= trueS and sAge = 33
         gf3: com.orion.GrandFather@1540e19d,detail: GrandFather{gfAge=99, gfName='grandFather', father=Father{fAge=55, fName='trueF'}, son=Son{sAge=22, sName='son'}},gf3 hashcode: 356573597
         gf4: com.orion.GrandFather@677327b6,detail: GrandFather{gfAge=88, gfName='trueGF', father=Father{fAge=55, fName='trueF'}, son=Son{sAge=33, sName='trueS'}},gf4 hashcode: 1735600054
         *
         * 结论：
         * GrandFather有实现cloneable接口并且重写clone方法，而它的属性Father对象是没有实现cloneable接口的，属性son有实现cloneable方法并重写clone方法
         * 同时GrandFather的clone方法也有子属性son的clone方法，如果子属性的子属性还有，就要一层一层嵌下去，比较麻烦，所以还有一种是serialize的方法进行深拷贝。
         * 由结果可以知道，实现了cloneable接口的son属性在被gf4改变的时候，gf3没有改变，说明他们的各自的son是独立的。
         * 所以实现深拷贝，就都需要实现cloneable接口并且重写clone方法，父类的clone方法还需要对子属性的clone。
         */
    }
}

/**
 * 非常需要注意的是，重写clone方法，是要实现cloneable接口的，
 * 如果不实现这个接口，则会抛出CloneNotSupportedException(克隆不被支持)异常
 */
class ShallowStudent implements Cloneable {
    private String name;
    private Integer age;
    private int height;
    private int[] cmeScores;

    public ShallowStudent(String name, Integer age, int height, int[] cmeScores) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.cmeScores = cmeScores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[] getCmeScores() {
        return cmeScores;
    }

    public void setCmeScores(int[] cmeScores) {
        this.cmeScores = cmeScores;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String showShallowStudentDetail() {
        return "ShallowStudent{" +
                "name='" + name + '\'' +
                ", cmeScores=" + Arrays.toString(cmeScores) +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}


class GrandFather implements Cloneable {
    private int gfAge;
    private String gfName;
    private Father father;
    private Son son;

    public GrandFather(int gfAge, String gfName) {
        this.gfAge = gfAge;
        this.gfName = gfName;
    }

    public GrandFather(int gfAge, String gfName, Father father) {
        this.gfAge = gfAge;
        this.gfName = gfName;
        this.father = father;
    }

    public GrandFather(int gfAge, String gfName, Son son) {
        this.gfAge = gfAge;
        this.gfName = gfName;
        this.son = son;
    }

    public GrandFather(int gfAge, String gfName, Father father, Son son) {
        this.gfAge = gfAge;
        this.gfName = gfName;
        this.father = father;
        this.son = son;
    }

    public int getGfAge() {
        return gfAge;
    }

    public void setGfAge(int gfAge) {
        this.gfAge = gfAge;
    }

    public String getGfName() {
        return gfName;
    }

    public void setGfName(String gfName) {
        this.gfName = gfName;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    public Son getSon() {
        return son;
    }

    public void setSon(Son son) {
        this.son = son;
    }

    public String showGrandFatherDetail() {
        return "GrandFather{" +
                "gfAge=" + gfAge +
                ", gfName='" + gfName + '\'' +
                (father == null ? "" : ", father=" + father.showFatherDetail()) +
                (son == null ? "" : ", son=" + son.showSonDetail()) +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        GrandFather grandFather = (GrandFather) super.clone();
        //深拷贝，除了子对象属性要实现cloneable接口重写clone方法，自身也要做同样操作，而且还要在自己的clone方法中写上子属性的
        //clone方法，有多少就嵌入多少，一直传递。
        if (son != null) {
            grandFather.son = (Son) son.clone();
        }
        return grandFather;
    }
}

class Father {
    private int fAge;
    private String fName;

    public Father(int fAge, String fName) {
        this.fAge = fAge;
        this.fName = fName;
    }

    public int getfAge() {
        return fAge;
    }

    public void setfAge(int fAge) {
        this.fAge = fAge;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String showFatherDetail() {
        return "Father{" +
                "fAge=" + fAge +
                ", fName='" + fName + '\'' +
                '}';
    }
}

class Son implements Cloneable {
    private int sAge;
    private String sName;

    public Son(int sAge, String sName) {
        this.sAge = sAge;
        this.sName = sName;
    }

    public int getsAge() {
        return sAge;
    }

    public void setsAge(int sAge) {
        this.sAge = sAge;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String showSonDetail() {
        return "Son{" +
                "sAge=" + sAge +
                ", sName='" + sName + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}



