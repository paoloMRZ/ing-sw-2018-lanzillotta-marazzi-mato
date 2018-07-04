package it.polimi.se2018.client.cli.game.utensil;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * La classe descrive una carta utensile e contiene tutte le informazioni da stampare a schermo.
 *
 * @author Marazzi Paolo
 */

public class UtensilCard implements Serializable {

    private String name;
    private String description;
    private int number;
    private int prize;

    /**
     * Costruttore della classe.
     *
     * @param name nome della carta.
     * @param description descrizione dell'effetto della carta.
     * @param number numero che identifica la carta.
     */
    public UtensilCard(String name, String description, int number){
        if(name != null && description != null && number > 0 && number < 13){
            this.name = name;
            this.description = description;
            this.number = number;
            this.prize = 1;
        }else
            throw new InvalidParameterException();
    }


    /**
     * Il metodo setta il prezzo da pagare per poter atticare la carta.
     * Il prezzo non può mai essere superiore a 2 e una volta salito non può più scendere.
     * @param prize nuovo prezzo.
     */
    public void setPrize(int prize){
        if(prize > 2)
            this.prize = 2;
        else
            this.prize = prize;
    }

    /**
     * Il metodo restituisce il numero di segnalini favore che è necessario spendere per poter attivare la carta.
     * @return prezzo della carta.
     */
    public int getPrize(){return prize;}

    /**
     * Il metodo restituisce il numero che identifica la carta.
     * @return numero id.
     */
    public int getNumber(){return number;}

    /**
     * Il metodo restituisce il nome della carta.
     * @return nome.
     */
    public String getName() {
        return name;
    }

    /**
     * Il metodo restituisce la descrizione della carta.
     * @return descrizione.
     */
    public String getDescription() {
        return description;
    }
}