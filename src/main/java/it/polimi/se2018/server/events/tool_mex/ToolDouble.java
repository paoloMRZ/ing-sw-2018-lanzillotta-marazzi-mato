package it.polimi.se2018.server.events.tool_mex;
/**
 * Evento specializzato nell'uso della toolDouble.
 * Costruttore simile alla tool 10 ma necessitando dell'attributo se è bis o no..
 * Questa Classe viene estesa in base da classi che necessitano del booleano isBis e nuove strutture dati.
 * @author Kevin Mato
 */
public class ToolDouble extends Activate {
    private boolean isBis;
    private int die;
    public ToolDouble(String player,int card,int die,boolean is){
        super(player,card);
        this.die=die;
        this.isBis=is;
    }
    public boolean getisBis(){
        return isBis;
    }

    public int getDie() {
        return die;
    }
}
