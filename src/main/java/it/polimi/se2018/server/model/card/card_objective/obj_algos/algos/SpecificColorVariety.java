package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alle carte della tipologia SpecificColorVariety che, a seconda del valore
 * in ingresso, istanzia una delle seguenti tipologia di carta:
 *
 * "Colori diversi - Riga" : "Righe senza colori ripetuti", Punti Favore: 6
 * "Colori diversi - Colonna" : "Colonne senza colori ripetuti", Punti Favore: 5
 *
 * @author Simone Lanzillotta
 */


public class SpecificColorVariety extends VarietyAlgorithm implements StrategyAlgorithm {

    private int maxRow = 0;
    private int maxCol = 0;

    /**
     * Costruttore della classe che passa in ingresso il numero di righe e di colonne
     *
     * @param maxRow valore massimo delle righe della carta Side
     * @param maxCol valore massimo delle colonne della carta Side
     */

    public SpecificColorVariety(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }


    /**
     * Metodo che implementa l'algoritmo associato alla carta Obbiettivo: a seconda del valore in ingresso di righe o colonna,
     * effettua una ricerca per righe se maxRow<maxCol, altrimenti effettua una ricerca per colonne.
     *
     * @param player riferimento alla classe Player (quindi alla Side su cui si sta applicando la carta obbiettivo)
     * @return i segnalini guadagnati dal Player su cui si è applicata la carta Obbiettivo
     * @throws Exception viene sollevata in caso di Eccezione generica
     */

    @Override
    public int use(Player player) throws Exception {

        int[] contArray = new int[5];
        int cont = 0;
        Color tempColor;
        Dice die;

        for (int i=0; i<maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                if (maxRow > maxCol) die = player.showSelectedCell(j, i).showDice();
                else die = player.showSelectedCell(i, j).showDice();

                if (die != null) {
                    tempColor = die.getColor();
                    switch (tempColor) {
                        case RED:
                            contArray[0]++;
                            break;
                        case YELLOW:
                            contArray[1]++;
                            break;
                        case GREEN:
                            contArray[2]++;
                            break;
                        case PURPLE:
                            contArray[3]++;
                            break;
                        case BLUE:
                            contArray[4]++;
                            break;
                    }
                    cont = cont + checkArray(contArray, maxRow, maxCol, j);
                }
            }
        }
        if (maxRow > maxCol) return 5 * cont;
        else return 6 * cont;
    }
}