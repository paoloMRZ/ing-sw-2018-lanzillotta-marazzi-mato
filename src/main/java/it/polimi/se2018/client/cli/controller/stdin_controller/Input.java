package it.polimi.se2018.client.cli.controller.stdin_controller;

import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * La classe iplementa un lettore di standard input.
 * In pratica rimane il ascolto sullo stdin e notifica al suo osservatore ogni valore che riceve.
 *
 * NB-> La classe fornisce all'osservatore solo valori interi.
 */

public class Input implements Runnable {

    private static final int INPUT_ERROR = 999; //Indica che è stato inserito un input non corretto.
    private static final int INPUT_INT_BACK_MENU = 888; //Indica che è stata inserita una richiesta per tornare al menù principale.
    private static final int EXIT_REQUEST = 1000; //Indica che il giocatore ha scelto di chiudere sagrada.

    private static final String INPUT_STR_BACK_MENU = "q"; //Carattere di richiesta per tornare la menù principale.
    private static final String INPUT_STR_EXIT = "exit"; //Carattere di richiesta per tornare la menù principale.


    private InputObserver observer;
    private boolean loop;

    /**
     * Costruttore della classe.
     * @param observer osservatore della classe.
     */
    public Input(InputObserver observer) {
        if (observer != null) {
            this.observer = observer;
            this.loop = true;
        } else
            throw new InvalidParameterException();
    }

    /**
     * Il seguente metodo (che va lanciato su un thread dedicato) è in ascolto sullo standard input ed ogni volta che riceve
     * un valore (eventualmente lo converte in un intero) lo passa all'osservatore.
     */
    @Override
    public void run() {

        Scanner input = new Scanner(System.in);

        int numberRequest;
        String stringRequest;

        while (loop) {

            if (input.hasNextInt()) { //Controllo se ho un intero da leggere, se si lo lego e lo invio al gestore di input (cioè l'osservatore).
                numberRequest = input.nextInt();
                observer.inputRequest(numberRequest);
            } else { //Se non ho un intero da leggere controllo se ho qualcosa sul buffer di ingresso.
                if (input.hasNext()) { //Se ho qualcosa (che per forza non sarà un intero) lo leggo come stringa.

                    stringRequest = input.next();

                    if(stringRequest.equals(INPUT_STR_EXIT)) //Controllo se è stata immessa una richiesta di chiusura.
                        observer.inputRequest(EXIT_REQUEST);

                    if(stringRequest.equals(INPUT_STR_BACK_MENU)) //Se la stringa letta è il carattere 'q' invio al gestore il numero di richiesta del menù principale.
                        observer.inputRequest(INPUT_INT_BACK_MENU);
                    else
                        observer.inputRequest(INPUT_ERROR);
                }
            }

            //NB-> Non uso l'eccezione InputMismatchException perché mi dava problemi.
        }

        input.close();
    }

    public void stop() {
        this.loop = false;
    }
}
