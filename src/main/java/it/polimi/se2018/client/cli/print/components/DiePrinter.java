package it.polimi.se2018.client.cli.print.components;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

public class DiePrinter {

    private static final String DOT = "O";
    private static final String FIVE_SPACES =  "     \n";

    private DiePrinter(){}


    private static boolean isAcceptableNumber(int number){
        return number >= 1 && number <= 6;
    }

    private static boolean areValidCoordinates(int row, int col){
        return row >=1 && col >= 1;
    }

    private static boolean isAcceptableColor(Ansi.Color color){
        return  color == Ansi.Color.GREEN ||
                color == Ansi.Color.RED ||
                color == Ansi.Color.YELLOW ||
                color == Ansi.Color.MAGENTA ||
                color == Ansi.Color.BLUE;
    }

    private static void printOne(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(FIVE_SPACES)
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(FIVE_SPACES).boldOff().bgDefault());
    }


    private static void printTwo(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT +"    \n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printThree(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "    \n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a("    " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printFour(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(FIVE_SPACES)
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printFive(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a("  " + DOT + "  \n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

    private static void printSix(Ansi.Color color, int startRow ,int startCol){
        System.out.print(ansi().bg(color).cursor(startRow, startCol).bold().a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n")
                .cursorToColumn(startCol).a(DOT + "   " + DOT + "\n").boldOff().bgDefault());
    }

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
