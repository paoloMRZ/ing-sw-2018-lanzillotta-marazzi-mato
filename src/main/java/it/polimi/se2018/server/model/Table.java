package it.polimi.se2018.server.model;

import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;
import it.polimi.se2018.server.model.reserve.Reserve;

import java.util.ArrayList;

public class Table {

   private ArrayList<Utensils> utensilsDeck;
   private ArrayList<Objective> objectiveDeck;
   private ArrayList<Player> playersList;
   private DiceSachet diceSachet;
   private Reserve reserve;
   private ScoreGrid scoreGrid;
   private RoundGrid roundGrid;


   public Table(ArrayList<Utensils> utensilsDeck, ArrayList<Objective> objectiveDeck, ArrayList<Player> playersList, DiceSachet diceSachet, Reserve reserve, ScoreGrid scoreGrid, RoundGrid roundGrid) {
        this.utensilsDeck = utensilsDeck;
        this.objectiveDeck = objectiveDeck;
        this.playersList = playersList;
        this.diceSachet = diceSachet;
        this.reserve = reserve;
        this.scoreGrid = scoreGrid;
        this.roundGrid = roundGrid;
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

   public ArrayList<Utensils> getDeckUtensils(){
        ArrayList<Utensils> temp = (ArrayList<Utensils>) utensilsDeck.clone();
        return temp;
   }

    public ArrayList<Objective> getDeckObjective(){
        ArrayList<Objective> temp = (ArrayList<Objective>) utensilsDeck.clone();
        return temp;
   }


   public Utensils getUtensils(int cardPosition){
        return utensilsDeck.get(cardPosition);
   }
}
