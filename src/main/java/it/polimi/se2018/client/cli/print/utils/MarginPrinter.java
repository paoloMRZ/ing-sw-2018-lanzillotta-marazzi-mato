package it.polimi.se2018.client.cli.print.utils;

import static org.fusesource.jansi.Ansi.ansi;

public class MarginPrinter {

    private static final String TEXTURE_VERTICAL_MARGIN = "|";
    private static final String TEXTURE_ORIZZONTAL_MARGIN = "-";

    private MarginPrinter(){}

    public static void printRowMargin(int startRow, int startCol, int len){
        for(int col = startCol; col < startCol + len; col++)
            System.out.print(ansi().cursor(startRow, col).a(TEXTURE_ORIZZONTAL_MARGIN));
    }

    public static  void printColMargin(int startRow, int startCol, int high){
        for(int row = startRow; row < startRow + high; row++)
            System.out.print(ansi().cursor(row,startCol).a(TEXTURE_VERTICAL_MARGIN));
    }

}
