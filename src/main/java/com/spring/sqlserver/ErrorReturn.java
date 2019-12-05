package com.spring.sqlserver;

/**
 * 类概述：抛出异常并在页面上显示的类
 * 功能：触发异常后执行下列内容
 * 其他：
 */

public class ErrorReturn {
    public static void errorInputMismatched() {
        // TODO: 2019/12/4 输入SQL和输入自然语言不匹配
    }

    public static void errorNoAttributeFoundInSheet() {
        // TODO: 2019/12/4 输入属性在输入表中不存在
    }

    public static void errorNoSheetFoundInDataBase() {
        // TODO: 2019/12/4 输入表在数据库中找不到
    }

    public static void errorFailToSearchInDataBase() {
        // TODO: 2019/12/4 输入SQL语句不符合SQL语法
    }
}
