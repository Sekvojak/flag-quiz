package com.dominik.flag_quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country {

    private String name;
    private String flag;

    public Country(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
