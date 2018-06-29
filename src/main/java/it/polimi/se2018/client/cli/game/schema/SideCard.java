package it.polimi.se2018.client.cli.game.schema;


import it.polimi.se2018.client.cli.game.info.CellInfo;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SideCard implements Serializable{

    private String name;
    private int favours;
    private CellInfo[] cells;

    public SideCard(String name, int favours, List<CellInfo> cells){
        if(name != null && favours >0 && cells != null){
            this.name = name;
            this.favours = favours;
            this.cells = cells.toArray(new CellInfo[0]);
        }else
            throw new InvalidParameterException();
    }

    public String getName() {
        return name;
    }

    public int getFavours(){
        return favours;
    }

    public List<CellInfo> getCells() {
        return new ArrayList<>(Arrays.asList(cells));
    }
}
