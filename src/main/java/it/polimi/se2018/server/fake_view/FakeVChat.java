package it.polimi.se2018.server.fake_view;


import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server.ServerMessageCreator;


public class FakeVChat implements ViewAsObserver,ViewAsObservable {
    private ControllerAsObserver controller;
    private FakeView fake;




    public FakeVChat(FakeView f){
        this.fake=f;
    }

    public void register(ControllerAsObserver o){
        this.controller=o;
        notifyObserver(new HookMessage(this));
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
    public void notifyObserver(HookMessage mex){
        controller.update(mex);
    }
    public void notifyObserver(PassTurn mex){controller.update(mex);}
    public void notifyObserver(Choice mex){controller.update(mex);}



    public void notifyObserver(UpdateReq mex){
        controller.update(mex);
    }

    public void update(SuccessSimpleMove mex){
        String out= ServerMessageCreator.getPutDieSuccesMessage(
                mex.getPlayer(),
                String.valueOf(mex.getDiceIndex()),
                String.valueOf(mex.getCoord().get(0)),
                String.valueOf(mex.getCoord().get(1)));
        fake.messageOutBox(out);
    }
    public void update(ErrorSelection mex){
        String out= ServerMessageCreator.getPutDieErrorMessage(
            mex.getPlayer(),
            String.valueOf(mex.getDiceIndex()),
            String.valueOf(mex.getCoord().get(0)),
            String.valueOf(mex.getCoord().get(1)));
        fake.messageOutBox(out);
    }
    public void update(ErrorSelectionUtensil mex){
        //todo
        fake.messageOutBox(ServerMessageCreator.getUseUtensilErrorMessage(mex.getPlayer(),String.valueOf(mex.getCard())));
    }
    public void update(SuccessColor mex){
        //todo discutere
        fake.messageOutBox(ServerMessageCreator.getUseUtensilSuccessMessage(mex.getPlayer(),String.valueOf(mex.getCard()),"Color",mex.getValue()));
    }
    public void update(SuccessValue mex) {
        //todo discutere
        fake.messageOutBox(ServerMessageCreator.getUseUtensilSuccessMessage(mex.getPlayer(),String.valueOf(mex.getCard()),"Number",String.valueOf(mex.getValue())));
    }
    public void update(SuccessActivation mex) {
        fake.messageOutBox(ServerMessageCreator.getActivateUtensilSuccessMessage(
                mex.getPlayer(),
                String.valueOf(mex.getIndex()),
                String.valueOf(mex.getMyNumber()),
                String.valueOf(mex.getCardPrice()),
                String.valueOf(mex.getFavs())
        ));
    }
    public void update(SuccessActivationFinalized mex){
        fake.messageOutBox(ServerMessageCreator.getUseUtensilEndMessage(
                mex.getPlayer(),
                String.valueOf(mex.getIndex()),
                String.valueOf(mex.getMyNumber()),
                String.valueOf(mex.getCardPrice()),
                String.valueOf(mex.getFavs())
        ));
    }

    public void update(ErrorActivation mex){
        fake.messageOutBox(ServerMessageCreator.getActivateUtensilErrorMessage(
                mex.getPlayer(),
                String.valueOf(mex.getIndex()),
                String.valueOf(mex.getMyNumber()),
                String.valueOf(mex.getCardPrice()),
                String.valueOf(mex.getFavs())
        ));
    }
    public void update(ErrorSomethingNotGood mex){
        //todo
    }
    public void update(UpdateM mex){
        switch(mex.getWhat()){
            case "round":
                fake.messageOutBox(ServerMessageCreator.getUpdateRoundMessage(mex.getContent()));
                break;
            case "RoundGrid":
                fake.messageOutBox(ServerMessageCreator.getUpdeteRoundgridMessage(mex.getContent()));
                break;
            case "ScoreGrid":
                fake.messageOutBox(ServerMessageCreator.getEndGameMessage(mex.getContent()));
                break;
            case "Side":
                fake.messageOutBox(ServerMessageCreator.getUpdateSideMessage(mex.getContent()));
                break;
            case "reserve":
                fake.messageOutBox(ServerMessageCreator.getUpdateReserveMessage(mex.getContent()));
                break;
            case "Objective":
                fake.messageOutBox(ServerMessageCreator.getPublicObjectiveMessage(mex.getContent()));
                break;
            case "turn":
                fake.messageOutBox(ServerMessageCreator.getUpdateTurnMessage(mex.getContent()));
                break;
            case "side_list":
                fake.messageOutBox(ServerMessageCreator.getSideListMessage(mex.getContent()));
                break;
            case "public_objective":
                fake.messageOutBox(ServerMessageCreator.getPublicObjectiveMessage(mex.getContent()));
                break;
            case "utensils":
                fake.messageOutBox(ServerMessageCreator.getSendUtensilsMessage(mex.getContent()));
                break;
            case "private_objective":
                fake.messageOutBox(ServerMessageCreator.getPrivateObjectiveMesage(mex.getPlayer(),mex.getContent()));
                break;
            default:
                break;
        }
    }
    public void update( TimeIsUp mex){
        fake.messageOutBox(ServerMessageCreator.getTimeoutMessage(mex.getPlayer()));
    }

    public void update( AskPlayer mex){
        fake.messageOutBox(
                ServerMessageCreator.getChoseSideMessage(mex.getPlayer(),mex.getArr())
        );
    }
}
