package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

public class InvalidColorValueException extends InvalidValueException{
    //OVERVIEW: viene lanciata quando si rileva un assegnamento di colore errato.

    public InvalidColorValueException(){super();}
    public InvalidColorValueException(String msg){super(msg);}
}
