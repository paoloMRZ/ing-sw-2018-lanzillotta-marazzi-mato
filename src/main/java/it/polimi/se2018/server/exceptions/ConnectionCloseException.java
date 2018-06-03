package it.polimi.se2018.server.exceptions;

/**
 *
 * Viene sollevata per indicare l'impossibilità di comunicare tramite la rete.
 * @author Marazzi Paolo
 */
public class ConnectionCloseException extends  SagradaException{
    public ConnectionCloseException(){super();}
}
