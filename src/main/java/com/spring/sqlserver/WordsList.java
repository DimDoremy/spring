package com.spring.sqlserver;

import java.util.LinkedList;

/**
 * 类概述：断词包
 * 主要用途：将标注好词性的语句拆为单词+词性的格式存放于链表待用
 * 其他：
 * 1.链表为wordList
 */

public class WordsList {
    private Words wordArray;//链表节点
    public LinkedList<Words> wordList;//链表

    public WordsList() {//构造函数
        wordArray = new Words();
        wordList = new LinkedList<Words>();
    }

    public void divideIntoWords(String str) {//分词并存入链表
        String temp[] = str.split(" ");
        int count = 0;
        while (count < temp.length - 1) {
            wordArray = new Words();
            String tmp[] = temp[count].split("_");
            wordArray.content = tmp[0];
            wordArray.attribute = tmp[1];
            wordList.add(wordArray);
            count++;
        }
    }

    public void cutIntoWords(LinkedList<Words> words, String str) {//将自然语言自左向右分解
        try {
            words = turnIntoOrder(words, str);
        } catch (IllegalArgumentException e) {
            ErrorReturn.errorInputMismatched();//ERROR提交SQL语句与自然语句不匹配情况
            return;
        }
        String temp[];
        int i = 0;
        int end = 0;
        for (end = 0; end != 1; ) {
            try {
                temp = str.split(words.get(i).content, 2);
            } catch (Exception e1) {
                try {
                    temp = str.split(words.get(i).content, 1);
                } catch (Exception e2) {
                    String saveme[] = new String[3];
                    saveme[0] = null;
                    saveme[1] = null;
                    saveme[2] = null;
                    temp = saveme;
                }
            }
            if (temp.length == 2) {
                wordArray = new Words();
                wordArray.content = temp[0];
                wordArray.attribute = "0";
                wordList.add(wordArray);
                wordArray = new Words();
                wordArray.attribute = words.get(i).attribute;
                wordList.add(wordArray);
                str = temp[1];
                i++;
            } else if (temp.length == 1) {
                wordArray = new Words();
                wordArray.attribute = words.get(i).attribute;
                wordList.add(wordArray);
                str = temp[0];
                i++;
            } else {
                wordArray = new Words();
                wordArray.content = str;
                wordArray.attribute = "0";
                wordList.add(wordArray);
                end = 1;
            }
        }
    }

    private LinkedList<Words> turnIntoOrder(LinkedList<Words> words, String str) {//将SQL语言中出现的关键词按自然语言中的出现顺序重新排列
        LinkedList<Words> temp = words;
        int count[] = new int[temp.size()];
        for (int i = 0; i < count.length; i++) {
            count[i] = str.indexOf(temp.get(i).content);
            if (count[i] < 0) {
                throw new IllegalArgumentException();
            }
        }
        int tp;
        String tmp;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count.length - 1; j++) {
                if (count[j] > count[j + 1]) {
                    tp = count[j];
                    count[j] = count[j + 1];
                    count[j + 1] = tp;
                    tmp = temp.get(j).content;
                    temp.get(j).content = temp.get(j + 1).content;
                    temp.get(j + 1).content = tmp;
                    tmp = temp.get(j).attribute;
                    temp.get(j).attribute = temp.get(j + 1).attribute;
                    temp.get(j + 1).attribute = tmp;
                }
            }
        }
        return temp;
    }

    public void showAllWords() {//显示全部节点内容
        for (int i = 0; i < wordList.size(); i++) {
            System.out.println(wordList.get(i).toString());
        }
    }

    public String returnWord() {
        return "Wrong Input!";
    }
}
