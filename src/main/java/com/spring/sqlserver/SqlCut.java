package com.spring.sqlserver;

import java.util.LinkedList;

/**
 * 类概述：SQL语句分隔类
 * 用途：按照一定的规则将SQL语句进行划分，同时进行适当储存
 * 其他：
 */

public class SqlCut {
    private String originalString;
    public int haveSelect;
    public LinkedList<Words> selectList;//select下元素链表
    public int haveFrom;
    public LinkedList<Words> fromList;//from下元素链表
    public int haveWhere;
    public LinkedList<Words> whereList;//where下元素链表
    public int haveGroupBy;
    public LinkedList<Words> groupbyList;//groupby链表

    private Words wordArray;//链表节点

    public LinkedList<Words> whereKeyWord1;//where下元素链表
    public LinkedList<Words> whereKeyWord2;//where下元素链表
    public LinkedList<Words> markedWords;//标记可变单词

    public SqlCut() {
        haveSelect = -1;
        haveFrom = -1;
        haveWhere = -1;
        haveGroupBy = -1;
    }

    public void haveKeyWord(String sqlwords) {//找出关键字在sql语句中的位置
        originalString = sqlwords;
        haveSelect = keyWordLocation(sqlwords.indexOf("select"), sqlwords.indexOf("Select"), sqlwords.indexOf("SELECT"));
        haveFrom = keyWordLocation(sqlwords.indexOf("from"), sqlwords.indexOf("From"), sqlwords.indexOf("FROM"));
        haveWhere = keyWordLocation(sqlwords.indexOf("where"), sqlwords.indexOf("Where"), sqlwords.indexOf("WHERE"));
        haveGroupBy = keyWordLocation(sqlwords.indexOf("group by"), sqlwords.indexOf("Group By"), sqlwords.indexOf("GROUP BY"));
    }

    private int keyWordLocation(int a, int b, int c) {//返回关键字位置
        int temp = -1;
        if (temp < a) {
            temp = a;
        }
        if (temp < b) {
            temp = b;
        }
        if (temp < c) {
            temp = c;
        }
        return temp;
    }

    public void addList() {//根据是否存在关键字添加相应链表
        if (haveSelect >= 0) {
            addSelectList();
        }
        if (haveFrom >= 0) {
            addFromList();
        }
        if (haveWhere >= 0) {
            addWhereList();
        }
        if (haveGroupBy >= 0) {
            addGroupbyList();
        }
        targeted();
        if (haveWhere >= 0) {
            whereWord();
        }
        markWords();
    }

    private void addSelectList() {//将select对象分词
        selectList = new LinkedList<Words>();
        String str = originalString.substring(haveSelect + 6, haveFrom);
        String temp[] = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
            wordArray = new Words();
            wordArray.content = temp[i];
            wordArray.attribute = "0";
            selectList.add(wordArray);
        }
    }

    private void addFromList() {//将from对象分词
        fromList = new LinkedList<Words>();
        String str;
        if (haveWhere > 0) {
            str = originalString.substring(haveFrom + 4, haveWhere);
        } else {
            str = originalString.substring(haveFrom + 4);
        }
        String temp[] = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
            wordArray = new Words();
            wordArray.content = temp[i];
            wordArray.attribute = "0";
            fromList.add(wordArray);
        }
    }

    private void addWhereList() {//将where对象分词
        whereList = new LinkedList<Words>();
        String str;
        if (haveGroupBy > 0) {
            str = originalString.substring(haveWhere + 5, haveGroupBy);
        } else {
            str = originalString.substring(haveWhere + 5);
        }
        str = str.trim();
        String temp[];
        String tmp = null;
        int end = 0;//循环终止信号
        while (end == 0) {
            int tempindex = 99999;//一般没人能写长度大于99999的查询吧
            String tempchoice = null;
            //以下选用空格、and、or作为关键分隔词
            if (tempindex > str.indexOf(" ") && str.indexOf(" ") >= 0) {
                tempindex = str.indexOf(" ");
                tempchoice = " ";
            }
            if (tempindex > str.indexOf("and") && str.indexOf("and") >= 0) {
                tempindex = str.indexOf("and");
                tempchoice = "and";
            }
            if (tempindex > str.indexOf("or") && str.indexOf("or") >= 0) {
                tempindex = str.indexOf("or");
                tempchoice = "or";
            }
            //关接分隔赋不选左右括号是因为TM那俩在引号内还会判断括号匹配并给我报TM的异常！！！
            try {//如果不能将字符串分隔成两端
                temp = str.split(tempchoice, 2);
            } catch (Exception e1) {//就把字符串只分成一段
                try {
                    temp = str.split(tempchoice, 1);
                } catch (Exception e2) {//但是分不出来我真的没辙啦！！！
                    String saveme[] = new String[3];
                    saveme[0] = null;//谁来帮帮我
                    saveme[1] = null;//有谁能救我
                    saveme[2] = null;//我真的好难
                    temp = saveme;
                }
            }
            if (temp.length == 2) {//如果分词分为两段，第一段入链，同时将分隔符入链
                wordArray = new Words();
                wordArray.content = temp[0];
                wordArray.attribute = "0";
                whereList.add(wordArray);
                if (tempchoice != null) {
                    wordArray = new Words();
                    wordArray.content = tempchoice;
                    wordArray.attribute = "0";
                    whereList.add(wordArray);
                }
                str = temp[1];//将第二段作为接下来分隔对象
            } else {//如果分词分为非两端
                if (tempchoice != null) {//如果能找到分隔符，分词为一段，将当前分隔符入链
                    wordArray = new Words();
                    wordArray.content = tempchoice;
                    wordArray.attribute = "0";
                    whereList.add(wordArray);
                    str = temp[0];//将这唯一一段作为接下来的分词对象
                } else {//如果分词都找不到了，分词不成功，直接将剩余部分入链
                    wordArray = new Words();
                    wordArray.content = str;
                    wordArray.attribute = "0";
                    whereList.add(wordArray);
                    end = 1;//终止信号置1表示循环停止
                }
            }
        }
    }

    private void addGroupbyList() {//将groupby对象分词
        groupbyList = new LinkedList<Words>();
        String str = originalString.substring(haveGroupBy + 8);
        String temp[] = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
            wordArray = new Words();
            wordArray.content = temp[i];
            wordArray.attribute = "0";
            groupbyList.add(wordArray);
        }
    }

    private void targeted() {
        int wordcount = 1;
        int contcount = 100;
        int sheetcount = 10000;
        if (haveSelect >= 0) {//找出可替换的属性
            for (int i = 0; i < selectList.size(); i++) {
                if (selectList.get(i).toString().indexOf("(") < 0 && selectList.get(i).toString().indexOf(")") < 0 && selectList.get(i).toString().indexOf("*") < 0) {
                    selectList.get(i).attribute = Integer.toString(wordcount);
                    wordcount = wordcount + 1;
                    if (haveGroupBy >= 0) {
                        for (int j = 0; j < groupbyList.size(); j++) {
                            if (selectList.get(i).content.contentEquals(groupbyList.get(j).content)) {
                                selectList.get(i).attribute = "0";
                                wordcount = wordcount - 1;
                            }
                        }
                    }
                }
            }
        }
        if (haveFrom >= 0) {//找出表
            for (int i = 0; i < fromList.size(); i++) {
                fromList.get(i).attribute = Integer.toString(sheetcount);
                sheetcount = sheetcount + 10000;
            }
        }
        if (haveWhere >= 0) {//找出可变属性值
            for (int i = 0; i < whereList.size(); i++) {
                if (whereList.get(i).toString().indexOf("'") >= 0) {
                    whereList.get(i).attribute = Integer.toString(contcount);
                    contcount = contcount + 100;
                }
            }
        }
        if (haveGroupBy >= 0) {//找出可变合组属性
            for (int i = 0; i < groupbyList.size(); i++) {
                groupbyList.get(i).attribute = Integer.toString(wordcount);
                wordcount = wordcount + 1;
                for (int j = 0; j < selectList.size(); j++) {
                    if (groupbyList.get(i).content.contentEquals(selectList.get(j).content)) {
                        groupbyList.get(i).attribute = "0";
                        wordcount = wordcount - 1;
                    }
                }
            }
        }
    }

    private void whereWord() {
        whereKeyWord1 = new LinkedList<Words>();
        whereKeyWord2 = new LinkedList<Words>();
        String temp[];
        for (int i = 0; i < whereList.size(); i++) {
            if (whereList.get(i).content.indexOf("='") >= 0) {
                wordArray = new Words();
                temp = whereList.get(i).content.split("='");
                wordArray.content = temp[0];
                wordArray.attribute = whereList.get(i).attribute;
                whereKeyWord1.add(wordArray);
                wordArray = new Words();
                temp = temp[1].split("'");
                wordArray.content = temp[0];
                wordArray.attribute = whereList.get(i).attribute;
                whereKeyWord2.add(wordArray);
            }
        }
    }

    private void markWords() {//找出所有可变属性并列为链表
        markedWords = new LinkedList<Words>();
        if (haveSelect >= 0) {
            for (int i = 0; i < selectList.size(); i++) {
                if (Integer.parseInt(selectList.get(i).attribute) > 0 && Integer.parseInt(selectList.get(i).attribute) < 10000) {
                    wordArray = new Words();
                    wordArray.content = selectList.get(i).content;
                    wordArray.attribute = selectList.get(i).attribute;
                    markedWords.add(wordArray);
                }
            }
        }
        if (haveWhere >= 0) {
            for (int i = 0; i < whereKeyWord2.size(); i++) {
                if (Integer.parseInt(whereKeyWord2.get(i).attribute) > 0 && Integer.parseInt(whereKeyWord2.get(i).attribute) < 10000) {
                    wordArray = new Words();
                    wordArray.content = whereKeyWord2.get(i).content;
                    wordArray.attribute = whereKeyWord2.get(i).attribute;
                    markedWords.add(wordArray);
                }
            }
        }
        if (haveGroupBy >= 0) {
            for (int i = 0; i < groupbyList.size(); i++) {
                if (Integer.parseInt(groupbyList.get(i).attribute) > 0 && Integer.parseInt(groupbyList.get(i).attribute) < 10000) {
                    wordArray = new Words();
                    wordArray.content = groupbyList.get(i).content;
                    wordArray.attribute = groupbyList.get(i).attribute;
                    markedWords.add(wordArray);
                }
            }
        }
    }

    public void showHaveKeyWord() {//辅助显示函数
        //System.out.println(haveSelect+" "+haveFrom+" "+haveWhere+" "+haveGroupBy+"\n"+originalString);

        try {
            for (int i = 0; i < selectList.size(); i++) {
                System.out.println(selectList.get(i).toString());
            }
        } catch (Exception e) {
        }
        /*
        try{
            for (int i = 0; i < fromList.size(); i++) {
                System.out.println(fromList.get(i).toString());
            }
        }catch(Exception e){
        }
        try{
            for (int i = 0; i < whereList.size(); i++) {
                System.out.println(whereList.get(i).toString());
            }
        }catch(Exception e){
        }
        try{
            for (int i = 0; i < groupbyList.size(); i++) {
                System.out.println(groupbyList.get(i).toString());
            }
        }catch(Exception e){
        }

        try{
            for (int i = 0; i < markedWords.size(); i++) {
                System.out.println(markedWords.get(i).toString());
            }
        }catch(Exception e){
        }

        try{
            for (int i = 0; i < whereKeyWord1.size(); i++) {
                System.out.println(whereKeyWord1.get(i).toString());
                System.out.println(whereKeyWord2.get(i).toString());
            }
        }catch(Exception e){
        }
*/

        //System.out.println(selectList.size()+" "+fromList.size()+" "+whereList.size()+" "+groupbyList.size());
    }
}
