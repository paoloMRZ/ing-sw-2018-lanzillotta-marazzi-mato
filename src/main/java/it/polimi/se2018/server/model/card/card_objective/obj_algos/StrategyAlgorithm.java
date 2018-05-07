package it.polimi.se2018.server.model.card.card_objective.obj_algos;


import it.polimi.se2018.server.model.Player;

public interface StrategyAlgorithm {
    public int use(Player player) throws Exception;
}
