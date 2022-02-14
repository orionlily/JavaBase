package com.orion.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理工厂
 *
 * @author Administrator
 */
public class ProxyFactory implements MethodInterceptor{

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //第一个参数传递的是this，代表代理类本身，第二个参数标示拦截的方法，第三个参数是入参，第四个参数是cglib方法，
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("begin cglib proxy..");
        Object invoke = method.invoke(target, objects);
        System.out.println("end cglib proxy...");
        return invoke;
    }

    public Object getInstance() {
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(target.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
