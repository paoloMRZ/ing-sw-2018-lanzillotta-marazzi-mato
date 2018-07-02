package it.polimi.se2018.client.cli.print.scenes;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

public class WaitScene {

    private static final int HIGH = 10;
    private static final int LEN =  40;

    private static final int MESSAGES_ROW =  5;
    private static final int MESSAGES_COL =  5;

    private static final int TEXT_ROW = 3;

    private static final int TEXT_COL = 5;

    private ArrayList<String> messages;
    private String text;

    public WaitScene(String message){
        if(message != null) {
            this.text = message;
            this.messages = new ArrayList<>();

        }else
            throw new InvalidParameterException();
    }

    //TODO Sarebbe carino aggiungere la stampa del logo di sagrada.


    private void printMessages(){

        int row = MESSAGES_ROW +1;
        int col = MESSAGES_COL +1;

        for(String message: messages){
            System.out.print(ansi().cursor(row,col).a(message));
            row++;
        }
    }

    public void printScene(){
        System.out.print(ansi().eraseScreen());

        System.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).bold().a(text).boldOff());

        printMessages();
    }

    public void addMessage(String message){

        if(messages.size() == 10){

            messages.clear();
            messages.add(message);
        }else
            messages.add(message);

    }
}
