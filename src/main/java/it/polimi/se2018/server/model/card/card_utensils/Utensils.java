package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.responses.ErrorActivation;
import it.polimi.se2018.server.events.responses.ErrorSomethingNotGood;
import it.polimi.se2018.server.events.responses.SuccessActivation;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;

public class Utensils implements Visitable{

    //OVERVIEW la classe è a sostegno delle classi delle carte utensili
    //i metodi comuni a tutte vengono dichiarati qui


    private Color squareColor;
    private String myType;
    private int number;
    private String description;
    private int cost;
    private boolean isFirstTime;
    private boolean isBusy=false;
    private boolean priceHasBeenChecked=false;

    private int previousCost;

    public Utensils(int numb, String typo, Color color, String desc){
        this.number=numb;
        this.myType=typo;
        this.squareColor=color;
        this.description=desc;
        this.cost=1;
        this.previousCost=cost;
        this.isFirstTime=true;
    }


    public void setTheUse(){
        isBusy= !isBusy;
        togglePriceHasBeenChecked();
    }
    public boolean getIsBusy(){
        return isBusy;
    }
    public String getMyType(){
        return myType;
    }

    public int getNumber() {
        return number;
    }

    public int getCost(){
        return cost;
    }

    public int getPreviousCost() {
        return previousCost;
    }
    public void undoCostUpdate(){
        cost=getPreviousCost();
    }

    public Color getSquareColor(){
        return squareColor;
    }

    public String getDesciption(){
        return description;
    }

    public boolean getPriceHasBeenChecked(){
        return  priceHasBeenChecked;
    }
    private void togglePriceHasBeenChecked(){
        priceHasBeenChecked=!priceHasBeenChecked;
    }

    //funzione di sostegno: quando viene richiamato function aggiorna il costo in base a che uso è stato
    //fatto-> da richiamare dentro function
    private void addToCost(){
       if(isFirstTime){
           cost=cost+1;
           isFirstTime=false;
       }
       else previousCost=cost;
    }
    private boolean checkerPrice(Controller controller,Activate m) throws InvalidValueException {
        if(cost<=controller.getPlayerByName(m.getPlayer()).getFavours()){
            addToCost();
            setTheUse();
            return true;
        }
        return false;
    }

    public void firstActivation(Controller controller,Activate m){
        try {
            if (checkerPrice(controller, m)) {
                controller.getcChat().notifyObserver(new SuccessActivation(number, m.getCard(),
                        controller.getTurn().getFavours(),
                        cost,
                        controller.getTurn().getName()));
            } else
                controller.getcChat().notifyObserver(
                        new ErrorActivation(number, m.getCard(),
                                controller.getTurn().getFavours(),
                                cost,
                                controller.getTurn().getName()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(
                    new ErrorSomethingNotGood(e));
        }

    }

    @Override
    public void accept(Visitor visitor, Activate m) {
        //ciaooooooooooooooooo
    }
}
