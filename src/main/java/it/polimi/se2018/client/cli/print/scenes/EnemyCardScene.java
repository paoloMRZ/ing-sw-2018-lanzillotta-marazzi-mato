package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa della schermata che mostra le carte degli avversari con i dadi posizionati su di esse.
 *
 * @author Marazzi Paolo
 */

public class EnemyCardScene {


    private static final int ROW_CARD = 6;
    private static final int COL_CARD = 2;

    private static final int COL_NEXT_CARD = 34;

    private static final int ROW_NICKNAME = 25;
    private static final int COL_NICKNAME = 17;
    private static final int COL_NEXT_NICKNAME = 34;

    private static final int ROW_TEXT1 = 3;
    private static final int COL_TEXT1 = 50;

    private static final int MENU_ROW = 7;
    private static final int MENU_COL = 103;

    private static final String TEXT1 = "***** Le carte dei tuoi avversari *****";

    private static final String TEXT0_MENU = "------------------ MENU -------------";
    private static final String TEXT1_MENU = "[1] Visualizza carte utensili.";
    private static final String TEXT2_MENU = "[2] Visualizza carte obiettivo.";
    private static final String TEXT3_MENU = "[3] Visualizza gli avversari.";
    private static final String TEXT4_MENU = "[4] Visualizza la roundgrid completa.";
    private static final String TEXT5_MENU = "[5] Piazza dado.";
    private static final String TEXT6_MENU = "[6] Gioca carta utensile.";
    private static final String TEXT7_MENU = "[7] Fine turno.";
    private static final String TEXT8_MENU = " >>";

    private ArrayList<SideCard> cards;
    private ArrayList<String> nicknames;
    private ArrayList<ArrayList<DieInfo>> dicesOnCards;

    private int menu;

    /**
     * Costruttore della classe.
     *
     * @param cards lista che contiene le carte degli avversari.
     * @param nicknames nickname degli avversari.
     * @param diceOnCards lista che contiene i dadi posizionati su ogni carta.
     */
    public EnemyCardScene(List<SideCard> cards, List<String> nicknames, List<ArrayList<DieInfo>> diceOnCards){
        if(cards != null && nicknames != null && diceOnCards != null)
        {
            this.cards = new ArrayList<>(cards);
            this.nicknames = new ArrayList<>(nicknames);
            this.dicesOnCards = new ArrayList<>(diceOnCards);
            this.menu = 0;
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il segunete metodo stampa a schermo il menù di gioco con le azioni fattibli dal giocatore nel suo turno.
     */
    private void printMyTurnMenu(){
        int row = MENU_ROW;

        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT1_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT2_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT3_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT4_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT5_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT6_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT7_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT8_MENU));
    }

    /**
     * Il segunete metodo stampa a schermo il menù di gioco con le azioni fattibli dal giocatore quando non è il suo turno.
     */
    private void printIsNotMyTurnMenu(){
        int row = MENU_ROW;

        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT1_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT2_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT3_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT4_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,MENU_COL).a(TEXT8_MENU));
    }

    /**
     * Il metodo stampa il menù corretto sulla base dei settaggi fatti dal giocatore tramite i metodi dedicati.
     */
    private void printMenu(){
        if(this.menu == 0)
            this.printIsNotMyTurnMenu();
        else
            this.printMyTurnMenu();
    }

    /**
     * Il metodo imposta che alla prossima stampa della schermata verrà mostrato il menù che ci mostra le azioni che possiamo fare
     * nel turno di un avversario.
     */
    public void setNotMyTurnMenu(){menu = 0;}

    /**
     * Il metodo imposta che alla prossima stampa della schermata verrà mostrato il menù che ci mostra le azioni che possiamo fare
     * nel nostro turno.
     */
    public void setMyTurnMenu(){menu = 1;}

    /**
     * Il metodo stampa il nickname di ogni avversario sotto la sua carta.
     */
    private void printNicknames(){

        int col = COL_NICKNAME;

        for(String nickname : nicknames){
            AnsiConsole.out.print(ansi().cursor(ROW_NICKNAME,col-(nickname.length()/2)).bold().a("[" + nickname + "]").boldOff());
            col += COL_NEXT_NICKNAME;
        }
    }

    /**
     * Il metodo stampa le carte degli avversari.
     */
    private void printCards(){
        int col = COL_CARD;
        int i = 0;

        AnsiConsole.out.print(ansi().eraseScreen());

        for(SideCard card : cards){
            SidePrinter.printSide(ROW_CARD, col,card,dicesOnCards.get(i));
            i++;
            col += COL_NEXT_CARD;
        }
    }


    public void printScene(){
        printCards();
        printNicknames();
        AnsiConsole.out.print(ansi().cursor(ROW_TEXT1,COL_TEXT1).bold().a(TEXT1).boldOff());
        printMenu();
    }
}
