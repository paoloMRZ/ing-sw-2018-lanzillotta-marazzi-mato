package it.polimi.se2018.client.cli.print.utils;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permette di stampare a schermo le due tipologie di margini utiizzate per costruire i vari oggetti di gioco.
 *
 * @author Marazzi Paolo.
 */

public class MarginPrinter {

    private static final String TEXTURE_VERTICAL_MARGIN = "|";
    private static final String TEXTURE_ORIZZONTAL_MARGIN = "-";

    private MarginPrinter(){}

    /**
     * Il metodo stampa un margine orizzontale.
     *
     * @param startRow riga di partenza.
     * @param startCol colonna di partenza.
     * @param len lunghezza del margine (cioè il numero di caratterei).
     */
    public static void printRowMargin(int startRow, int startCol, int len){
        for(int col = startCol; col < startCol + len; col++)
            AnsiConsole.out.print(ansi().cursor(startRow, col).a(TEXTURE_ORIZZONTAL_MARGIN));
    }

    /**
     * Il metodo stampa un margine verticale.
     *
     * @param startRow riga di partenza.
     * @param startCol colonna di partenza.
     * @param high altezza del margine (cioè il numero di caratterei).
     */
    public static  void printColMargin(int startRow, int startCol, int high){
        for(int row = startRow; row < startRow + high; row++)
            AnsiConsole.out.print(ansi().cursor(row,startCol).a(TEXTURE_VERTICAL_MARGIN));
    }

}
