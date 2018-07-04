package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

/**
 * viene lanciata quando si rileva una coppia di coordinate (relative alla griglia di un gioactore) non valide.
 */
public class InvalidCoordinatesException extends InvalidValueException {

    public InvalidCoordinatesException(){super();}
}
