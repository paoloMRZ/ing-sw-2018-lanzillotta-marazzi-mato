package it.polimi.se2018.server.model.reserve;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.dice_sachet.Dice;


public class Reserve {
    private ArrayList<Dice> Dices;


    //il costruttore prende alla costruzione il riferimento ad un ArrayList
    public Reserve(ArrayList<Dice> dices){
        this.Dices= dices;
        setUpdate();
    }


    //ritorna una copia dell'istanza del dado contenuto dentro la riserva
    public Dice pick(int pos) throws ArrayIndexOutOfBoundsException,NullPointerException{
        Dice tmp= new Dice(Dices.get(pos).getColor(),Dices.get(pos).getNumber());
        Dices.remove(pos);
        setUpdate();
        return tmp;
    }


    public void put(Dice d){
        Dice tmp= new Dice(d.getColor(),d.getNumber());
        Dices.add(tmp);
        setUpdate();
    }

    //passo ina copia per rispettare le regole di incapsulamento
    public ArrayList<Dice> getDices(){
        ArrayList<Dice> ritorno = new ArrayList<>();
        Iterator<Dice> el = Dices.iterator();

        while(el.hasNext()){
            Dice tmp =el.next();
            Dice toPass= new Dice(tmp.getColor(),tmp.getNumber());
            ritorno.add(toPass);
        }

        return ritorno;
    }
    /////////////Comunicazione/////
    private UpdateM createResponse(){
        String who = this.getClass().getName();
        String content = this.toString();

        return new UpdateM(who, content);
    }
    //todo toString da fare

    public UpdateM updateForcer(UpdateReq m){
        if(m.getWhat().contains(this.getClass().getName())){
            return createResponse();}
        return null;
    }

    public UpdateM setUpdate(){
        return createResponse();
    }
}
