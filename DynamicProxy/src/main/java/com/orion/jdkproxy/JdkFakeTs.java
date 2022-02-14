package com.orion.jdkproxy;

import java.util.Arrays;

/**
 * 真正处理业务的类
 *
 * @author Administrator
 */
public class JdkFakeTs implements IJdkFakeTs {

    @Override
    public String jump(String[] favors) {
        return "I have favors ：" + Arrays.toString(favors);
    }
}
