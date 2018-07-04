package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

/**
 * viene sollevata quando si tenta di inserire un dado in una cella che non possiede dadi confianti.
 */
public class NoDicesNearException extends InvalidCellException {

    public NoDicesNearException(){super();}
}
