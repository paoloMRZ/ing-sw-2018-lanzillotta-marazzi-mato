package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.exceptions.InvalidValueException;


public class ControllerChat implements ObserverForth {

    private Controller controller;

    public ControllerChat(Controller controller){
        this.controller=controller;
    }

    public void update(Activate mex){
        //if(checker(mex.getPlayer()))

    }
    public void update(SimpleMove mex);

    public void update(MoreThanSimple mex);
    public void update(ToolCard2 mex);
    public void update(ToolCard3 mex);
    public void update(ToolCard4 mex);
    public void update(ToolCard5 mex);
    public void update(ToolCard6 mex);
    public void update(ToolCard6Bis mex);
    public void update(ToolCard7 mex);
    public void update(ToolCard8 mex);
    public void update(ToolCard9 mex);
    public void update(ToolCard10 mex);
    public void update(ToolCard11 mex);
    public void update(ToolCard11Bis mex);
    public void update(ToolCard12 mex);


    public void update(UpdateReq mex);

    public void update(HookMessage m){
        controller.getcAction().hook(m);
    }

/* NEL CASO SI VOGLIAMO LASCIARE INTERAGIRE GLI UTENTI QUANDO NON è IL LORO TURNO
    è UNA SOLUZIONE INIZIALE MODIFICABILE IN TUTTI I MODI. SI DISPONE UNA PRIMA SOLZIONE PRONTA ALL'UTILIZZO.

    private boolean checker(String name){
        try{
        if(name.equals(controller.getcTurn().getTurn().getName())) return true;}
        catch(InvalidValueException e){
            controller.getcAction().sendErrorWasNotGood(name);
        }
    }*/
}
