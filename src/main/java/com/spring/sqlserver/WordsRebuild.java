package com.spring.sqlserver;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 类概述：将零散的词重组成句
 * 功能：将可替换位置使用替换词替代重构成新的自然语言和SQL语言
 * 其他：
 */

public class WordsRebuild {
    private String str;
    public LinkedList<String> answer_me_natural_word;
    public LinkedList<String> answer_me_SQL_word;

    public WordsRebuild() {
        str = "";
        answer_me_natural_word = new LinkedList<String>();
        answer_me_SQL_word = new LinkedList<String>();
    }

    public void mainWordRebuildAll(SqlCut SC, WordsList WL, NotePad NP) {
        int alllength = 1;
        String natural_word;//暂时用来存储一条合成自然语言句子
        String SQL_word;//暂时用来存储一条合成SQL语言句子
        int a[] = new int[NP.notePadList.size()];
        //int b[]=new int[NP.notePadList.size()];
        int c[] = new int[NP.notePadList.size()];
        for (int i = 0; i < NP.notePadList.size(); i++) {
            alllength = alllength * NP.notePadList.get(i).size();
            a[i] = NP.notePadList.get(i).size();
            //b[i]=0;
            c[i] = 0;
        }
        for (int i = 0; i < alllength; i++) {//笛卡尔积列出所有可变列表
            natural_word = "";
            SQL_word = "";
            //以下句块重组自然语句
            for (int wltemp = 0; wltemp < WL.wordList.size(); wltemp++) {//重组自然语句
                if (Objects.equals(WL.wordList.get(wltemp).attribute, "0")) {
                    natural_word = natural_word + WL.wordList.get(wltemp).content;
                } else {
                    int loc = 0;
                    for (int nwtemp = 0; nwtemp < WL.wordList.size(); nwtemp++) {
                        if (!Objects.equals(WL.wordList.get(nwtemp).attribute, "0")) {
                            if (Objects.equals(WL.wordList.get(wltemp).attribute, WL.wordList.get(nwtemp).attribute)) {
                                natural_word = natural_word + NP.notePadList.get(loc).get(c[loc]).content;
                                break;
                            }
                            loc++;
                        }
                    }
                }
            }
            answer_me_natural_word.add(natural_word);//使合成的自然语言入链
            //以下句块重组SQL自然语句
            if (SC.haveSelect >= 0) {//重构select部分
                SQL_word = SQL_word + "SELECT ";
                for (int sctemp = 0; sctemp < SC.selectList.size(); sctemp++) {
                    if (Objects.equals(SC.selectList.get(sctemp).attribute, "0")) {
                        SQL_word = SQL_word + SC.selectList.get(sctemp).content;
                    } else {
                        int loc = 0;
                        for (int wltemp = 0; wltemp < WL.wordList.size(); wltemp++) {
                            if (!Objects.equals(WL.wordList.get(wltemp).attribute, "0")) {
                                if (Objects.equals(SC.selectList.get(sctemp).attribute, WL.wordList.get(wltemp).attribute)) {
                                    SQL_word = SQL_word + NP.notePadList.get(loc).get(c[loc]).content;
                                    break;
                                }
                                loc++;
                            }
                        }
                    }
                    if (sctemp < SC.selectList.size() - 1) {
                        SQL_word = SQL_word + ",";
                    }
                }
            }
            if (SC.haveFrom >= 0) {//重构from部分
                SQL_word = SQL_word + " FROM ";
                for (int sctemp = 0; sctemp < SC.fromList.size(); sctemp++) {
                    SQL_word = SQL_word + SC.fromList.get(sctemp).content;
                    if (sctemp < SC.fromList.size() - 1) {
                        SQL_word = SQL_word + ",";
                    }
                }
            }
            if (SC.haveWhere >= 0) {//重构where部分
                SQL_word = SQL_word + " WHERE ";
                for (int sctemp = 0; sctemp < SC.whereList.size(); sctemp++) {
                    if (Objects.equals(SC.whereList.get(sctemp).attribute, "0")) {
                        SQL_word = SQL_word + SC.whereList.get(sctemp).content;
                    } else {
                        for (int wKWtemp = 0; wKWtemp < SC.whereKeyWord1.size(); wKWtemp++) {
                            if (Objects.equals(SC.whereKeyWord1.get(wKWtemp).attribute, SC.whereList.get(sctemp).attribute)) {
                                SQL_word = SQL_word + SC.whereKeyWord1.get(wKWtemp).content + "='";
                            }
                        }
                        int loc = 0;
                        for (int wltemp = 0; wltemp < WL.wordList.size(); wltemp++) {
                            if (!Objects.equals(WL.wordList.get(wltemp).attribute, "0")) {
                                if (Objects.equals(SC.whereList.get(sctemp).attribute, WL.wordList.get(wltemp).attribute)) {
                                    SQL_word = SQL_word + NP.notePadList.get(loc).get(c[loc]).content + "'";
                                    break;
                                }
                                loc++;
                            }
                        }
                    }
                }
                SQL_word = SQL_word + " ";
            }
            if (SC.haveGroupBy >= 0) {//重构groupby部分
                SQL_word = SQL_word + "GROUP BY ";
                for (int sctemp = 0; sctemp < SC.groupbyList.size(); sctemp++) {
                    if (Objects.equals(SC.groupbyList.get(sctemp).attribute, "0")) {
                        SQL_word = SQL_word + SC.groupbyList.get(sctemp).content;
                    } else {
                        int loc = 0;
                        for (int wltemp = 0; wltemp < WL.wordList.size(); wltemp++) {
                            if (!Objects.equals(WL.wordList.get(wltemp).attribute, "0")) {
                                if (Objects.equals(SC.groupbyList.get(sctemp).attribute, WL.wordList.get(wltemp).attribute)) {
                                    SQL_word = SQL_word + NP.notePadList.get(loc).get(c[loc]).content;
                                    break;
                                }
                                loc++;
                            }
                        }
                    }
                    if (sctemp < SC.groupbyList.size() - 1) {
                        SQL_word = SQL_word + ",";
                    }
                }
            }
            answer_me_SQL_word.add(SQL_word);//使合成的SQL语句入链
            if (i != alllength - 1) {
                c = AddOne.getResult(a, c);//使得下一组的数值更改一位
            }
        }
    }

    public void showAllWords() {
        for (int i = 0; i < answer_me_natural_word.size(); i++) {
            System.out.println(answer_me_natural_word.get(i) + " " + i);
        }
        for (int i = 0; i < answer_me_SQL_word.size(); i++) {
            System.out.println(answer_me_SQL_word.get(i) + " " + i);
        }
    }
}
