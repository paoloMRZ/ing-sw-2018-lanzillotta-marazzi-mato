package it.polimi.se2018.client.cli.print.components;


import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import it.polimi.se2018.client.cli.print.utils.TextPrinter;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permette di stampare a schermo la descrizione di una carta utensile.
 *
 * @author Marazzi Paolo
 */

public class UtensilPrinter {

    private UtensilPrinter(){}


    private static final int LEN = 33;
    private static final int HIGH = 12;

    private static final int SECOND_ROW = 2;
    private static final int NUMBER_COL = LEN/2;

    private static final int TITLE_ROW = 1;

    private static final int DESCRIPTION_ROW = 3;
    private static final int DESCRIPTION_COL = 1;

    private static final int TEXT_SPACE = LEN - 2;

    /**
     * Il metodo stampa i margini della carta.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     */

    private static void printMargin(int startRow, int startCol){

        MarginPrinter.printColMargin(startRow,startCol,HIGH);
        MarginPrinter.printColMargin(startRow, startCol+LEN-1, HIGH);

        MarginPrinter.printRowMargin(startRow,startCol,LEN);
        MarginPrinter.printRowMargin(startRow+SECOND_ROW,startCol,LEN);
        MarginPrinter.printRowMargin(startRow+HIGH, startCol, LEN);
    }

    /**
     * Il metodo stamapa il costo della carta.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param prize prezzo.
     */
    private static void printPrize(int startRow, int startCol, int prize){
        System.out.print(ansi().cursor(startRow+HIGH, startCol+1).bold().a("Costo: " + prize).boldOff());
    }

    /**
     * Il metodo consente di stampare a schermo la descrizione di una carta utensile nella psizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param utensilCard carta utensile da stampare.
     */
    public static void printUtensil(int startRow, int startCol, UtensilCard utensilCard){

        printMargin(startRow,startCol);

        System.out.print(ansi().cursor(startRow+SECOND_ROW, startCol+NUMBER_COL).bold().a(utensilCard.getNumber()).boldOff());
        System.out.print(ansi().cursor(startRow+TITLE_ROW, startCol + ( (LEN - 1 - utensilCard.getName().length() ) / 2) ).bold().a(utensilCard.getName()).boldOff());

        printPrize(startRow, startCol, utensilCard.getPrize());

        TextPrinter.printText(startRow+DESCRIPTION_ROW, startCol+DESCRIPTION_COL, utensilCard.getDescription(), TEXT_SPACE );
    }

}
