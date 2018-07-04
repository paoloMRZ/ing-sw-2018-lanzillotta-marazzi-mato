package it.polimi.se2018.client.cli.game.schema;


import it.polimi.se2018.client.cli.game.info.CellInfo;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * La classe descrive una carta finestra e contiene tutte le informazioni da stampare a schermo.
 *
 * @author Marazzi Paolo
 */

public class SideCard implements Serializable{

    private String name;
    private int favours;
    private CellInfo[] cells;

    /**
     * Costruttore della classe.
     *
     * @param name nome della carta.
     * @param favours segnalini favore che la carta fornisce al giocatore che la sceglie.
     * @param cells lista contenete "CellInfo", cioè descrizioni di celle che compongono la carta.
     */
    public SideCard(String name, int favours, List<CellInfo> cells){
        if(name != null && favours >0 && cells != null){
            this.name = name;
            this.favours = favours;
            this.cells = cells.toArray(new CellInfo[0]);
        }else
            throw new InvalidParameterException();
    }

    /**
     * Restituisce il nome della carta.
     * @return nome
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce i segnalini favore associati alla carta.
     * @return numero di segnalini favore.
     */
    public int getFavours(){
        return favours;
    }

    /**
     * Restituisce una lista che desrive le celle che compongono la carta.
     * @return lista di celle.
     */
    public List<CellInfo> getCells() {
        return new ArrayList<>(Arrays.asList(cells));
    }
}
