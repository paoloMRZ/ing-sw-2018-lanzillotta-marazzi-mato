package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

/**
 * Viene lanciata quando si rileva un valore impossibile per un parametro che rappresenta la sfumatura di un dado.
 * @author Marazzi Paolo
 */
public class InvalidShadeValueException extends InvalidValueException {

    public InvalidShadeValueException(){super();}
}
