package it.polimi.se2018.client.cli.print.utils;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa di un testo.
 *
 * @author Marazzi Paolo
 */
public class TextPrinter {

    private TextPrinter(){}

    /**
     * Il metodo stampa il testo dato seguendo le seguenti regole:
     *  - su una riga devono essere presenti al massimo un numero di caratterei pari a quelli indicati dal parametro rowLen.
     *  - le parole che compongono il testo non devono mai essere spezzate su più righe.
     *  - se non c'è abbastanza spazio per stampare una parola sulla ruga corrente si passa alla riga successiva.
     *
     * @param startRow riga su cui iniziare a stampare.
     * @param startCol colonna su cui iniziare a stampare.
     * @param text testo da stampare.
     * @param rowLen numero massimo di caratteri per riga (compresi gli spazi).
     */
    public static void printText(int startRow, int startCol, String text, int rowLen){
        String[] words = text.split(" "); //Estraggo le parole.
        int counter = 0; //Contatore dei caratteri già stampati.
        int row = startRow;

        AnsiConsole.out.print(ansi().cursor(row, startCol)); //Porto il cursore nella posizione iniziale.

        for(String word : words) {
            if (counter + word.length() + 1 < rowLen) { //Controllo se la lunghezza della parola da stampare più lo spazio che la segue non ci porta fuori dalla lunghezza massima.
                AnsiConsole.out.print(ansi().a(word + " ")); //Stampo la parola seguita da uno spazio.

                counter += word.length() + 1; //Aggiorno il contatore aggiungedo il numero di caratteri stampati.
            } else {
                row += 1;
                AnsiConsole.out.print(ansi().cursor(row, startCol)); //Porto il cursore all'inizio della riga successiva.
                AnsiConsole.out.print(word + " "); //Stampo la parola seguita da uno spazio.

                counter = word.length() + 1; //Resetto il contatore imponendo il numero di caratteri stampati.
            }
        }
    }
}
