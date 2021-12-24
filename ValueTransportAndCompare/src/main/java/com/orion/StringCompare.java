package com.orion;

/**
 * @author Administrator
 */
public class StringCompare {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = new String("abc");
        String str3 = str2;
        String str4 = "abc";
        char[] ary = {'a', 'b', 'c'};
        String str5 = new String(ary);
        String str6 = new String("abc").intern();
        String str7 = "ab" + "c";

        //==比的是内存地址
        System.out.println("str1 == str2 :" + (str1 == str2)); //str1在常量池，str2在heap，false
        System.out.println("str2 == str3 :" + (str2 == str3));//指向heap的内存地址是一样的，true
        System.out.println("str1 == str3 :" + (str1 == str3));//同理①，false
        System.out.println("str1 == str4 :" + (str1 == str4));//都在常量池，复用，true
        System.out.println("str1 == str5 :" + (str1 == str5));//str1在常量池，str5在heap，false
        System.out.println("str2 == str5 :" + (str2 == str5));//str2、str5分别new，不同的heap地址，false
        System.out.println("str1 == str6 :" + (str1 == str6));//都是常量池的，true
        System.out.println("str2 == str6 :" + (str2 == str6));//不同heap，false
        System.out.println("str1 == str7 :" + (str1 == str7)); //str7编译器已经确定，底层拼好abc放去常量池，true

        //str8在堆会创建两个对象ab，c，并且弄到常量池中，+是底层用了stringbuild拼接的，str8指向堆中的结果abc对象，但它不会存到常量池
        String str8 = new String("ab") + new String("c");
        String str9 = new String("ab") + "c";
        System.out.println("str8 == str1 :" + (str8 == str1)); //不同heap，false
        System.out.println("str8 == str2 :" + (str8 == str2)); //不同heap，false
        System.out.println("str8 == str7 :" + (str8 == str7)); //不同heap，false

        System.out.println("str8 == str9 :" + (str8 == str9)); //不同heap，false
        System.out.println("str9 == str7 :" + (str9 == str7)); //不同heap，false
        //使用intern，由于str1是字面量直接创建，str8.intern()无需再创建，如果没有它才创建到常量池，注意1.6是直接拷贝字符串值，而1.7+是拷贝对象地址
        System.out.println("str8 intern == str1 :" + (str8.intern() == str1)); //常量池，true
        System.out.println("str9 intern == str1 :" + (str9.intern() == str1)); //常量池，true

        System.out.println("str8 intern self == str8 :" + (str8.intern() == str8)); //intern是常量池，str8是heap，false
        System.out.println("str9 intern self == str9 :" + (str9.intern() == str9)); //intern是常量池，str9是heap，false

        String str10 = new String("hij");//常量池一个，堆一个
        System.out.println("new string str10 intern self :" + (str10.intern() == str10));//intern指向常量池，str10指向堆，false

        String str11 = new String("rst");//生成在堆一个，常量池一个，但str11只指向堆的
        str11.intern(); //等于没用
        String str12 = "rst";//常量池有了这个字符串，指向了它，跟str11毫无关系所以false
        System.out.println("new string str11 before str12 intern == str12 :" + (str11 == str12));


        String str13 = new String("uy");//生成在堆一个，常量池一个，但str11只指向堆的
        String str14 = "uy";//常量池有了这个字符串，指向了它，跟str13毫无关系,所以还是false
        str13.intern();//等于没用，在前在后都一个样
        System.out.println("new string str13 after str14 intern == str14 :" + (str13 == str14));

        //5个对象，2个常量池(lm,n)，3个堆(lm,n,lmn)，但str15的值是lmn,1.7的intern,常量池找不到lmn，将str15的地址弄到常量池中
        // (如果已存在就不弄，会返回false，看str19，str20)，
        //即str15 intern的地址就是str15的地址，所以是true
        String str15 = new String("lm") + new String("n");
        System.out.println("new double string str11 intern self :" + (str15.intern() == str15));

        String str16 = new String("op") + "q";
        System.out.println("one new one constant string str12 intern self :" + (str16.intern() == str16));

        //与上其实同理
        String str17 = new String("vw") + new String("x");//还是5个对象，不赘述
        str17.intern(); //将vwx的地址，即str17的地址直接弄到常量池
        String str18 = "vwx";//常量池已经有了vwx的地址了，故一样true
        System.out.println("new string str17 before str18 intern == str18 :" + (str17 == str18));

        //换个intern顺序
        String str19 = new String("y") + new String("z");//还是5个对象，不赘述
        String str20 = "yz";//直接在常量池生产yz字符串，str20并指向了它
        str19.intern();//常量池已经有了，这步等于没用，堆和常量池比较，false
        System.out.println("new string str19 before str20 intern == str20:" + (str19 == str20));
    }

}
