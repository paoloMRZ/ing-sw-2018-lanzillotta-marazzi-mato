package it.polimi.se2018.client.cli.print.scenes;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa di una schermata che invita l'utente ad attendere il verificarsi di un determinato evento.
 *
 * @author Marazzi Paolo
 */

public class WaitScene {

    private static final int MESSAGES_ROW =  5;
    private static final int MESSAGES_COL =  5;

    private static final int TEXT_ROW = 3;

    private static final int TEXT_COL = 5;

    private ArrayList<String> messages;
    private String text;

    /**
     * Costruttore della classe.
     *
     * @param message messaggio da mostrare a schermo per dare istruzioni all'utente.
     */
    public WaitScene(String message){
        if(message != null) {
            this.text = message;
            this.messages = new ArrayList<>();

        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa tutti i messaggi contenuti nella lista dei messaggi.
     */
    private void printMessages(){

        int row = MESSAGES_ROW +1;
        int col = MESSAGES_COL +1;

        for(String message: messages){
            System.out.print(ansi().cursor(row,col).a(message));
            row++;
        }
    }

    /**
     * Il metodo stampa a schermo la scena.
     */
    public void printScene(){
        System.out.print(ansi().eraseScreen());

        System.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).bold().a(text).boldOff());

        printMessages();
    }

    /**
     * Il metodo permette di aggiungere un messaggio alla lista dei messaggi.
     * I messaggi contenuti nella lista vengono stampati a schermo ogni volta che si richiama la funzione 'printScene'.
     *
     * @param message messaggio da aggiungere alla lista.
     */
    public void addMessage(String message){

        if(messages.size() == 10){

            messages.clear();
            messages.add(message);
        }else
            messages.add(message);

    }
}
