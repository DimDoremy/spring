package com.spring.sqlserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * 类概述：jdbc数据库连接整合引用包
 * 主要用途：java与sql server接口
 * 静态对象：SqlConnection
 * 主要函数：getConnection()
 * 使用方法：SqlConnection.getConnection(user,pass,dburl,dbname)
 * 返回值类型：void
 * 其他：
 * 1.如果不输入任何参数，则使用默认身份登录默认数据库
 * 2.在登陆数据库成功后，dbConn将保存该登录
 */

public class SqlConnection {
    private static Connection dbConn;//数据库连接对象

    public Words wordArray;//链表节点
    public LinkedList<Words> wordList;//链表

    public SqlConnection() {
    }

    public static void getConnection() {//默认登录函数
        //登录用户名
        String user = "sa";
        //登录密码
        String pass = "123456";
        //数据库本地接口
        String dburl = "localhost:1433";
        //数据库名称
        String dbname = "SQLconnection";
        dbConn = null;
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://" + dburl + ";DatabaseName=" + dbname;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("加载JDBC驱动成功！");
            dbConn = DriverManager.getConnection(dbURL, user, pass);
            System.out.println("数据库连接成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getConnection(String user, String pass, String dburl, String dbname) {//用户输入登录函数
        dbConn = null;
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://" + dburl + ";DatabaseName=" + dbname;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //System.out.println("加载JDBC驱动成功！");
            dbConn = DriverManager.getConnection(dbURL, user, pass);
            //System.out.println("数据库连接成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Words> searchattribute(String sheet) {//找出表下所有属性
        wordList = new LinkedList<Words>();
        try {
            Statement statement = dbConn.createStatement();
            String sentence = "select name from syscolumns where id=(select max(id) from sysobjects where xtype='u' and name='" + sheet + "')";
            ResultSet sets = statement.executeQuery(sentence);
            while (sets.next()) {
                wordArray = new Words();
                wordArray.content = sets.getString("Name");
                wordList.add(wordArray);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return wordList;
    }

    public LinkedList<Words> searchcontent(String sheet, String attribute) {//找出某属性下所有属性值
        wordList = new LinkedList<Words>();
        try {
            Statement statement = dbConn.createStatement();
            String sentence = "select " + attribute + " from " + sheet;
            ResultSet sets = statement.executeQuery(sentence);
            while (sets.next()) {
                wordArray = new Words();
                wordArray.content = sets.getString(attribute);
                wordList.add(wordArray);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return wordList;
    }

    public boolean selectDataBase(String sheet) {//判断该表是否在该数据库下
        try {
            Statement statement = dbConn.createStatement();
            String sentence = "select name from syscolumns where id=(select max(id) from sysobjects where xtype='u' and name='" + sheet + "')";
            ResultSet sets = statement.executeQuery(sentence);
            if (sets.next()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean selectSheet(String sheet, String attribute) {//判断该属性是否在该表下
        try {
            Statement statement = dbConn.createStatement();
            String sentence = "select " + attribute + " from " + sheet;
            ResultSet sets = statement.executeQuery(sentence);
            if (sets.next()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean trySentance(String str) {//判断该句是否能得到运行结果
        try {
            Statement statement = dbConn.createStatement();
            String sentence = str;
            ResultSet sets = statement.executeQuery(sentence);
            if (sets.next()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void showAllWords(LinkedList<Words> temp) {
        try {
            for (int i = 0; i < temp.size(); i++) {
                System.out.println(temp.get(i).toString());
            }
        } catch (Exception e) {
        }
    }
}
