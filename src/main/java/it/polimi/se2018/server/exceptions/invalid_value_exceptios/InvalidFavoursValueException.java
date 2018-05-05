package it.polimi.se2018.server.exceptions.invalid_value_exceptios;

import it.polimi.se2018.server.exceptions.InvalidValueException;

public class InvalidFavoursValueException extends InvalidValueException {

    //OVERVIEW: viene lanciata quando si rileva un valore imossibile per un parametro che rappresenta il numero di segnalini favore associati ad una carta.

    public InvalidFavoursValueException(){super();}
    public InvalidFavoursValueException(String msg){super(msg);}
}
