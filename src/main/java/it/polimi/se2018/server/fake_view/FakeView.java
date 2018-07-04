package it.polimi.se2018.server.fake_view;



import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.Freeze;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.events.Unfreeze;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.message.server_message.ServerMessageParser;
import it.polimi.se2018.server.network.Lobby;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che ha il compito di ricevere i messaggi dal Server,
 * decodificarne il significato in base a determinati parser,
 * creare i loro relativi eventi ed inoltrarli all'mvc tramite fakeViewChat.
 * La classe si occupa anche dell'istradamento delle stringhe di messaggi, per il client provenienti dall'mvc.
 * Tutti i messaggi scambiati seguono la convenzione, in ordine:
 * extra/DiePerIndexDie/Box Round Grid/In Box RoundGrid/Row1 Vecchia/Col1 Vecchia/Row1New/Col1New/Row2 vecchia/Col2 Vecchia
 * dove extra 1 o 0 in base a che booleano si voglia esprimere, oppure è un altro parametro non numerico.
 * Se si hanno due extra, quello meno comune viene prima.
 * @author Kevin Mato
 */

public class FakeView{
    private Lobby toOut;
    private FakeVChat chat;
    private String message;

    /**
     * Costruttore della classe che non solo si collega alla lobby ma instanzia anche la sua classe comunicatore.
     * @param blob lobby del gioco.
     */
    public FakeView(Lobby blob){
        this.chat=new FakeVChat(this);
        this.toOut= blob;
    }

    /**
     * Costruttore che non prede parametri per il testing del solo mvc, o per metodi di connessione alternativi,
     * da implemtare in seguito.
     */
    public FakeView(){
        this.chat=new FakeVChat(this);
        this.toOut= null;
    }

    /**
     * Sebbene la classe non sia osservabile è l'interfaccia tra ciò che le è sotto e la rete quindi, la usiamo
     * per registrare gli osservatori del gioco.
     * @param controller controller del gioco istanziato dalla lobby.
     */
    public void register(Controller controller){
        if(controller!=null) chat.register( controller.getcChat());
    }
/**
 * Metodo che si occupa del riconoscimento del tipo di messaggio in base ad un parser dedicato, per
 * poi creare gli eventi relativi ai messaggi.
 * Viene letto il sender. Riconosciuto il tipo grazie ad un parametro alfanumerico per identificare se di tipo utensile.
 *
 */
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
                    chat.notifyObserver(new ToolCard11Bis(sender,Integer.parseInt(data.get(0)),data.get(2).equals("1"),transformer(data,2)));
                    break;
                case "12":
                    chat.notifyObserver(new ToolCard12(sender,Integer.parseInt(data.get(0)),transformer(data,2)));
                    break;
                default:
                    break;
            }

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

    /**
     * Metodo di connessione fra la fakeview e la lobby oer l'uscota dei messaggi.
     * @param mex stringa messaggio del protocollo.
     */
    public void messageOutBox(String mex){
        //todo da rimuovere
        System.out.println(mex);
        if(toOut!=null) toOut.notifyFromFakeView(mex);
        message=mex;
    }

    /**
     * Metodo privato che traduce un arraylist di stringhe in una struttura dati numerica.
     * @param ins lista di stringhe.
     * @param dadove da quale punto analizzare la lista.
     * @return la lista numerica.
     */
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

    public String getMessage() {
        return message;
    }
}
