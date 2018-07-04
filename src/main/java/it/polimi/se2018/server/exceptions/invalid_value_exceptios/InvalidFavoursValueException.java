package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

/**
 * Viene lanciata quando si rileva un valore imossibile per un parametro che rappresenta il numero di segnalini favore associati ad una carta.
 */
public class InvalidFavoursValueException extends InvalidValueException {
    public InvalidFavoursValueException(){super();}
}
