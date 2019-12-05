package com.spring.sqlserver;

/**
 * 类概述：链表节点元素
 * 主要用途：存放链表上每个节点对应存放的元素
 * 其他：
 */

public class Words {
    public String content;//中文单词
    public String attribute;//词性

    public Words() {//构造函数
        content = "";
        attribute = "";
    }

    @Override
    public String toString() {//重载：按规则显示内容
        return content + " " + attribute;
    }
}
