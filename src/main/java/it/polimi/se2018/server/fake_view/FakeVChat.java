package it.polimi.se2018.server.fake_view;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.Observable;
import it.polimi.se2018.server.events.Observer;
import it.polimi.se2018.server.model.Table;


public class FakeVChat{
    private Controller controller;
    private Table table;

    public FakeVChat(){
    }

    public void notifyObs(){};
    public void update(){};
}
