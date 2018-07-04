package it.polimi.se2018.server.model.grid;

import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Classe che si occupa della raccolta dei dadi alla fine del round.
 * Si può fare una immisione e un prevelievo.
 * L'immisione può essere singola o multipla.
 * Se si tenta di prelevare un Dice dall'ArrayList, le eccezioni che potrebbero insorgere riguardano
 *una posizione p tale che p>10 or p<1 (ovvero si richiede un dado in una posizione che non esiste)
 *oppure quando si sceglie una posizione in cui non c'è un Dice
 *
 *Pertanto la prima eccezione riguarda il range dell'Array -> ArrayOutOfBoundsException
 *La seconda eccezione se la cella dell'array selezionata sia null -> NullPointerException
 *le gestisco lanciando una eccezione InvalidValue.
 * @author Kevin Mato
 */
public class RoundGrid {
    private ArrayList<ArrayList<Dice>> roundDices;
    private int actualRound;

    /**
     * Costruttore della classe che inizializza le celle di memoria del'arraylist principale.
     * Inizializza anche il numero del round.
     */

    public RoundGrid() {

        this.roundDices =   new ArrayList<ArrayList<Dice>>(Arrays.asList(
                new ArrayList<>(),new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>(),
                new ArrayList<>(),new ArrayList<>()));

        this.actualRound = 1;
    }



    /**
     *Metodo che permette l'immisione di un dado nella collezione dispondendo dell'indice della cella.
     * @param posOnGrid indice della cella dove si vuole immettere un dado.
     * @param d reference del dado da inserire.
     * @throws InvalidValueException eccezione lanciata oer gestire eccezioni unchecked.
     */
    public void put(int posOnGrid,Dice d) throws InvalidValueException{
        //ricopro il dado in ArrayList nel caso nella posizoine non ci siano già dati
        if(d!=null) {
            try {
                if (posOnGrid > 9 || posOnGrid < 0) throw new InvalidValueException();
                else {
                    Dice tmp = new Dice(d.getColor(), d.getNumber());
                    roundDices.get(posOnGrid).add(tmp);
                }
                setUpdate();
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidValueException();
            }
        }
    }

    /**
     * Metodo che permette il prelievo di un dado nella collezione dispondendo dell'indice della cella e di un indice nella cella.
     * @param posOnGrid indice della cella della roundgrid.
     * @param posOnLilGroup indice nella cella
     * @return reference della copia del dado che viene passato in copia, in seguito viene rimosso.
     * @throws InvalidValueException eccezione lanciata oer gestire eccezioni unchecked.
     */
    public Dice pick(int posOnGrid,int posOnLilGroup)throws InvalidValueException{

        if(posOnGrid>9 || posOnGrid <0) throw new InvalidValueException();
        else{
            try{
           Dice tmp = roundDices.get(posOnGrid).get(posOnLilGroup);
           Dice ret = new Dice(tmp.getColor(),tmp.getNumber());
           roundDices.get(posOnGrid).remove(posOnLilGroup);
           //se non ci sono più elementi mi garantisco che sia posto a null, utile alla put
           if(roundDices.get(posOnGrid).isEmpty()) roundDices.set(posOnGrid,new ArrayList<>());
           setUpdate();
           return ret;}
           catch (NullPointerException | IndexOutOfBoundsException e){
               throw new InvalidValueException();
           }
        }

    }

    /**
     * Metodo che mostra una copia del dado in una determinata cella.
     * @param posOnGrid indice della cella della roundgrid.
     * @param posOnLilGroup indice nella cella
     * @return reference della copia del dado che viene passato in copia.
     * @throws InvalidValueException eccezione lanciata oer gestire eccezioni unchecked.
     */
    public Dice show(int posOnGrid,int posOnLilGroup)throws InvalidValueException{
        try {
            if (posOnGrid > 9 || posOnGrid < 0) throw new InvalidValueException();
            else {
                Dice tmp = roundDices.get(posOnGrid).get(posOnLilGroup);
                if (tmp == null) throw new InvalidValueException();
                return  new Dice(tmp.getColor(), tmp.getNumber());
            }
        }
        catch (NullPointerException| IndexOutOfBoundsException e){
            throw new InvalidValueException();
        }
    }


    /**
     * Metodo che permette l'immissione simulatanea di un gruppo di dadi,solitamente quelli rimasti a fine round.
     * Vengono copiati all'interno e salvati nella prima cella libera.
     * @param lastD arraylist di dadi.
     */
    public void putAtFinishedRound(ArrayList<Dice> lastD){
        if(lastD!=null && !lastD.isEmpty()) {

            ArrayList<Dice> save = new ArrayList<>();
            Iterator<Dice> el = lastD.iterator();

            while(el.hasNext()) {
                Dice tmp = el.next();
                Dice toSave = new Dice(tmp.getColor(), tmp.getNumber());
                save.add(toSave);
            }
            roundDices.add(save);
            setUpdate();
            actualRound=actualRound+1;
        }
        else roundDices.add(new ArrayList<>());
    }

    /**
     * getter del numero del round attuale.
     * @return numero intero che indica round attuale.
     */
    public int getRound(){
        return actualRound;
    }
    /////////////Comunicazione/////
    /**
     * Metodo che crea l'evento di aggiornamento della roundgrid.
     * @return evento contenete la rappresentazione.
     */
    private UpdateM createResponse(){

            String content = this.toString();

            return new UpdateM(null,"RoundGrid", content);
    }
    /**
     * override del toString di Object, serve a creare una rappresentazione customizzata secondo protocollo
     * della classe.
     * @return stringa di rappresentazione della classe.
     */
    public String toString(){

            String message = "";
            Dice die;
            for (int i=0;i<roundDices.size();i++) {

                if(roundDices.get(i).isEmpty()){
                    if (i == 0){
                        message = message.concat("white0");
                    }
                    else message = message.concat("&white0");
                }
                else{
                    if (i != 0){
                        message = message.concat("&");
                    }
                    for (int j = 0; j < roundDices.get(i).size(); j++) {
                        die = roundDices.get(i).get(j);
                        if (j == 0)
                            message = message.concat(die.getColor().toString().toLowerCase() + die.getNumber());
                        else
                            message = message.concat(":" + die.getColor().toString().toLowerCase() + die.getNumber());
                    }
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
