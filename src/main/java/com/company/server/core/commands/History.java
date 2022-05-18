package com.company.server.core.commands;

import com.company.common.CommandList;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<CommandList> historyList = new ArrayList<>();
    public void storyAdd(CommandList command){
        historyList.add(command);
        if(historyList.size()>8){
            historyList.remove(0);
        }
    }
    public String history(){
        return historyList.toString();
    }

}
