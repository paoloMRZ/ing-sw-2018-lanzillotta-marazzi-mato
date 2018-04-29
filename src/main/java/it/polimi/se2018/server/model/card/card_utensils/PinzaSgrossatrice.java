package it.polimi.se2018.server.model.card.card_utensils;

public class PinzaSgrossatrice extends Utensils {

    PinzaSgrossatrice(){
        super(1,"Purple","Dopo aver scelto un dado, " +
                "aumenta o dominuisci il valore del dado scelto di 1 " +
                "Non puoi cambiare un 6 in 1 o un 1 in 6");

    }
    public void accept(VisitorCard card){
  //da decidere
        System.out.println("questa non è la implementazione");
    }

}
