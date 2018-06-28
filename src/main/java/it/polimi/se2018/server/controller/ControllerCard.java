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
    private final Table lobby;


    public ControllerCard(Table table,Controller controller){
        this.lobby=table;
        this.controller=controller;
    }

/////////////////////////////////////////////////////////////////////////



    @Override
    public void visit(Utensils itemUtensil, Activate m){
        itemUtensil.firstActivation(controller,m);
    }


    public void visit(PinzaSgrossatrice itemPinza,MoreThanSimple m){
        try{
            if(itemPinza.getIsBusy()){
                itemPinza.function(controller,m);
                itemPinza.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemPinza.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPinza.getNumber(),m.getCard(),
                                                        controller.getTurn().getFavours(),
                                                        itemPinza.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPinza.getNumber(),m.getCard(),
                                                            controller.getTurn().getFavours(),
                                                            itemPinza.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcAction().putBackInReserve(controller.getHoldingResDie());

            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
        controller.cleanAll();
    }

    public void visit(PennelloPerEglomise itemPennelloEglo, ToolCard2 m){
        try{
            if(itemPennelloEglo.getIsBusy()){
                itemPennelloEglo.function(controller,m);
                itemPennelloEglo.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemPennelloEglo.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPennelloEglo.getNumber(),m.getCard(),
                                                                controller.getTurn().getFavours(),
                                                                itemPennelloEglo.getCost(),m.getPlayer()));
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

    public void visit(Lathekin itemLathekin, ToolCard4 m){
        try{
            if(itemLathekin.getIsBusy()){
                itemLathekin.function(controller,m);
                itemLathekin.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemLathekin.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemLathekin.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemLathekin.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemLathekin.getNumber(),m.getCard(),
                                                                    controller.getTurn().getFavours(),
                                                                        itemLathekin.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6 m){
        try{
            if(itemPennelloPasta.getIsBusy()){
                int value= itemPennelloPasta.function(controller,m);
                controller.getcChat().notifyObserver(new SuccessValue(m.getPlayer(),m.getCard(),value));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                                 itemPennelloPasta.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6Bis m){
        try{
            if(itemPennelloPasta.getIsBusy()){
                itemPennelloPasta.function(controller,m);
                itemPennelloPasta.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemPennelloPasta.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemPennelloPasta.getNumber(),m.getCard(),
                                                                                controller.getTurn().getFavours(),
                                                                                itemPennelloPasta.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(),m.getCard(),
                                                                    controller.getTurn().getFavours(),
                                                                    itemPennelloPasta.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(AlesatorePerLaminaDiRame itemAlesatore,ToolCard3 m){
        try{
            if(itemAlesatore.getIsBusy()){
                itemAlesatore.function(controller,m);
                itemAlesatore.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemAlesatore.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemAlesatore.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemAlesatore.getCost(),m.getPlayer()));
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

    public void visit(Martelletto itemMartelletto, ToolCard7 m){
        try{
            if(itemMartelletto.getIsBusy()){
                itemMartelletto.function(controller);
                itemMartelletto.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemMartelletto.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemMartelletto.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemMartelletto.getCost(),m.getPlayer()));
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


    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11 m){
        try{
            if(itemDiluente.getIsBusy()){
                String color= itemDiluente.function(controller,m);
                controller.getcChat().notifyObserver(
                        new  SuccessColor(m.getPlayer(),m.getCard(),color));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(),m.getCard(),
                                                                         controller.getTurn().getFavours(),
                                                                itemDiluente.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11Bis m){
        try{
            if(itemDiluente.getIsBusy()){
                itemDiluente.function(controller,m);
                itemDiluente.setTheUse();
                //riga che veniva fatta dentro
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemDiluente.getPreviousCost());
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemDiluente.getNumber(),m.getCard(),
                                                                                controller.getTurn().getFavours(),
                                                                                 itemDiluente.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(),m.getCard(),
                                                                        controller.getTurn().getFavours(),
                                                                         itemDiluente.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(RigaInSughero itemRiga, ToolCard9 m){
        try{
            if(itemRiga.getIsBusy()){
                itemRiga.function(controller,m);
                itemRiga.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemRiga.getPreviousCost());
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemRiga.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                     itemRiga.getCost(),m.getPlayer()));
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

    public void visit(TaglierinaCircolare itemT, ToolCard5 m){
        try{
            if(itemT.getIsBusy()){
                itemT.function(controller,m);
                itemT.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemT.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemT.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemT.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemT.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemT.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }


    public void visit(TaglierinaManuale itemTM, ToolCard12 m){
        try{
            if(itemTM.getIsBusy()){
                itemTM.function(controller,m);
                itemTM.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTM.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTM.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemTM.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTM.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemTM.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(TamponeDiamantato itemTD, ToolCard10 m){
        try{
            if(itemTD.getIsBusy()){
                itemTD.function(controller,m);
                itemTD.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTD.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTD.getNumber(),m.getCard(),
                                                                                        controller.getTurn().getFavours(),
                                                                                           itemTD.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTD.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                    itemTD.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    public void visit(TenagliaARotelle itemTe, ToolCard8 m){
        try{
            if(itemTe.getIsBusy()){
                itemTe.function(controller,m);
                itemTe.setTheUse();
                controller.getcAction().playerActivatedCard(controller.getTurn().getName(),itemTe.getPreviousCost());

                controller.getcChat().notifyObserver(new SuccessActivationFinalized(itemTe.getNumber(),m.getCard(),
                                                                                    controller.getTurn().getFavours(),
                                                                                      itemTe.getCost(),m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTe.getNumber(),m.getCard(),
                                                                            controller.getTurn().getFavours(),
                                                                            itemTe.getCost(),m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelectionUtensil(m.getPlayer(),m.getCard()));
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
