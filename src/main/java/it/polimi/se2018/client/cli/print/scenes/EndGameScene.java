package it.polimi.se2018.client.cli.print.scenes;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class EndGameScene {

    private static final String TITLE = "*** Punteggi ***";

    private ArrayList<String> nicknames;
    private ArrayList<Integer> scores;

    public EndGameScene(List<String> nicknames, List<Integer> scores ){

        if(nicknames != null && scores != null && scores.size() == nicknames.size()){
            this.nicknames = new ArrayList<>(nicknames);
            this.scores = new ArrayList<>(scores);

        }else
            throw new InvalidParameterException();
    }


    public void printScene(){

        System.out.print(ansi().eraseScreen());
        System.out.print(ansi().cursor(3,5).bold().a(TITLE).boldOff());

        int row = 8;

        for(String nickname : nicknames){
            System.out.print(ansi().cursor(row,3).a(nickname + "\t\t\t" + scores.get(nicknames.indexOf(nickname))));
            row++;
        }
    }
}
