package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


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
        switch(row){
            case 0: if(col==0 || col==4) corner = true;
                break;
        }
        return corner;
    }

    //Metodo supporto -> riconosce le Celle Frame (Ovvero i lati destro e sinistro) della carta schema che non siano anche Celle Corner
    private boolean isFrameCell(int row, int col){
        boolean frame = false;
            if(col==0 || col==4 && row!=0) frame = true;
        return frame;
    }

    //Metodo supporto -> a seconda del tipo di Corner Cell, se questo ha un vicino, ritorna 2 (se stesso e l'eventuale vicino), altrimenti 0
    private int hasCornerNeighbors(Player player, int row, int col) throws Exception {

        int neighbors =0;
        Dice die;
        String tempColor1;
        String tempColor2;

        Dice dieRoot = player.showSelectedCell(row,col).showDice();
        if(dieRoot != null) {
            tempColor1 = dieRoot.getColor();
            switch (col) {
                case 0:
                    die = player.showSelectedCell(row + 1, col + 1).showDice();
                    if (die != null) {
                        tempColor2 = die.getColor();
                        if (tempColor1.equals(tempColor2))neighbors++;
                    }
                    break;

                case 4:
                    die = player.showSelectedCell(row + 1, col - 1).showDice();
                    if (die != null) {
                        tempColor2 = die.getColor();
                        if (tempColor1.equals(tempColor2)) neighbors++;
                    }
                    break;
            }
        }
        return neighbors;
    }

    //Metodo supporto -> sia per il lato destro che sinistro controlla se ci sono vicini e ne ritorna il numero
    private int hasFrameNeighbors(Player player, int row, int col) throws Exception {

        String tempColor1;
        String tempColor2;
        int neighbors = 0;
        Dice die;

        Dice dieRoot = player.showSelectedCell(row,col).showDice();
        if(dieRoot != null) {
            tempColor1 = dieRoot.getColor();
            switch (col) {
                case 0:
                    die = player.showSelectedCell(row + 1, col + 1).showDice();
                    if (die != null) {
                        tempColor2 = die.getColor();
                        if (tempColor2.equals(tempColor1)) neighbors++;
                    }
                    break;
                case 4:
                    die = player.showSelectedCell(row + 1, col - 1).showDice();
                    if (die != null) {
                        tempColor2 = die.getColor();
                        if (tempColor2.equals(tempColor1)) neighbors++;
                    }
                    break;
            }
        }
        return neighbors;
    }

    @Override
    public int use(Player player) throws Exception {

        int favours=1;
        String tempColor1;
        String tempColor2;
        String tempColor3;

        for (int i=0; i<3; i++) {
            for (int j=0; j<5; j++){
                    if(isCornerCell(i,j)) favours = favours + hasCornerNeighbors(player,i,j);
                    else if (isFrameCell(i,j)) favours = favours + hasFrameNeighbors(player,i,j);
                    else {

                        Dice dieTemp1 = player.showSelectedCell(i,j).showDice();
                        Dice dieTemp2 = player.showSelectedCell(i+1,j+1).showDice();
                        Dice dieTemp3 = player.showSelectedCell(i+1,j-1).showDice();

                        if(dieTemp1!=null){
                            tempColor1 = dieTemp1.getColor();
                            if(dieTemp2!=null) {
                                tempColor2 = dieTemp2.getColor();
                                if (tempColor1.equals(tempColor2)) favours++;
                            }
                            if(dieTemp3!=null){
                                tempColor3 = dieTemp3.getColor();
                                if (tempColor1.equals(tempColor3)) favours++;
                            }
                        }
                    }
                }
            }
        return favours;
    }
}
