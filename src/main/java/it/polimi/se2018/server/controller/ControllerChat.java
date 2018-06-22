package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.exceptions.InvalidValueException;


public class ControllerChat implements ControllerAsObserver,ControllerAsObservable {

    private final Controller controller;
    private ViewAsObserver view;

    public void register(ViewAsObserver v){
        view=v;
    }

    //costruttore
    public ControllerChat(Controller controller){
        this.controller=controller;
    }

    public void update(Activate mex){
        if(checker(mex)) controller.callThrough(mex);
    }
    public void update(SimpleMove mex){
        if(checker(mex)) controller.simpleMove(mex);
    }


    //in questo momento solo il turnante può richiedere update, se tolto checker può chiunque
    public void update(UpdateReq mex){
        if(checker(mex)) controller.getcAction().refresher(mex);
    }

    public void update(HookMessage m){
        register(m.getObserver());
        controller.getcAction().hook(m);
    }
    public void update(PassTurn m){
        controller.passTurn(m);
    }
    public void update(Choice m) throws InvalidValueException {
        controller.chosen(m);
    }

/* NEL CASO SI VOGLIAMO LASCIARE INTERAGIRE GLI UTENTI QUANDO NON è IL LORO TURNO
    è UNA SOLUZIONE INIZIALE MODIFICABILE IN TUTTI I MODI. SI DISPONE UNA PRIMA SOLZIONE PRONTA ALL'UTILIZZO.
    Sigillo di garanzia che la richiesta è del turnante così da utilizzare direttamente il turante
    per il fill dei campi destinatario per le risposte
*/
    private boolean checker(EventMVC mex){

            if(controller.messageComingChecking(mex)){
                return mex.getPlayer().equals(controller.getTurn().getName());
            }
            else{notifyObserver( new TimeIsUp(mex.getPlayer()));
                 return false;
            }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void notifyObserver(SuccessColor mex){
    view.update(mex);
}
    public void notifyObserver(SuccessValue mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessActivation mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessActivationFinalized mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSelection mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSelectionUtensil mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorActivation mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSomethingNotGood mex){
        view.update(mex);
    }
    public void notifyObserver( TimeIsUp mex){
        view.update(mex);
    }
    public void notifyObserver( UpdateM mex){
        view.update(mex);
    }




}
