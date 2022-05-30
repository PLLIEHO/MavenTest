package com.company.common;

import java.io.Serializable;
import java.util.List;

public class Pack implements Serializable {
    private static final long serialVersionUID = -8901997896855607915L;
    private String argB;
    private List<String> argA;
    public Pack(List<String> argA, String argB){
        this.argA = argA;
        this.argB = argB;
    }

    public String getArgB() {
        return argB;
    }

    public List<String> getArgA() {
        return argA;
    }
}
