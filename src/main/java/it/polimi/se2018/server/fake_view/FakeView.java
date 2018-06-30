package it.polimi.se2018.server.fake_view;



import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.ControllerAsObserver;
import it.polimi.se2018.server.events.Freeze;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.events.Unfreeze;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server_message.ServerMessageCreator;
import it.polimi.se2018.server.message.server_message.ServerMessageParser;
import it.polimi.se2018.server.network.Lobby;

import java.util.ArrayList;
import java.util.List;

public class FakeView{
    private Lobby toOut;
    private FakeVChat chat;

    public FakeView(Lobby blob){
        this.chat=new FakeVChat(this);
        this.toOut= blob;
    }
    //directed to out
    public void register(Controller controller){
        chat.register( controller.getcChat());
    }

    public void messageIncoming(String m){
        String sender= ServerMessageParser.getSender(m);

        if(ServerMessageParser.isActivateUtensilMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);
                chat.notifyObserver(new Activate(sender,Integer.parseInt(data.get(0))));
        }
        else if(ServerMessageParser.isUseUtensilMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);
            switch(data.get(1)){
                case "1":
                    chat.notifyObserver(new MoreThanSimple(
                                        sender,Integer.parseInt(data.get(0)),
                                        data.get(2).equals("1"),
                                        Integer.parseInt(data.get(3))));
                    break;

                case "2":
                    chat.notifyObserver(new ToolCard2(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;

                case "3":
                    chat.notifyObserver(new ToolCard3(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "4":
                    chat.notifyObserver(new ToolCard4(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "5":
                    chat.notifyObserver(new ToolCard5(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "6":
                    chat.notifyObserver(new ToolCard6(sender,Integer.parseInt(data.get(0)),Integer.parseInt(data.get(2))));
                    break;
                case "6bis":
                    chat.notifyObserver(new ToolCard6Bis(sender,Integer.parseInt(data.get(0)),data.get(2).equals("1"),
                                                        transformer(data,3)));
                    break;
                case "7":
                    chat.notifyObserver(new ToolCard7(sender,Integer.parseInt(data.get(0))));
                    break;
                case "8":
                    chat.notifyObserver(new ToolCard8(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "9":
                    chat.notifyObserver(new ToolCard9(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "10":
                    chat.notifyObserver(new ToolCard10(sender,Integer.parseInt(data.get(0)),Integer.parseInt(data.get(2))));
                    break;
                case "11":
                    chat.notifyObserver(new ToolCard11(sender,Integer.parseInt(data.get(0)),Integer.parseInt(data.get(2))));
                    break;
                case "11bis":
                    chat.notifyObserver(new ToolCard11Bis(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                case "12":
                    chat.notifyObserver(new ToolCard12(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                default:
                    break;
            }

            chat.notifyObserver(new Activate(sender,Integer.parseInt(data.get(0))));
        }

        else if(ServerMessageParser.isPutMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);

            chat.notifyObserver(new SimpleMove(Integer.parseInt(data.get(0)),Integer.parseInt(data.get(1)),
                    Integer.parseInt(data.get(2)),sender));
        }
        else if(ServerMessageParser.isPassTurnMessage(m)){
                chat.notifyObserver(new PassTurn(sender));
        }
        else if(ServerMessageParser.isSideReplyMessage(m)){
                List<String> data= ServerMessageParser.getInformationsFromMessage(m);
                chat.notifyObserver(new Choice(sender, Integer.parseInt(data.get(0))));
        }
        else if (ServerMessageParser.isFreezeMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);
            chat.notifyObserver(new Freeze(data.get(0)));
        }
        else if (ServerMessageParser.isUnfreezeMessage(m)){
            List<String> data= ServerMessageParser.getInformationsFromMessage(m);
            chat.notifyObserver(new Unfreeze(data.get(0)));
        }



    }


    public void messageOutBox(String mex){
       toOut.notifyFromFakeView(mex);
    }

    private ArrayList<Integer> transformer(List<String> ins,int dadove){
        ArrayList<Integer> ret = new ArrayList<>();
        if(ins!=null){
            for(int i=dadove;i<ins.size();i++){
                String val=ins.get(i);
                if(val!=null){
                    ret.add(Integer.parseInt(val));
                }
                else chat.update(new ErrorSomethingNotGood(null));
            }
        }
        return ret;
    }

}
