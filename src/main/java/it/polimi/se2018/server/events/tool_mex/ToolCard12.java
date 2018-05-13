package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard12 extends ToolMultiParam {

    private int coordOnBox;
    private int coordInBox;
    private int D1RowOld;
    private int D1ColOld;
    private int D2RowOld;
    private int D2ColOld;
    private int D1RowNew;
    private int D1ColNew;
    private int D2RowNew;
    private int D2ColNew;
//todo rivedere il numero di parametri

    public ToolCard12(String player, int cardIndex, int coordBox, int coordInBox,int D1RowOld, int D1ColOld,int D1RowNew, int D1ColNew){
        super(player,cardIndex);
        this.coordInBox=coordInBox;
        this.coordOnBox=coordBox;
        this.D1RowOld=D1RowOld;
        this.D1ColOld=D1ColOld;
        this.D1RowNew=D1RowNew;
        this.D1ColNew=D1ColNew;
        this.D2RowOld=-1;
        this.D2ColOld=-1;
        this.D2RowNew=-1;
        this.D2ColNew=-1;
    }
    public ToolCard12(String player, int cardIndex, int coordBox, int coordInBox,int D1RowOld, int D1ColOld,int D1RowNew, int D1ColNew,int D2RowOld, int D2ColOld,int D2RowNew, int D2ColNew){
        super(player,cardIndex);
        this.coordInBox=coordInBox;
        this.coordOnBox=coordBox;
        this.D1RowOld=D1RowOld;
        this.D1ColOld=D1ColOld;
        this.D1RowNew=D1RowNew;
        this.D1ColNew=D1ColNew;
        this.D2RowOld=D2RowOld;
        this.D2ColOld=D2ColOld;
        this.D2RowNew=D2RowNew;
        this.D2ColNew=D2ColNew;
    }

    @Override
    public ArrayList<Integer> getAttributes(){
        if(D2ColNew==-1) return new ArrayList<>(Arrays.asList(coordOnBox, coordInBox,D1RowOld, D1ColOld, D1RowNew,  D1ColNew));
        else return new ArrayList<>(Arrays.asList(coordOnBox, coordInBox,D1RowOld, D1ColOld, D1RowNew,  D1ColNew, D2RowOld, D2ColOld, D2RowNew, D2ColNew));
    }

}
