package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

public class NotEmptyCellException extends InvalidCellException {

    //OVERVIEW: viene lanciata quando si tenta di inserire un dado in una cella già occupata.

    public NotEmptyCellException(){super();}
}
