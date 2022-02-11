package com.orion;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 测试下改变对象是否会改变其hashCode
 *
 * @author Administrator
 */
public class HashCodeWillChangeTs {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        Object value = new Object();

        Integer integer = new Integer(5);
        //integer重写hashCode方法，hashCode就等于value
        System.out.println(integer.hashCode()); // 5

        hashMap.put(integer, value);
        System.out.println(hashMap);//5

        integer = 128;
        System.out.println(integer.hashCode()); // 128,integer是不可变类，这样等于指向另外一个对象
        System.out.println(hashMap.get(integer));// null,已经换了对象了，肯定找不出来了，内存泄露了
        hashMap.clear();

        System.out.println("=======================");

        //数组是可变对象
        String[] strAy = new String[]{"1", "2", "3"};
        System.out.println(strAy.hashCode()); //460141958
        hashMap.put(strAy, value);
        hashMap.forEach((k, v) ->System.out.println(Arrays.toString((String[])k) + "--" + v));//[1, 2, 3]--java.lang.Object@28d93b30

        strAy[2] = "44";
        System.out.println(strAy.hashCode()); //460141958，strAry指向堆中一块内存，内存地址一直没变，变的是那块内存上的数据
        System.out.println(hashMap.get(strAy));//java.lang.Object@28d93b30，依然能拿出来，它的hashCode、equals本质没有重写过。

        hashMap.clear();
        System.out.println("=======================");

        //没有重写hashCode和equals的自定义类
        DemoClazz demoClazz = new DemoClazz(4, "demoClazz");
        System.out.println(demoClazz.hashCode());//460141958
        hashMap.put(demoClazz,value);
        System.out.println(hashMap);//{DemoClazz{age=4, name='demoClazz'}=java.lang.Object@28d93b30}

        demoClazz.setName("fakeDemoClazz");
        System.out.println(demoClazz.hashCode());//460141958,和上面同样的道理
        System.out.println(hashMap.get(demoClazz));//java.lang.Object@28d93b30

        hashMap.clear();
        System.out.println("=======================");

        //只重写hashCode没有重写equals的自定义类
        DemoClazzOverHashCode demoClazzOverHashCode = new DemoClazzOverHashCode(99, "demoClazzOverHashCode");
        System.out.println(demoClazzOverHashCode.hashCode());//822272993
        hashMap.put(demoClazzOverHashCode,value);
        System.out.println(hashMap);//{DemoClazzOverHashCode{age=99, name='demoClazzOverHashCode'}=java.lang.Object@28d93b30}

        demoClazzOverHashCode.setName("fakeDemoClazzOverHashCode");
        System.out.println(demoClazzOverHashCode.hashCode());//-617106548,hashCode已经变化
        System.out.println(hashMap.get(demoClazzOverHashCode));//null，找不到桶的位置了，造成内存泄露

        hashMap.clear();
        System.out.println("=======================");

        //既重写hashCode也重写equals的自定义类
        DemoClazzOverHashAndEquals demoClazzOverHashAndEquals = new DemoClazzOverHashAndEquals(88, "demoClazzOverHashAndEquals");
        System.out.println(demoClazzOverHashAndEquals.hashCode());//-962172025
        hashMap.put(demoClazzOverHashAndEquals,value);
        System.out.println(hashMap);//{DemoClazzOverHashAndEquals{age=88, name='demoClazzOverHashAndEquals'}=java.lang.Object@28d93b30}

        demoClazzOverHashAndEquals.setName("fakeDemoClazzOverHashAndEquals");
        System.out.println(demoClazzOverHashAndEquals.hashCode());//-1142444356,hashCode已经变化
        System.out.println(hashMap.get(demoClazzOverHashAndEquals));//null，找不到桶的位置了，造成内存泄露


    }

    static class DemoClazz {
        private Integer age;
        private String name;

        public DemoClazz(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DemoClazz{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static class DemoClazzOverHashCode {
        private Integer age;
        private String name;

        public DemoClazzOverHashCode(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            int result = age.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "DemoClazzOverHashCode{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static class DemoClazzOverHashAndEquals{
        private Integer age;
        private String name;

        public DemoClazzOverHashAndEquals(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DemoClazzOverHashAndEquals that = (DemoClazzOverHashAndEquals) o;

            if (!age.equals(that.age)) return false;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            int result = age.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "DemoClazzOverHashAndEquals{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
