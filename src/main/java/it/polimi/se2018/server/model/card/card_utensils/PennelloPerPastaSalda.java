package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard6;
import it.polimi.se2018.server.events.tool_mex.ToolCard6Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 6.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 * La carta presenta il metodo accept e due funzioni una per ogni fase dell'utilizzo, poichè richiede due interazioni
 * da parte dell'utente.
 */
public class PennelloPerPastaSalda extends Utensils implements Visitable  {

   public PennelloPerPastaSalda(){
       super(6,"pennello-per-pasta-salda", Color.PURPLE,"Dopo aver scelto un dado, " +
               "tira nuovamente quel dado Se non puoi piazzarlo, " +
               "riponilo nella Riserva");
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
     * Metodo che attua la prima fase del funzionamento. Prende un evento tool6 semplice.
     * -------------------------------------------------------------------------------------
     * Si rilancia un dado dalla riserva si restituisce il valore al giocatore.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @return valore del dado
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi inaspettati.
     */
    public int function(Controller controller, ToolCard6 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        int indexDie= myMessage.getDie();
        Dice picked = controller.getcAction().pickFromReserve(indexDie);
        picked.rollDice();
        controller.setHoldingADiceMoveInProgress(picked);
        return picked.getNumber();
   }
    /**
     * Metodo che attua la seconda fase del funzionamento. Prende un evento tool6bis semplice.
     * -------------------------------------------------------------------------------------
     * Si decide se piazzare il dado nella carta schema o riporlo.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidCellException lanciato nel caso in cui la cella richiesta per il piazzamento sia occupata o non buona.
     */
    public void function(Controller controller, ToolCard6Bis myMessage) throws InvalidValueException, InvalidCellException {
        String name= myMessage.getPlayer();

        if(myMessage.getDecision()){
            controller.getcAction().putBackInReserve();

        }
        else{
            ArrayList<Integer> cont= myMessage.getAttributes();
            int row=cont.get(0);
            int col=cont.get(1);
            controller.getcAction().workOnSide(name,controller.getHoldingADiceMoveInProgress(),row,col);
        }
    }


}
