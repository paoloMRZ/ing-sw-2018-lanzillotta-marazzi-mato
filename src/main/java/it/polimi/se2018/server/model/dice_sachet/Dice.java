package it.polimi.se2018.server.model.dice_sachet;

import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;

import java.util.Random;

/**
 * Classe che definisce le caratteristiche dei dai del gioco e le operazioni effetuali su di loro.
 * Una delle classi più importanti del gioco.
 * @author Kevin Mato
 */
public class Dice {

    private int number;
    private Color color;
    private Random extractor = new Random();

    /**
     * Costruttore utilizzato per generare un dado con numero randomico dato un colore di partenza.
     * Molto utile alla classe factory che estre dadi in base alla loro disponibilità di colore.
     * @param color tipo enum del colore del dado desiderato.
     */
    public Dice(Color color){
        this.color = color;
        rollDice();
    }

    /**
     * Costruttore dei dadi che avviene in passando il colore e il numero desiderato. Utile alla copia dei dadi.
     * @param color tipo enum del colore del dado che si desidera.
     * @param number numero del dado che si desidera.
     */
    public Dice(Color color, int number){
        this.color= color;
        this.number=number;
    }

    /**
     * getter del colore per motivi di incapsulamento.
     * @return tipo enum di colore
     */
    public Color getColor() {
        return color;
    }

    /**
     * getter del numero.
     * @return intero che indica il numero sulla faccia superiore del dado.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Il metodo simula il lancio di un dado.
     */
    public void rollDice(){
        this.number = extractor.nextInt(6) + 1; //nextInt estrae un numero tra 0(incluso) e 6(escluso), per questo sommo 1.
    }

    /**
     * Si può imporre un numero manualmente ad un dado nel caso di modifiche specifiche attraverso funzionalità delle
     * carte utensili.
     * @param decision il numero da imporre al dado
     * @throws InvalidValueException lanciato nel caso in cui il valore che si vuole imporre sia non valido per il tipo di dado.
     */
    public void manualSet(int decision) throws InvalidValueException {

        if(decision>6 || decision<1){ throw new InvalidValueException();}
        else{ this.number=decision;}

    }
}
