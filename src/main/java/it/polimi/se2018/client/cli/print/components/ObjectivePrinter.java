package it.polimi.se2018.client.cli.print.components;

import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import it.polimi.se2018.client.cli.print.utils.TextPrinter;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe consente di stampare a schermo la rappresentazione di una carta obiettivo.
 *
 * @author  Marazzi Paolo
 */

public class ObjectivePrinter {

    private ObjectivePrinter(){}


    private static final int HIGH = 6;

    private static final int LEN = 33;

    private static final int SECOND_COL = 4;

    private static final int REWARD_COL = 2;
    private static final int REWARD_ROW = 3;;

    private static final int TITLE_ROW = 1;

    private static final int DESCRIPTION_ROW = 3;
    private static final int DESCRIPTION_COL = 5;


    private static final int TEXT_SPACE = LEN - 5;


    /**
     * Il metodo stampa a schermo i margini della carta.
     *
     * @param startRow riga su è posizionato l'angolo superiore sinistro della carta.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della carta.
     */
    private static void printMargin(int startRow, int startCol){
        MarginPrinter.printColMargin(startRow,startCol, HIGH);
        MarginPrinter.printColMargin(startRow,startCol+SECOND_COL, HIGH);
        MarginPrinter.printColMargin(startRow,startCol+LEN-1, HIGH);

        MarginPrinter.printRowMargin(startRow,startCol, LEN);
        MarginPrinter.printRowMargin(startRow+HIGH ,startCol, LEN);

    }

    /**
     * Il metodo stampa a schermo la ricompensa che la carta obiettivo fornisce.
     *
     * @param startRow riga su è posizionato l'angolo superiore sinistro della carta.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della carta.
     */
    private static void printReward(int startRow, int startCol, int reward) {
        if (reward == 0)
            AnsiConsole.out.print(ansi().cursor(startRow + REWARD_ROW, startCol + REWARD_COL).bold().a("#").boldOff()); //Stampo la ricompensa.
        else
            AnsiConsole.out.print(ansi().cursor(startRow + REWARD_ROW, startCol + REWARD_COL).bold().a(reward).boldOff()); //Stampo la ricompensa.
    }

    /**
     * Il metodo stampa a schermo la carta obiettivo indicata nella posizione specificata.
     * @param startRow riga su è posizionato l'angolo superiore sinistro della carta.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della carta.
     * @param objectiveCard descrizione della carta da stampare a schermo.
     */

    public static void printObjective(int startRow, int startCol, ObjectiveCard objectiveCard){

        printMargin(startRow,startCol);
        printReward(startRow,startCol,objectiveCard.getReward());

        AnsiConsole.out.print(ansi().cursor(startRow+TITLE_ROW,startCol+( (LEN + 4 - objectiveCard.getName().length() ) / 2)).bold().a(objectiveCard.getName()).boldOff()); //Stampo il titolo della carta.
        TextPrinter.printText(startRow+DESCRIPTION_ROW, startCol+DESCRIPTION_COL, objectiveCard.getDescription(), TEXT_SPACE);

    }


}
