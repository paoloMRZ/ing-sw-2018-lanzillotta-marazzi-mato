package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.UpdateReq;

import java.util.ArrayList;
import java.util.List;
/**
 * Evento lanciato per chiedere quale delle carte side voglia scegliere prima di iniziare la partita.
 * L'evento conterrà l'elenco dei nomi delle carta schema a disposizione per la scelta.
 * @author Kevin Mato
 */
public class AskPlayer extends UpdateReq{

    private ArrayList<String> content;

    public AskPlayer(String player,String what,ArrayList<String> cont){
        super(player,what);
        this.content=new  ArrayList<>(cont);
    }
    public List<String> getArr(){
        return new ArrayList<>(content);
    }
}
