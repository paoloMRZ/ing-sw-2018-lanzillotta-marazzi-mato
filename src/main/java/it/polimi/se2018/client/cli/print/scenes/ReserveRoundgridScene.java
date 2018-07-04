package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.ReservePrinter;
import it.polimi.se2018.client.cli.print.components.RoundGrindPrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa di una schermata dove vengono mostrate la riserva, la roundgrid e la carta utensile che
 * necessita di questi due elementi.
 *
 * @author Marazzi Paolo
 */
public class ReserveRoundgridScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final int CARD_ROW = 15;
    private static final int CARD_COL = 3;

    private static final int ROUNDGRID_ROW = 4;
    private static final int ROUNDGRID_COL = 70;

    private static final int RESERVE_ROW = 4;
    private static final int RESERVE_COL = 3;

    private static final int TEXT_ROW = 30;
    private static final int TEXT_COL = 3;

    private Game game;
    private String text;
    private UtensilCard utensilCard;

    /**
     * Costruttore della classe.
     *
     * @param utensilCard carta utensile che necessita di questa scena e che deve essere mostrata a schermo.
     */
    public ReserveRoundgridScene(UtensilCard utensilCard){

        if(utensilCard != null){
            this.utensilCard = utensilCard;
            game = Game.factoryGame();
        }else
            throw new InvalidParameterException();

    }


    /**
     * Il metodo stampa un messaggio di testo.
     */
    private void printText(){
        System.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).a(TEXT_HEAD + text + TEXT_TAIL));
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
        System.out.print(ansi().eraseScreen());

        RoundGrindPrinter.printRoundGrid(ROUNDGRID_ROW,ROUNDGRID_COL,game.getRoundgrid());
        ReservePrinter.printReserve(RESERVE_ROW,RESERVE_COL,game.getReserve());
        UtensilPrinter.printUtensil(CARD_ROW,CARD_COL,utensilCard);

        printText();
    }
}
