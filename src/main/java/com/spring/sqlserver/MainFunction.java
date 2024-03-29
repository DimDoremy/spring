package com.spring.sqlserver;

import java.util.LinkedList;

/**
 * 类概述：主要功能实现类
 * 用途：连接数据库，输入两个字符串，返回两个字符串数组
 * 其他：
 */

public class MainFunction {
    private SqlCut SC;
    private WordsList WL;
    private SqlConnection SQL;
    private NotePad NP;
    private WordsRebuild WR;
    public LinkedList<String> answer_me_natural_word;
    public LinkedList<String> answer_me_SQL_word;

    public MainFunction() {
        SC = new SqlCut();
        WL = new WordsList();
        SQL = new SqlConnection();
        NP = new NotePad();
        WR = new WordsRebuild();
        answer_me_natural_word = new LinkedList<String>();
        answer_me_SQL_word = new LinkedList<String>();
    }

    public void funSQLConnection(String user, String pass, String dburl, String dbname) {//连接数据库函数
        SqlConnection.getConnection(user, pass, dburl, dbname);
    }

    public void funMain(String strNaturalWords, String strSQL) {//执行分词、重组操作函数
        if (SQL.trySentance(strSQL)) {
            SC.haveKeyWord(strSQL);
            SC.addList();
            WL.cutIntoWords(SC.markedWords, strNaturalWords);
            if (ErrorReturn.err == 1) {
                answer_me_natural_word.add("输入语句无法得到查询结果！");
                answer_me_SQL_word.add("输入SQL和输入自然语言不匹配！");
                ErrorReturn.err--;
                return;
            }
            NP.addNotes(SC.markedWords, SC.whereKeyWord1, SC.fromList, SQL);
            WR.mainWordRebuildAll(SC, WL, NP);
            answer_me_natural_word = WR.answer_me_natural_word;
            answer_me_SQL_word = WR.answer_me_SQL_word;
        } else {
            String err1 = "输入语句无法得到查询结果！";
            String err2 = "输入SQL语句不符合SQL语法！";
            answer_me_natural_word.add(err1);
            answer_me_SQL_word.add(err2);
            ErrorReturn.errorFailToSearchInDataBase();
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
