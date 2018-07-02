package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.SidePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

public class UtensilSideScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final int CARD_ROW = 5;
    private static final int CARD_COL = 3;

    private static final int SIDE_ROW = 4;
    private static final int SIDE_COL = 40;

    private static final int TEXT_ROW = 25;
    private static final int TEXT_COL = 3;

    private UtensilCard utensilCard;
    private Game game;
    private String text;

    public UtensilSideScene(UtensilCard utensilCard){

        if(utensilCard != null) {
            this.utensilCard = utensilCard;
            game = Game.factoryGame();
        }
        else
            throw new InvalidParameterException();
    }

    public void printScene(){
        System.out.print(ansi().eraseScreen());

        UtensilPrinter.printUtensil(CARD_ROW, CARD_COL, utensilCard);
        SidePrinter.printSide(SIDE_ROW, SIDE_COL, game.getMyCard(), game.getDiceOnMyCard());
        printText();
    }

    public void setText(String text){
        if(text != null)
            this.text = text;
        else
            throw new InvalidParameterException();
    }

    private void printText(){
        System.out.print(ansi().cursor(TEXT_ROW,TEXT_COL).a(TEXT_HEAD + text + TEXT_TAIL));
    }
}
