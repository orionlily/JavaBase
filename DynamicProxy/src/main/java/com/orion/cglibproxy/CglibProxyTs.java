package com.orion.cglibproxy;

/**
 * 可见代理类是CglibTs$$EnhancerByCGLIB$$c36e618b，后面加了一串东西
 *
 * @author Administrator
 */
public class CglibProxyTs {
    public static void main(String[] args) {
        CglibTs cglibTs = new CglibTs();
        ProxyFactory proxyFactory = new ProxyFactory(cglibTs);
        Object instance = proxyFactory.getInstance();
        System.out.println("instance clazz : " + instance.getClass() + " , cglibTs clazz : " + cglibTs.getClass());
        CglibTs temp = (CglibTs) instance;
        String[] favors = {"basketball","football"};
        System.out.println("show method : " + temp.showFavors(favors));

        System.out.println("=============================================");

        CglibFakeTs cglibFakeTs = new CglibFakeTs();
        proxyFactory = new ProxyFactory(cglibFakeTs);
        Object fakeInstance = proxyFactory.getInstance();
        CglibFakeTs fakeTemp = (CglibFakeTs) fakeInstance;
        String[] fakeFavors = {"ballet","locking"};
        System.out.println("fakeInstance clazz : " + fakeInstance.getClass() + " , cglibTs clazz : " + cglibFakeTs.getClass());

        System.out.println("show fake method : " + fakeTemp.jump(fakeFavors));
    }
    /**
     * 运行结果：
     *
     instance clazz : class com.orion.cglibproxy.CglibTs$$EnhancerByCGLIB$$c36e618b , cglibTs clazz : class com.orion.cglibproxy.CglibTs
     begin cglib proxy..
     end cglib proxy...
     show method : I have favors ：[basketball, football]
     =============================================
     fakeInstance clazz : class com.orion.cglibproxy.CglibFakeTs$$EnhancerByCGLIB$$60ee5df2 , cglibTs clazz : class com.orion.cglibproxy.CglibFakeTs
     begin cglib proxy..
     end cglib proxy...
     show fake method : I have interests ：[ballet, locking]

     */
}
