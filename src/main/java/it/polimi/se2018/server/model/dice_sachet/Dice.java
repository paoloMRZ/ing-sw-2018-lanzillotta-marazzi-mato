package it.polimi.se2018.server.model.dice_sachet;

import it.polimi.se2018.server.exceptions.InvalidValueException;

import java.util.Random;

public class Dice {

    //OVERVIEW: La classe rappresenta un dado del gioco.

    private int number;
    private String color;
    private Random extractor = new Random();

    public Dice(String color){
        this.color = color;
        rollDice(); //TODO Controllare  se il valore è corretto in caso contrario sollevare un'eccezione
    }

    public Dice(String color, int number){
        this.color= color;
        this.number=number;
    }
    public String getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    //Il metodo assegna un valore casuale al dado (number) e ritorna questo valore al chiamante.
    public int rollDice(){
        this.number = extractor.nextInt(6) + 1; //nextInt estrae un numero tra 0(incluso) e 6(escluso), per questo sommo 1.
        return number;
    }
    public void manualSet(int decision) throws InvalidValueException {

        if(decision>6 || decision<1){ throw new InvalidValueException();}
        else{ this.number=decision;}

    }
}
