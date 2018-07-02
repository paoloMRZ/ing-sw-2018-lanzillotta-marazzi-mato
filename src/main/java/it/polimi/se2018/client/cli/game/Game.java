package it.polimi.se2018.client.cli.game;


import it.polimi.se2018.client.cli.game.info.CellInfo;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe è una raccolta degli elementi che vanno a comporre le varie schermate di gioco.
 * I vari elementi quì contenuti vengono aggiornati nelle varie fasi della partita.
 *
 * NB--> La classe è singleton! Infatti lo stesso modello di gioco deve essere condiviso tra lo stato attivo ed il controllore degli stati,
 * ma soprattutto il client può possedere un solo modello di gioco (perchè un client può giocare una sola partita alla volta).
 */

public class Game {

    private static Game instance = null;

    private ArrayList<ObjectiveCard> publicObjective;
    private ObjectiveCard privateObjective;

    private ArrayList<UtensilCard> utensils;

    private ArrayList<DieInfo> reserve;

    private ArrayList<ArrayList<DieInfo>> roundgrid;

    private SideCard myCard;
    private String myNickname;
    private ArrayList<DieInfo> diceOnMyCard;

    private ArrayList<SideCard> enemyCards;
    private ArrayList<String> enemyNicknames;
    private ArrayList<ArrayList<DieInfo>> diceOnEnemysCards;

    private DieInfo secondPhaseDie;

    private Ansi.Color secondPhaseColor;

    private int secondPhaseValue;

    private int favours;

    private String turnOf;


    private Game(){

        //Inizializzo lo stato dellle informazioni stampabili mettendo informazioni di default.
        //Le info di defaut non hanno "significato" dal punto di vista del gioco, ma verrarro sostituite da informazioni
        //utili quando il server manda i messaggi di inizializzazione della partita.

        ObjectiveCard objectiveCard = new ObjectiveCard("empty", "empty",0,false);
        privateObjective = objectiveCard;

        publicObjective = new ArrayList<>();
        publicObjective.add(objectiveCard);
        publicObjective.add(objectiveCard);
        publicObjective.add(objectiveCard);


        UtensilCard utensilCard = new UtensilCard("empty", "empty", 1);
        utensils = new ArrayList<>();
        utensils.add(utensilCard);
        utensils.add(utensilCard);
        utensils.add(utensilCard);

        reserve = new ArrayList<>();
        roundgrid = new ArrayList<>();


        diceOnMyCard = new ArrayList<>();
        diceOnEnemysCards = new ArrayList<>();
        DieInfo noDie = new DieInfo(Ansi.Color.WHITE,0);


        CellInfo cellInfo = new CellInfo(Ansi.Color.WHITE,0);
        ArrayList<CellInfo> emptyCells = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            emptyCells.add(cellInfo);
            diceOnMyCard.add(noDie);
        }

        diceOnEnemysCards.add(diceOnMyCard);
        diceOnEnemysCards.add(diceOnMyCard);
        diceOnEnemysCards.add(diceOnMyCard);


        SideCard emptyCard = new SideCard("empty", 1, emptyCells);

        myCard = emptyCard;

        enemyCards = new ArrayList<>();
        enemyCards.add(emptyCard);
        enemyCards.add(emptyCard);
        enemyCards.add(emptyCard);


        myNickname = "empty";
        enemyNicknames = new ArrayList<>();
        enemyNicknames.add(myNickname);
        enemyNicknames.add(myNickname);
        enemyNicknames.add(myNickname);

        favours = 0;
        turnOf = "empty";

        secondPhaseDie = new DieInfo(Ansi.Color.WHITE, 0);
        secondPhaseColor = Ansi.Color.WHITE;
        secondPhaseValue = 0;
    }

    public static Game factoryGame(){
        if(instance == null)
            instance = new Game();

        return instance;
    }

    public void setEnemyCards(List<SideCard> enemyCards) {
        if(enemyCards != null)
            this.enemyCards = new ArrayList<>(enemyCards);
        else
            throw new InvalidParameterException();
    }

    public void setEnemyNicknames(List<String> enemyNicknames) {
        if(enemyNicknames != null)
            this.enemyNicknames = new ArrayList<>(enemyNicknames);
        else
            throw new InvalidParameterException();
    }

    public void setFavours(int favours) {
        if(favours >= 0)
            this.favours = favours;
        else
            throw new InvalidParameterException();
    }

    public void setMyCard(SideCard myCard) {
        if(myCard != null)
            this.myCard = myCard;
        else
            throw new InvalidParameterException();
    }

    public void setMyNickname(String myNickname) {
        if(myNickname != null)
            this.myNickname = myNickname;
        else
            throw new InvalidParameterException();
    }

    public void setPrivateObjective(ObjectiveCard privateObjective) {
        if(privateObjective != null)
            this.privateObjective = privateObjective;
        else
            throw new InvalidParameterException();
    }

    public void setPublicObjective(List<ObjectiveCard> publicObjective) {
        if(publicObjective != null)
            this.publicObjective = new ArrayList<>(publicObjective);
        else
            throw new InvalidParameterException();
    }

    public void setReserve(List<DieInfo> reserve) {
        if(reserve != null)
            this.reserve = new ArrayList<>(reserve);
        else
            throw new InvalidParameterException();
    }

    public void setRoundgrid(List<ArrayList<DieInfo>> roundgrid) {
        if(roundgrid != null)
            this.roundgrid = new ArrayList<>(roundgrid);
        else
            throw new InvalidParameterException();
    }

    public void setTurnOf(String turnOf) {
        if(turnOf != null)
            this.turnOf = turnOf;
        else
            throw new InvalidParameterException();
    }

    public void setUtensils(List<UtensilCard> utensils) {
        if(utensils != null)
            this.utensils = new ArrayList<>(utensils);
        else
            throw new InvalidParameterException();
    }

    public void setUtensilPrize(int indexOfUtensil, int prize){
        this.utensils.get(indexOfUtensil).setPrize(prize);
    }

    public int getUtensilNumberbyIndex(int indexOfUtensil){
        return utensils.get(indexOfUtensil).getNumber();
    }

    public void setDiceOnEnemysCards(ArrayList<ArrayList<DieInfo>> diceOnEnemysCards) {
        if(diceOnEnemysCards != null)
            this.diceOnEnemysCards = new ArrayList<>(diceOnEnemysCards);
        else
            throw new InvalidParameterException();
    }

    public void setDiceOnMyCard(ArrayList<DieInfo> diceOnMyCard) {
        if(diceOnMyCard != null)
            this.diceOnMyCard = new ArrayList<>(diceOnMyCard);
        else
            throw new InvalidParameterException();
    }

    public void setSecondPhaseDie(DieInfo dieInfo){
        if(dieInfo != null)
            this.secondPhaseDie = dieInfo; //DieInfo è immutabile.
        else
            throw new InvalidParameterException();
    }

    public void setSecondPhaseColor(Ansi.Color color){
        this.secondPhaseColor = color;
    }

    public void setSecondPhaseValue(int value){
        this.secondPhaseValue = value;
    }

    public int getFavours() {
        return favours;
    }

    public String getMyNickname() {
        return myNickname;
    }

    public String getTurnOf() {
        return turnOf;
    }

    public ObjectiveCard getPrivateObjective() {
        return privateObjective;
    }

    public SideCard getMyCard() {
        return myCard;
    }

    public List<String> getEnemyNicknames() {
        return new ArrayList<>(enemyNicknames);
    }

    public List<DieInfo> getReserve() {
        return new ArrayList<>(reserve);
    }

    public List<ObjectiveCard> getPublicObjective() {
        return new ArrayList<>(publicObjective);
    }

    public List<ObjectiveCard> getAllObjectives(){
        ArrayList<ObjectiveCard> all = new ArrayList<>(this.publicObjective);
        all.add(this.privateObjective);
        return all;
    }

    public List<SideCard> getEnemyCards() {
        return new ArrayList<>(enemyCards);
    }

    public List<UtensilCard> getUtensils() {
        return new ArrayList<>(utensils);
    }

    public List<ArrayList<DieInfo>> getRoundgrid() {
        return new ArrayList<>(roundgrid);
    }

    public List<DieInfo> getDiceOnMyCard() {
        return new ArrayList<>(diceOnMyCard);
    }

    public List<ArrayList<DieInfo>> getDiceOnEnemysCards() {
        return new ArrayList<>(diceOnEnemysCards);
    }

    public DieInfo getSecondPhaseDie() {
        return secondPhaseDie; //Die info è immutabile.
    }

    public Ansi.Color getSecondPhaseColor() {
        return secondPhaseColor;
    }

    public int getSecondPhaseValue() {
        return secondPhaseValue;
    }

    public int getUtensilIndexFromNumber(int number){

        if(number >= 1 && number <= 12){
            for(UtensilCard card: utensils){
                if(card.getNumber() == number)
                    return utensils.indexOf(card);
            }

            throw new InvalidParameterException();


        }else
            throw new InvalidParameterException();
    }
}
