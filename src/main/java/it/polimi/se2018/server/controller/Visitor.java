package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.model.card.card_utensils.*;

public interface Visitor {

    void visit(Utensils itemUtensil);

    void visit(PinzaSgrossatrice itemPinza);

    void visit(PennelloPerEglomise itemPennelloEglo);

    void visit(Lathekin itemLathekin);


    void visit(PennelloPerPastaSalda itemPennelloPasta);

    void visit(AlesatorePerLaminaDiRame itemAlesatore);

    void visit(Martelletto itemMartelletto);


    void visit(DiluentePerPastaSalda itemDiluente);

    void visit(RigaInSughero itemRiga);

    void visit(TaglierinaCircolare itemT);


    void visit(TaglierinaManuale itemTM);

    void visit(TamponeDiamantato itemTD);

    void visit(TenagliaARotelle itemTe);

}
