package com.orion;

import java.io.*;

/**
 * 序列化方式进行深拷贝，避免层层clone,各个对象都要实现序列化Serializable接口
 *
 * @author Administrator
 */
public class SerializableDeepCopy {
    public static void main(String[] args) {
        SmartPhone sp = new SmartPhone("smartPhone", 1000);
        Phone phone1 = new Phone("phone", 5000, sp);
        Phone phone2 = phone1.cloneit();
        System.out.println("phone1: " + phone1 + ",detail: " + phone1.showPhoneDetail() + ",ss1 hashcode: " + phone1.hashCode());
        System.out.println("phone2: " + phone2 + ",detail: " + phone2.showPhoneDetail() + ",ss2 hashcode: " + phone2.hashCode());
        System.out.println("change phone2 name = fakePhone2 , name = 3000 , set smartPhone name = fakeSmartPhone2 , holdTime = 9000");
        phone2.setName("fakePhone2");
        phone2.setHoldTime(3000);
        phone2.getSp().setName("fakeSmartPhone2");
        phone2.getSp().setHoldTime(9000);
        System.out.println("phone1: " + phone1 + ",detail: " + phone1.showPhoneDetail() + ",ss1 hashcode: " + phone1.hashCode());
        System.out.println("phone2: " + phone2 + ",detail: " + phone2.showPhoneDetail() + ",ss2 hashcode: " + phone2.hashCode());
        /**
         * 运行结果：
           phone1: com.orion.Phone@7f31245a,detail: Phone{name='phone', holdTime=5000, sp=SmartPhone{name='smartPhone', holdTime=1000}},ss1 hashcode: 2133927002
           phone2: com.orion.Phone@4f3f5b24,detail: Phone{name='phone', holdTime=5000, sp=SmartPhone{name='smartPhone', holdTime=1000}},ss2 hashcode: 1329552164
           change phone2 name = fakePhone2 , name = 3000 , set smartPhone name = fakeSmartPhone2 , holdTime = 9000
           phone1: com.orion.Phone@7f31245a,detail: Phone{name='phone', holdTime=5000, sp=SmartPhone{name='smartPhone', holdTime=1000}},ss1 hashcode: 2133927002
           phone2: com.orion.Phone@4f3f5b24,detail: Phone{name='fakePhone2', holdTime=3000, sp=SmartPhone{name='fakeSmartPhone2', holdTime=9000}},ss2 hashcode: 1329552164
         *
         * 可见，通过serializable的深拷贝是成功的，phone2是phone1克隆出来，但phone2变化并不影响到phone1。
         */
    }

}

class Phone implements Serializable {
    private String name;
    private int holdTime;
    private SmartPhone sp;

    public Phone(String name, int holdTime, SmartPhone sp) {
        this.name = name;
        this.holdTime = holdTime;
        this.sp = sp;
    }

    public Phone cloneit() {
        Phone phone = null;
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Phone) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phone;
    }

    public String showPhoneDetail() {
        return "Phone{" +
                "name='" + name + '\'' +
                ", holdTime=" + holdTime +
                (sp == null ? "" : ", sp=" + sp.showSmartPhoneDetail()) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(int holdTime) {
        this.holdTime = holdTime;
    }

    public SmartPhone getSp() {
        return sp;
    }

    public void setSp(SmartPhone sp) {
        this.sp = sp;
    }
}

class SmartPhone implements Serializable {
    private String name;
    private int holdTime;

    public SmartPhone(String name, int holdTime) {
        this.name = name;
        this.holdTime = holdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(int holdTime) {
        this.holdTime = holdTime;
    }

    public String showSmartPhoneDetail() {
        return "SmartPhone{" +
                "name='" + name + '\'' +
                ", holdTime=" + holdTime +
                '}';
    }
}