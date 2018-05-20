package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.SagradaException;

public class InvalidSomethingWasNotDoneGood extends SagradaException {
    //OVERVIEW: La classe è la radice del sottoalbero che raccoglie
    // le eccezioni riguardanti il passaggio di valori errati
    //questa eccezione viene lanciata  al posto di eccezioni che davvero non dovrebbero mai essere lanciate. In teoria.
    //Così se esce questa allora abbiamo sbagliato qualcosa di grave, ma molto.

    public InvalidSomethingWasNotDoneGood(){super();}
    public InvalidSomethingWasNotDoneGood(String msg){super(msg);}

}
