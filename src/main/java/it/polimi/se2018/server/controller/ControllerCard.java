package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_utensils.*;

public class ControllerCard implements Visitor {
    private Table lobby;
    private Controller controller;


    public ControllerCard(Table LOBBY,Controller controller){
        this.lobby=LOBBY;
        this.controller=controller;
    }

    //metodo che controlla se i favori del giocatore sia abbastanza per una certa carta utensile
    public void checkFavour(Player player, Utensils Item){
        //todo
    }

    public void welcome(Activate message){
        //todo
    }
/////////////////////////////////////////////////////////////////////////

    //todo gestire eccezione in alto


    @Override
    public void visit(Utensils ItemUtensil) {
        //todo?NOTHING T O D O puro obbligo di intellij
    }

    @Override
    public void visit(Lathekin ItemLathekin) {
        //todo
    }

    @Override
    public void visit(RigaInSughero ItemRiga) {
        //todo
    }

    @Override
    public void visit(TenagliaARotelle ItemTe) {
        //todo
    }

    @Override
    public void visit(TaglierinaCircolare ItemT) {
        //todo
    }

    @Override
    public void visit(TaglierinaManuale ItemTM) {
        //todo
    }

    @Override
    public void visit(TamponeDiamantato ItemTD) {
        //todo
    }

    @Override
    public void visit(Martelletto ItemMartelletto) {
        //todo
    }

    @Override
    public void visit(PinzaSgrossatrice ItemPinza) {
        //todo
    }

    @Override
    public void visit(DiluentePerPastaSalda ItemDiluente) {
        //todo
    }

    @Override
    public void visit(PennelloPerEglomise ItemPennelloEglo) {
        //todo
    }

    @Override
    public void visit(AlesatorePerLaminaDiRame ItemAlesatore) {
        //todo
    }

    @Override
    public void visit(PennelloPerPastaSalda ItemPennelloPasta) {
        //todo
    }

}
