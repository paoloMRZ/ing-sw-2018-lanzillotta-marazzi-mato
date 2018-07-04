package it.polimi.se2018.server.events.tool_mex;

/**
 * Evento specializzato nell'uso della toolcard numero 1.
 * @author Kevin Mato
 */
public class MoreThanSimple extends Activate{
    private int die;
    private boolean decision;//se valore da aggiungere all'attuale valore del dado
    // il vecchio prototipo
    //da allineare a tutti quelli troppo lunghi
    //era public MoreThanSimple (String player, int cardIndex, boolean addUp, int iDie)

    /**
     * Costruttore che raccoglie i dati singolarmente.
     * @param player nome del giocatore
     * @param cardIndex indice della carta
     * @param addUp booleano che dice se vogliono aggiungere (true) o togliere uno (false)
     * @param whichDie indice del dado nella riserva
     */
    public MoreThanSimple (String player, int cardIndex, boolean addUp, int whichDie){

        super(player,cardIndex);
            this.die=whichDie;
            this.decision=addUp;
    }
    public int getDie(){
        return die;
    }
    public boolean getDecision(){
        return decision;
    }
}
