package com.spring.resource;

public class doing {
    private Forms forms;

    doing(Forms forms) {
        this.forms = forms;
    }

    public String link() {
        return forms.getProblem() + forms.getModule();
    }
}
