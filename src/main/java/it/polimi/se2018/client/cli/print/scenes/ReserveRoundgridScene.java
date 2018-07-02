package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.ReservePrinter;
import it.polimi.se2018.client.cli.print.components.RoundGrindPrinter;
import it.polimi.se2018.client.cli.print.components.UtensilPrinter;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

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

    public ReserveRoundgridScene(UtensilCard utensilCard){

        if(utensilCard != null){
            this.utensilCard = utensilCard;
            game = Game.factoryGame();
        }else
            throw new InvalidParameterException();

    }


    private void printText(){
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

        RoundGrindPrinter.printRoundGrid(ROUNDGRID_ROW,ROUNDGRID_COL,game.getRoundgrid());
        ReservePrinter.printReserve(RESERVE_ROW,RESERVE_COL,game.getReserve());
        UtensilPrinter.printUtensil(CARD_ROW,CARD_COL,utensilCard);

        printText();
    }
}
