package it.polimi.se2018.client.cli.print.components;

import it.polimi.se2018.client.cli.game.info.CellInfo;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.info.Info;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class SidePrinter {

    private static final String DOT = "O";
    private static final String TEXTURE = "\u2591";

    private static final String FIVE_SPACES = "     \n";

    private static final int NUMBER_OF_CELLS = 20;
    private static final int NEXT_COL = 6;
    private static final int NEXT_ROW = 4;

    private static final int NAME_ROW = 18;
    private static final int NAME_COL = 2;

    private static final int FAVOURS_COL = 26;
    private static final int FAVOURS_ROW = 18;

    private SidePrinter() {
    }

    private static boolean areValidCoordinates(int row, int col) {
        return row >= 1 && col >= 1;
    }

    //------------------------ Metodi di appoggio per stampare le celle. ----------------------------------------------

    private static void printOne(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(FIVE_SPACES)
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(FIVE_SPACES).boldOff().bgDefault());
    }

    private static void printTwo(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printThree(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printFour(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printFive(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printSix(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    //----------------------- Metodi che stampano i tre tipi possibili di celle ---------------------------------------

    private static void printCell(int startRow, int startCol) {
        System.out.print(ansi().bgBright(Ansi.Color.WHITE).cursor(startRow, startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(FIVE_SPACES).bgDefault());
    }

    private static void printCell(int startRow, int startCol, Ansi.Color color) {
        System.out.print(ansi().cursor(startRow, startCol).fg(color)
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .fgDefault());
    }

    private static void printCell(int startRow, int startCol, int number) {

        switch (number) {
            case 1:
                printOne(startRow, startCol);
                break;

            case 2:
                printTwo(startRow, startCol);
                break;

            case 3:
                printThree(startRow, startCol);
                break;

            case 4:
                printFour(startRow, startCol);
                break;

            case 5:
                printFive(startRow, startCol);
                break;

            case 6:
                printSix(startRow, startCol);
                break;

            default:
        }
    }

    private static void printSingleCell(int startRow, int startCol, Ansi.Color color, int number){
        //Controllo che tipo di cella stampare.

        if(color != Ansi.Color.WHITE)
            printCell(startRow,startCol,color); //Cella con restrizione di colore.

        if(number != 0)
            printCell(startRow,startCol,number); //Cella con restrizione di numero.

        if(number == 0 && color == Ansi.Color.WHITE)
            printCell(startRow,startCol); //Cella semplice.
    }


    //----------------------- Metodi di appoggio per stampare la carta ------------------------------------------------

    private static void printAllCells(int startRow, int startCol, List<CellInfo> cells, List<DieInfo> dice){

        //La prima riga e la prima colonna sono occupati dai numeri che indicano le coordinate.
        //La seconda riga e la seconda colonna sono occupate dai margini esterni.

        int row = startRow + 2;
        int col = startCol + 2;

        int counter = 0; //contatore delle celle/dadi stampati su una riga.
        int cellNumber = 0;

        //Ciclo che stampa le caselle/dadi.

        for (Info info : dice) {

            if (info.getColor() != Ansi.Color.WHITE && info.getNumber() != 0) { //Controllo se è presente un dado in quella cella.
                DiePrinter.printDie(info.getColor(),info.getNumber(),row,col); //Stampo il dado.
            } else {

                //Stampo la cella.
                Info infoCell = cells.get(cellNumber); //Recupero la cella.
                printSingleCell(row, col, infoCell.getColor(),infoCell.getNumber());
            }

            counter++;
            cellNumber++;

            if(counter < 5){ //Controllo se la riga è ancora da completare.
                col += NEXT_COL; //Mi sposto lateralmente.
            }
            else {

                //Mi sposto all'inizio della riga sotto.
                col = startCol + 2;
                row += NEXT_ROW;

                counter = 0; //Resetto il contatore.
            }
        }
    }

    private static void printAllCells(int startRow, int startCol, List<CellInfo> cells){

        //La prima riga e la prima colonna sono occupati dai numeri che indicano le coordinate.
        //La seconda riga e la seconda colonna sono occupate dai margini esterni.

        int row = startRow + 2;
        int col = startCol + 2;

        int counter = 0; //contatore delle celle/dadi stampati su una riga.
        int cellNumber = 0;

        //Ciclo che stampa le caselle/dadi.

        for (Info cell : cells) {

            //Stampo la cella.
            printSingleCell(row, col, cell.getColor(),cell.getNumber());


            counter++;
            cellNumber++;

            if(counter < 5){ //Controllo se la riga è ancora da completare.
                col += NEXT_COL; //Mi sposto lateralmente.
            }
            else {

                //Mi sposto all'inizio della riga sotto.
                col = startCol + 2;
                row += NEXT_ROW;

                counter = 0; //Resetto il contatore.
            }
        }
    }



    private static void printAllVerticalMargin(int startRow, int startCol){

        //Ciclo che stampa i margini verticali.

        int row = startRow + 1; //Resetto l'indice di riga.
        int col = startCol + 1; //Resetto l'indice di colonna.

        for(int i=0; i<6; i++){
            MarginPrinter.printColMargin(row,col,17); //Stampo il margine.
            col += 6; //Mi sposto alla colonna successiva.
        }

    }

    private static void printAllOrizzontalMargin(int startRow, int startCol){

        //Cliclo che stampa i margini orizzontali.

        int row = startRow + 1; //Resetto l'indice di riga.
        int col = startCol + 1; //Resetto l'indice di colonna.

        for(int i=0; i<5; i++){
            MarginPrinter.printRowMargin(row,col,31); //Stampo il margine.
            row += 4; //Mi sposto alla colonna successiva.
        }
    }

    private static void printCoordinates(int startRow, int startCol){

        int row = startRow;
        int col = startCol+4; //Mi metto al "centro" della riga.

        //Stampo le coordinate delle righe.
        for(int i= 0; i<5; i++){
            System.out.print(ansi().cursor(row,col).bold().a(i).boldOff());
            col += NEXT_COL;
        }

        row = startRow + 3; //Mi metto al "centro" della colonna.
        col = startCol;

        for(int i = 0; i<4; i++){
            System.out.print(ansi().cursor(row,col).bold().a(i).boldOff());
            row += NEXT_ROW;
        }

    }

    private static void printFavours(int startRow, int startCol, int favours){
        for(int i = 0;  i< favours; i++){
            System.out.print(ansi().cursor(startRow+FAVOURS_ROW, startCol+FAVOURS_COL+i).a(DOT));
        }
    }

    //---------------------- Metodo di stampa -------------------------------------------------------------------------

    public static void printSide(int startRow, int startCol, SideCard card, List<DieInfo> dice) {

        if (areValidCoordinates(startRow, startCol) && dice.size() == NUMBER_OF_CELLS) {

            printAllCells(startRow,startCol,card.getCells(),dice);

            printAllVerticalMargin(startRow,startCol);

            printAllOrizzontalMargin(startRow,startCol);

            printCoordinates(startRow,startCol);

            printFavours(startRow,startCol,card.getFavours());

            System.out.print(ansi().cursor(startRow + NAME_ROW, startCol + NAME_COL).bold().a(card.getName()).boldOff());

        }
        else
            throw new InvalidParameterException();
    }

    public static void printSide(int startRow, int startCol, SideCard card) {

        if (areValidCoordinates(startRow, startCol)) {

            printAllCells(startRow,startCol,card.getCells());

            printAllVerticalMargin(startRow,startCol);

            printAllOrizzontalMargin(startRow,startCol);

            printCoordinates(startRow,startCol);

            printFavours(startRow,startCol,card.getFavours());

            System.out.print(ansi().cursor(startRow + NAME_ROW, startCol + NAME_COL).bold().a(card.getName()).boldOff());
        }
        else
            throw new InvalidParameterException();
    }

}
