package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
/**
 * Evento specializzato nell'uso della toolMultiParam.
 * Costruttore standard per molte carte utensile di tipo più specifico.
 * La sua struttura dati segue la convezione del protocollo..
 * extra/DiePerIndexDie/Box Round Grid/In Box RoundGrid/Row1 Vecchia/Col1 Vecchia/Row1New/Col1New/Row2 vecchia/Col2 Vecchia
 * @author Kevin Mato
 */
public  class ToolMultiParam extends Activate{
    private ArrayList<Integer> data;

    public ToolMultiParam(String player,int card,ArrayList<Integer> inputs){
        super(player,card);
        data= new ArrayList<>(inputs);
    }

    /**
     * getter della struttura ati contentente i parametri per l'uso delle carte.
     * Viene ereditato dalle classi più specifiche.
     * @return arraylist di valori numerici.
     */
    public ArrayList<Integer> getAttributes(){
        return data;
    }


}
