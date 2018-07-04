package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa della schermata che permette al giocatore di scegliere la carta finestra con cui giocare.
 *
 * @author Marazzi Paolo
 */

public class ChoseSideScene {

    private static final int TEXT_ROW = 3;
    private static final int TEXT_COL = 3;

    private static final int ROW_CARD1 = 6;
    private static final int COL_CARD1 = 2;

    private static final int ROW_CARD2 = 6;
    private static final int COL_CARD2 = 36;

    private static final int ROW_CARD3 = 6;
    private static final int COL_CARD3 = 70;

    private static final int ROW_CARD4 = 6;
    private static final int COL_CARD4 = 104;

    private static final int NUMBER_ROW = 26;
    private static final int NUMBER_COL = 17;
    private static final int NUMBER_NEXT_COL = 34;

    private static final int MESSAGES_ROW = 30;
    private static final int MESSAGES_COL = 2;
    private static final int MESSAGES_HIGH = 8;
    private static final int MESSAGES_LEN = 70;

    private static final String TEXT = "Con quale carta vuoi giocare?";
    private static final String MESSAGE_TEXT = "MESSAGGI";
    private static final String MESSAGE_HEAD = " [*] ";

    private ArrayList<String> messages;

   private ArrayList<SideCard> cards;


    /**
     * Costruttore della classe.
     *
     * @param cards carte finestra tra cui il giocatore può scegliere.
     */

    public ChoseSideScene(List<SideCard> cards) {
        if (cards != null && cards.size() == 4) {
            messages = new ArrayList<>();
            this.cards = new ArrayList<>(cards);

        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampo il numero identificativo sotto alla relativa carta.
     * Il giocatore selezionerà la carta tramite questo numero.
     *
     * @param starRow riga su cui stampare il numero.
     * @param startCol colonna su cui stampare il numero.
     * @param number numero da stampare.
     */
    private void printNumber(int starRow, int startCol, int number){
        System.out.print(ansi().cursor(starRow,startCol).bold().a("[" + number + "]").boldOff());
    }

    /**
     * Il metodo stampa i numeri identificativi delle quattro carte.
     *
     * @param starRow riga su cui si trova il primo numero.
     * @param startCol colonna su cui si trova il primo numero.
     */
    private void printNumbers(int starRow, int startCol){
        int col = startCol;

        for(int i = 1; i<=4; i++) {
            printNumber(starRow,col,i);
            col += NUMBER_NEXT_COL;
        }
    }

    /**
     * Il metodo stampa la box e i messaggi che notificano il giocatore.
     */

    private void printMessages(){

        System.out.print(ansi().cursor(MESSAGES_ROW,MESSAGES_COL+1).bold().a(MESSAGE_TEXT).boldOff());

        //Stampo i margini verticali.
        MarginPrinter.printColMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_HIGH);
        MarginPrinter.printColMargin(MESSAGES_ROW+1,MESSAGES_COL+MESSAGES_LEN-1 ,MESSAGES_HIGH);

        //Stampo i margini orizzontali.
        MarginPrinter.printRowMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_LEN);
        MarginPrinter.printRowMargin(MESSAGES_ROW+MESSAGES_HIGH+1, MESSAGES_COL, MESSAGES_LEN);

        //Stampo i messaggi.
        int offset = 2;

        for(String message : messages){
            System.out.print(ansi().cursor(MESSAGES_ROW+offset, MESSAGES_COL+1).a(MESSAGE_HEAD + message));
            offset++;
        }

    }

    /**
     * Il metodo stampa a schermo la scena che permette al giocatore di scegliere la carta finestra con cui giocare.
     */
    public void printScene() {


        System.out.print(ansi().eraseScreen()); //Pulisco lo schermo.

        System.out.print(ansi().cursor(TEXT_ROW, TEXT_COL).a(TEXT)); //Stampo il testo.

        //Stampo le carte.
        SidePrinter.printSide(ROW_CARD1, COL_CARD1, cards.get(0));
        SidePrinter.printSide(ROW_CARD2, COL_CARD2, cards.get(1));
        SidePrinter.printSide(ROW_CARD3, COL_CARD3, cards.get(2));
        SidePrinter.printSide(ROW_CARD4, COL_CARD4, cards.get(3));

        printNumbers(NUMBER_ROW, NUMBER_COL); //Stampo i numeri per la scelta delle carte.

        //Stampo la casella dei messagggi.
        printMessages();

        System.out.print(ansi().cursor(TEXT_ROW, TEXT_COL + TEXT.length() + 1)); //Poiziono il cursore alla fine del testo.

    }

    /**
     * Il metodo permette di aggiungere un nuovo messaggio tra i messaggi da mostrare a schermo, dopo di che aggiorna
     * la schermata.
     *
     * @param message messaggio da aggiungere alla lista.
     */
    public void addMessage(String message){
        if(messages.size() < 7) //La casella contiene al massimo sette messaggi.
            messages.add(message);
        else { //Se la casella è piene aggiungo in coda e rimuovo il primo messaggio.
            messages.add(message);
            messages.remove(0);
        }

        this.printScene();
    }
}
