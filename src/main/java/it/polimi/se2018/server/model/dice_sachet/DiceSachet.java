package it.polimi.se2018.server.model.dice_sachet;

public class DiceSachet{

    private FactoryDice factory;

    public DiceSachet(){
        this.factory=new FactoryDice();
    }

    public Dice getDiceFromSachet(){
        return factory.createDice();
    }
    public void reput(Dice D){
        factory.reput(D);
    }

}
