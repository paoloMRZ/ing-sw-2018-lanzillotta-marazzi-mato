package it.polimi.se2018.server.model.card.card_objective;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.objAlgos.StrategyAlgorithm;

public class Objective {
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

    public int useAlgorithm(Player player){ return myObjective.use();
    }
}
