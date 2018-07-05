package it.polimi.se2018.client.cli.print.components;

import it.polimi.se2018.client.cli.game.info.CellInfo;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.info.Info;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe consente di stampare a schermo una carta finestra.
 *
 * @author Marazzi Paolo
 */

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

    /**
     * Il metodo controlla se la coppia di cordinate è valida.
     * @param row riga.
     * @param col colonna.
     * @return true se la coppia di coordinate è valida.
     */
    private static boolean areValidCoordinates(int row, int col) {
        return row >= 1 && col >= 1;
    }

    //------------------------ Metodi di appoggio per stampare le celle. ----------------------------------------------

    /**
     * Il metodo stampa una celle con restrizione di valore 1 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */

    private static void printOne(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(FIVE_SPACES)
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(FIVE_SPACES).boldOff().bgDefault());
    }


    /**
     * Il metodo stampa una celle con restrizione di valore 2 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */

    private static void printTwo(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stampa una celle con restrizione di valore 3 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */

    private static void printThree(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stampa una celle con restrizione di valore 4 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */
    private static void printFour(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stampa una celle con restrizione di valore 5 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */
    private static void printFive(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stampa una celle con restrizione di valore 6 nella posizione indicata.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */
    private static void printSix(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.BLACK).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    //----------------------- Metodi che stampano i tre tipi possibili di celle ---------------------------------------

    /**
     * Il metodo permette di stampare a schermo una cella senza nessuna restrizione.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     */
    private static void printCell(int startRow, int startCol) {
        AnsiConsole.out.print(ansi().bgBright(Ansi.Color.WHITE).cursor(startRow, startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(FIVE_SPACES).bgDefault());
    }

    /**
     * Il metodo permette di stampare a schermo una cella con restrizione di colore.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     * @param color colore della restrizione.
     */
    private static void printCell(int startRow, int startCol, Ansi.Color color) {
        AnsiConsole.out.print(ansi().cursor(startRow, startCol).fg(color)
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .cursorToColumn(startCol).a(TEXTURE + TEXTURE + TEXTURE + TEXTURE + TEXTURE + "\n")
                .fgDefault());
    }

    /**
     * Il metodo permette di stampare a schermo una cella con restrizione di valore.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     * @param number valore della restrizione.
     */
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

    /**
     * Il metodo permette di stampare una singola cella nella posizione indicata.
     * In base alla combinazione di colore/numero passata viene stampato un determinato tipo di cella.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della cella.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della cella.
     * @param color colore della cella.
     * @param number valore della cella.
     */

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

    /**
     * Il metodo stampa tutte le celle e gli eventuali dadi posizionati su di esse.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param cells lista che contiene le descrizioni delle celle che compongono la carta.
     * @param dice lista che contiene le descrizioni dei dadi posizionati sulla carta.
     */
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


    /**
     * Il metodo stampa tutte le celle della carta.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param cells lista che contiene le descrizioni delle celle che compongono la carta.
     */
    private static void printAllCells(int startRow, int startCol, List<CellInfo> cells){

        //La prima riga e la prima colonna sono occupati dai numeri che indicano le coordinate.
        //La seconda riga e la seconda colonna sono occupate dai margini esterni.

        int row = startRow + 2;
        int col = startCol + 2;

        int counter = 0; //contatore delle celle/dadi stampati su una riga.

        //Ciclo che stampa le caselle/dadi.

        for (Info cell : cells) {

            //Stampo la cella.
            printSingleCell(row, col, cell.getColor(),cell.getNumber());


            counter++;

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


    /**
     * Il metodo stampa tutti i margini verticali della carta.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     */
    private static void printAllVerticalMargin(int startRow, int startCol){

        //Ciclo che stampa i margini verticali.

        int row = startRow + 1; //Resetto l'indice di riga.
        int col = startCol + 1; //Resetto l'indice di colonna.

        for(int i=0; i<6; i++){
            MarginPrinter.printColMargin(row,col,17); //Stampo il margine.
            col += 6; //Mi sposto alla colonna successiva.
        }

    }

    /**
     * Il metodo stampa tutti i margini orizzontali della carta.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     */
    private static void printAllOrizzontalMargin(int startRow, int startCol){

        //Cliclo che stampa i margini orizzontali.

        int row = startRow + 1; //Resetto l'indice di riga.
        int col = startCol + 1; //Resetto l'indice di colonna.

        for(int i=0; i<5; i++){
            MarginPrinter.printRowMargin(row,col,31); //Stampo il margine.
            row += 4; //Mi sposto alla colonna successiva.
        }
    }

    /**
     * Il metodo stampa le coordinate di riga e colonna che aiutano il giocatore nell'individuazione di una cella.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     */
    private static void printCoordinates(int startRow, int startCol){

        int row = startRow;
        int col = startCol+4; //Mi metto al "centro" della riga.

        //Stampo le coordinate delle righe.
        for(int i= 0; i<5; i++){
            AnsiConsole.out.print(ansi().cursor(row,col).bold().a(i).boldOff());
            col += NEXT_COL;
        }

        row = startRow + 3; //Mi metto al "centro" della colonna.
        col = startCol;

        for(int i = 0; i<4; i++){
            AnsiConsole.out.print(ansi().cursor(row,col).bold().a(i).boldOff());
            row += NEXT_ROW;
        }

    }

    private static void printFavours(int startRow, int startCol, int favours){
        for(int i = 0;  i< favours; i++){
            AnsiConsole.out.print(ansi().cursor(startRow+FAVOURS_ROW, startCol+FAVOURS_COL+i).a(DOT));
        }
    }

    //---------------------- Metodo di stampa -------------------------------------------------------------------------

    /**
     * Il metodo permette di stampare, nella posizione indicata, una carta finestra e i dadi posizionati su di essa.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param card carta da stamapre.
     * @param dice lista che contiene le descrizioni dei dadi posizionati sulla carta.
     */

    public static void printSide(int startRow, int startCol, SideCard card, List<DieInfo> dice) {

        if (areValidCoordinates(startRow, startCol) && dice.size() == NUMBER_OF_CELLS) {

            printAllCells(startRow,startCol,card.getCells(),dice);

            printAllVerticalMargin(startRow,startCol);

            printAllOrizzontalMargin(startRow,startCol);

            printCoordinates(startRow,startCol);

            printFavours(startRow,startCol,card.getFavours());

            AnsiConsole.out.print(ansi().cursor(startRow + NAME_ROW, startCol + NAME_COL).bold().a(card.getName()).boldOff());

        }
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo permette di stampare, nella posizione indicata, una carta finestra senza gli eventuali dadi posizionati su di essa.
     *
     * @param startRow riga su cui si trova l'angolo superiore sinistro della carta.
     * @param startCol colonna su cui si trova l'angolo superiore sinistro della carta.
     * @param card carta da stampare.
     */
    public static void printSide(int startRow, int startCol, SideCard card) {

        if (areValidCoordinates(startRow, startCol)) {

            printAllCells(startRow,startCol,card.getCells());

            printAllVerticalMargin(startRow,startCol);

            printAllOrizzontalMargin(startRow,startCol);

            printCoordinates(startRow,startCol);

            printFavours(startRow,startCol,card.getFavours());

            AnsiConsole.out.print(ansi().cursor(startRow + NAME_ROW, startCol + NAME_COL).bold().a(card.getName()).boldOff());
        }
        else
            throw new InvalidParameterException();
    }

}
