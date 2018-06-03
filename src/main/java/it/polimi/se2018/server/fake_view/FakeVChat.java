package it.polimi.se2018.server.fake_view;


import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.ObservableForth;
import it.polimi.se2018.server.events.ObserverBack;
import it.polimi.se2018.server.events.ObserverForth;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;


public class FakeVChat implements ObserverBack,ObservableForth {
    private ObserverForth controller;
    private FakeView fake;




    public FakeVChat(FakeView f){
        this.fake=f;
    }

    public void register(ObserverForth o){
        this.controller=o;
    }

    public void unregister(){
        this.controller=null;
    }


    public void notifyObserver(Activate mex){
        controller.update(mex);
    }
    public void notifyObserver(SimpleMove mex){
        controller.update(mex);
    }

    public void notifyObserver(MoreThanSimple mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard2 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard3 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard4 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard5 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard6 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard6Bis mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard7 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard8 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard9 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard10 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard11 mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard11Bis mex){
        controller.update(mex);
    }
    public void notifyObserver(ToolCard12 mex){
        controller.update(mex);
    }


    public void notifyObserver(UpdateReq mex){
        controller.update(mex);
    }
    public void update(SuccessSimpleMove mex){//todo
         }

    public void update(SuccessColor mex){//todo
         }
    public void update(SuccessValue mex) {//todo
    }
    public void update(SuccessActivation mex) {//todo
    }
    public void update(SuccessActivationFinalized mex){//todo
    }
    public void update(ErrorSelection mex){//todo
    }
    public void update(ErrorActivation mex){//todo
    }
    public void update(ErrorSomethingNotGood mex){//todo
    }
    public void update(UpdateM mex){//todo
    }
}
