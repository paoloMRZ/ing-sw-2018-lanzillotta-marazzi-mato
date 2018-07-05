package it.polimi.se2018.client.cli.print.components;

import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.info.Info;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permetti di stampare a schermo la riserva dei dadi.
 *
 * @author Marazzi Paolo
 */

public class ReservePrinter {

    private static final int HIGH = 5;
    private static final int DIE_LEN = 5;

    private static final int NAME_COL = 1;
    private static final int NAME_ROW = 6;
    private static final String TEXT_NAME = "RISERVA";

    private static final int NUMBER_ROW = 0;
    private static final int NUMBER_COL = 3;
    private static final int NUMBER_NEXT_COL = DIE_LEN + 1;

    private ReservePrinter(){}


    /**
     * Il metodo controlla se la coppia di cordinate è valida.
     * @param row riga.
     * @param col colonna.
     * @return true se la coppia di coordinate è valida.
     */
    private static boolean areValidCoordinates(int row, int col){
        return row >= 1 && col >= 1;
    }

    /**
     * Il metodo controlla se le coppie di valori (colore, numero) che descrivono i dadi sono accettabili.
     * @param dice lista che contiene le descrizioni dei dadi.
     * @return true se le descrizioni sono accettabili.
     */
    private static boolean arePrintableInfo(List<DieInfo> dice){
        for(Info info: dice){
            if(info.getColor() == Ansi.Color.WHITE && info.getNumber() == 0)
                return false;
        }
        return true;
    }

    /**
     * Il metodo stampa a schermo gli indici di riferimento della riserva.
     * @param startRow riga su è posizionato l'angolo superiore sinistro della riserva.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della riserva.
     * @param numberOfDice numero dei dadi presenti nella riserva.
     */
    private static void printNumbers(int startRow, int startCol, int numberOfDice){
        int col = startCol + NUMBER_COL;

        for(int i = 0; i< numberOfDice; i++){
            AnsiConsole.out.print(ansi().cursor(startRow+NUMBER_ROW, col).bold().a(i).boldOff());
            col += NUMBER_NEXT_COL;
        }
    }

    /**
     * Il metodo stamapa a schermo la riserva indicata nella posizione specificata.
     * @param startRow riga su è posizionato l'angolo superiore sinistro della riserva.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della riserva.
     * @param dice descrizione della riserva.
     */
    public static void printReserve(int startRow, int startCol, List<DieInfo> dice){


        if(areValidCoordinates(startRow,startCol) && arePrintableInfo(dice)) { //Controllo se i parametri sono accettabili.
            int col = startCol + 1; // nella colonna di "partenza" verrà stampato il margine.
            int row = startRow + 2; //Nella riga di partenza verrà stampato il nome ("riserva"), nella riga successiva il margine.

            for (Info info : dice) { //Stampo i dadi.
                DiePrinter.printDie(info.getColor(), info.getNumber(), row, col);
                col += DIE_LEN+1;
            }

            //Stampo i margini verticali.
            MarginPrinter.printColMargin(startRow+1,startCol, HIGH);
            MarginPrinter.printColMargin(startRow+1,startCol + (DIE_LEN*dice.size()) + dice.size(),HIGH); //Mi metto nella colonna immediatamente successiva all'ultimo dado stampato.

            //Stampo i margini orizzontali.
            MarginPrinter.printRowMargin(startRow+1,startCol, (DIE_LEN*dice.size()) + dice.size() + 1);
            MarginPrinter.printRowMargin(startRow+1 + HIGH-1, startCol, (5*dice.size()) + dice.size() + 1);

            //Stampo i nome.
            AnsiConsole.out.print(ansi().cursor(startRow+NAME_ROW, startCol+ NAME_COL).bold().a(TEXT_NAME).boldOff());

            //Stampo i numeri.
            printNumbers(startRow, startCol, dice.size());

        }
        else
            throw new InvalidParameterException();
    }
}
