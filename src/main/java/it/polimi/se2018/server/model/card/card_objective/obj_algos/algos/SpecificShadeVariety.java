package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;



/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alle carte della tipologia SpecificShadeVariety che, a seconda del valore
 * in ingresso, istanzia una delle seguenti tipologia di carta:
 *
 * "Sfumature diverse - Riga" : "Righe senza sfumature ripetute", Punti Favore: 5
 * "Sfumature diverse - Colonna" : "Colonne senza sfumature ripetute", Punti Favore: 4
 *
 * @author Simone Lanzillotta
 */



public class SpecificShadeVariety extends VarietyAlgorithm implements StrategyAlgorithm {

    private int maxRow = 0;
    private int maxCol = 0;

    /**
     * Costruttore della classe che passa in ingresso il numero di righe e di colonne
     *
     * @param maxRow valore massimo delle righe della carta Side
     * @param maxCol valore massimo delle colonne della carta Side
     */

    public SpecificShadeVariety(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
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

        int[] contArray = new int[6];
        int cont = 0;
        int tempNumber;
        Dice die;

        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {

                if(maxRow>maxCol) die = player.showSelectedCell(j, i).showDice();
                else die = player.showSelectedCell(i, j).showDice();

                if (die != null) {
                    tempNumber = die.getNumber();
                    switch (tempNumber) {
                        case 1:
                            contArray[0]++;
                            break;
                        case 2:
                            contArray[1]++;
                            break;
                        case 3:
                            contArray[2]++;
                            break;
                        case 4:
                            contArray[3]++;
                            break;
                        case 5:
                            contArray[4]++;
                            break;
                        case 6:
                            contArray[5]++;
                            break;
                    }
                    cont = cont + checkArray(contArray,maxRow,maxCol,j);
                }
            }
        }
        if(maxRow>maxCol) return 4*cont;
        else return 5*cont;
    }
}
