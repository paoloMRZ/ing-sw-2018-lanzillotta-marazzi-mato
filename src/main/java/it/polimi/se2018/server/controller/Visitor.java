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

     void visit(PinzaSgrossatrice itemPinza,Activate m);

     void visit(PennelloPerEglomise itemPennelloEglo, Activate m);

     void visit(Lathekin itemLathekin, Activate m);

     void visit(AlesatorePerLaminaDiRame itemAlesatore,Activate m);

     void visit(Martelletto itemMartelletto, Activate m);

     void visit(RigaInSughero itemRiga, Activate m);

     void visit(TaglierinaCircolare itemT, Activate m);


     void visit(TaglierinaManuale itemTM, Activate m);

     void visit(TamponeDiamantato itemTD, Activate m);

     void visit(TenagliaARotelle itemTe, Activate m);

    void visit(PennelloPerPastaSalda pennelloPerPastaSalda, Activate m);

    void visit(DiluentePerPastaSalda diluentePerPastaSalda, Activate m);
}
