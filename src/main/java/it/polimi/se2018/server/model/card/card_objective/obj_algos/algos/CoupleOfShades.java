package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/**
 * Rappresenta l'incapsulamento dell'algoritmo relativo alle carte della tipologia CoupleOfShades che, a seconda della coppia di valori
 * in ingresso, istanzia una delle seguenti tipologia di carta:
 *
 *  - Sfumature Chiare, Set di 1 e 2 ovunque, Punti Favore 2
 *  - Sfumature Medie, Set di 3 e 4 ovunque, Punti Favore 2
 *  - Sfumature Scure, Set di 5 e 6 ovunque, Punti Favore 2
 *
 * @author Simone Lanzillotta
 */



public class CoupleOfShades implements StrategyAlgorithm{


    private int firstShade;
    private int secondShade;


    /**
     * Costruttore della classe che, a seconda della coppia di valori in ingresso (ovvero le coppie di sfumature), istanzia una tra le
     * tre tipologie di carte sopra citate
     *
     * @param firstShade valore corrispondente alla prima sfumatura su cui si basa il calcolo dei punti favore
     * @param secondShade valore corrispondente alla seconda sfumatura su cui si basa il calcolo dei punti favore
     */

    public CoupleOfShades(int firstShade, int secondShade){
        this.firstShade = firstShade;
        this.secondShade = secondShade;
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

        if(contOne<contTwo && contOne!=0) return favours*contOne;
        else if(contTwo<contOne && contTwo!=0) return favours*contTwo;
        else if(contOne==contTwo) return favours*contOne;
        else return 0;
    }
}
