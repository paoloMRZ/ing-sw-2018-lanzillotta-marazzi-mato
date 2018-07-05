package it.polimi.se2018.client.cli.print.scenes;

import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.print.components.RoundGrindPrinter;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa a schermo della scena che mostra la roundgrid completa (non solo il primo dado di ogni round).
 *
 * @author Marazzi Paolo
 */

public class ShowRoundGridScene {


    private static final int ROW_ROUNDGRID = 3;
    private static final int COL_ROUNDGRID = 5;

    private static final int ROW_TEXT = 1;
    private static final int COL_TEXT = 5;

    private static final int MENU_ROW = 5;
    private static final int MENU_COL = 68;

    private static final String TEXT1 = "***** Round *****";
    private static final String TEXT0_MENU = "------------------ MENU -------------";
    private static final String TEXT1_MENU = "[1] Visualizza carte utensili.";
    private static final String TEXT2_MENU = "[2] Visualizza carte obiettivo.";
    private static final String TEXT3_MENU = "[3] Visualizza gli avversari.";
    private static final String TEXT4_MENU = "[4] Visualizza la roundgrid completa.";
    private static final String TEXT5_MENU = "[5] Piazza dado.";
    private static final String TEXT6_MENU = "[6] Gioca carta utensile.";
    private static final String TEXT7_MENU = "[7] Fine turno.";
    private static final String TEXT8_MENU = " >>";

    private ArrayList<ArrayList<DieInfo>> rounds;
    private int menu;

    public ShowRoundGridScene(List<ArrayList<DieInfo>> rounds){
        if(rounds != null){
            this.rounds =  new ArrayList<>(rounds);
            this.menu = 0;
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo gestisce la stampa del menù visibile durante il nostro turno di gioco.
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
     * Il metodo gestisce la stampa del menù visibile durante turno di gioco degli avversari.
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
     * Il metodo stampa il menù che è stato selezionato tramite gli appositi metodi pubblici.
     */
    private void printMenu(){
        if(this.menu == 0)
            this.printIsNotMyTurnMenu();
        else
            this.printMyTurnMenu();
    }

    /**
     * Il metodo permette di mostrare a schermo il menù delle azioni fattibili dal giocatore durante il turno di un avversario
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setNotMyTurnMenu(){menu = 0;}


    /**
     * Il metodo permette di mostrare a schermo il menù delle azioni fattibili dal giocatore durante il proprio turno
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setMyTurnMenu(){menu = 1;}

    /**
     * Il metodo stampa a schermo la scena.
     */
    public void printScene(){
        AnsiConsole.out.print(ansi().eraseScreen());
        RoundGrindPrinter.printRoundGrid(ROW_ROUNDGRID,COL_ROUNDGRID,rounds);
        AnsiConsole.out.print(ansi().cursor(ROW_TEXT,COL_TEXT).bold().a(TEXT1).boldOff());
        printMenu();
    }
}
