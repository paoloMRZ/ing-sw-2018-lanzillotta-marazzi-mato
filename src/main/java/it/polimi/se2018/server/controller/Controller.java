package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.EventMVC;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.Observer;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;

import java.util.ArrayList;


public class Controller implements Observer {
    private Table lobby;
    private ControllerCard cCard;
    private ControllerAction cAction;
    private ControllerPoints cPoints;
    private ControllerTurn cTurn;
    private EventMVC event;

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
//TODO utilizzare overload oer ogni tipo di evento
    public void update(SimpleMove message){

    }
    public void update(Activate message){

    }
}
