package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.SagradaException;

/**
 * Il lancio di questa eccezione presuppone che ci sia stata una negligenza nella progettazione.
 * Eccezione il cui lancio avviene nel momento in cui ci siamo eccezioni inaspettate o per attuare il
 * e gestione degli errori attraverso il relativo logger evento chamato similimente.
 * @author Kevin Mato
 */
public class InvalidSomethingWasNotDoneGood extends SagradaException {

    public InvalidSomethingWasNotDoneGood(){super();}

}
