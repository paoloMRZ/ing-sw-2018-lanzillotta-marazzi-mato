package it.polimi.se2018.server.fake_view;


import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server_message.ServerMessageCreator;


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

    public void notifyObserver(HookMessage mex){
        controller.update(mex);
    }
    public void notifyObserver(PassTurn mex){controller.update(mex);}
    public void notifyObserver(Choice mex){controller.update(mex);}
    public void notifyObserver(Freeze mex){controller.update(mex);}
    public void notifyObserver(Unfreeze mex){controller.update(mex);}

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
        fake.messageOutBox(ServerMessageCreator.getUseUtensilErrorMessage(mex.getPlayer(),String.valueOf(mex.getCard())));
    }
    public void update(SuccessColor mex){
        fake.messageOutBox(ServerMessageCreator.getUseUtensilSuccessMessage(mex.getPlayer(),String.valueOf(mex.getCard()),"Color",mex.getValue()));
    }
    public void update(SuccessValue mex) {
        fake.messageOutBox(ServerMessageCreator.getUseUtensilSuccessMessage(mex.getPlayer(),String.valueOf(mex.getCard()),"Value",String.valueOf(mex.getValue())));
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
            case "price":
                fake.messageOutBox( ServerMessageCreator.getUpdatePriceMessage(mex.getContent()));
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
    public void update(DisconnectPlayer mex){
        fake.messageOutBox(ServerMessageCreator.getDisconnectedMessage(mex.getPlayer()));
    }

    @Override
    public void update(IgnoreMex mex) {
        fake.messageOutBox(ServerMessageCreator.getUnauthorizedMessage(mex.getPlayer()));
    }

    public void update(Freeze mex){
        fake.messageOutBox(ServerMessageCreator.getFreezeMessage(mex.getPlayer()));
    }
}
