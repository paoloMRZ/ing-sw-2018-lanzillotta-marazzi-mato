package it.polimi.se2018.server.model.reserve;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.se2018.server.model.dice_sachet.Dice;


public class Reserve {

    private ArrayList<Dice> Dices;


    //il costruttore prende alla costruzione il riferimento ad un ArrayList
    public Reserve(ArrayList<Dice> dices){
        this.Dices= dices;
    }


    //ritorna una copia dell'istanza del dado contenuto dentro la riserva
    public Dice pick(int pos) throws ArrayIndexOutOfBoundsException,NullPointerException{
        Dice tmp= new Dice(Dices.get(pos).getColor(),Dices.get(pos).getNumber());
        Dices.remove(pos);
        return tmp;
    }


    public void put(Dice d){
        Dice tmp= new Dice(d.getColor(),d.getNumber());
        Dices.add(tmp);
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

}
