package it.polimi.se2018.server.fake_view;



import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server.ServerMessageCreator;
import it.polimi.se2018.server.message.server.ServerMessageParser;
import it.polimi.se2018.server.network.Lobby;

import javax.jnlp.IntegrationService;
import java.util.List;

public class FakeView{
    private Lobby toOut;
    private FakeVChat chat;

    public FakeView(Lobby blob){
        this.chat=new FakeVChat(this);
        this.toOut= blob;
    }
    //directed to out

    public void messageIncoming(String m){
        String sender= ServerMessageParser.getSender(m);

        if(ServerMessageParser.isActivateUtensilMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);
                chat.notifyObserver(new Activate(sender,));
        }
        if(ServerMessageParser.isPassTurnMessage(m)){
                chat.notifyObserver(new PassTurn(sender));
        }
        if(ServerMessageParser.isPutMessage(m)){

        }
        if(ServerMessageParser.isSideReplyMessage(m)){
                List<String> data= ServerMessageParser.getInformationsFromMessage(m);
                chat.notifyObserver(new Choice(sender, Integer.parseInt(data.get(0))));
        }
        if(ServerMessageParser.isUseUtensilMessage(m)){

        }


    }


    public void messageOutBox(String mex){
        //todo
    }



}
