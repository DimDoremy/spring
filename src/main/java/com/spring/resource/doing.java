package com.spring.resource;

import com.spring.sqlserver.MainFunction;

import java.util.Arrays;

public class Doing {
    private Forms forms;

    Doing(Forms forms) {
        this.forms = forms;
    }

    public String link() {
        MainFunction mf = new MainFunction();
        mf.funSQLConnection("sa", "186536_wlj", "localhost:1433", "SQLconnection");
        mf.funMain(forms.getProblem(), forms.getModule());
        System.out.println(Arrays.toString(mf.answer_me_natural_word.toArray()));
        System.out.println(Arrays.toString(mf.answer_me_SQL_word.toArray()));
        return "";
    }
}
