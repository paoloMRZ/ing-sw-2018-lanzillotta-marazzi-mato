package it.polimi.se2018.server.exceptions;

/**
 * L'eccezione viene sollevata dalla lobby quando un client non congelato tenta di collegarsi a partita già iniziata.
 *@author Marazzi Paolo
 */
public class GameStartedException extends SagradaException {
    public GameStartedException(){super();}
}
