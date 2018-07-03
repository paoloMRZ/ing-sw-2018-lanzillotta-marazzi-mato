package it.polimi.se2018.server.model.grid;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.exceptions.InvalidValueException;
//todo studiare meglio la possibilità di usare le celle per definire le posizioni nella grid
//import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
     * @return refrence della copia del dado che viene passato per copia.
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
     * @param posOnGrid
     * @param posOnLilGroup
     * @return
     * @throws InvalidValueException
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


    //metodo dedicato alle rimanenze della riserva che finiscono sulla griglia, viene appeso perchè è l'ultimo
    //round appena giocato
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


    public int getRound(){
        return actualRound;
    }
    /////////////Comunicazione/////
    private UpdateM createResponse(){

            String content = this.toString();

            return new UpdateM(null,"RoundGrid", content);
    }
    public String toString(){

            String message = "";
            Dice die=null;
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
