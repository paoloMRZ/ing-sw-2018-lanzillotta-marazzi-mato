package it.polimi.se2018.server.model.card.card_objective.obj_algos.algos;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;


/*
    Lista Carta Ability raggruppate

    Algoritm11:
    Nome: "Sfumature Rosse - Privata"
    Effetto: "Somma dei valori su tutti i dadi rossi"
    Punti Favore: #

    Algoritm12:
    Nome: "Sfumature Gialle - Privata"
    Effetto: "Somma dei valori su tutti i dadi gialli"
    Punti Favore: #

    Algoritm14:
    Nome: "Sfumature Blu - Privata"
    Effetto: "Somma dei valori su tutti i dadi blu"
    Punti Favore: #


    Algoritm13:
    Nome: "Sfumature Verdi - Privata"
    Effetto: "Somma dei valori su tutti i dadi verdi"
    Punti Favore: #

    Algoritm15:
    Nome: "Sfumature Viola - Privata"
    Effetto: "Somma dei valori su tutti i dadi viola"
    Punti Favore: #

*/


public class ShadesOfCard implements StrategyAlgorithm{

    private String color;

        //Costruttore
        public ShadesOfCard(String color){
            this.color = color;
        }

        @Override
        public int use(Player player) throws Exception {
            int favours =0;
            String tempColor;
            int tempNumber;
            for (int i=0; i<4; i++) {
                for (int j=0; j<5; j++) {
                    if (player.showSelectedCell(i, j).showDice() != null) {
                        tempColor = player.getDiceColor(i, j);
                        tempNumber = player.getDiceNumber(i, j);
                        if (tempColor == color) favours = favours + tempNumber;
                    }
                }
            }
            return favours;
        }
}
