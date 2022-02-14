package com.orion.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类工厂
 *
 * @author Administrator
 */
public class ProxyFactory {

    private Object object;

    public ProxyFactory(Object object) {
        this.object = object;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 这个抽象的invoke方法在代理类中动态实现。
                     *
                     * @param proxy  代理类
                     * @param method 被代理的方法
                     * @param args   该方法的参数数组
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("begin proxy...");
                        Object o = method.invoke(object, args);
                        System.out.println("end proxy...");
                        return o;
                    }
                });
    }
}
