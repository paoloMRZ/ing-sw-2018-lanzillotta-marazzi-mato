package it.polimi.se2018.client.cli.game.objective;

import java.io.Serializable;

public class ObjectiveCard implements Serializable{

    private String name;
    private String description;
    private int reward; //0 indica #.
    private boolean isPrivate;

    public ObjectiveCard(String name, String description, int reward, boolean isPrivate){ //Inserire 0 per indicare # come ricompesa.
        if(name!= null && description != null && reward >= 0){
            this.name = name;
            this.description = description;
            this.reward = reward;
            this.isPrivate = isPrivate;
        }
    }


    public String getDescription() {
        return description;
    }

    public int getReward() {
        return reward;
    }

    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
