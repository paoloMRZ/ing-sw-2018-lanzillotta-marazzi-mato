package it.polimi.se2018.client.cli.controller.stdin_controller;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Input implements Runnable {

    private static final int INPUT_ERROR = 999; //Indica che è stato inserito un input non corretto.
    private static final int INPUT_INT_BACK_MENU = 888; //Indica che è stata inserita una richiesta per tornare al menù principale.
    private static final int EXIT_REQUEST = 1000; //Indica che il giocatore ha scelto di chiudere sagrada.

    private static final String INPUT_STR_BACK_MENU = "q"; //Carattere di richiesta per tornare la menù principale.
    private static final String INPUT_STR_EXIT = "exit"; //Carattere di richiesta per tornare la menù principale.


    private InputObserver observer;
    private boolean loop;


    public Input(InputObserver observer) {
        if (observer != null) {
            this.observer = observer;
            this.loop = true;
        } else
            throw new InvalidParameterException();
    }

    /**
     * Il seguente metodo va lanciato su un thread dedicato in modo che sia in ascolto sullo stdin senza bloccare il programma.
     */
    @Override
    public void run() {

        Scanner input = new Scanner(System.in);

        int numberRequest;
        String stringRequest;

        while (loop) {

            if (input.hasNextInt()) { //Controllo se ho un intero da leggere, se si lo lego e lo invio al gestore di input (cioè l'osservatore).
                numberRequest = input.nextInt();
                observer.InputRequest(numberRequest);
            } else { //Se non ho un intero da leggere controllo se ho qualcosa sul buffer di ingresso.
                if (input.hasNext()) { //Se ho qualcosa (che per forza non sarà un intero) lo leggo come stringa.

                    stringRequest = input.next();

                    if(stringRequest.equals(INPUT_STR_EXIT)) //Controllo se è stata immessa una richiesta di chiusura.
                        observer.InputRequest(EXIT_REQUEST);

                    if(stringRequest.equals(INPUT_STR_BACK_MENU)) //Se la stringa letta è il carattere 'q' invio al gestore il numero di richiesta del menù principale.
                        observer.InputRequest(INPUT_INT_BACK_MENU);
                    else
                        observer.InputRequest(INPUT_ERROR);
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
