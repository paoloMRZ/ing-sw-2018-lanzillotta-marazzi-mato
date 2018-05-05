package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

public class InvalidCoordinatesException extends InvalidValueException {

    //OVERVIEW: viene lanciata quando si rileva una coppia di coordinate (relative alla griglia di un gioactore) non valide.

    public InvalidCoordinatesException(){super();}
    public InvalidCoordinatesException(String msg){super(msg);}
}
