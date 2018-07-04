package it.polimi.se2018.server.events;

/**
 * Evento del mvc che viene inviato in risposta ad una richiesta di piazzamento o di uso di una carta utensile,
 * se una di queste due azioni è già stata effettuata.
 * @author Kevin Mato
 */
public class IgnoreMex extends EventMVC{
    public IgnoreMex(String name){
        super(name);
    }
}
