package com.spring.sqlserver;

import java.util.LinkedList;
import java.util.Objects;

/**
 * 类概述：找出与存放替换词的类
 * 功能：替换目标为属性，则查表获得属性；替换目标为属性值，则查对应表的属性获得属性值
 * 其他：
 */

public class NotePad {
    public LinkedList<LinkedList<Words>> notePadList;//存放链表的链表

    public NotePad() {
        notePadList = new LinkedList<LinkedList<Words>>();
    }

    public void addNotes(LinkedList<Words> markedWords, LinkedList<Words> whereKeyWord1, LinkedList<Words> fromList, SqlConnection SQL) {
        for (int i = 0; i < markedWords.size(); i++) {
            if (Integer.parseInt(markedWords.get(i).attribute) >= 1 && (Integer.parseInt(markedWords.get(i).attribute) < 100)) {
                for (int j = 0; j < fromList.size(); j++) {
                    if (SQL.selectSheet(fromList.get(j).content, markedWords.get(i).content)) {
                        notePadList.add(SQL.searchattribute(fromList.get(j).content));
                        break;
                    }
                    if (j == fromList.size()) {
                        ErrorReturn.errorNoAttributeFoundInSheet();
                        return;
                    }
                }
            } else if (Integer.parseInt(markedWords.get(i).attribute) >= 100 && (Integer.parseInt(markedWords.get(i).attribute) < 10000)) {
                for (int j = 0; j < whereKeyWord1.size(); j++) {
                    if (Objects.equals(whereKeyWord1.get(j).attribute, markedWords.get(i).attribute)) {
                        for (int k = 0; k < fromList.size(); k++) {
                            if (SQL.selectSheet(fromList.get(k).content, whereKeyWord1.get(j).content)) {
                                notePadList.add(SQL.searchcontent(fromList.get(k).content, whereKeyWord1.get(j).content));
                                break;
                            }
                            if (k == fromList.size()) {
                                ErrorReturn.errorNoSheetFoundInDataBase();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void showAllWords(LinkedList<LinkedList<Words>> notePadList) {
        if (notePadList.size() > 0) {
            for (int i = 0; i < notePadList.size(); i++) {
                for (int j = 0; j < notePadList.get(i).size(); j++) {
                    System.out.print(notePadList.get(i).get(j).content + " ");
                }
                System.out.print("\n");
            }
        } else {
            System.out.println("notepad is empty!");
        }
    }
}
