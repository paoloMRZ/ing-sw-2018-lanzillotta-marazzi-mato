package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;

/*
    Lista carte Ability raggruppate

    Algoritmo5:
    Nome: "Sfumature Chiare"
    Effetto: "Set di 1 & 2 ovunque"
    Punti Favore: 2

    Algoritmo6:
    Nome: "Sfumature Medie"
    Effetto: "Set di 3 & 4 ovunque"
    Punti Favore: 2

    Algoritmo7:
    Nome: "Sfumature Scure"
    Effetto: "Set di 5 & 6 ovunque"
    Punti Favore: 2

*/

public class CoupleOfShades implements StrategyAlgorithm{


    private int firstShade;
    private int secondShade;


    public CoupleOfShades(int firstShade, int secondShade){
        this.firstShade = firstShade;
        this.secondShade = secondShade;
    }

    @Override
    public int use(Player player) throws Exception {
        int favours = 2;
        int contOne=0;
        int contTwo=0;
        int tempNumber;

        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++) {
                Dice die = player.showSelectedCell(i,j).showDice();
                if (die != null) {
                    tempNumber = die.getNumber();

                    if(tempNumber==firstShade) contOne++;
                    if(tempNumber==secondShade) contTwo++;

                }
            }
        }

        if(contOne>contTwo && contTwo!=0) return favours*contTwo;
        else if(contTwo>contOne && contOne!=0) return favours*contOne;
        else if(contOne==contTwo) return favours*contOne;
        else return 0;
    }
}
