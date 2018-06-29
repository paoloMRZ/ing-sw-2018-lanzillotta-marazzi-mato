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

public class ControllerCard implements Visitor {

    private final Controller controller;

    public ControllerCard(Controller controller){
        this.controller=controller;
    }

/////////////////////////////////////////////////////////////////////////


    public void visit(PinzaSgrossatrice itemPinza,Activate mex){
        MoreThanSimple m=(MoreThanSimple) mex;
        try{
            if(itemPinza.getIsBusy()){
                itemPinza.function(controller,m);

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemPinza.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPinza.getNumber(),m.getCard(),
                                                        controller.getTurn().getFavours(),
                                                        itemPinza.getCost(),m.getPlayer()));
                itemPinza.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPinza.getNumber(),m.getCard(),
                                                            controller.getTurn().getFavours(),
                                                            itemPinza.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            controller.cleanAll();
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemPennelloEglo.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPennelloEglo.getNumber(),m.getCard(),
                                                                controller.getTurn().getFavours(),
                                                                itemPennelloEglo.getCost(),m.getPlayer()));
                itemPennelloEglo.setTheUse();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloEglo.getNumber(),m.getCard(),
                                                        controller.getTurn().getFavours(),
                                                             itemPennelloEglo.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemLathekin.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemLathekin.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemLathekin.getCost(),m.getPlayer()));
                itemLathekin.setTheUse();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemLathekin.getNumber(),m.getCard(),
                                                                    controller.getTurn().getFavours(),
                                                                        itemLathekin.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            try {
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(0),
                        m.getAttributes().get(1));
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(4),
                        m.getAttributes().get(5));
            } catch (InvalidCellException e1) {
                e1.printStackTrace();
            } catch (InvalidValueException e1) {
                e1.printStackTrace();
            }

            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(PennelloPerPastaSalda itemPennelloPasta,Activate mex){
        ToolCard6 m=(ToolCard6) mex;
        if(!m.getisBis()) {
            try {
                if (itemPennelloPasta.getIsBusy()) {
                    int value = itemPennelloPasta.function(controller, m);
                    controller.getcChat().notifyObserver(new SuccessValue(m.getPlayer(), m.getCard(), value));
                } else
                    controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(), m.getCard(),
                            controller.getTurn().getFavours(),
                            itemPennelloPasta.getCost(), m.getPlayer()));
            } catch (InvalidValueException e) {
                controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(), m.getCard()));
            } catch (Exception e) {
                controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            }
        }
        else {
            try {
                ToolCard6Bis mess=(ToolCard6Bis) mex;
                if (itemPennelloPasta.getIsBusy()) {
                    itemPennelloPasta.function(controller, mess);

                    controller.getcAction().playerActivatedCard(controller.getTurn().getName(), itemPennelloPasta.getPreviousCost());

                    controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPennelloPasta.getNumber(), mess.getCard(),
                            controller.getTurn().getFavours(),
                            itemPennelloPasta.getCost(), mess.getPlayer()));

                    itemPennelloPasta.setTheUse();
                    controller.cleanAll();
                } else
                    controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(), mess.getCard(),
                            controller.getTurn().getFavours(),
                            itemPennelloPasta.getCost(), mess.getPlayer()));
            } catch (InvalidValueException e) {

                controller.getcAction().putBackInReserve();
                controller.cleanAll();
                controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(), m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemAlesatore.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemAlesatore.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemAlesatore.getCost(),m.getPlayer()));
                itemAlesatore.setTheUse();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemAlesatore.getNumber(),m.getCard(),
                                                                        controller.getTurn().getFavours(),
                                                                        itemAlesatore.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemMartelletto.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemMartelletto.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemMartelletto.getCost(),m.getPlayer()));
                itemMartelletto.setTheUse();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemMartelletto.getNumber(),m.getCard(),
                                                                controller.getTurn().getFavours(),
                                                                  itemMartelletto.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch( InvalidActivationException e){
            controller.getcChat().notifyObserver(new ErrorActivation(itemMartelletto.getNumber(),m.getCard(),
                                                                        controller.getTurn().getFavours(),
                                                                        itemMartelletto.getCost(),m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(DiluentePerPastaSalda itemDiluente,Activate mex){

        ToolCard11 m=(ToolCard11) mex;
        if(!m.getisBis()){
            try {
                if (itemDiluente.getIsBusy()) {
                    String color = itemDiluente.function(controller, m);
                    controller.getcChat().notifyObserver(
                            new SuccessColor(m.getPlayer(), m.getCard(), color));
                } else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(), m.getCard(),
                        controller.getTurn().getFavours(),
                        itemDiluente.getCost(), m.getPlayer()));
            } catch (InvalidValueException e) {
                controller.getcAction().putBackInReserve(controller.getHoldingResDie());
                controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(), m.getCard()));
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

                //riga che veniva fatta dentro
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemDiluente.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemDiluente.getNumber(),mess.getCard(),
                                                                                controller.getTurn().getFavours(),
                                                                                 itemDiluente.getCost(),m.getPlayer()));
                itemDiluente.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(),mess.getCard(),
                                                                        controller.getTurn().getFavours(),
                                                                         itemDiluente.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(mess.getPlayer(),mess.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemRiga.getPreviousCost());
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemRiga.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                     itemRiga.getCost(),m.getPlayer()));
                itemRiga.setTheUse();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemRiga.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemRiga.getCost(),m.getPlayer()));
        }
        catch( InvalidValueException| InvalidShadeException| NotEmptyCellException| InvalidColorException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemT.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemT.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemT.getCost(),m.getPlayer()));
                itemT.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemT.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemT.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            try {
                controller.getcAction().putOnGrid(m.getAttributes().get(0),controller.getHoldingRoundGDie());
            } catch (InvalidValueException e1) {
                e1.printStackTrace();
            }
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTM.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTM.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemTM.getCost(),m.getPlayer()));
                itemTM.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTM.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemTM.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){

            try {
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                                                                        m.getAttributes().get(2),
                                                                        m.getAttributes().get(3));
                controller.getPlayerByName(m.getPlayer()).putDice(controller.getHoldingADiceMoveInProgress(),
                        m.getAttributes().get(6),
                        m.getAttributes().get(7));
            } catch (InvalidCellException e1) {
                e1.printStackTrace();
            } catch (InvalidValueException e1) {
                e1.printStackTrace();
            }

            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTD.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTD.getNumber(),m.getCard(),
                                                                                        controller.getTurn().getFavours(),
                                                                                           itemTD.getCost(),m.getPlayer()));
                itemTD.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTD.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemTD.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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

                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTe.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTe.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                      itemTe.getCost(),m.getPlayer()));
                itemTe.setTheUse();
                controller.cleanAll();
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTe.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemTe.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
            controller.cleanAll();
        }
        catch( InvalidActivationException | InvalidHowManyTimes e){
            controller.getcChat().notifyObserver(new ErrorActivation(itemTe.getNumber(),m.getCard(),
                                                                        controller.getTurn().getFavours(),
                                                                        itemTe.getCost(),m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


}
