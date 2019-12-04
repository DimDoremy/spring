package com.spring.resource;

public class Forms {
    private String problem;
    private String module;

    public Forms() {
        problem = "";
        module = "";
    }

    public Forms(String problem, String module) {
        this.problem = problem;
        this.module = module;
    }

    public String getProblem() {
        return problem;
    }

    public String getModule() {
        return module;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setModule(String module) {
        this.module = module;
    }
}

