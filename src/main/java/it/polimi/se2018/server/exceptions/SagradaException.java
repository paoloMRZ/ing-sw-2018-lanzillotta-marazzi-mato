package it.polimi.se2018.server.exceptions;

public class SagradaException extends Exception {

    //OVERVIEW: La calsse è la radice dell'albero delle eccezioni per questa applicazione, quindi rappresenta
    //una generica eccezione del gioco Sagrada.

    public SagradaException(){ super();}
    public SagradaException(String msg){super(msg);}
}
