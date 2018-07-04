package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;
/**
 * Evento laciato per indicare un successo di attivazione di una carta utensile multi fase che richieda il ripescaggio di un dado.
 * La classe possiede un getter per ogni attributo.
 * @author Kevin Mato
 */
public class SuccessColor extends Activate {
    private String value;

    public SuccessColor( String player,int index,String color){
        super(player,index);
        this.value=color ;

    }
    public String getValue(){
        return value;
    }
}
