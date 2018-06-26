package it.polimi.se2018.server.model.reserve;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.dice_sachet.Dice;


public class Reserve {
    private ArrayList<Dice> dices;


    //il costruttore prende alla costruzione il riferimento ad un ArrayList
    public Reserve(ArrayList<Dice> dices){
        this.dices = dices;
        setUpdate();
    }


    //ritorna una copia dell'istanza del dado contenuto dentro la riserva
    public Dice pick(int pos) throws ArrayIndexOutOfBoundsException,NullPointerException{
        Dice tmp= new Dice(dices.get(pos).getColor(), dices.get(pos).getNumber());
        dices.remove(pos);
        setUpdate();
        return tmp;
    }


    public void put(Dice d){
        Dice tmp= new Dice(d.getColor(),d.getNumber());
        dices.add(tmp);
        setUpdate();
    }

    //passo ina copia per rispettare le regole di incapsulamento
    public ArrayList<Dice> getDices(){
        ArrayList<Dice> ritorno = new ArrayList<>();
        Iterator<Dice> el = dices.iterator();

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

        return new UpdateM(null,"reserve", content);
    }
    //todo toString da fare
    public String toString(){
        String message = "";
        for (Dice die : dices) {
            if (dices.indexOf(die) == 0)
                message = message.concat(die.getColor().name() + die.getNumber());
            else
                message = message.concat("&" + die.getColor().name() + die.getNumber());
        }

        message = message.concat("\n");

        return message;
    }
    public UpdateM updateForcer(UpdateReq m){
        if(m.getWhat().contains(this.getClass().getName())){
            return createResponse();}
        return null;
    }

    public UpdateM setUpdate(){
        return createResponse();
    }
}
