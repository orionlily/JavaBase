package com.orion.jdkproxy;

/**
 * JDK动态代理测试，jdk动态代理是基于接口的，无接口只能用cglib
 *
 * 可见代理出来的是$Proxy,为代理对象，打印不能直接打印这个类，不然它的toString这样的方法又走一次invoke
 *
 * 动态生成，传入不同的对象均可以出来不同的代理
 *
 * @author Administrator
 */
public class JdkProxyTs {

    public static void main(String[] args) {
        IJdkTs iJdkTs = new JdkTs();
        ProxyFactory proxyFactory = new ProxyFactory(iJdkTs);
        Object o = proxyFactory.getInstance();

        System.out.println("show theirs clazz，iJdkTs = " + iJdkTs.getClass() + " : proxy o = " + o.getClass());

        IJdkTs jdkTs = (IJdkTs) o;
        System.out.println("run showFavor method : " + jdkTs.show(new String[]{"basketball,football"}));

        System.out.println("==================================================================");

        IJdkFakeTs iJdkFakeTs = new JdkFakeTs();
        ProxyFactory proxyFakeFactory = new ProxyFactory(iJdkFakeTs);
        Object fakeO = proxyFakeFactory.getInstance();

        System.out.println("show theirs clazz，iJdkFakeTs = " + iJdkFakeTs.getClass() + " : proxy fakeO = " + fakeO.getClass());

        IJdkFakeTs jdkFakeTs = (IJdkFakeTs) fakeO;
        System.out.println("run jumpFavor method : " + jdkFakeTs.jump(new String[]{"ballet,locking"}));
    }
    /*
     * 运行结果：
     *
    show theirs clazz，iJdkTs = class com.orion.jdkproxy.JdkTs : proxy o = class com.sun.proxy.$Proxy0
    begin proxy...
    end proxy...
    run showFavor method : I have favors ：[basketball,football]
    ==================================================================
    show theirs clazz，iJdkFakeTs = class com.orion.jdkproxy.JdkFakeTs : proxy fakeO = class com.sun.proxy.$Proxy1
    begin proxy...
    end proxy...
    run jumpFavor method : I have favors ：[ballet,locking]
     *
     */
}
