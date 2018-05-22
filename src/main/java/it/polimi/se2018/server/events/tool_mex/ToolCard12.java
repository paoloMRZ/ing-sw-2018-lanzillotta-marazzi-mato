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

    public ToolCard12(String player, int cardIndex, ArrayList<Integer> inputs){
        super(player,cardIndex);
        if(inputs.size()==6){
            this.coordOnBox=inputs.get(0);
            this.coordInBox=inputs.get(1);
            this.D1RowOld=inputs.get(2);
            this.D1ColOld=inputs.get(3);
            this.D1RowNew=inputs.get(4);
            this.D1ColNew=inputs.get(5);
            this.D2RowOld=-1;
            this.D2ColOld=-1;
            this.D2RowNew=-1;
            this.D2ColNew=-1;
        }
        else{
            this.coordOnBox=inputs.get(0);
            this.coordInBox=inputs.get(1);
            this.D1RowOld=inputs.get(2);
            this.D1ColOld=inputs.get(3);
            this.D1RowNew=inputs.get(4);
            this.D1ColNew=inputs.get(5);
            this.D2RowOld=inputs.get(6);
            this.D2ColOld=inputs.get(7);
            this.D2RowNew=inputs.get(8);
            this.D2ColNew=inputs.get(9);
        }
    }

    @Override
    public ArrayList<Integer> getAttributes(){
        return new ArrayList<>(Arrays.asList(coordOnBox, coordInBox,D1RowOld, D1ColOld, D1RowNew,  D1ColNew, D2RowOld, D2ColOld, D2RowNew, D2ColNew));
    }

}
