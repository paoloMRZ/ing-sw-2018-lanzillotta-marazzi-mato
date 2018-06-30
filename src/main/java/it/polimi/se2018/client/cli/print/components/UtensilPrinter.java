package it.polimi.se2018.client.cli.print.components;


import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import it.polimi.se2018.client.cli.print.utils.TextPrinter;

import static org.fusesource.jansi.Ansi.ansi;

public class UtensilPrinter {

    private UtensilPrinter(){}


    private static final int LEN = 33;
    private static final int HIGH = 12;
    private static final int SMALL_HIGH = 3;

    private static final int SECOND_ROW = 2;
    private static final int NUMBER_COL = LEN/2;

    private static final int TITLE_ROW = 1;

    private static final int DESCRIPTION_ROW = 3;
    private static final int DESCRIPTION_COL = 1;

    private static final int TEXT_SPACE = LEN - 2;

    private static void printMargin(int startRow, int startCol){

        MarginPrinter.printColMargin(startRow,startCol,HIGH);
        MarginPrinter.printColMargin(startRow, startCol+LEN-1, HIGH);

        MarginPrinter.printRowMargin(startRow,startCol,LEN);
        MarginPrinter.printRowMargin(startRow+SECOND_ROW,startCol,LEN);
        MarginPrinter.printRowMargin(startRow+HIGH, startCol, LEN);
    }

    private static void printSmallMargin(int startRow, int startCol){

        MarginPrinter.printColMargin(startRow,startCol,SMALL_HIGH);
        MarginPrinter.printColMargin(startRow,startCol+LEN-1,SMALL_HIGH);

        MarginPrinter.printRowMargin(startRow,startCol,LEN);
        MarginPrinter.printRowMargin(startRow+SECOND_ROW,startCol,LEN);

    }

    public static void printUtensil(int startRow, int startCol, UtensilCard utensilCard){

        printMargin(startRow,startCol);

        System.out.print(ansi().cursor(startRow+SECOND_ROW, startCol+NUMBER_COL).bold().a(utensilCard.getNumber()).boldOff());
        System.out.print(ansi().cursor(startRow+TITLE_ROW, startCol + ( (LEN - 1 - utensilCard.getName().length() ) / 2) ).bold().a(utensilCard.getName()).boldOff());

        TextPrinter.printText(startRow+DESCRIPTION_ROW, startCol+DESCRIPTION_COL, utensilCard.getDescription(), TEXT_SPACE );
    }

    @Deprecated
    public static void printSmallUtensil(int startRow, int startCol, UtensilCard utensilCard){

        printSmallMargin(startRow,startCol);

        System.out.print(ansi().cursor(startRow+SECOND_ROW, startCol+NUMBER_COL).bold().a(utensilCard.getNumber()).boldOff());
        System.out.print(ansi().cursor(startRow+TITLE_ROW, startCol + ( (LEN - 1 - utensilCard.getName().length() ) / 2) ).bold().a(utensilCard.getName()).boldOff());
    }
}
