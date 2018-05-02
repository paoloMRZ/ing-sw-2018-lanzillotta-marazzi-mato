package it.polimi.se2018.server.model;

import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Side;


/*
    Ho provissoriamente inserito nella calsse player dei metodi che mi permettano di estrarre le informazioni sia
    sulle celle del Side delgiocatore sia sugli eventuali dadi che vi sono posizionati.

    L'ho fatto per poter scrivere gli algoritmi 1,2, ma essendo che si tartta di scelte implementative comuni, ho
    solo scritto due algoritmi che ne fanno uso. Gli altri dovrò completarli non appena definiamo bene cosa va
    dentro Player

*/

public class Player {
    private Side choisePattern;
    private Objective obj;
    private int favours;

    public Player(Side choisePattern, Objective obj, int favours) {
        this.choisePattern = choisePattern;
        this.obj = obj;
        this.favours = favours;
    }

    public String getColorCell(int i, int j) throws Exception{
        return choisePattern.getColor(i,j);
    }

    public int getNumberCell(int i, int j)  throws Exception{
        return choisePattern.getNumber(i,j);
    }


    //Ritorna il colore del dado posizionato nella cella (i,j), se esiste
    public String getDiceColor(int i, int j) throws Exception{
        return choisePattern.getCellsDiceColorInformation(i,j);
    }

    //Ritorna il numero del dado posizionato nella cella (i,j), se esiste
    public int getDiceNumber(int i, int j) throws Exception{
        return choisePattern.getCellsDiceNumberInformation(i,j);
    }
}
