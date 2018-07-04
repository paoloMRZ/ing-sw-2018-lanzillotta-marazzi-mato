package it.polimi.se2018.server.events;

/**
 * Evento che si occupa delle richieste di risposta con aggiornamento dello stato di alcuni elemnti del model.
 * L'evento era stato usato inizialmente per rispondere a richieste da cli di aggiornamento, ora non più utile rimane padre
 * per alcuni tipi di eventi che gli assomiglino.
 * @author Kevin Mato
 */
public class UpdateReq extends EventMVC {
    protected String what;

    public UpdateReq(String player, String what){
        super(player);
        this.what= what;
    }

    public String getWhat() {
        return what;
    }
}
