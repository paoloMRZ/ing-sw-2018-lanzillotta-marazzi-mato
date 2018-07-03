package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.EventMVC;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_utensils.*;

/**
 * Classe che si occupa dell'esecuzione delle funzionalità delle carte utensile.
 * Qui non troviamo deìirettamente l'esecuzione della funzionalità ma c'è l'avvio dell'algoritmo della carta e la gestione dell'esito che questa
 * esecuzione può dare. Da qui partiranno messaggi di successo o errore di svariato tipo.
 */
public class ControllerCard implements Visitor {

    private final Controller controller;

    /**
     * Costruttore della classe.
     * @param controller
     */
    public ControllerCard(Controller controller){
        this.controller=controller;
    }

/////////////////////////////////////////////////////////////////////////


    public void visit(PinzaSgrossatrice itemPinza,Activate mex){
        MoreThanSimple m=(MoreThanSimple) mex;
        try{
            if(itemPinza.getIsBusy()){
                itemPinza.function(controller,m);
                successUseFin(itemPinza,mex);
                controller.cleanAll();
            }
            else errorActivation(itemPinza,mex);
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            controller.cleanAll();
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }

    }

    public void visit(PennelloPerEglomise itemPennelloEglo, Activate mex){
        ToolCard2 m=(ToolCard2)mex;
        try{
            if(itemPennelloEglo.getIsBusy()){
                itemPennelloEglo.function(controller,m);
                successUseFin(itemPennelloEglo,mex);
            }
            else errorActivation(itemPennelloEglo,mex);
        }
        catch(InvalidValueException | InvalidCellException e){
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(Lathekin itemLathekin, Activate mex){
        ToolCard4 m=(ToolCard4) mex;
        try{
            if(itemLathekin.getIsBusy()){
                itemLathekin.function(controller,m);
                successUseFin(itemLathekin,mex);
            }
            else errorActivation(itemLathekin,mex);
        }
        catch(InvalidValueException | InvalidCellException e){
            try {
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(0),
                        m.getAttributes().get(1));
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(4),
                        m.getAttributes().get(5));
            } catch (InvalidValueException |InvalidCellException e1) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e1));
            }
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(PennelloPerPastaSalda itemPennelloPasta,Activate mex){
        ToolDouble mu=(ToolDouble) mex;
        if(!mu.getisBis()) {
            try {
                ToolCard6 m=(ToolCard6) mex;
                if (itemPennelloPasta.getIsBusy()) {
                    int value = itemPennelloPasta.function(controller, m);
                    controller.getcChat().notifyObserver(new SuccessValue(m.getPlayer(), m.getCard(), value));
                }
                else errorActivation(itemPennelloPasta,mex);
            }
            catch (InvalidValueException e) {
                errorSelection(mex);
            } catch (Exception e) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            }
        }
        else {
            try {
                ToolCard6Bis mess=(ToolCard6Bis) mex;
                if (itemPennelloPasta.getIsBusy()) {
                    itemPennelloPasta.function(controller, mess);
                    successUseFin(itemPennelloPasta,mex);
                    controller.cleanAll();
                } else errorActivation(itemPennelloPasta,mex);
            } catch (InvalidValueException e) {
                controller.getcAction().putBackInReserve();
                controller.cleanAll();
                errorSelection(mex);
            } catch (Exception e) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            }
        }

    }

    public void visit(AlesatorePerLaminaDiRame itemAlesatore,Activate mex){
        ToolCard3 m=(ToolCard3)mex;
        try{
            if(itemAlesatore.getIsBusy()){
                itemAlesatore.function(controller,m);

                successUseFin(itemAlesatore,mex);
            }
            else errorActivation(itemAlesatore,mex);
        }
        catch(InvalidValueException | InvalidCellException e){
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(Martelletto itemMartelletto, Activate mex){
        ToolCard7 m=(ToolCard7)mex;
        try{
            if(itemMartelletto.getIsBusy()){
                itemMartelletto.function(controller);

                successUseFin(itemMartelletto,mex);
            }
            else{
                itemMartelletto.undoCostUpdate();
                errorActivation(itemMartelletto,mex);
            }
        }
        catch(InvalidValueException e){
            errorSelection(mex);
        }
        catch( InvalidActivationException e){
            itemMartelletto.undoCostUpdate();
            errorActivation(itemMartelletto,mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(DiluentePerPastaSalda itemDiluente,Activate mex){
        ToolDouble mu=(ToolDouble) mex;
        if(!mu.getisBis()){
            ToolCard11 m=(ToolCard11) mex;
            try {
                if (itemDiluente.getIsBusy()) {
                    String color = itemDiluente.function(controller, m);
                    controller.getcChat().notifyObserver(
                            new SuccessColor(m.getPlayer(), m.getCard(), color));
                }
                else errorActivation(itemDiluente,mex);
            } catch (InvalidValueException e){
                controller.getcAction().putBackInReserve(controller.getHoldingResDie());
                errorSelection(mex);
                controller.cleanHoldingResDie();
            } catch (Exception e) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            }
        }
    else{
            ToolCard11Bis mess=(ToolCard11Bis) mex;
        try{
            if(itemDiluente.getIsBusy()){
                itemDiluente.function(controller,mess);
                successUseFin(itemDiluente,mex);
                controller.cleanAll();
            }
            else errorActivation(itemDiluente,mex);
        }
        catch(InvalidValueException | InvalidCellException e){
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
        }
    }


    public void visit(RigaInSughero itemRiga, Activate mex){
        ToolCard9 m=(ToolCard9)mex;
        try{
            if(itemRiga.getIsBusy()){
                itemRiga.function(controller,m);

               successUseFin(itemRiga,mex);
            }
            else errorActivation(itemRiga,mex);
        }
        catch( InvalidValueException| InvalidShadeException| NotEmptyCellException| InvalidColorException e){
            errorSelection(mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(TaglierinaCircolare itemT, Activate mex){
        ToolCard5 m=(ToolCard5)mex;
        try{
            if(itemT.getIsBusy()){
                itemT.function(controller,m);

                successUseFin(itemT,mex);
                controller.cleanAll();
            }
            else errorActivation(itemT,mex);
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            try {
                controller.getcAction().putOnGrid(m.getAttributes().get(0),controller.getHoldingRoundGDie());
            } catch (InvalidValueException e1) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e1));
            }
            errorSelection(mex);
            controller.cleanAll();
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(TaglierinaManuale itemTM, Activate mex){
        ToolCard12 m= (ToolCard12) mex;
        try{
            if(itemTM.getIsBusy()){
                itemTM.function(controller,m);
                successUseFin(itemTM,mex);
                controller.cleanAll();
            }
            else errorActivation(itemTM,mex);
        }
        catch(InvalidValueException | InvalidCellException e){

            try {
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                                                                        m.getAttributes().get(2),
                                                                        m.getAttributes().get(3));
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(6),
                        m.getAttributes().get(7));
            } catch (InvalidValueException|InvalidCellException e1) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e1));
            }

            errorSelection(mex);
            controller.cleanAll();
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(TamponeDiamantato itemTD, Activate mex){
        ToolCard10 m=(ToolCard10)mex;
        try{
            if(itemTD.getIsBusy()){
                itemTD.function(controller,m);
                successUseFin(itemTD,mex);
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTD.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemTD.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            errorSelection(mex);
            controller.cleanAll();
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(TenagliaARotelle itemTe, Activate mex){
        ToolCard8 m=(ToolCard8)mex;
        try{
            if(itemTe.getIsBusy()){
                itemTe.function(controller,m);
                successUseFin(itemTe,mex);
                controller.cleanAll();
            }
            else{
                itemTe.undoCostUpdate();
                errorActivation(itemTe,mex);
            }
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            errorSelection(mex);
            controller.cleanAll();
        }
        catch( InvalidActivationException | InvalidHowManyTimes e){
            itemTe.undoCostUpdate();
            errorActivation(itemTe,mex);
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    private void successUseFin(Utensils item,Activate m) throws InvalidValueException {
        if(item!=null && m!=null) {
            controller.updateUtensilsCost();
            playerActivated(item);
            controller.getcChat().notifyObserver(new SuccessActivationFinalized(item.getNumber(), m.getCard(),
                    controller.getTurn().getFavours(),
                    item.getCost(), m.getPlayer()));
            item.setTheUse();
        }
    }
    private void playerActivated(Utensils item) throws InvalidValueException {
        controller.getcAction().playerActivatedCard(controller.getTurn().getName(),item.getPreviousCost());
    }
    private void  errorActivation(Utensils item,Activate m){
        if(item!=null && m!=null) {
            controller.getcChat().notifyObserver(new ErrorActivation(item.getNumber(), m.getCard(),
                    controller.getTurn().getFavours(), item.getCost(), m.getPlayer()));
            item.undoCostUpdate();
        }
    }
    private void errorSelection(Activate m){
        if(m!=null) controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
    }

}
