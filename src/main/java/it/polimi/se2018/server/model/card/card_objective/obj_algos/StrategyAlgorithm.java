package it.polimi.se2018.server.model.card.card_objective.obj_algos;


import it.polimi.se2018.server.model.Player;

/**
 * L'interfaccia rappresenta l'implementazione dello Strategy Pattern che rende possibile la chiamata di un algoritmo a seconda
 * della tipologiaa di carta che richiama il metodo da essa richiesto
 *
 * @author Simone Lanzillotta
 */



public interface StrategyAlgorithm {
    public int use(Player player) throws Exception;
}
