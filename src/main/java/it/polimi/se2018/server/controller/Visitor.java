package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.model.card.card_utensils.*;

public interface Visitor {

    public void visit(Utensils itemUtensil, Activate m);

    public void visit(PinzaSgrossatrice itemPinza,MoreThanSimple m);

    public void visit(PennelloPerEglomise itemPennelloEglo, ToolCard2 m);

    public void visit(Lathekin itemLathekin, ToolCard4 m);


    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6 m);

    public void visit(PennelloPerPastaSalda itemPennelloPasta,ToolCard6Bis m);

    public void visit(AlesatorePerLaminaDiRame itemAlesatore,ToolCard3 m);

    public void visit(Martelletto itemMartelletto, ToolCard7 m);


    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11 m);

    public void visit(DiluentePerPastaSalda itemDiluente,ToolCard11Bis m);


    public void visit(RigaInSughero itemRiga, ToolCard9 m);

    public void visit(TaglierinaCircolare itemT, ToolCard5 m);


    public void visit(TaglierinaManuale itemTM, ToolCard12 m);

    public void visit(TamponeDiamantato itemTD, ToolCard10 m);

    public void visit(TenagliaARotelle itemTe, ToolCard8 m);

}
