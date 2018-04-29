package it.polimi.se2018.server.model.card.card_utensils;

public abstract class Utensils{
    private String squareColor;
    private int number;
    private String description;

    Utensils(int numb, String color, String desc){
        number=numb;
        squareColor=color;
        description=desc;
    }
    public abstract void accept(VisitorCard visitor);

//metodi con implementazione ancora da chiarire
//ritorna le reference, tanto gli attributi sono immutabili
    public String getSquareColor(){
        return squareColor;
    }

    public String getDesciption(){
        return description;
    }
}
