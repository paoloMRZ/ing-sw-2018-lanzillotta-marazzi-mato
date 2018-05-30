package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alla carta "Diagonali Colorate" : "Numero di dadi dello stesso colore diagonalmente adiacenti"
 * con Punti Favore #
 *
 * I metodi contrassegnati con "last" nel loro nome sono utilizzati per analizzare le celle dell'ultima riga della carta Side o eventualemente le Celle
 * che non hanno prossime adiacenze (quindi le ultime possibili "vicine" di celle precedenti
 *
 * @author Simone Lanzillotta
 */


public class ColorDiagonals implements StrategyAlgorithm {

    /**
     * Metodo supporto: riconosce le Celle Frame (Ovvero i lati destro e sinistro) della carta Side che non siano anche Celle Corner
     *
     * @param col colonna della cella da classificare
     * @return TRUE se la cella è una Cella Frame, altrimenti FALSE
     */

    private boolean isFrameCell(int col) {
        boolean frame = false;
        if (col == 0 || col == 4) frame = true;
        return frame;
    }


    /**
     * Metodo supporto: a seconda della cella Frame (Destra o Sinistra), conta la cella stessa in caso di adiecenza sulle diagonali
     * Si noti infatti che i controlli possibili per le celle Frame sono differenti da una cella cosiddetta "normale" presa non ai
     * bordi
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @param row riga della cella da analizzare
     * @param col colonna della cella da analizzare
     * @return 1(nel caso di Cella Corner) o al massimo 2 (nel caso di Cella Frame) in caso di adiacenza, altrimenti 0 in entrambi i casi
     * @throws InvalidShadeValueException lanciata quando il dado eventuale della cella non rispetta la restrizione di sfumatura della cella.
     * @throws InvalidCoordinatesException lanciata quando le coordinate inserite non sono valide.
     */

    private int hasFrameNeighbors(Player player, int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {

        Color tempColor1;
        Color tempColor2;

        Dice dieTemp1 = player.showSelectedCell(row, col).showDice();
        Dice dieTemp2;

        int neighbors = 0;

            if (dieTemp1 != null) {
                tempColor1 = dieTemp1.getColor();
                switch(col){
                    case 0:
                        //Siamo nel lato sinistro, quindi devo controllare l'adiacenza sulla diagonale di destra
                        dieTemp2 = player.showSelectedCell(row + 1, col + 1).showDice();
                        if (dieTemp2 != null) {
                            tempColor2 = dieTemp2.getColor();
                            if (tempColor2.equals(tempColor1)) neighbors++;
                        }
                        break;
                    case 4:
                        //Siamo nel lato destro, quindi devo controllare l'adiacenza sulla diagonale di destra
                        dieTemp2 = player.showSelectedCell(row + 1, col - 1).showDice();
                        if (dieTemp2 != null) {
                            tempColor2 = dieTemp2.getColor();
                            if (tempColor2.equals(tempColor1)) neighbors++;
                        }
                        break;
                }
            }
            return neighbors;
        }


    /**
     * Metodo supporto: analizzando una cella Normale, conta la cella stessa in caso di adiecenza sulle diagonali
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @param row riga della cella da analizzare
     * @param col colonna della cella da analizzare
     * @return 1(nel caso di Cella Corner) o al massimo 2 (nel caso di Cella Frame) in caso di adiacenza, altrimenti 0 in entrambi i casi
     * @throws InvalidShadeValueException lanciata quando il dado eventuale della cella non rispetta la restrizione di sfumatura della cella.
     * @throws InvalidCoordinatesException lanciata quando le coordinate inserite non sono valide.
     */

    private int hasCellNeighbors(Player player, int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {

        Color tempColor1;
        Color tempColor2;
        Color tempColor3;

        Dice dieTemp1 = player.showSelectedCell(row, col).showDice();
        Dice dieTemp2;
        Dice dieTemp3;

        int neighbors = 0;
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

        if(neighbors==2) return neighbors -=1;
        else return neighbors;
    }


    /**
     * Metodo di supporto che controlla se la Cella Frame sia un'ultima adiacenza e ritorna 1 (ovvero conta se stessa)
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @param row riga della cella da analizzare
     * @param col colonna della cella da analizzare
     * @return 1(nel caso di Cella Corner) o al massimo 2 (nel caso di Cella Frame) in caso di adiacenza, altrimenti 0 in entrambi i casi
     * @throws InvalidShadeValueException lanciata quando il dado eventuale della cella non rispetta la restrizione di sfumatura della cella.
     * @throws InvalidCoordinatesException lanciata quando le coordinate inserite non sono valide.
     */

    private int lastFrameNeighbors(Player player, int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {

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


    /**
     * Metodo di supporto che controlla se la Cella Normale sia un'ultima adiacenza e ritorna 1 (ovvero conta se stessa)
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @param row riga della cella da analizzare
     * @param col colonna della cella da analizzare
     * @return 1(nel caso di Cella Corner) o al massimo 2 (nel caso di Cella Frame) in caso di adiacenza, altrimenti 0 in entrambi i casi
     * @throws InvalidShadeValueException lanciata quando il dado eventuale della cella non rispetta la restrizione di sfumatura della cella.
     * @throws InvalidCoordinatesException lanciata quando le coordinate inserite non sono valide.
     */

    private int lastCellNeighbors(Player player, int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {

        Dice dieTemp1 = player.showSelectedCell(row, col).showDice();
        Dice dieTemp2;
        Dice dieTemp3;
        Color colorTemp1;
        Color colorTemp2;
        Color colorTemp3;

        int neighbors = 0;

        if (dieTemp1 != null) {
            colorTemp1 = dieTemp1.getColor();
            dieTemp2 = player.showSelectedCell(row - 1, col + 1).showDice();
            dieTemp3 = player.showSelectedCell(row - 1, col - 1).showDice();

            if (dieTemp2 != null) {
                colorTemp2 = dieTemp2.getColor();
                if (colorTemp1.equals(colorTemp2)) neighbors += 1;
            }

            if (dieTemp3 != null) {
                colorTemp3 = dieTemp3.getColor();
                if (colorTemp1.equals(colorTemp3)) neighbors += 1;
            }
        }

        if(neighbors==2) return neighbors -= 1;
        else return neighbors;
    }


    /**
     * Metodo di supporto che ritorna TRUE se la cella non ha prossime adiacenze (controllo che viene fatto per le celle non appartenenti all'ultima riga)
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @param row riga della cella da analizzare
     * @param col colonna della cella da analizzare
     * @return 1(nel caso di Cella Corner) o al massimo 2 (nel caso di Cella Frame) in caso di adiacenza, altrimenti 0 in entrambi i casi
     * @throws InvalidShadeValueException lanciata quando il dado eventuale della cella non rispetta la restrizione di sfumatura della cella.
     * @throws InvalidCoordinatesException lanciata quando le coordinate inserite non sono valide.
     */

    private boolean isLastCellNeighbors(Player player, int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {
        if(col==0 || col==4) return hasFrameNeighbors(player,row,col)==0;
        else return hasCellNeighbors(player,row,col)==0;
    }


    /**
     * Metodo che implementa l'algoritmo associato alla carta Obbiettivo
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @return i segnalini guadagnati dal Player su cui si è applicata la carta Obbiettivo
     * @throws Exception viene sollevata in caso di Eccezione generica
     */

    @Override
    public int use(Player player) throws Exception {

        int favours = 0;

        for (int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                switch(i) {
                    case (0): //Sono nella prima riga, quindi devo controllare solo se ho prossime adiacenze
                              if(j==0 || j==4) favours = favours + hasFrameNeighbors(player, i, j);
                              else favours = favours + hasCellNeighbors(player,i,j);
                              break;
                    case (3): //Sono nell'ultima riga, quindi devo controllare semplicemente se le celle sono un'ultima eventuale adiacenza di celle precedenti
                              if (j==0 || j==4) favours = favours + lastFrameNeighbors(player, i, j);
                              else favours = favours + lastCellNeighbors(player, i, j);
                              break;
                    default: //Sono in una riga in mezzo, quindi devo controllare liberamente le adiacenze lungo entrambe le diagonali
                             //Controllo se la cella incriminata è l'ultima adiacenza possibile
                             if (isLastCellNeighbors(player, i, j)) {
                                if(isFrameCell(j)) favours = favours + lastFrameNeighbors(player, i, j);
                                else favours = favours + lastCellNeighbors(player,i,j);
                             } else {
                                 if(isFrameCell(j)) favours = favours + hasFrameNeighbors(player, i, j);
                                 else favours = favours + hasCellNeighbors(player,i,j);
                             }
                             break;
                }
            }
        }

        return favours;
    }

}
