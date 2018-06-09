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

public abstract class Utensils implements Visitable{

    //OVERVIEW la classe è a sostegno delle classi delle carte utensili
    //i metodi comuni a tutte vengono dichiarati qui


    private Color squareColor;
    private String myType;
    private int number;
    private String description;
    private int cost;
    private boolean isFirstTime;
    private boolean isBusy=false;

    public Utensils(int numb, String typo, Color color, String desc){
        this.number=numb;
        this.myType=typo;
        this.squareColor=color;
        this.description=desc;
        this.cost=1;
        this.isFirstTime=true;
    }
    public void accept(Visitor visitor,Activate m){
        visitor.visit(this,m);
    }

    public void setTheUse(){
        isBusy= !isBusy;
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

    public Color getSquareColor(){
        return squareColor;
    }

    public String getDesciption(){
        return description;
    }

    //funzione di sostegno: quando viene richiamato function aggiorna il costo in base a che uso è stato
    //fatto-> da richiamare dentro function
    private void addToCost(){
       if(isFirstTime){
           cost=cost+1;
           isFirstTime=false;
       }
    }
    private boolean checkerPrice(Controller controller,Activate m) throws InvalidValueException {
        if(cost<=controller.getPlayerByName(m.getPlayer()).getFavours()){
            controller.getPlayerByName(m.getPlayer()).resetFavours(cost);
            addToCost();
            setTheUse();
            return true;
        }
        return false;
    }

    public void firstActivation(Controller controller,Activate m){
        try {
            if (checkerPrice(controller, m)) {
                controller.getcChat().notifyObserver(new SuccessActivation(myType, m.getCard(),
                        controller.getTurn().getFavours(),
                        cost,
                        controller.getTurn().getName()));
            } else
                controller.getcChat().notifyObserver(
                        new ErrorActivation(m.getCard(), "", controller.getTurn().getName()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(
                    new ErrorSomethingNotGood());
        }

    }
}
