package it.polimi.se2018.client.cli.print.utils;

import static org.fusesource.jansi.Ansi.ansi;

public class TextPrinter {

    private TextPrinter(){}

    public static void printText(int startRow, int startCol, String text, int rowLen){
        String[] words = text.split(" "); //Estraggo le parole.
        int counter = 0; //Contatore dei caratteri già stampati.
        int row = startRow;

        System.out.print(ansi().cursor(row, startCol)); //Porto il cursore nella posizione iniziale.

        for(String word : words) {
            if (counter + word.length() + 1 < rowLen) { //Controllo se la lunghezza della parola da stampare più lo spazio che la segue non ci porta fuori dalla lunghezza massima.
                System.out.print(ansi().a(word + " ")); //Stampo la parola seguita da uno spazio.

                counter += word.length() + 1; //Aggiorno il contatore aggiungedo il numero di caratteri stampati.
            } else {
                row += 1;
                System.out.print(ansi().cursor(row, startCol)); //Porto il cursore all'inizio della riga successiva.
                System.out.print(word + " "); //Stampo la parola seguita da uno spazio.

                counter = word.length() + 1; //Resetto il contatore imponendo il numero di caratteri stampati.
            }
        }
    }
}
