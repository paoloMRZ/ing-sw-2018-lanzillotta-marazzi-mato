package it.polimi.se2018.server.model.card.card_objective;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;

public class Objective {

    //OVERVIEW: La classe rappresenta la carta Obbiettivo, in cui si è inserita un'istanza dell'interfaccia StrategyAlgorithm
    //per richiamare poi i vari algoritmi a seconda della tipologia di carta selezionata

    private String name;
    private String description;
    private int favours;
    private StrategyAlgorithm myObjective;
    private boolean ImPrivate;

    public Objective(String name, String description, int favours, StrategyAlgorithm myObjective, boolean imPrivate) {
        this.name = name;
        this.description = description;
        this.favours = favours;
        this.myObjective = myObjective;
        ImPrivate = imPrivate;
    }

    public String getDescription(){
        return description;
    }

    public int getFavours(){
        return favours;
    }

    //Metodo useAlgorithm -> richiama al suo interno il metodo di myObjective che, tramite override, utilizzerà
    //l'algoritmo a seconda della carte richiamata

    public int useAlgorithm(Player player) throws Exception {
        return myObjective.use(player);
    }
}
