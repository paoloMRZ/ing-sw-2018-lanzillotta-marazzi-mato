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



    @Override
    public void visit(Utensils itemUtensil, Activate m){
        itemUtensil.firstActivation(controller,m);
    }


    public void visit(PinzaSgrossatrice itemPinza,MoreThanSimple m){
        try{
            if(itemPinza.getIsBusy()){
                itemPinza.function(controller,m);
                itemPinza.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPinza.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(PennelloPerEglomise itemPennelloEglo, ToolCard2 m){
        try{
            if(itemPennelloEglo.getIsBusy()){
                itemPennelloEglo.function(controller,m);
                itemPennelloEglo.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloEglo.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(Lathekin itemLathekin, ToolCard4 m){
        try{
            if(itemLathekin.getIsBusy()){
                itemLathekin.function(controller,m);
                itemLathekin.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemLathekin.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }


    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6 m){
        try{
            if(itemPennelloPasta.getIsBusy()){
                itemPennelloPasta.function(controller,m);
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6Bis m){
        try{
            if(itemPennelloPasta.getIsBusy()){
                itemPennelloPasta.function(controller,m);
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemPennelloPasta.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(AlesatorePerLaminaDiRame itemAlesatore,ToolCard3 m){
        try{
            if(itemAlesatore.getIsBusy()){
                itemAlesatore.function(controller,m);
                itemAlesatore.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemAlesatore.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(Martelletto itemMartelletto, ToolCard7 m){
        try{
            if(itemMartelletto.getIsBusy()){
                itemMartelletto.function(controller);
                itemMartelletto.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemMartelletto.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch( InvalidActivationException e){
            controller.getcChat().notifyObserver(new ErrorActivation(m.getCard(),"",m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }


    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11 m){
        try{
            if(itemDiluente.getIsBusy()){
                itemDiluente.function(controller,m);
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11Bis m){
        try{
            if(itemDiluente.getIsBusy()){
                itemDiluente.function(controller,m);
                itemDiluente.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemDiluente.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }


    public void visit(RigaInSughero itemRiga, ToolCard9 m){
        try{
            if(itemRiga.getIsBusy()){
                itemRiga.function(controller,m);
                itemRiga.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemRiga.getNumber(),"",m.getPlayer()));
        }
        catch( InvalidValueException| InvalidShadeException| NotEmptyCellException| InvalidColorException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(TaglierinaCircolare itemT, ToolCard5 m){
        try{
            if(itemT.getIsBusy()){
                itemT.function(controller,m);
                itemT.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemT.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }


    public void visit(TaglierinaManuale itemTM, ToolCard12 m){
        try{
            if(itemTM.getIsBusy()){
                itemTM.function(controller,m);
                itemTM.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTM.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(TamponeDiamantato itemTD, ToolCard10 m){
        try{
            if(itemTD.getIsBusy()){
                itemTD.function(controller,m);
                itemTD.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTD.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }

    public void visit(TenagliaARotelle itemTe, ToolCard8 m){
        try{
            if(itemTe.getIsBusy()){
                itemTe.function(controller,m);
                itemTe.setTheUse();
                controller.getcChat().notifyObserver(new SuccessActivationFinalized(m.getCard(),"",m.getPlayer()));
            }
            else controller.getcChat().notifyObserver(new ErrorActivation(itemTe.getNumber(),"",m.getPlayer()));
        }
        catch(InvalidValueException | InvalidCellException e){
            controller.getcChat().notifyObserver(new ErrorSelection(m.getPlayer()));
        }
        catch( InvalidActivationException | InvalidHowManyTimes e){
            controller.getcChat().notifyObserver(new ErrorActivation(m.getCard(),"",m.getPlayer()));
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood());
        }
    }


}
