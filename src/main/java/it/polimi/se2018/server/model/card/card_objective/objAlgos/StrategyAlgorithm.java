package it.polimi.se2018.server.model.card.card_objective.objAlgos;


import it.polimi.se2018.server.model.Player;

public interface StrategyAlgorithm {
    public int use(Player player) throws Exception;
}
