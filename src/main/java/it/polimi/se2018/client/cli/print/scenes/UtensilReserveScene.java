package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.ReservePrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

public class UtensilReserveScene {

    private static final String TEXT_HEAD = "[] ";
    private static final String TEXT_TAIL = " >> ";

    private static final int CARD_ROW = 5;
    private static final int CARD_COL = 3;

    private static final int RESERVE_ROW = 4;
    private static final int RESERVE_COL = 40;

    private static final int TEXT_ROW = 20;
    private static final int TEXT_COL = 3;

    private UtensilCard utensilCard;

    private Game game;
    private String text;

    public UtensilReserveScene(UtensilCard utensilCard){

        if(utensilCard != null) {
            this.utensilCard = utensilCard;
            game = Game.factoryGame();
        }
        else
            throw new InvalidParameterException();
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

    public void printScene(){
        System.out.print(ansi().eraseScreen());

        UtensilPrinter.printUtensil(CARD_ROW,CARD_COL,utensilCard);
        ReservePrinter.printReserve(RESERVE_ROW,RESERVE_COL, game.getReserve());
        printText();
    }
}
