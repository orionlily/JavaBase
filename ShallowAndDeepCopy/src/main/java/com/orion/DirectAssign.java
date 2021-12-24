package com.orion;

/**
 * 直接赋值 ,既改变原始的，也改变赋值的，因为指向都是通过一个地址，即一个地址两个别名罢了。
 * @author Administrator
 *
 */
public class DirectAssign {
    public static void main(String[] args) {
        DirectTeacher t1 = new DirectTeacher("Chinese", 3, 5);
        DirectTeacher t2 = t1;
        System.out.println("t1 : " + t1 + " , t2 : " + t2);

        t2.setFloor(4);
        t2.setLevel(2);
        t2.setSubject("Math");
        System.out.println("t1 : " + t1 + " , t2 : " + t2);
    }
    /*
     * t1 : DirectTeacher{subject='Chinese', level=3, floor=5} , t2 : DirectTeacher{subject='Chinese', level=3, floor=5}
     * t1 : DirectTeacher{subject='Math', level=2, floor=4} , t2 : DirectTeacher{subject='Math', level=2, floor=4}
     */
}

class DirectStudent {
    private String name;
    private Integer age;
    private int height;

    public DirectStudent(String name, Integer age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
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

    @Override
    public String toString() {
        return "DirectStudent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}

class DirectTeacher {
    private String subject;
    private Integer level;
    private int floor;

    public DirectTeacher(String subject, Integer level, int floor) {
        this.subject = subject;
        this.level = level;
        this.floor = floor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "DirectTeacher{" +
                "subject='" + subject + '\'' +
                ", level=" + level +
                ", floor=" + floor +
                '}';
    }
}
