package it.polimi.se2018.client.cli.print.scenes;

import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa della schermata che mostra i punteggi finali di tutti i giocatori.
 *
 * @author Marazzi Paolo
 */
public class EndGameScene {

    private static final String TITLE = "*** Punteggi ***";

    private ArrayList<String> nicknames;
    private ArrayList<Integer> scores;

    /**
     * Costruttore della classe.
     *
     * @param nicknames lista che contiene i nickname di tutti i giocatori.
     * @param scores lista che contiene i punteggi di tutti i giocatori.
     */
    public EndGameScene(List<String> nicknames, List<Integer> scores ){

        if(nicknames != null && scores != null && scores.size() == nicknames.size()){
            this.nicknames = new ArrayList<>(nicknames);
            this.scores = new ArrayList<>(scores);

        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa a schermo la scena che mostra i punteggi di fine partita.
     */
    public void printScene(){

        AnsiConsole.out.print(ansi().eraseScreen());
        AnsiConsole.out.print(ansi().cursor(3,5).bold().a(TITLE).boldOff());

        int row = 8;

        for(String nickname : nicknames){
            AnsiConsole.out.print(ansi().cursor(row,3).a(nickname + "\t\t\t" + scores.get(nicknames.indexOf(nickname))));
            row++;
        }
    }
}
