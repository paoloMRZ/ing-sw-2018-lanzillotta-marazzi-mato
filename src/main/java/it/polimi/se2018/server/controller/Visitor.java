package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.model.card.card_utensils.*;

/**
 * Interfaccia che implementa il pattern Visitor.
 * Il grande numero di metodi è dovuto alla numerosa presenza di carte utensili differenti che
 * non hanno solo il compito di essere un effetto ma di incapsulare il loro contenuto che è il vero effetto
 * e può riscritto come si vuole.
 */
public interface Visitor {

    /**
     * Prototipo del metodo che si occupa della visita
     * @param itemPinza
     * @param m
     */
    public void visit(PinzaSgrossatrice itemPinza,Activate m);

    public void visit(PennelloPerEglomise itemPennelloEglo, Activate m);

    public void visit(Lathekin itemLathekin, Activate m);

    public void visit(AlesatorePerLaminaDiRame itemAlesatore,Activate m);

    public void visit(Martelletto itemMartelletto, Activate m);

    public void visit(RigaInSughero itemRiga, Activate m);

    public void visit(TaglierinaCircolare itemT, Activate m);


    public void visit(TaglierinaManuale itemTM, Activate m);

    public void visit(TamponeDiamantato itemTD, Activate m);

    public void visit(TenagliaARotelle itemTe, Activate m);

    void visit(PennelloPerPastaSalda pennelloPerPastaSalda, Activate m);

    void visit(DiluentePerPastaSalda diluentePerPastaSalda, Activate m);
}
