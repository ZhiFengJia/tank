package com.mashibing.tank.util;

import java.util.Random;

/**
 * @author Jia ZhiFeng <312290710@qq.com>
 * @date 2019/5/22 19:45:39
 */
public class RandomUtils {
    private static final Random random = new Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
