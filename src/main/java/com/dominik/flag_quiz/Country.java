package com.dominik.flag_quiz;

import java.util.List;

public class Country {

    private String name;
    private String flag;
    private List<String> answers;

    public Country(String name, String flag, List<String> answers) {
        this.name = name;
        this.flag = flag;
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public List<String> getAnswers() {
        return answers;
    }
}