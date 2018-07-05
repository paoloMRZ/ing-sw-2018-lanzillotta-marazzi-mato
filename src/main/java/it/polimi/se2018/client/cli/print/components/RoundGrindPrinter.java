package it.polimi.se2018.client.cli.print.components;


import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permette di stampare a schermo la roundgrid.
 *
 * @author Marazzi Paolo
 */

public class RoundGrindPrinter {

    private static final String TEXT_NAME = "ROUND";
    private static final int TEXT_NAME_ROW = 6;
    private static final int TEXT_NAME_COL = 1;


    private RoundGrindPrinter(){}

    /**
     * Il metodo stampa i round vuoti della roundgrid.
     * In pratica si occupa solo della costruzione dei margini mancnti, creando così dei quadrati vuoti in corrispondenza
     * dei round vuoti.
     *
     * @param startRow riga su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param numberOfRoundPlayed numero dei round già giocatai.
     */
    private static void printEmptyRound(int startRow, int startCol, int numberOfRoundPlayed){

        //Stampo le caselle vuote dei round mancanti.
        int counter = numberOfRoundPlayed+1;
        int col = startCol;

        if(numberOfRoundPlayed == 0) { //Se non è ancora stato giocato nessun round.
            MarginPrinter.printColMargin(startRow + 1, col - 1, 4); //Stampo il margine verticale sinistro.
            AnsiConsole.out.print(ansi().cursor(startRow+TEXT_NAME_ROW, startCol+TEXT_NAME_COL-1).bold().a(TEXT_NAME).boldOff());//Stampo il nome.

        }

        while(counter <= 10){

            AnsiConsole.out.print(ansi().cursor(startRow, col+2).bold().a(counter).boldOff());

            MarginPrinter.printColMargin(startRow+1, col+5, 4); //Stampo il margine verticale destro.
            MarginPrinter.printRowMargin(startRow+1, col-1,7); //Stampo il margine orizzontale superiore.

            MarginPrinter.printRowMargin(startRow+5,col-1,7); //Stampo il margine orizzontale inferiore.

            col += 6; //Mi sposto alla colonna successiava.

            counter++;
        }

    }

    /**
     *
     * Il metodo stampa a schermo la roundgrid nella posizione specificata.
     * @param startRow riga su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param rounds round già giocati. Un round è descritto come una lista che contiene le descrizioni dei dadi che lo compongono.
     */
    public static void printRoundGrid(int startRow, int startCol, List<ArrayList<DieInfo>> rounds){

        int row = startRow + 2;
        int col = startCol + 1;
        int counter = 1;

        //Stampo le caselle relative ai round giocati.

        for(ArrayList<DieInfo> dice: rounds){

            AnsiConsole.out.print(ansi().cursor(row-2, col+2).bold().a(counter).boldOff()); //Stampo il numero del round.

            MarginPrinter.printColMargin(row-1, col-1, (3*dice.size()) + dice.size()); //Stampo il margine verticale sinistro.
            MarginPrinter.printColMargin(row-1, col+5, (3*dice.size()) + dice.size()); //Stampo il margine verticale destro.
            MarginPrinter.printRowMargin(row-1,col-1,7); //Stampo il margine orizzontale superiore.

            for(DieInfo die :dice){
                DiePrinter.printDie(die.getColor(),die.getNumber(),row, col); //Stampo il dado.
                row = row + 4; //Scendo alla riga successiva.
            }

            if(dice.isEmpty()) //Se il round non contiene dadi il for precedente non viene eseguito, quindi devo scendere "manualmente" di riga per stampare il margine inferiore.
                row = row + 4; //Scendo alla riga successiva.

            MarginPrinter.printRowMargin(row-1,col,5); //Stampo il margine orizzontale inferiore.

            row = startRow + 2; //Mi riporto alla riga iniziale.
            col += 6; //Mi sposto alla colonna successiava.

            counter ++; //Incremento il numero del round da stampare.
        }


        printEmptyRound(startRow, col, rounds.size() );

    }

    /**
     * Il metodo stampa a schermo la roundgrid di dimesioni ridotte nella posizione specificata.
     * Con "dimensioni ridotte" si intende che viene stampato solo il primo dado di ogni round (e possibile che su un round ci siano più dadi).
     *
     * @param startRow riga su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param startCol colonna su è posizionato l'angolo superiore sinistro della roundgrid.
     * @param rounds round già giocati. Un round è descritto come una lista che contiene le descrizioni dei dadi che lo compongono.
     */

    public static void printSmallRoundGrid(int startRow, int startCol, List<ArrayList<DieInfo>> rounds){
        int row = startRow + 2;
        int col = startCol + 1;
        int counter = 1;

        //Stampo le caselle relative ai round giocati.

        for(ArrayList<DieInfo> dice: rounds){

            AnsiConsole.out.print(ansi().cursor(row-2, col+2).bold().a(counter).boldOff()); //Stampo il numero del round.

            MarginPrinter.printColMargin(row-1, col-1, 4); //Stampo il margine verticale sinistro.
            MarginPrinter.printColMargin(row-1, col+5, 4); //Stampo il margine verticale destro.
            MarginPrinter.printRowMargin(row-1,col-1,7); //Stampo il margine orizzontale superiore.

            if(!dice.isEmpty()) //Se il round contiene dadi stampo il primo se no lascio lo spazio vuoto.
                DiePrinter.printDie(dice.get(0).getColor(),dice.get(0).getNumber(),row, col); //Stampo il dado.

            MarginPrinter.printRowMargin(row+3,col-1,7); //Stampo il margine orizzontale inferiore.

            row = startRow + 2; //Mi riporto alla riga iniziale.
            col += 6; //Mi sposto alla colonna successiava.

            counter ++; //Incremento il numero del round da stampare.

            //Stampo il nome.
            AnsiConsole.out.print(ansi().cursor(startRow+TEXT_NAME_ROW, startCol+TEXT_NAME_COL).bold().a(TEXT_NAME).boldOff());
        }


        printEmptyRound(startRow, col, rounds.size() );
    }

}
