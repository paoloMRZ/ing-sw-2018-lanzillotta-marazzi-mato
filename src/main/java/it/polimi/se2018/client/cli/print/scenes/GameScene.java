package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.*;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class GameScene {

    private static final int CARD_SCHEMA1_ROW = 12; //Posizione per quando si printano gli obiettivi.
    private static final int CARD_SCHEMA1_COL = 56;

    private static final int CARD_SCHEMA2_ROW = 12; //Posizione per quando si printano gli utensili.
    private static final int CARD_SCHEMA2_COL = 70;

    private static final int CARD_OBJECTIVE1_ROW = 12;
    private static final int CARD_OBJECTIVE1_COL = 2;

    private static final int CARD_OBJECTIVE2_ROW = 19;
    private static final int CARD_OBJECTIVE2_COL = 2;

    private static final int CARD_OBJECTIVE3_ROW = 26;
    private static final int CARD_OBJECTIVE3_COL = 2;

    private static final int CARD_OBJECTIVE4_ROW = 33;
    private static final int CARD_OBJECTIVE4_COL = 2;

    private static final int CARD_UTENSIL1_ROW = 12;
    private static final int CARD_UTENSIL1_COL = 2;

    private static final int CARD_UTENSIL2_ROW = 12;
    private static final int CARD_UTENSIL2_COL = 35;

    private static final int CARD_UTENSIL3_ROW = 27;
    private static final int CARD_UTENSIL3_COL = 2;

    private static final int RESERVE_ROW = 4;
    private static final int RESERVE_COL = 3;

    private static final int ROUNDGRID_SMALL_ROW = 4;
    private static final int ROUNDGRID_SMALL_COL = 70;


    private static final int MESSAGES_ROW = 32;
    private static final int MESSAGES_COL = 56;
    private static final int MESSAGES_HIGH = 6;
    private static final int MESSAGES_LEN = 70;

    private static final int INFO_ROW = 2;
    private static final int INFO_COL = 3;

    private static final String TEXT_TURN = "Turno di ";
    private static final String TEXT_FAVOURS = "Segnalini favore disponibili: ";


    private static final int MENU_ROW = 13;
    private static final int MENU1_COL = 90; //Posizione del menu quando sono visualizzati gli obiettivi.
    private static final int MENU2_COL = 103; //Posizione del menu quando sono visualizzati gli utensili.

    private static final String TEXT0_MENU = "------------------ MENU -------------";
    private static final String TEXT1_MENU = "[1] Visualizza carte utensili.";
    private static final String TEXT2_MENU = "[2] Visualizza carte obiettivo.";
    private static final String TEXT3_MENU = "[3] Visualizza gli avversari.";
    private static final String TEXT4_MENU = "[4] Visualizza la roundgrid completa.";
    private static final String TEXT5_MENU = "[5] Piazza dado.";
    private static final String TEXT6_MENU = "[6] Gioca carta utensile.";
    private static final String TEXT7_MENU = "[7] Fine turno.";
    private static final String TEXT8_MENU = " >>";


    private static final String TEXT1_SELECTDICE = "Dado della riserva ('q' per annullare): ";
    private static final String TEXT2_SELECTDICE = "Seleziona riga ('q' per annullare): ";
    private static final String TEXT3_SELECTDICE = "Seleziona colonna ('q' per annullare): ";

    private static final String TEXT_SELECTUTENSIL = "Numero carta ('q' per annullare): ";

    private static final String MESSAGE_TEXT = "MESSAGGI";
    private static final String MESSAGE_HEAD = " [*] ";


    private SideCard card;
    private ArrayList<DieInfo> reserve;
    private ArrayList<DieInfo> diceOnCard;
    private ArrayList<ArrayList<DieInfo>> roundGrid;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ArrayList<UtensilCard> utensilCards;

    private ArrayList<String> messages;

    private int favours;
    private int menu;
    private String turnOf;

    private boolean printObjective;

    public GameScene(SideCard card, List<DieInfo> diceOnCard ,List<DieInfo> reserve, List<ArrayList<DieInfo>> roundGrid, List<ObjectiveCard> objectiveCards, List<UtensilCard> utensilCards, int favours){
        if(card != null && reserve != null && roundGrid!=null && objectiveCards != null && utensilCards != null && favours>=0) {

            this.card = new SideCard(card.getName(),card.getFavours(), new ArrayList<>(card.getCells()));

            this.reserve = new ArrayList<>();
            this.reserve.addAll(reserve);

            this.diceOnCard = new ArrayList<>(diceOnCard);

            this.roundGrid = new ArrayList<>();
            this.roundGrid.addAll(roundGrid);

            messages = new ArrayList<>();

            this.objectiveCards = new ArrayList<>(objectiveCards);
            this.utensilCards = new ArrayList<>(utensilCards);

            this.printObjective = true;

            this.menu = 0; //Il menù di default è il menù di quando non è il mio turno.
            this.favours = favours;
        }else
            throw new InvalidParameterException();
    }

    private void printObjectiveCard(){

        ObjectivePrinter.printObjective(CARD_OBJECTIVE1_ROW, CARD_OBJECTIVE1_COL, objectiveCards.get(0));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE2_ROW, CARD_OBJECTIVE2_COL, objectiveCards.get(1));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE3_ROW, CARD_OBJECTIVE3_COL, objectiveCards.get(2));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE4_ROW, CARD_OBJECTIVE4_COL, objectiveCards.get(3));
    }

    private void printUtensil(){
        UtensilPrinter.printUtensil(CARD_UTENSIL1_ROW,CARD_UTENSIL1_COL,utensilCards.get(0));
        UtensilPrinter.printUtensil(CARD_UTENSIL2_ROW,CARD_UTENSIL2_COL,utensilCards.get(1));
        UtensilPrinter.printUtensil(CARD_UTENSIL3_ROW,CARD_UTENSIL3_COL,utensilCards.get(2));
    }

    private void printMyTurnMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;


        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT1_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT2_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT3_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT4_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT5_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT6_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT7_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT8_MENU));
    }

    private void printIsNotMyTurnMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;


        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT1_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT2_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT3_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT4_MENU));
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT8_MENU));
    }

    private void printSelectReserveDieMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT1_SELECTDICE));

    }

    private void printSelectRowMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT2_SELECTDICE));

    }

    private void printSelectColMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT3_SELECTDICE));

    }

    private void printSelectUtensilMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        System.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        System.out.print(ansi().cursor(row,col).a(TEXT_SELECTUTENSIL));

    }

    private void printTurnAndFavours(){
        System.out.print(ansi().cursor(INFO_ROW, INFO_COL).bold().a( " | " + TEXT_TURN + turnOf + " | " + TEXT_FAVOURS + favours).boldOff());
    }

    private void printMessages(){

        System.out.print(ansi().cursor(MESSAGES_ROW,MESSAGES_COL+1).bold().a(MESSAGE_TEXT).boldOff());

        //Stampo i margini verticali.
        MarginPrinter.printColMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_HIGH);
        MarginPrinter.printColMargin(MESSAGES_ROW+1,MESSAGES_COL+MESSAGES_LEN-1 ,MESSAGES_HIGH);

        //Stampo i margini orizzontali.
        MarginPrinter.printRowMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_LEN);
        MarginPrinter.printRowMargin(MESSAGES_ROW+MESSAGES_HIGH+1, MESSAGES_COL, MESSAGES_LEN);

        //Stampo i messaggi.
        int offset = 2;

        for(String message : messages){
            System.out.print(ansi().cursor(MESSAGES_ROW+offset, MESSAGES_COL+1).a(MESSAGE_HEAD + message));
            offset++;
        }

    }

    private void printMenu(){

        switch (this.menu){
            case 0: printIsNotMyTurnMenu(); break;

            case 1: printMyTurnMenu(); break;

            case 2: printSelectReserveDieMenu(); break;

            case 3: printSelectRowMenu(); break;

            case 4: printSelectColMenu(); break;

            case 5: printSelectUtensilMenu(); break;
        }

    }


    public void printScene(){
        System.out.print(ansi().eraseScreen());

        ReservePrinter.printReserve(RESERVE_ROW, RESERVE_COL, reserve);
        RoundGrindPrinter.printSmallRoundGrid(ROUNDGRID_SMALL_ROW, ROUNDGRID_SMALL_COL,roundGrid);
        printTurnAndFavours();

        if(this.printObjective){
            SidePrinter.printSide(CARD_SCHEMA1_ROW, CARD_SCHEMA1_COL,card, diceOnCard);
            printObjectiveCard();
        }else{
            SidePrinter.printSide(CARD_SCHEMA2_ROW, CARD_SCHEMA2_COL,card, diceOnCard);
            printUtensil();
        }

        printMessages();
        printMenu();
    }


    public void updateReserve(List<DieInfo> reserve){
        if(reserve != null) {
            this.reserve = new ArrayList<>();
            this.reserve.addAll(reserve);
        }else
            throw new InvalidParameterException();
    }

    public void updateRoundGrid(List<ArrayList<DieInfo>> roundGrid){
        if(roundGrid != null) {
            this.roundGrid = new ArrayList<>();
            this.roundGrid.addAll(roundGrid);
        }else
            throw new InvalidParameterException();
    }

    public void updateCard(List<DieInfo> diceOnCard){
        if(diceOnCard != null){
            this.diceOnCard = new ArrayList<>();
            this.diceOnCard.addAll(diceOnCard);
        }else
            throw new InvalidParameterException();
    }

    public void setShowUtensils(){
        this.printObjective = false;
    }

    public void setShowObjective(){
        this.printObjective = true;
    }

    public void updateFavours(int favours){
        if(favours >= 0){
            this.favours = favours;
        }else
            throw new InvalidParameterException();
    }

    public void updateTurn(String turnOf){
        if(turnOf != null){
            this.turnOf = turnOf;
        }else
            throw new InvalidParameterException();
    }

    public void setIsNotMyturnMenu(){
        this.menu = 0;
    }

    public void setMyTurnMenu(){
        this.menu = 1;
    }

    public void setSelectDieMenu(){
        this.menu = 2;
    }

    public void setSelectRowMenu(){
        this.menu = 3;
    }

    public void setSelectColMenu(){
        this.menu = 4;
    }

    public void setSelectUtensilMenu(){
        this.menu = 5;
    }

    public void addMessage(String message){
        if(messages.size() < 5) //La casella contiene al massimo sette messaggi.
            messages.add(message);
        else { //Se la casella è piena la svuoto e aggiungo il messaggio.
            messages.clear();
            messages.add(message);
        }
    }
}