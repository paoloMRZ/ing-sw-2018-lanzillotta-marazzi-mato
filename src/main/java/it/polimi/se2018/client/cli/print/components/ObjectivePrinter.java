package it.polimi.se2018.client.cli.print.components;

import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import it.polimi.se2018.client.cli.print.utils.TextPrinter;

import static org.fusesource.jansi.Ansi.ansi;

public class ObjectivePrinter {

    private ObjectivePrinter(){}


    private static final int HIGH = 6;
    private static final int SMALL_HIGH = 2;

    private static final int LEN = 33;

    private static final int SECOND_COL = 4;

    private static final int REWARD_COL = 2;
    private static final int REWARD_ROW = 3;
    private static final int REWARD_ROW_SMALL = 1;

    private static final int TITLE_ROW = 1;

    private static final int DESCRIPTION_ROW = 3;
    private static final int DESCRIPTION_COL = 5;


    private static final int TEXT_SPACE = LEN - 5;

    private static void printMargin(int startRow, int startCol){
        MarginPrinter.printColMargin(startRow,startCol, HIGH);
        MarginPrinter.printColMargin(startRow,startCol+SECOND_COL, HIGH);
        MarginPrinter.printColMargin(startRow,startCol+LEN-1, HIGH);

        MarginPrinter.printRowMargin(startRow,startCol, LEN);
        MarginPrinter.printRowMargin(startRow+HIGH ,startCol, LEN);

    }

    private static void printSmallMargin(int startRow, int startCol){
        MarginPrinter.printColMargin(startRow, startCol, SMALL_HIGH);
        MarginPrinter.printColMargin(startRow,startCol+SECOND_COL, SMALL_HIGH);
        MarginPrinter.printColMargin(startRow,startCol+LEN-1, SMALL_HIGH);

        MarginPrinter.printRowMargin(startRow,startCol, LEN);
        MarginPrinter.printRowMargin(startRow+SMALL_HIGH ,startCol, LEN);

    }

    private static void printReward(int startRow, int startCol, int reward) {
        if (reward == 0)
            System.out.print(ansi().cursor(startRow + REWARD_ROW, startCol + REWARD_COL).bold().a("#").boldOff()); //Stampo la ricompensa.
        else
            System.out.print(ansi().cursor(startRow + REWARD_ROW, startCol + REWARD_COL).bold().a(reward).boldOff()); //Stampo la ricompensa.
    }

    public static void printObjective(int startRow, int startCol, ObjectiveCard objectiveCard){

        printMargin(startRow,startCol);
        printReward(startRow,startCol,objectiveCard.getReward());

        System.out.print(ansi().cursor(startRow+TITLE_ROW,startCol+( (LEN + 4 - objectiveCard.getName().length() ) / 2)).bold().a(objectiveCard.getName()).boldOff()); //Stampo il titolo della carta.
        TextPrinter.printText(startRow+DESCRIPTION_ROW, startCol+DESCRIPTION_COL, objectiveCard.getDescription(), TEXT_SPACE);

    }

    @Deprecated
    public static void printSmallObjective(int startRow, int startCol, String title, String reward){
        printSmallMargin(startRow,startCol);
        System.out.print(ansi().cursor(startRow+TITLE_ROW, startCol+ ( (LEN + 4 - title.length() ) / 2) ).bold().a(title).boldOff());
        System.out.print(ansi().cursor(startRow+REWARD_ROW_SMALL, startCol+REWARD_COL).bold().a(reward).boldOff());
    }

}
