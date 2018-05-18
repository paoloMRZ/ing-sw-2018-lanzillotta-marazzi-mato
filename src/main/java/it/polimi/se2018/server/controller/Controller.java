package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;

import java.util.ArrayList;


public class Controller{

    private final Table lobby;
    private final ControllerCard cCard;
    private final ControllerAction cAction;
    private final ControllerPoints cPoints;
    private final ControllerTurn cTurn;


    //TODO TABLE DA CHIARIRE IN CHE ORDINE COSTRUIRE LE COSE
    public Controller(ArrayList<Player> players){
        this.lobby=new Table(extractorUtensils(),extractorObjectives(),players,new DiceSachet(),new ScoreGrid(players.size()),new RoundGrid());
        this.cCard=new ControllerCard(lobby);
        this.cAction=new ControllerAction(lobby);
        this.cPoints=new ControllerPoints(lobby);
        this.cTurn=new ControllerTurn(lobby);
    }
    public ArrayList<Utensils> extractorUtensils(){
        //TODO
        //todo
        return null;
    }
    public ArrayList<Objective> extractorObjectives(){
        //TODO
        //todo
        return null;
    }

    public ControllerAction getcAction(){
        return cAction;
    }


}
