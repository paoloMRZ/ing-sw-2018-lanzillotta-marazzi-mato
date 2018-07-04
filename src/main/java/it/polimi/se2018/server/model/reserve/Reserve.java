package it.polimi.se2018.server.model.reserve;

import java.util.ArrayList;
import java.util.Iterator;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.dice_sachet.Dice;

/**
 * Classe che si occupa di conservare una collezione di dadi, che può subire un inserimento di dadi o una estrazione.
 * Le istanze della classe vengono create all'inizio di ogni round.
 * Alla fine di ogni round si ricavano i suoi dadi per essere posti nella roundgrid.
 *@author Kevin Mato
 */

public class Reserve {
    private ArrayList<Dice> dices;


    /**
     * Metodo costruttore della classe.
     * @param dices dadi da immettere nella riserva
     *
     */
    public Reserve(ArrayList<Dice> dices){
        this.dices = dices;
        setUpdate();
    }


    /**
     * Metodo che ritorna una copia del dado della riserva scelto in base al suo indice, l'originale viene rimosso.
     * @param pos indice del dado nella riserva.
     * @return reference della copia del dado scelto.
     */
    public Dice pick(int pos){
        Dice tmp= new Dice(dices.get(pos).getColor(), dices.get(pos).getNumber());
        dices.remove(pos);
        setUpdate();
        return tmp;
    }

    /**
     * Metodo che salva un nuovo dado nella riseva attraverso una copia dello stesso.
     * @param d dado da salvare.
     */
    public void put(Dice d){
        if(d!=null) {
            Dice tmp = new Dice(d.getColor(), d.getNumber());
            dices.add(tmp);
            setUpdate();
        }
    }

    /**
     * Metodo che ritorna tutti i dadi della riserva
     * @return copia dei dadi cintenuti nella riserva.
     */
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

    /**
     * Metodo che crea l'evento di aggiornament della riserva.
     * @return evento contenete la rappresentazione.
     */
    private UpdateM createResponse(){
        String content = this.toString();
        return new UpdateM(null,"reserve", content);
    }

    /**
     * override del toString di Object, serve a creare una rappresentazione customizzata secondo protocollo
     * della classe.
     * @return stringa di rappresentazione della classe.
     */
    public String toString(){
        String message = "";
        if(dices.isEmpty()){
            message = message.concat("white0");
        }
        else{
            for (Dice die : dices) {
                if (dices.indexOf(die) == 0)
                    message = message.concat(die.getColor().name() + die.getNumber());
                else
                    message = message.concat("&" + die.getColor().name() + die.getNumber());
            }
        }
        message = message.concat("\n");

        return message;
    }

    /**
     * Metodo designato dalla classe tavolo che triggera la generazione di un aggiornamento della classe.
     * Il tavolo recuperare gli eventi di update grazie questi metodi.
     * @return l'evento di aggiornamento.
     */
    public UpdateM setUpdate(){
        return createResponse();
    }
}
