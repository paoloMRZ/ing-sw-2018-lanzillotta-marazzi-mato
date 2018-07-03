package it.polimi.se2018.server.exceptions.invalid_cell_exceptios;

import it.polimi.se2018.server.exceptions.InvalidCellException;

public class NearDiceInvalidException extends InvalidCellException {

    //OVERVIEW: viene sollevata quando l'inserimento di un dado non rispetta le condizioni imposte dai dadi ortogonalmente confinanti.

    public NearDiceInvalidException(){super();}
}
