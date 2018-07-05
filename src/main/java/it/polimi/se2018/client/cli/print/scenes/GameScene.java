package it.polimi.se2018.client.cli.print.scenes;


import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.components.*;
import it.polimi.se2018.client.cli.print.utils.MarginPrinter;
import org.fusesource.jansi.AnsiConsole;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce la stampa della schermata di principale gioco.
 *
 * @author Marazzi Paolo
 */

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

    private static final String TEXT_SELECTUTENSIL = "Num. carta ('q' per annullare): ";

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

    /**
     * Costruttore della classe.
     *
     * @param card carta finestra del giocatore.
     * @param diceOnCard lista dei dadi sulla carta finestra.
     * @param reserve lista dei dadi nella riserva.
     * @param roundGrid lista dei round.
     * @param objectiveCards lista delle carte obiettivo.
     * @param utensilCards lista delle carte utensile.
     * @param favours segnalini favore disponibili al giocatore.
     */
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

    /**
     * Il metodo stampa a schermo le carte obiettivo.
     */
    private void printObjectiveCard(){

        ObjectivePrinter.printObjective(CARD_OBJECTIVE1_ROW, CARD_OBJECTIVE1_COL, objectiveCards.get(0));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE2_ROW, CARD_OBJECTIVE2_COL, objectiveCards.get(1));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE3_ROW, CARD_OBJECTIVE3_COL, objectiveCards.get(2));
        ObjectivePrinter.printObjective(CARD_OBJECTIVE4_ROW, CARD_OBJECTIVE4_COL, objectiveCards.get(3));
    }

    /**
     * Il metodo stampa a schermo le carte utensile.
     */
    private void printUtensil(){
        UtensilPrinter.printUtensil(CARD_UTENSIL1_ROW,CARD_UTENSIL1_COL,utensilCards.get(0));
        UtensilPrinter.printUtensil(CARD_UTENSIL2_ROW,CARD_UTENSIL2_COL,utensilCards.get(1));
        UtensilPrinter.printUtensil(CARD_UTENSIL3_ROW,CARD_UTENSIL3_COL,utensilCards.get(2));
    }

    /**
     * Il metodo gestisce la stampa del menù visibile durante il nostro turno di gioco.
     * La posizione del menù varia a seconda di quale carte sono mostrate a schermo (obiettivi o utensili).
     */
    private void printMyTurnMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;


        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT1_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT2_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT3_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT4_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT5_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT6_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT7_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT8_MENU));
    }

    /**
     * Il metodo gestisce la stampa del menù visibile durante turno di gioco degli avversari.
     * La posizione del menù varia a seconda di quale carte sono mostrate a schermo (obiettivi o utensili).
     */
    private void printIsNotMyTurnMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;


        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT1_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT2_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT3_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT4_MENU));
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT8_MENU));
    }

    /**
     * Il metodo stampa il menù che permette di selezionare un dado dalla riserva.
     */
    private void printSelectReserveDieMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT1_SELECTDICE));

    }

    /**
     * Il metodo stampa il menù che permette di selezionare una riga della carta finestra.
     */
    private void printSelectRowMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT2_SELECTDICE));

    }

    /**
     * Il metodo stampa il menù che permette di selezionare una colonna.
     */
    private void printSelectColMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT3_SELECTDICE));

    }

    /**
     * Il metodo stampa il menù che permette di selezionare una carta utensile.
     */
    private void printSelectUtensilMenu(){
        int row = MENU_ROW;
        int col;

        if(printObjective)
            col = MENU1_COL;
        else
            col = MENU2_COL;

        AnsiConsole.out.print(ansi().cursor(row,col).bold().a(TEXT0_MENU).boldOff());
        row++;
        AnsiConsole.out.print(ansi().cursor(row,col).a(TEXT_SELECTUTENSIL));

    }

    /**
     * Il metodo stampa a schermo i segnlini favore in possesso del giocatore e i nickname del giocatore che sta giocando il turno.
     */
    private void printTurnAndFavours(){
        AnsiConsole.out.print(ansi().cursor(INFO_ROW, INFO_COL).bold().a( " | " + TEXT_TURN + turnOf + " | " + TEXT_FAVOURS + favours).boldOff());
    }

    /**
     * Il metodo stampa a schermo i messaggi che notificano gli eventi di gioco.
     */
    private void printMessages(){

        AnsiConsole.out.print(ansi().cursor(MESSAGES_ROW,MESSAGES_COL+1).bold().a(MESSAGE_TEXT).boldOff());

        //Stampo i margini verticali.
        MarginPrinter.printColMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_HIGH);
        MarginPrinter.printColMargin(MESSAGES_ROW+1,MESSAGES_COL+MESSAGES_LEN-1 ,MESSAGES_HIGH);

        //Stampo i margini orizzontali.
        MarginPrinter.printRowMargin(MESSAGES_ROW+1, MESSAGES_COL, MESSAGES_LEN);
        MarginPrinter.printRowMargin(MESSAGES_ROW+MESSAGES_HIGH+1, MESSAGES_COL, MESSAGES_LEN);

        //Stampo i messaggi.
        int offset = 2;

        for(String message : messages){
            AnsiConsole.out.print(ansi().cursor(MESSAGES_ROW+offset, MESSAGES_COL+1).a(MESSAGE_HEAD + message));
            offset++;
        }

    }


    /**
     * Il metodo stampa il menù che è stato selezionato tramite gli appositi metodi pubblici.
     */
    private void printMenu(){

        switch (this.menu){
            case 0: printIsNotMyTurnMenu(); break;

            case 1: printMyTurnMenu(); break;

            case 2: printSelectReserveDieMenu(); break;

            case 3: printSelectRowMenu(); break;

            case 4: printSelectColMenu(); break;

            case 5: printSelectUtensilMenu(); break;

            default: //Non esiste nessun'azione per il caso di default.
        }

    }

    /**
     * Il metodo stampa s schermo l'intera scena di gioco.
     */
    public void printScene(){
        AnsiConsole.out.print(ansi().eraseScreen());

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


    /**
     * Il metodo aggiorna la riserva.
     * @param reserve lista di dadi che rappresentano la riserva aggiornata.
     */
    public void updateReserve(List<DieInfo> reserve){
        if(reserve != null) {
            this.reserve = new ArrayList<>();
            this.reserve.addAll(reserve);
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo aggiorna la roundgrid.
     * @param roundGrid lista di round che rappresentano il nuovo stato della roundgrid..
     */
    public void updateRoundGrid(List<ArrayList<DieInfo>> roundGrid){
        if(roundGrid != null) {
            this.roundGrid = new ArrayList<>();
            this.roundGrid.addAll(roundGrid);
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo aggiorna i dadi posizionati sulla carta finestra del giocatore.
     * @param diceOnCard lista dei dadi posizionati sulla carta.
     */
    public void updateCard(List<DieInfo> diceOnCard){
        if(diceOnCard != null){
            this.diceOnCard = new ArrayList<>();
            this.diceOnCard.addAll(diceOnCard);
        }else
            throw new InvalidParameterException();
    }


    /**
     * Il metodo permette di mostrare a schermo le carte utensili la prossima volta che si richiama il metodo 'printScene'
     */
    public void setShowUtensils(){
        this.printObjective = false;
    }

    /**
     * Il metodo permette di mostrare a schermo le carte obiettivo la prossima volta che si richiama il metodo 'printScene'
     */
    public void setShowObjective(){
        this.printObjective = true;
    }


    /**
     * Il metodo aggiona il nickname del giocatore che sta giocando il turno.
     * Per far apparire a schermo la modifica è necessario richiamare la il metodo 'printScene'.
     *
     * @param turnOf nickname del giocatore che sta giocando il turno.
     */
    public void updateTurn(String turnOf){
        if(turnOf != null){
            this.turnOf = turnOf;
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo permette di mostrare a schermo il menù delle azioni fattibili dal giocatore durante il turno di un avversario
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setIsNotMyturnMenu(){
        this.menu = 0;
    }

    /**
     * Il metodo permette di mostrare a schermo il menù delle azioni fattibili dal giocatore durante il proprio turno
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setMyTurnMenu(){
        this.menu = 1;
    }

    /**
     * Il metodo permette di mostrare a schermo il menù che consente di scegliere un dado dalla riserva
     * la prossima volta che si richiama il metodo 'printScene'
     */

    public void setSelectDieMenu(){
        this.menu = 2;
    }

    /**
     * Il metodo permette di mostrare a schermo il menù che consente di selezionare una riga della carta finestra
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setSelectRowMenu(){
        this.menu = 3;
    }


    /**
     * Il metodo permette di mostrare a schermo il menù che consente di selezionare una colonna della carta finestra
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setSelectColMenu(){
        this.menu = 4;
    }


    /**
     * Il metodo permette di mostrare a schermo il menù che consente di selezionare una carta utensile
     * la prossima volta che si richiama il metodo 'printScene'
     */
    public void setSelectUtensilMenu(){
        this.menu = 5;
    }


    /**
     * Il metodo consente di aggiungere un messaggio alla lista dei messaggi che notificano degli eventi.
     * Il nuovo messaggio verrà mostrato all successiva chiamata del metodo 'printScene'.
     *
     * @param message messaggio da aggiungere.
     */
    public void addMessage(String message){
        if(messages.size() < 5) //La casella contiene al massimo sette messaggi.
            messages.add(message);
        else { //Se la casella è piena la svuoto e aggiungo il messaggio.
            messages.clear();
            messages.add(message);
        }
    }
}