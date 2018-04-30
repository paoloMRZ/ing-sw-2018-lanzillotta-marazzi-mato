package it.polimi.se2018.server.model.card.card_utensils;

public abstract class Utensils{

    //OVERVIEW la classe è a sostegno delle classi delle carte utensili
    //i metodi comuni a tutte vengono dichiarati qui

    //TODO riflettere se serva un attributo cost e un metodo per cambiarlo
    private String squareColor;
    private int number;
    private String description;

    public Utensils(int numb, String color, String desc){
        this.number=numb;
        this.squareColor=color;
        this.description=desc;
    }
    public void accept(Visitor visitor){
        visitor.visit(this);
    }

//TODO chiarire pattern
    public String getSquareColor(){
        return squareColor;
    }

    public String getDesciption(){
        return description;
    }

    public abstract void function();
}
