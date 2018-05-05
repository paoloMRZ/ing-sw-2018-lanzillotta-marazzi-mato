package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

public class NoDicesNearException extends InvalidCellException {

    //OVERVIEW: viene sollevata quando si tenta di inserire un dado in una cella che non possiede dadi confianti.

    public NoDicesNearException(){super();}
    public NoDicesNearException(String msg){super(msg);}
}
