package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.ReservePrinter;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa di una schermata dove vengono mostrate la carta finestra, la riserva
 * e la carta utensile che necessita di questi due elementi.
 *
 * @author Marazzi Paolo
 */

public class SideReserverScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final int CARD_ROW = 5;
    private static final int CARD_COL = 3;

    private static final int RESERVE_ROW = 4;
    private static final int RESERVE_COL = 41;

    private static final int SIDE_ROW = 15;
    private static final int SIDE_COL = 40;

    private static final int TEXT_ROW = 20;
    private static final int TEXT_COL = 3;

    private UtensilCard utensilCard;
    private Game game;

    private String text;

    /**
     * Costruttore della classe.
     *
     * @param utensilCard utensile da mostrare a schermo.
     */
    public SideReserverScene(UtensilCard utensilCard){
        if(utensilCard != null)
        {
            this.game = Game.factoryGame();
            this.utensilCard = utensilCard;
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa un messaggio di testo.
     */
    private void printText(){
        AnsiConsole.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).a(TEXT_HEAD + text + TEXT_TAIL));
    }

    /**
     * Il metodo salva il messaggio di testo che deve essere mostrato a schermo.
     * @param text messaggio di testo.
     */
    public void setText(String text){
        if(text != null)
            this.text = text;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa a schermo la scena.
     */
    public void printScene(){
        AnsiConsole.out.print(ansi().eraseScreen());

        SidePrinter.printSide(SIDE_ROW,SIDE_COL, game.getMyCard(), game.getDiceOnMyCard());
        ReservePrinter.printReserve(RESERVE_ROW, RESERVE_COL,game.getReserve());
        UtensilPrinter.printUtensil(CARD_ROW,CARD_COL,utensilCard);
        printText();
    }
}
