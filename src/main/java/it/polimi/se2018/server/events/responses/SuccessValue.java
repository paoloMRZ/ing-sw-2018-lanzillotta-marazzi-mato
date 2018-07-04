package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;
/**
 * Evento laciato per indicare un successo di attivazione di una carta utensile multi fase che richieda il rilancio di un dado.
 * La classe possiede un getter per ogni attributo.
 * @author Kevin Mato
 */
public class SuccessValue extends Activate{
    private int value;


    public SuccessValue( String player,int index,int numb){
        super(player,index);
        this.value= numb ;
    }

    public int getValue(){
        return value;
    }
}
