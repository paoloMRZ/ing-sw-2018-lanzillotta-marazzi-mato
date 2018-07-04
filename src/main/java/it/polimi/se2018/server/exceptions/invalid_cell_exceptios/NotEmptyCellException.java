package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

/**
 * viene lanciata quando si tenta di inserire un dado in una cella già occupata.
 */
public class NotEmptyCellException extends InvalidCellException {

    public NotEmptyCellException(){super();}
}
