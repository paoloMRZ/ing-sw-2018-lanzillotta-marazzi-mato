package it.polimi.se2018.server.model.card.card_utensils;

public abstract class Utensils{
    private String squareColor;
    private int number;
    private String description;

    public abstract void accept(VisitorCard visitor);

//metodi con implementazione ancora da chiarire
//ritorna le reference, tanto gli attributi sono immutabili
    public String getSquareColor(){
        //  System.out.println(squareColor);
        return squareColor;
    }

    public String getDesciption(){
      //  System.out.println(description);
        return description;
    }
}
