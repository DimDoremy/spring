package com.spring.resource;

import com.spring.sqlserver.MainFunction;

import java.util.LinkedList;

public class Doing {
    private Forms forms;

    Doing(Forms forms) {
        this.forms = forms;
    }

    public LinkedList<LinkedList<String>> link() {
        MainFunction mf = new MainFunction();
        mf.funSQLConnection("sa", "186536_wlj", "localhost:1433", "SQLconnection");
        mf.funMain(forms.getProblem(), forms.getModule());
        //System.out.println(Arrays.toString(mf.answer_me_natural_word.toArray()));
        //System.out.println(Arrays.toString(mf.answer_me_SQL_word.toArray()));
        LinkedList<LinkedList<String>> answerList = new LinkedList<>();
        for (int i = 0; i < mf.answer_me_natural_word.size(); i++) {
            LinkedList<String> answers = new LinkedList<>();
            answers.add(mf.answer_me_natural_word.get(i));
            answers.add(mf.answer_me_SQL_word.get(i));
            answerList.add(answers);
        }
        return answerList;
    }

}
