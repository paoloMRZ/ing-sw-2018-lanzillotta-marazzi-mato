package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;
import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

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


    public SideColorScene(Ansi.Color color, UtensilCard utensilCard){

        if(color != Ansi.Color.WHITE && color != null && utensilCard != null){

            this.game = Game.factoryGame();

            this.color = color;
            this.utensilCard = utensilCard;

        }else
            throw new InvalidParameterException();
    }


    private void printText(){

        System.out.print(ansi().cursor(TITLE_ROW,TITLE_COL).bold().a(TITLE).boldOff());
        System.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).a(TEXT_HEAD + text + TEXT_TAIL));
    }

    public void setText(String text){
        if(text != null)
            this.text = text;
        else
            throw new InvalidParameterException();
    }


    public void printScene(){

        System.out.print(ansi().eraseScreen());

        UtensilPrinter.printUtensil(CARD_ROW, CARD_COL, utensilCard);
        SidePrinter.printSide(SIDE_ROW,SIDE_COL, game.getMyCard(), game.getDiceOnMyCard());

        System.out.print(ansi().cursor(COLOR_ROW,COLOR_COL).bg(this.color).a(FIVE_SPACES)
                                                                            .cursorToColumn(COLOR_COL).a(FIVE_SPACES)
                                                                            .cursorToColumn(COLOR_COL).a(FIVE_SPACES).bgDefault());

        printText();
    }

}
