package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;



/*
    Algoritmo1:
    Nome: "Colori diversi - Riga"
    Effetto: "Righe senza colori ripetuti"
    Punti Favore: 6

    Algoritmo2:
    Nome: "Colori diversi - Colonna"
    Effetto: "Colonne senza colori ripetuti"
    Punti Favore: 5

     Il costruttore genera quindi l'algoritmo4 se maxRow > maxCol, altrimenti crea l'algoritmo3

*/


import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;

public class SpecificColorVariety extends VarietyAlgorithm implements StrategyAlgorithm {

    private int maxRow = 0;
    private int maxCol = 0;

    //Costruttore
    public SpecificColorVariety(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    @Override
    public int use(Player player) throws Exception {

        int[] contArray = new int[5];
        int cont = 0;
        String tempColor;
        Dice die;

        for (int i=0; i<maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                if (maxRow > maxCol) die = player.showSelectedCell(j, i).showDice();
                else die = player.showSelectedCell(i, j).showDice();

                if (die != null) {
                    tempColor = die.getColor();
                    switch (tempColor) {
                        case "red":
                            contArray[0]++;
                            break;
                        case "yellow":
                            contArray[1]++;
                            break;
                        case "green":
                            contArray[2]++;
                            break;
                        case "purple":
                            contArray[3]++;
                            break;
                        case "blue":
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