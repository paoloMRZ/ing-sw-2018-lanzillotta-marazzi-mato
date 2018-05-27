package it.polimi.se2018.server.model.grid;

import it.polimi.se2018.server.exceptions.InvalidValueException;
//todo studiare meglio la possibilità di usare le celle per definire le posizioni nella grid
//import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RoundGrid {
    private ArrayList<ArrayList<Dice>> roundDices;
    private int actualRound;


    public RoundGrid() {
        //metto valori di default
        this.roundDices =   new ArrayList<>(Arrays.asList(null,null,null,null,null,null,null,null,null,null));
        this.actualRound = 1;
        //for (int where=0;where<10;where++){
        //    roundDices.set(where,null);
       // }
    }

    /*
        Tenta di prelevare un Dice dall'ArrayList, le eccezioni che potrebbero insorgere riguardano
        una posizione p tale che p>10 or p<1 (ovvero si richiede un dado in una posizione che non esiste)
        oppure quando si sceglie una posizione in cui non c'è un Dice

        Pertanto la prima eccezione riguarda il range dell'Array -> ArrayOutOfBoundsException
        La seconda eccezione se la cella dell'array selezionata sia null -> NullPointerException

        Sono entrambe di tipo unchecked, quindi posso semplicemente inserire una generica Exception nel catch

    */
    //metodo dediacto al singolo dado

    public void put(int posOnGrid,Dice d) throws InvalidValueException{
        //ricopro il dado in ArrayList nel caso nella posizoine non ci siano già dati
        if(posOnGrid>10 || posOnGrid <0) throw new InvalidValueException();
        else{
            Dice tmp = new Dice(d.getColor(), d.getNumber());

            //SE LA POSIZIONE NON HA ELEMENTI
            if (roundDices.get(posOnGrid) == null) {
                ArrayList<Dice> toSave = new ArrayList<>();
                toSave.add(tmp);
                roundDices.add(posOnGrid,toSave);
            } else {
                roundDices.get(posOnGrid).add(tmp);
            }
        }
    }

    public Dice pick(int posOnGrid,int posOnLilGroup)throws InvalidValueException{

        if(posOnGrid>10 || posOnGrid <0) throw new InvalidValueException();
        else{
           Dice tmp = roundDices.get(posOnGrid).get(posOnLilGroup);
           Dice ret = new Dice(tmp.getColor(),tmp.getNumber());
           roundDices.get(posOnGrid).remove(posOnLilGroup);
           //se non ci sono più elementi mi garantisco che sia posto a null, utile alla put
           if(roundDices.get(posOnGrid).isEmpty()) roundDices.set(posOnGrid,null);
           return ret;
        }

    }
    public Dice show(int posOnGrid,int posOnLilGroup)throws InvalidValueException{

        if(posOnGrid>10 || posOnGrid <0) throw new InvalidValueException();
        else{
            Dice tmp = roundDices.get(posOnGrid).get(posOnLilGroup);
            Dice ret = new Dice(tmp.getColor(),tmp.getNumber());
            return ret;
        }

    }


    //metodo dedicato alle rimanenze della riserva che finiscono sulla griglia, viene appeso perchè è l'ultimo
    //round appena giocato
    public void putAtFinishedRound(ArrayList<Dice> lastD){
        ArrayList<Dice> save = new ArrayList<>();
        Iterator<Dice> el = lastD.iterator();

        while(el.hasNext()){
            Dice tmp =el.next();
            Dice toSave= new Dice(tmp.getColor(),tmp.getNumber());
            save.add(toSave);
        }
        roundDices.add(save);
    }


    public int getRound(){
        return actualRound;
    }
}
