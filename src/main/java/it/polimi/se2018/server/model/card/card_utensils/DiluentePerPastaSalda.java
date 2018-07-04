package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard11;
import it.polimi.se2018.server.events.tool_mex.ToolCard11Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 11.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 * La carta presenta il metodo accept e due funzioni una per ogni fase dell'utilizzo, poichè richiede due interazioni
 * da parte dell'utente.
 */
public class DiluentePerPastaSalda extends Utensils  implements Visitable {

    public DiluentePerPastaSalda(){
        super(11,"diluente-per-pasta-salda", Color.PURPLE,"Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal" +
                " Sacchetto Scegli il valore del nuovo dado e piazzalo, " +
                "rispettando tutte le restrizioni di piazzamento");
    }
    /**
     * Metodo dell'interfaccia visitable che viene overridato.
     * @param c reference del visitatore.
     * @param m evento che richiede l'uso di una carta.
     */
    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }

    /**
     * Metodo che attua la prima fase del funzionamento. Prende un evento tool11 semplice.
     * -------------------------------------------------------------------------------------
     * Si riestrae un dado dal sacchetto dopo averne immesso uno, e di esso si restituisce il colore al giocatore.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @return colore del dado
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi inaspettati.
     */
    public String function(Controller controller, ToolCard11 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        int die= myMessage.getDie();
        Dice toReput=controller.getcAction().pickFromReserve(die);
        controller.setHoldingResDie(toReput);
        Dice d= new Dice(toReput.getColor());
        d.manualSet(toReput.getNumber());
        Dice picked=controller.getcAction().extractDieAgain(d);
        controller.setHoldingADiceMoveInProgress(picked);
        return picked.getColor().name();
    }
    /**
     * Metodo che attua la seconda fase del funzionamento. Prende un evento tool11bis.
     * -------------------------------------------------------------------------------------
     * Si decide se piazzare il dado nella carta schema o buttarlo via.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidCellException lanciato nel caso in cui la cella richiesta per il piazzamento sia occupata o non buona.
     */
    public void function(Controller controller, ToolCard11Bis myMessage) throws InvalidValueException, InvalidCellException {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int value=messageCont.get(0);
        int row=messageCont.get(1);
        int col=messageCont.get(2);

        if(myMessage.getDecision()) {

            Dice dadozzo = controller.getHoldingADiceMoveInProgress();
            Dice copyofDadozzo = new Dice(dadozzo.getColor(), value);
            controller.getcAction().workOnSide(name, copyofDadozzo, row, col);

        }
        else controller.cleanHoldingADiceMoveInProgress();
    }


}
