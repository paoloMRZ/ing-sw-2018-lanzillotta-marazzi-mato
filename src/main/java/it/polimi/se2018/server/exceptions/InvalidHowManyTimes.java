package it.polimi.se2018.server.exceptions;

/**
 * Eccezione dedicata per il player lanciata nel caso in cui il player debba giocatore più turni di quanto gli è permesso.
 * Nel caso della DiceSachet troppe estrazioni.
 */
public class InvalidHowManyTimes extends SagradaException {


    public InvalidHowManyTimes(){super();}
}
