package com.orion.base;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 通过 Hook 线程防止程序重复启动
 *
 * 现在很多软件包括 MySQL, Zookeeper, kafka 等都存在 Hook 线程的校验机制, 目的是校验进程是否已启动,防止重复启动程序.
 * Hook 线程也称为钩子线程, 当 JVM 退出的时候会执行 Hook 线程.
 * 经常在程序启动时创建一个.lock 文件, 用.lock 文件校验程序是否启动,在程序退出(JVM 退出)时删除该.lock 文件,
 * 在 Hook 线程中除了防止重新启动进程外,还可以做资源释放, 尽量避免在 Hook 线程中进行复杂的操作.
 *
 */
public class Hook {
    public static void main(String[] args) {
//1)注入 Hook 线程,在程序退出时删除.lock 文件
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("JVM 退出,会启动当前 Hook 线程,在 Hook 线程中删除.lock 文件");
                        getLockFile().toFile().delete();
            }
        });
//2)程序运行时,检查 lock 文件是否存在,如果 lock 文件存在,则抛出异常
        if (getLockFile().toFile().exists()) {
            throw new RuntimeException("程序已启动");
        } else { //文件不存在,说明程序是第一次启动,创建 lock 文件
            try {
                getLockFile().toFile().createNewFile();
                System.out.println("程序在启动时创建了 lock 文件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//模拟程序运行
        for (int i = 0; i < 10; i++) {
            System.out.println("程序正在运行");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static Path getLockFile() {
        return Paths.get("", "tmp.lock");
    }
}
/**
 * 运行结果： （根目录 会生产一个lock文件，程序运行结束后会自动删除）
 *
 程序在启动时创建了 lock 文件
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 程序正在运行
 JVM 退出,会启动当前 Hook 线程,在 Hook 线程中删除.lock 文件
 */
