package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.card.card_schema.Cell;

/*
    Algoritmo9:
    Nome: "Diagonali Colorate"
    Effetto: "Numero di dadi dello stesso colore diagonalmente adiacenti"
    Punti Favore: #
*/

public class ColorDiagonals implements StrategyAlgorithm{

    //Metodo supporto -> Riconosce le Celle Corner della carta schema
    private boolean isCornerCell(int row, int col){
        boolean corner = false;
        switch(col){
            case 0: if(row==0 || row==3) corner = true;
                break;
            case 4: if(row==0 || row==3) corner = true;
                break;
        }
        return corner;
    }

    //Metodo supporto -> riconosce le Celle Frame (Ovvero i lati destro e sinistro) della carta schema che non siano anche Celle Corner
    private boolean isFrameCell(int row, int col){
        boolean frame = false;
            if(col==0 || col==4) frame = true;
        return frame;
    }

    //Metodo supporto -> a seconda del tipo di Corner Cell, ritorna il numero di vicini con lo stesso colore
    private int hasCornerNeighbors(Player player, int row, int col) throws Exception {

        int neighbors = 0;
        String tempColor1 = player.getDiceColor(row,col);
        String tempColor2;
        String tempColor3;

        switch(row){
            case 0:
                if(col==0) {
                    tempColor2 = player.getDiceColor(row+1, col+1);
                    if (tempColor1.equals(tempColor2)) neighbors++;
                } else if (col==4) {
                    tempColor2 = player.getDiceColor(row+1, col-1);
                    if (tempColor1.equals(tempColor2)) neighbors++;
                }
                break;

            case 3:
                if (col==0) {
                    tempColor2 = player.getDiceColor(row-1, col+1);
                    if (tempColor1.equals(tempColor2)) neighbors++;
                } else if (col==4) {
                    tempColor2 = player.getDiceColor(row-1, col-1);
                    if (tempColor1.equals(tempColor2)) neighbors++;
                }
                break;
        }
        return neighbors;
    }

    //Metodo supporto -> sia per il lato destro che sinistro controlla se ci sono vicini e ne ritorna il numero
    private int hasFrameNeighbors(Player player, int row, int col) throws Exception {

        String tempColor1 = player.getDiceColor(row,col);
        String tempColor2;
        int neighbors = 0;

        switch(col){
            case 0: tempColor2 = player.getDiceColor(row+1, col+1);
                    if(tempColor2.equals(tempColor1)) neighbors++;
                    break;
            case 4: tempColor2 = player.getDiceColor(row+1, col-1);
                    if(tempColor2.equals(tempColor1)) neighbors++;
                    break;
        }

        return neighbors;
    }

    @Override
    public int use(Player player) throws Exception {

        int favours=0;

        String tempColor1;
        String tempColor2;
        String tempColor3;

        for (int i=0; i<3; i++) {
            for (int j=0; j<5; j++){
                if(player.showSelectedCell(i,j).showDice()!= null) {
                    if(isCornerCell(i,j)) favours = favours + hasCornerNeighbors(player,i,j);
                    else if (isFrameCell(i,j)) favours = favours + hasFrameNeighbors(player,i,j);
                    else {
                        tempColor1 = player.getDiceColor(i, j);
                        tempColor2 = player.getDiceColor(i+1, j+1);
                        tempColor3 = player.getDiceColor(i+1, j+1);

                        if (tempColor1.equals(tempColor2)) favours++;
                        if (tempColor1.equals(tempColor3)) favours++;
                    }
                }
                }
            }
        return favours;
    }
}
