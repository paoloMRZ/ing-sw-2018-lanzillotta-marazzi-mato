package it.polimi.se2018.client.cli.print.components;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permette di stampare un dado a schermo.
 *
 * @author Marazzi Paolo
 */

public class DiePrinter {

    private static final String DOT = "O";
    private static final String FIVE_SPACES =  "     \n";

    private DiePrinter(){}


    /**
     * Il metodo controlla se il numero è compreso tra 1 e 6.
     * @param number numero da controllare.
     * @return true se il numero è accettabile.
     */
    private static boolean isAcceptableNumber(int number){
        return number >= 1 && number <= 6;
    }

    /**
     * Il metodo controlla se la coppia di cordinate è valida.
     * @param row riga.
     * @param col colonna.
     * @return true se la coppia di coordinate è valida.
     */
    private static boolean areValidCoordinates(int row, int col){
        return row >=1 && col >= 1;
    }

    /**
     * Il metodo controlla se il colore rientra nella lista dei colori validi per un dado.
     * @param color colore da controllare.
     * @return true se il colore è valido.
     */
    private static boolean isAcceptableColor(Ansi.Color color){
        return  color == Ansi.Color.GREEN ||
                color == Ansi.Color.RED ||
                color == Ansi.Color.YELLOW ||
                color == Ansi.Color.MAGENTA ||
                color == Ansi.Color.BLUE;
    }

    /**
     * Il metodo stamapa un dado di valore 1 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */

    private static void printOne(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(FIVE_SPACES)
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(FIVE_SPACES).boldOff().bgDefault());
    }

    /**
     * Il metodo stamapa un dado di valore 2 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */
    private static void printTwo(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT +"    \n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stamapa un dado di valore 3 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */
    private static void printThree(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stamapa un dado di valore 4 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */
    private static void printFour(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }
    /**
     * Il metodo stamapa un dado di valore 5 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */
    private static void printFive(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stamapa un dado di valore 6 del colore e nella posizione indicati.
     * @param color colore del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado.
     */
    private static void printSix(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    /**
     * Il metodo stampa un dado con le caratteristiche indicate dai parametri.
     *
     * @param color colore del dado.
     * @param number numero del dado.
     * @param startRow riga su cui stampare il dado.
     * @param startCol colonna su cui stampare il dado
     */

    public static void printDie(Ansi.Color color, int number, int startRow ,int startCol){

        if(isAcceptableColor(color) && isAcceptableNumber(number) && areValidCoordinates(startRow,startCol)) {
            switch (number) {

                case 1:
                    printOne(color, startRow, startCol);
                    break;

                case 2:
                    printTwo(color, startRow, startCol);
                    break;

                case 3:
                    printThree(color, startRow, startCol);
                    break;

                case 4:
                    printFour(color, startRow, startCol);
                    break;

                case 5:
                    printFive(color, startRow, startCol);
                    break;

                case 6:
                    printSix(color, startRow, startCol);
                    break;

                default:
            }
        }
        else
            throw new InvalidParameterException();
    }
}
