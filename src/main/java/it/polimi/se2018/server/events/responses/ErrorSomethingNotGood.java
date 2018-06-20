package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

import java.util.ArrayList;
import java.util.Arrays;

public class ErrorSomethingNotGood extends EventMVC {
    ArrayList<String> stacktrace;
    public ErrorSomethingNotGood(Exception e){
        super("****");
        if(e!=null){
        this.stacktrace = new ArrayList<>(Arrays.asList(Arrays.toString(e.getStackTrace())));}
        else this.stacktrace = new ArrayList<>(Arrays.asList("just for support"));
    }

    public ArrayList<String> getStacktrace() {
        return stacktrace;
    }
}
