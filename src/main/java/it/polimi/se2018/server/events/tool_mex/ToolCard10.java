package it.polimi.se2018.server.events.tool_mex;


/**
 * Evento specializzato nell'uso della toolcard numero 10.
 * Costruttore speciale che prende il nome del giocatore, l'indice della carta e l'indice del dado.
 * @author Kevin Mato
 */
public class ToolCard10 extends Activate {
    private int die;

    public ToolCard10(String player,int cardIndex,int die){
        super(player,cardIndex);
        this.die=die;
    }
    public int getDie(){
        return die;
    }
}

