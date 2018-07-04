package it.polimi.se2018.client.cli.game.objective;

import java.io.Serializable;

/**
 * L'oggetto descrive una carta obiettivo e contiene tutte le informazioni da stampara a schermo.
 *
 * @author Marazzi Paolo.
 */

public class ObjectiveCard implements Serializable{

    private String name;
    private String description;
    private int reward; //0 indica #.
    private boolean isPrivate;

    /**
     * Costruttore della classe.
     *
     * @param name nome della carta.
     * @param description descrizione dell'obiettivo.
     * @param reward ricompensa (il valore a schermo viene rappresentato con il carattere '#').
     * @param isPrivate indica se l'obiettivo è privato.
     */
    public ObjectiveCard(String name, String description, int reward, boolean isPrivate){ //Inserire 0 per indicare # come ricompesa.
        if(name!= null && description != null && reward >= 0){
            this.name = name;
            this.description = description;
            this.reward = reward;
            this.isPrivate = isPrivate;
        }
    }

    /**
     * Restituisce la descrizione dell'obiettivo.
     * @return descrizione.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Restituisce la ricompensa data dall'obiettivo.
     * @return ricompensa.
     */
    public int getReward() {
        return reward;
    }

    /**
     * Restituisce il nome dell'obiettivo.
     * @return nome
     */
    public String getName() {
        return name;
    }

    /**
     * Indica se l'obiettivo è privato.
     *
     * @return true se è privato.
     */
    public boolean isPrivate() {
        return isPrivate;
    }
}
