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

    //Array di interi che tiene conto delle celle che ho già controllato nelle passate precedenti
    int[][] marked = new int[4][5];

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

    //Metodo supporto -> riconosce le Celle Frame della carta schema che non siano anche Celle Corner
    private boolean isFrameCell(int row, int col){
        boolean frame = false;
        switch(row){
            case 0: case 3: if(!isCornerCell(row,col)) frame = true;
                break;
            default: if(col==0 || col==4) frame = true;
                break;
        }
        return frame;
    }

    //Metodo supporto -> a seconda del tipo di Frame Cell (private dei corner), controlla se i vicini possibili hanno lo stesso colore e ritorna il numero dei vicini
    private int hasFrameNeighbors(Player player, int row, int col, int[][] marked) throws Exception {

        String tempColor1 = player.getDiceColor(row,col);
        String tempColor2;
        String tempColor3;

        int neighbors =0;

        switch(row){
            case 0: case 3:  tempColor2 = player.getDiceColor(row,col-1);
                             tempColor3 = player.getDiceColor(row, col+1);
                             if(tempColor2.equals(tempColor1) && marked[row][col-1]==0){
                                     marked[row][col-1]=1;
                                     neighbors++;
                             }
                             if(tempColor3.equals(tempColor1) && marked[row][col+1]==0){
                                     marked[row][col+1]=1;
                                     neighbors++;
                             }
                             break;
            default: if(col==0 || col==4){
                        tempColor2 = player.getDiceColor(row-1, col);
                        tempColor3 = player.getDiceColor(row+1,col);
                        if(tempColor2.equals(tempColor1) && marked[row-1][col]==0){
                                neighbors++;
                                marked[row-1][col]=1;
                        }
                        if(tempColor3.equals(tempColor1) && marked[row+1][col]==0){
                                neighbors++;
                                marked[row+1][col]=1;
                        }
                     }
                     break;
        }
        return neighbors;
    }

    //Metodo supporto -> a seconda del tipo di Corner Cell, ritorna il numero di vicini con lo stesso colore
    private int hasCornerNeighbors(Player player, int row, int col, int[][] marked) throws Exception {

        int neighbors = 0;
        String tempColor1 = player.getDiceColor(row,col);
        String tempColor2;

        switch(row){
            case 0:
                if(col==0) {
                    tempColor2 = player.getDiceColor(row+1, col+1);
                    if (tempColor1.equals(tempColor2) && marked[row+1][col+1]==0){
                            neighbors++;
                            marked[row + 1][col + 1] = 1;
                    }
                } else if (col==4) {
                    tempColor2 = player.getDiceColor(row+1, col-1);
                    if (tempColor1.equals(tempColor2) && marked[row+1][col-1] ==0){
                            neighbors++;
                            marked[row + 1][col - 1] = 1;
                    }
                }
                break;

            case 3:
                if (col==0) {
                    tempColor2 = player.getDiceColor(row-1, col+1);
                    if (tempColor1.equals(tempColor2) && marked[row-1][col+1]==0) {
                            neighbors++;
                            marked[row-1][col+1] = 1;
                    }
                } else if (col==4) {
                    tempColor2 = player.getDiceColor(row-1, col-1);
                    if (tempColor1.equals(tempColor2) && marked[row-1][col-1]==0) {
                            neighbors++;
                            marked[row-1][col-1]=1;
                    }
                }
                break;
        }
        return neighbors;
    }

    //Metodo supporto -> Riconosce la tipologia di Cella e restituisce il numero di celle adiacenti dello stesso colore
    //Questo mi permetterà di determianre  il numero di volte che il ciclo while dovrà essere ripetuto dalla stessa cella
    private int hasCellNeighbors(Player player, int row, int col, int[][] marked) throws Exception {

        int neighbors = 0;
        String tempColor = player.getDiceColor(row,col);
        String tempColor1;
        String tempColor2;
        String tempColor3;
        String tempColor4;

        if(isCornerCell(row,col)){
            neighbors = neighbors + hasCornerNeighbors(player,row,col,marked);
        }
        else if(isFrameCell(row,col)){
            neighbors = neighbors + hasFrameNeighbors(player,row,col,marked);
        }

        //Altrimenti è una cella normale e ha bisogno di controllare 4 adiacenze possibili

        else{
            tempColor1= player.getDiceColor(row-1, col-1);
            tempColor2= player.getDiceColor(row-1, col+1);
            tempColor3= player.getDiceColor(row+1, col-1);
            tempColor4= player.getDiceColor(row+1, col+1);

            if(tempColor.equals(tempColor1)) {
                neighbors++;
                marked[row-1][col-1]=1;
            }
            if(tempColor.equals(tempColor2)) {
                neighbors++;
                marked[row-1][col+1]=1;
            }
            if(tempColor.equals(tempColor3)) {
                neighbors++;
                marked[row+1][col-1]=1;
            }
            if(tempColor.equals(tempColor4)) {
                neighbors++;
                marked[row+1][col+1]=1;
            }
        }
        return neighbors;
    }

    @Override
    public int use(Player player) throws Exception {

        int favours=0;
        int tempNeighbors =0;

        for (int i=0; i<4; i++) {
            for (int j = 0; j < 5; j++){
                tempNeighbors = hasCellNeighbors(player, i, j, marked);
                favours = favours + tempNeighbors;
                }
            }
        return favours;
    }
}
