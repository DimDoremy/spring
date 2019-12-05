package com.spring.sqlserver;

/**
 * 类概述：取用顺序表
 * 功能：在可替换词中按一定规则进行替换，该部分提供当时应替换的目标
 * 其他：
 */

public class AddOne {
    public static int[] getResult(int[] len, int[] num) {
        try {//参数长度不一异常检测
            if (len.length != num.length)
                throw new ArrayIndexOutOfBoundsException("addOne.getResult方法的参数长度不一");
        } catch (ArrayIndexOutOfBoundsException e) {//异常处理
            System.out.println("异常：" + e.toString());
        }
        int pointer = num.length - 1;//定位到待处理数据的最后一位
        while (pointer > -1) {
            if (++num[pointer] >= len[pointer]) {
                num[pointer] = 0;
                --pointer;
            } else
                pointer = -2;
        }
        try {//进位超位异常检测
            if (pointer == -1)
                throw new NullPointerException("addOne.getResult方法进位超过限制长度");
        } catch (NullPointerException e) {//异常处理
            System.out.println("异常：" + e.toString());
        }
        return num;
    }
}
