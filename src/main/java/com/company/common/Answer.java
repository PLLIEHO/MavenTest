package com.company.common;

import java.io.Serializable;

public class Answer implements Serializable {
    private Object str;
    public Answer(Object answer){
        this.str = answer;
    }

    public Object getStr() {
        return str;
    }
}
