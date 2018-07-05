package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * La classe gestisce la stampa di una schermata dove vengono mostrate la carta finestra, un quadrato colorato
 * e la carta utensile che necessita di questi due elementi.
 *
 * @author Marazzi Paolo
 */

public class SideColorScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final String TITLE = "Colore estratto";

    private static final String FIVE_SPACES = "     \n";

    private static final int CARD_ROW = 5;
    private static final int CARD_COL = 3;

    private static final int COLOR_ROW = 7;
    private static final int COLOR_COL = 80;

    private static final int SIDE_ROW = 4;
    private static final int SIDE_COL = 40;

    private static final int TEXT_ROW = 25;
    private static final int TEXT_COL = 3;

    private static final int TITLE_ROW = 5;
    private static final int TITLE_COL = 80;

    private Game game;

    private Ansi.Color color;
    private UtensilCard utensilCard;

    private String text;


    /**
     * Costruttore della classe.
     *
     * @param color colore del quadrato.
     * @param utensilCard carta utensile da mostrare a schermo.
     */
    public SideColorScene(Ansi.Color color, UtensilCard utensilCard){

        if(color != Ansi.Color.WHITE && color != null && utensilCard != null){

            this.game = Game.factoryGame();

            this.color = color;
            this.utensilCard = utensilCard;

        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo stampa un messaggio di testo.
     */
    private void printText(){

        AnsiConsole.out.print(ansi().cursor(TITLE_ROW,TITLE_COL).bold().a(TITLE).boldOff());
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

        UtensilPrinter.printUtensil(CARD_ROW, CARD_COL, utensilCard);
        SidePrinter.printSide(SIDE_ROW,SIDE_COL, game.getMyCard(), game.getDiceOnMyCard());

        AnsiConsole.out.print(ansi().cursor(COLOR_ROW,COLOR_COL).bg(this.color).a(FIVE_SPACES)
                                                                            .cursorToColumn(COLOR_COL).a(FIVE_SPACES)
                                                                            .cursorToColumn(COLOR_COL).a(FIVE_SPACES).bgDefault());

        printText();
    }

}
