package com.orion.volatileEx;

/**
 * 单例模式，懒汉式，双重检测锁+volatileEx
 * 因为new 创建对象有多个步骤，jvm可能会重排序，所有用volatile
 *
 * @author Administrator
 */
public class DoubleCheckLockVolatile {

    /**
     * volatileEx 防止指令重排 和 可见性
     */
    private static volatile Object object;

    public static Object getObjectInstance() {
        if (object == null) {
            synchronized (DoubleCheckLockVolatile.class) {
                if (object == null) {
                    object = new Object();
                    System.out.println("thread =" + Thread.currentThread().getName());
                }
            }
        }
        return object;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                Object object = getObjectInstance();
                if (object == null) {
                    System.out.println("object == null ? " + (object == null));
                }
            }).start();
        }
    }


}
