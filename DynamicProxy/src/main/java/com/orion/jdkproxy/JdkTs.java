package com.orion.jdkproxy;

import java.util.Arrays;

/**
 * 真正处理业务的类
 *
 * @author Administrator
 */
public class JdkTs implements IJdkTs {

    @Override
    public String show(String[] favors) {
        return "I have favors ：" + Arrays.toString(favors);
    }
}
