package it.polimi.se2018.server.fake_view;


import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server_message.ServerMessageCreator;

/**
 * Classe che si occupa della comunicazione fra l'mvc e la fakeView, ultimo elemento a seprazione dalla rete.
 * Inoltra gli eventi in ingresso verso l'mvc, preparati nella fakeView attraverso il metodo notifyObserver
 * metodo della interfaccia ViewAsObservable.
 * Gestisce gli eventi in uscita dall'mvc, traformandoli in stringhe messaggio secondo il protocollo del server
 * con degli appositi creator statici, arrivati fin lì grazie al metodo update dell'interfaccia ViewAsObserver
 * per cui questa classe cominucatore osserva il model per ricevere notifiche sui cambiamenti.
 * Questa classe ha l'utilità principale di suddividere il carico di metodi dell'interfaccia tra rete e gioco.
 * Si può dire che sia la valvola di uscita degli eventi.
 * @author Kevin Mato
 */
public class FakeVChat implements ViewAsObserver,ViewAsObservable {
    private ControllerAsObserver controller;
    private FakeView fake;

    public FakeVChat(FakeView f){
        this.fake=f;
    }

    /**
     * Metodo dell'interfaccai view as observable.
     * @param o osservatore da registare.
     */
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * I metodi update in questa classe trasformano eventi in messaggi del protocollo grazie metodi costruttori statici ad hoc.
     *
     * @param mex evento da analizzare.
     */
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

    /**
     * Metodo endpoint del logging deglle eccezioni del gioco.
     * @param mex evento eccezione.
     */
    public void update(ErrorSomethingNotGood mex){
        //todo
    }
    public void update(UpdateM mex){
        if(mex.getIsbroadcast()) {
            switch (mex.getWhat()) {
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
                    fake.messageOutBox(ServerMessageCreator.getPrivateObjectiveMesage(mex.getPlayer(), mex.getContent()));
                    break;
                case "price":
                    fake.messageOutBox(ServerMessageCreator.getUpdatePriceMessage(mex.getContent()));
                    break;
                default:
                    break;
            }
        }
        else{
            switch (mex.getWhat()) {
                case "side_list":
                    fake.messageOutBox(ServerMessageCreator.getSideListMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "public_objective":
                    fake.messageOutBox(ServerMessageCreator.getPublicObjectiveMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "utensils":
                    fake.messageOutBox(ServerMessageCreator.getSendUtensilsMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "reserve":
                    fake.messageOutBox(ServerMessageCreator.getUpdateReserveMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "RoundGrid":
                    fake.messageOutBox(ServerMessageCreator.getUpdeteRoundgridMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "turn":
                    fake.messageOutBox(ServerMessageCreator.getUpdateTurnMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "Side":
                    fake.messageOutBox(ServerMessageCreator.getUpdateSideMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "price":
                    fake.messageOutBox(ServerMessageCreator.getUpdatePriceMessage(mex.getContent(),mex.getPlayer()));
                    break;
                case "favours":
                    fake.messageOutBox(ServerMessageCreator.getUpdateFavsMessage(mex.getContent(),mex.getPlayer()));
                    break;
                default:
                    break;
            }

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
