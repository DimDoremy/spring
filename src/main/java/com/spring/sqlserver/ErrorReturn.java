package com.spring.sqlserver;

/**
 * 类概述：抛出异常并在页面上显示的类
 * 功能：触发异常后执行下列内容
 * 其他：
 */

class ErrorReturn {
    static int err = 0;

    static void errorInputMismatched() {
        System.out.println("输入SQL和输入自然语言不匹配");
        err++;
    }

    static void errorFailToSearchInDataBase() {
        System.out.println("输入SQL语句不符合SQL语法");
    }
}
