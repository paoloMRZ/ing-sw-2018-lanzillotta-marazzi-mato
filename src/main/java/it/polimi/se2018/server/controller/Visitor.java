package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.model.card.card_utensils.*;

public interface Visitor {

    public void visit(Utensils ItemUtensil);

    public void visit(PinzaSgrossatrice ItemPinza);

    public void visit(PennelloPerEglomise ItemPennelloEglo);

    public void visit(Lathekin ItemLathekin);


    public void visit(PennelloPerPastaSalda ItemPennelloPasta);

    public void visit(AlesatorePerLaminaDiRame ItemAlesatore);

    public void visit(Martelletto ItemMartelletto);


    public void visit(DiluentePerPastaSalda ItemDiluente);

    public void visit(RigaInSughero ItemRiga);

    void visit(TaglierinaCircolare itemT);


    public void visit(TaglierinaManuale ItemTM);

    public void visit(TamponeDiamantato ItemTD);

    public void visit(TenagliaARotelle ItemTe);

}
