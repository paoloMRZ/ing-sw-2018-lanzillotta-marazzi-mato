package it.polimi.se2018.client.cli.game.utenil;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class UtensilCard implements Serializable {

    private String name;
    private String description;
    private int number;
    private int prize;

    public UtensilCard(String name, String description, int number){
        if(name != null && description != null && number > 0 && number < 13){
            this.name = name;
            this.description = description;
            this.number = number;
            this.prize = 1;
        }else
            throw new InvalidParameterException();
    }

    public void upPrize(){
        if(prize == 1)
            prize++;
    }

    public int getPrize(){return prize;}

    public int getNumber(){return number;}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
