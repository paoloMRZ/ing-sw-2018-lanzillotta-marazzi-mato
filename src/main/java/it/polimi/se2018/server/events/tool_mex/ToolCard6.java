package it.polimi.se2018.server.events.tool_mex;


/**
 * Evento specializzato nell'uso della toolcard numero 6.
 * Costruttore siile alla tool 10 ma necessitando dell'attributo se è bis o no, che viene settato di default.
 * @author Kevin Mato
 */
public class ToolCard6 extends ToolDouble{
    /**
     * Costruttore
     * @param player nome del giocatore.
     * @param card indice della carta utensile.
     * @param die indice del dado della riserva.
     */
    public ToolCard6(String player,int card,int die){
        super(player,card,die,false);
    }
}
