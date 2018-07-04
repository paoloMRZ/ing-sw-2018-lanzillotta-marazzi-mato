package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

/**
 * Viene lanciata quando l'inserimento di un dado in una cella non rispetta le restrizioni di colore.
 * @author Marazzi Paolo
 */
public class InvalidColorException extends InvalidCellException {
    public InvalidColorException(){super();}
}
