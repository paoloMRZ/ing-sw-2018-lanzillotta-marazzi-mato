package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.EventMVC;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;

public abstract class Utensils implements Visitable{

    //OVERVIEW la classe è a sostegno delle classi delle carte utensili
    //i metodi comuni a tutte vengono dichiarati qui

    //TODO riflettere se serva un attributo cost e un metodo per cambiarlo
    private Color squareColor;
    private String myType;
    private int number;
    private String description;
    private int cost;
    private boolean isFirstTime;

    public Utensils(int numb, String typo, Color color, String desc){
        this.number=numb;
        this.myType=typo;
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

    public Color getSquareColor(){
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

    //public abstract void function(Controller controller, EventMVC mvc);
}
