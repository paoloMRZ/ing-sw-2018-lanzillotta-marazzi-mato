package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.DiePrinter;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

public class SideDieScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final String TITLE = "Dado da piazzare";

    private static final int CARD_ROW = 5;
    private static final int CARD_COL = 3;

    private static final int DIE_ROW = 7;
    private static final int DIE_COL = 80;

    private static final int SIDE_ROW = 4;
    private static final int SIDE_COL = 40;

    private static final int TEXT_ROW = 25;
    private static final int TEXT_COL = 3;

    private static final int TITLE_ROW = 5;
    private static final int TITLE_COL = 80;


    private UtensilCard utensilCard;
    private DieInfo dieInfo;
    private Game game;

    private String text;

    public SideDieScene(UtensilCard utensilCard, DieInfo dieInfo){
        if(utensilCard != null)
        {
            this.game = Game.factoryGame();
            this.utensilCard = utensilCard;
            this.dieInfo = dieInfo;

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

        SidePrinter.printSide(SIDE_ROW,SIDE_COL,game.getMyCard(),game.getDiceOnMyCard());
        DiePrinter.printDie(this.dieInfo.getColor(), this.dieInfo.getNumber(), DIE_ROW,DIE_COL);
        UtensilPrinter.printUtensil(CARD_ROW,CARD_COL,utensilCard);

        printText();
    }
}
