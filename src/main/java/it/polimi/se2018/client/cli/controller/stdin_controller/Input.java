package it.polimi.se2018.client.cli.controller.stdin_controller;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Input implements Runnable {

    private static final int INPUT_ERROR = 999; //Indica che è stato inserito un input non corretto.
    private static final int INPUT_INT_BACK_MENU = 888; //Indica che è stata inserita una richiesta per tornare al menù principale.
    private static final String INPUT_STR_BACK_MENU = "q"; //Carattere di richiesta per tornare la menù principale.


    private InputObserver observer;
    private boolean loop;


    public Input(InputObserver observer) {
        if (observer != null) {
            this.observer = observer;
            this.loop = false;
        } else
            throw new InvalidParameterException();
    }

    /**
     * Il seguente metodo va lanciato su un thread dedicato in modo che sia in ascolto sullo stdin senza bloccare il programma.
     */
    @Override
    public void run() {

        Scanner input = new Scanner(System.in);
        loop = true;

        int number_request;
        String string_request;

        while (loop) { //TODO Scegliere un valore intero di defaul per terminare il cliclo??

            if (input.hasNextInt()) { //Controllo se ho un intero da leggere, se si lo lego e lo invio al gestore di input (cioè l'osservatore).
                number_request = input.nextInt();
                observer.InputRequest(number_request);
            } else { //Se non ho un intero da leggere controllo se ho qualcosa sul buffer di ingresso.
                if (input.hasNext()) { //Se ho qualcosa (che per forza non sarà un intero) lo leggo come stringa.

                    string_request = input.next();

                    if(string_request.equals(INPUT_STR_BACK_MENU)) //Se la stringa letta è il carattere 'q' invio al gestore il numero di richiesta del menù principale.
                        observer.InputRequest(INPUT_INT_BACK_MENU);
                    else
                        observer.InputRequest(INPUT_ERROR);
                }
            }

            //NB-> Non uso l'eccezione InputMismatchException perché mi dava problemi.
        }

        input.close();
    }

    public void stop() { //TODO è necessario??
        this.loop = false;
    }
}
