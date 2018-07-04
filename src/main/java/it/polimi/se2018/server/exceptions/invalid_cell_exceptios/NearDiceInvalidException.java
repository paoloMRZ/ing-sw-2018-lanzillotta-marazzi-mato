package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

/**
 * viene sollevata quando l'inserimento di un dado non rispetta le condizioni imposte dai dadi ortogonalmente confinanti.
 */
public class NearDiceInvalidException extends InvalidCellException {


    public NearDiceInvalidException(){super();}
}
