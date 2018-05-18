package it.polimi.se2018.server.model;


import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;
import it.polimi.se2018.server.model.reserve.Reserve;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Table{

   private ArrayList<Utensils> utensilsDeck;
   private ArrayList<Objective> objectiveDeck;
   private ArrayList<Player> playersList;
   private DiceSachet diceSachet;
   private Reserve reserve;
   private ScoreGrid scoreGrid;
   private RoundGrid roundGrid;


//todo parlare della modifica al prototipo: tolto riserva dal costruttore
   public Table(ArrayList<Utensils> utensilsDeck, ArrayList<Objective> objectiveDeck, ArrayList<Player> playersList, DiceSachet diceSachet, ScoreGrid scoreGrid, RoundGrid roundGrid) {
        this.utensilsDeck = utensilsDeck;
        this.objectiveDeck = objectiveDeck;
        this.playersList = playersList;
        this.diceSachet = diceSachet;
        this.scoreGrid = scoreGrid;
        this.roundGrid = roundGrid;
       this.reserve = createReserve(); //todo discutere se assegnabile a costruttore
   }

   //Metodo che simula l'estrazione della riserva. Per ogni giocatore, vanno estratti 2 dadi +1 al risultato complessivo
   //Quindi alla collezione contenuta in riservca devo aggiungere (put) un dado estratto dal sacchetto dei dadi (getDiceFromSachet)
   public void extractReserve(int numbOfDice){

       int cont = (playersList.size())*2+1;
       for(; cont>0; cont--){
           reserve.put(diceSachet.getDiceFromSachet());
       }
   }

   //DOMANDA: nell'UML abbiamo specificato come tipo di ritorno un ArrayList<Dice>, non si potrebbe ritornare direttamente una copia dell'ggetto oggetto Riserva?

   public Reserve getReserve(){
       Reserve temp = new Reserve(reserve.getDices());
        return temp;
   }

   public Dice pickFromReserve(int position){
       return reserve.pick(position);
   }

   //TODO: da discutere sull'utilità dei due metodi

   public List<Utensils> getDeckUtensils(){
        ArrayList<Utensils> temp = (ArrayList<Utensils>) utensilsDeck.clone();
        return temp;
   }

    public List<Objective> getDeckObjective(){
        ArrayList<Objective> temp = (ArrayList<Objective>)utensilsDeck.clone();
        return temp;
   }


   public Utensils getUtensils(int cardPosition){
        return utensilsDeck.get(cardPosition);
   }

    //metodo che serve a a cambiare la riserva se ci sono stati cambiamenti al suo interno
    //fonadmentale per le carte utensile

   public ArrayList<Dice>  setReserve(Reserve toStore){
       ArrayList<Dice> preStored= toStore.getDices();
       ArrayList<Dice> ritorno = new ArrayList<>();
       Iterator<Dice> el = preStored.iterator();

       while(el.hasNext()){
           Dice tmp =el.next();
           Dice toPass= new Dice(tmp.getColor(),tmp.getNumber());
           ritorno.add(toPass);
       }
       return ritorno;

   }
    //todo ma serviva sto metodo che ho fatto??? o avevate altri propositi per
   //metodo che crea una nuova riserva da zero

    public Reserve createReserve(){
        ArrayList<Dice> giveTo= new ArrayList<>();
        for(int i=(playersList.size()*2+1);i>0;i--){
            giveTo.add(diceSachet.getDiceFromSachet());
        }
        return new Reserve(giveTo);
    }

    //todo è lagale questa return?
    public Player callPlayerByName(String name) throws InvalidValueException{
       for(Player p : playersList){
           if(p.getName().equals(name)) return p;
       }
       throw new InvalidValueException();
    }
}
