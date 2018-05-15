package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/*
    Algoritmo9:
    Nome: "Diagonali Colorate"
    Effetto: "Numero di dadi dello stesso colore diagonalmente adiacenti"
    Punti Favore: #
*/

public class ColorDiagonals implements StrategyAlgorithm {

    //Metodo supporto -> riconosce le Celle Frame (Ovvero i lati destro e sinistro) della carta schema che non siano anche Celle Corner
    private boolean isFrameCell(int row, int col) {
        boolean frame = false;
        if (col == 0 || col == 4) frame = true;
        return frame;
    }

    //Metodo supporto -> A seconda della tipologia di cella (se Corner o Frame), conta la cella stessa in caso di adiecenza
    private int hasFrameNeighbors(Player player, int row, int col) throws InvalidColorValueException, InvalidShadeValueException, InvalidCoordinatesException {

        Color tempColor1;
        Color tempColor2;
        Color tempColor3;

        Dice dieTemp1 = player.showSelectedCell(row, col).showDice();
        Dice dieTemp2;
        Dice dieTemp3;

        int neighbors = 0;

        //Controlla se siamo nel caso di cella Frame
        if (isFrameCell(row, col)) {

            if (dieTemp1 != null) {
                tempColor1 = dieTemp1.getColor();
                switch (col) {
                    case 0:
                        dieTemp2 = player.showSelectedCell(row + 1, col + 1).showDice();
                        if (dieTemp2 != null) {
                            tempColor2 = dieTemp2.getColor();
                            if (tempColor2.equals(tempColor1)) neighbors++;
                        }
                        break;
                    case 4:
                        dieTemp2 = player.showSelectedCell(row + 1, col - 1).showDice();
                        if (dieTemp2 != null) {
                            tempColor2 = dieTemp2.getColor();
                            if (tempColor2.equals(tempColor1)) neighbors++;
                        }
                        break;
                }
            }
        } else {
            if (dieTemp1 != null) {
                tempColor1 = dieTemp1.getColor();
                dieTemp2 = player.showSelectedCell(row + 1, col + 1).showDice();
                dieTemp3 = player.showSelectedCell(row + 1, col - 1).showDice();

                if (dieTemp2 != null) {
                    tempColor2 = dieTemp2.getColor();
                    if (tempColor1.equals(tempColor2)) neighbors++;
                }
                if (dieTemp3 != null) {
                    tempColor3 = dieTemp3.getColor();
                    if (tempColor1.equals(tempColor3)) neighbors++;

                }
            }
        }

        return neighbors;
    }

    //Metodi di supporto per le consideraioni di contorno finali (ultima riga o ultima adiacenza possibile)

    //Metodo di supporto -> controlla se la Cella corner sia un'ultima adiacenza e ritorna 1 (ovvero conta se stessa)
    private int lastCornerNeighbors(Player player, int row, int col) throws InvalidColorValueException, InvalidShadeValueException, InvalidCoordinatesException {

        Dice dieTemp1 = player.showSelectedCell(row,col).showDice();
        Dice dieTemp2;
        Color colorTemp1;
        Color colorTemp2;

        int neighbors = 0;

        if(dieTemp1!=null) {
            switch (col) {
                case 0: colorTemp1 = dieTemp1.getColor();
                        dieTemp2 = player.showSelectedCell(row-1,col+1).showDice();
                        if(dieTemp2!=null){
                            colorTemp2 = dieTemp2.getColor();
                            if(colorTemp1.equals(colorTemp2)) neighbors++;
                        }
                        break;
                case 4: colorTemp1 = dieTemp1.getColor();
                        dieTemp2 = player.showSelectedCell(row-1,col-1).showDice();
                        if(dieTemp2!=null){
                            colorTemp2 = dieTemp2.getColor();
                            if(colorTemp1.equals(colorTemp2)) neighbors++;
                        }
                        break;
            }
        }
        return neighbors;
    }

    //Metodo di supporto -> controlla se la Cella Frame sia un'ultima adiacenza e ritorna 1 (ovvero conta se stessa)
    private int lastFrameNeighbors(Player player, int row, int col) throws InvalidColorValueException, InvalidShadeValueException, InvalidCoordinatesException {

        Dice dieTemp1 = player.showSelectedCell(row,col).showDice();
        Dice dieTemp2;
        Dice dieTemp3;
        Color colorTemp1;
        Color colorTemp2;
        Color colorTemp3;

        int neighbors = 0;

        if(dieTemp1!=null) {
            colorTemp1 = dieTemp1.getColor();
            dieTemp2 = player.showSelectedCell(row-1,col+1).showDice();
            dieTemp3 = player.showSelectedCell(row-1,col-1).showDice();
            if(dieTemp2!=null){
                colorTemp2 = dieTemp2.getColor();
                if(colorTemp1.equals(colorTemp2)) neighbors++;
            }
            if(dieTemp3!=null){
                colorTemp3 = dieTemp3.getColor();
                if (colorTemp1.equals(colorTemp3)) neighbors++;
            }
        }
        return neighbors;
    }

    //Metodo di supporto -> ritorna TRUE se la cella non ha prossime adiacenze (controllo che viene fatto per le celle non appartenenti all'ultima riga)
    private boolean isLastCellNeighbors(Player player, int row, int col) throws InvalidColorValueException, InvalidShadeValueException, InvalidCoordinatesException {
        return hasFrameNeighbors(player,row,col)==0;
    }

    //Metodo supporto -> Conta in aggiunta l'ultima adiacenza in caso di corrispondenza
    private int lastNeighbors(Player player, int row, int col) throws InvalidColorValueException, InvalidShadeValueException, InvalidCoordinatesException {

        int neighbors = 0;

        //Cella Frame
        if(isFrameCell(row,col)){
            if(isLastCellNeighbors(player,row,col)) neighbors = neighbors+lastCornerNeighbors(player,row,col);
        }

        else{
            if(isLastCellNeighbors(player,row,col))  neighbors = neighbors + lastFrameNeighbors(player,row,col);
        }
        return neighbors;
    }

    @Override
    public int use(Player player) throws Exception {

        int favours = 0;
        Color tempColor1;
        Color tempColor2;
        Color tempColor3;

        Dice dieTemp2;
        Dice dieTemp3;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {

                //Sono all'ultima riga, quindi devo controllare solo sopra
                if (i == 3) {
                    if (isFrameCell(i, j)) favours = favours + lastCornerNeighbors(player, i, j);
                    else favours = favours + lastFrameNeighbors(player, i, j);
                } else {
                    if (isLastCellNeighbors(player, i, j)) {
                        if (isFrameCell(i, j)) favours = favours + lastCornerNeighbors(player, i, j);
                        else favours = favours + lastFrameNeighbors(player, i, j);
                    } else favours = favours + hasFrameNeighbors(player, i, j);
                }
            }
        }

        return favours;
    }

}
