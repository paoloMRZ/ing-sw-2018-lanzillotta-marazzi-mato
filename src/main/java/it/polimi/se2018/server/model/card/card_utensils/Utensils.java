package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.model.card.Visitable;

public abstract class Utensils implements Visitable{

    //OVERVIEW la classe è a sostegno delle classi delle carte utensili
    //i metodi comuni a tutte vengono dichiarati qui

    //TODO riflettere se serva un attributo cost e un metodo per cambiarlo
    private String squareColor;
    private int number;
    private String description;
    private int cost;
    private boolean isFirstTime;

    public Utensils(int numb, String color, String desc){
        this.number=numb;
        this.squareColor=color;
        this.description=desc;
        this.cost=1;
        this.isFirstTime= false;
    }
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

    //funzione che ritorna il costo attuale della carta
    public int getCost(){
        return cost;
    }

    public String getSquareColor(){
        return squareColor;
    }

    public String getDesciption(){
        return description;
    }

    //funzione di sostegno: quando viene richiamato function aggiorna il costo in base a che uso è stato
    //fatto-> da richiamare dentro function
    public void addToCost(){
       if(isFirstTime){
           cost=cost+1;
           isFirstTime=true;
       }
    }

    //public abstract void function();
}
