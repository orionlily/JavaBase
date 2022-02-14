package com.orion.cglibproxy;

import java.util.Arrays;

/**
 cglib与动态代理最大的区别就是

 使用动态代理的对象必须实现一个或多个接口
 使用cglib代理的对象则无需实现接口，达到代理类无侵入。
 使用cglib需要引入cglib的jar包，如果你已经有spring-core的jar包，则无需引入，因为spring中包含了cglib。
 *
 * @author Administrator
 */
public class CglibTs {

    public String showFavors(String[] favors){
        return "I have favors ：" + Arrays.toString(favors);
    }
}
