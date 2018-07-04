package it.polimi.se2018.server.events.tool_mex;


/**
 * Evento specializzato nell'uso della toolcard numero 11.
 * Costruttore siile alla tool 10 ma necessitando dell'attributo se è bis o no, che viene settato di default.
 * @author Kevin Mato
 */
public class ToolCard11 extends ToolDouble{
    public ToolCard11(String player,int card,int die){
        super(player,card,die,false);
    }
}

