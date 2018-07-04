package it.polimi.se2018.client.cli.game;


import it.polimi.se2018.client.cli.game.info.CellInfo;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.objective.ObjectiveCard;
import it.polimi.se2018.client.cli.game.schema.SideCard;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe raccoglie tutti gli elementi necessari per costruire le varie schermate di gioco visualizzavili durante una partita.
 * Le informazioni contenute vengono aggiornate nelle varie fasi della partita sulla base dei messaggi inviati dal server.
 *
 * La classe è singleton, infatti lo stesso modello di gioco deve essere condiviso tra lo stato attivo ed il controllore degli stati (Cli),
 * ma soprattutto il client può possedere un solo modello di gioco perchè può giocare una sola partita alla volta.
 */

public class Game {

    private static final String EMPTY = "empty";

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


    /**
     * Costrutture della classe.
     * Vengono inizilizzati con i valori di default tutti i parametri contenuti nell classe.
     *
     * Le info di defaut non hanno "significato" dal punto di vista del gioco, ma verrarro sostituite da informazioni
     * utili quando il server manda i messaggi di inizializzazione della partita.
     */
    private Game(){

        ObjectiveCard objectiveCard = new ObjectiveCard(EMPTY, EMPTY,0,false);
        privateObjective = objectiveCard;

        publicObjective = new ArrayList<>();
        publicObjective.add(objectiveCard);
        publicObjective.add(objectiveCard);
        publicObjective.add(objectiveCard);


        UtensilCard utensilCard = new UtensilCard(EMPTY, EMPTY, 1);
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


        SideCard emptyCard = new SideCard(EMPTY, 1, emptyCells);

        myCard = emptyCard;

        enemyCards = new ArrayList<>();
        enemyCards.add(emptyCard);
        enemyCards.add(emptyCard);
        enemyCards.add(emptyCard);


        myNickname = EMPTY;
        enemyNicknames = new ArrayList<>();
        enemyNicknames.add(myNickname);
        enemyNicknames.add(myNickname);
        enemyNicknames.add(myNickname);

        favours = 0;
        turnOf = EMPTY;

        secondPhaseDie = new DieInfo(Ansi.Color.WHITE, 0);
        secondPhaseColor = Ansi.Color.WHITE;
        secondPhaseValue = 0;
    }

    /**
     * Metodo di costruzione della classe.
     * Permette di implementare il patter singleton.
     * @return istanza della classe.
     */
    public static Game factoryGame(){
        if(instance == null)
            instance = new Game();

        return instance;
    }

    /**
     * Settaggio delle informazioni relative alle carte finestra scelte dai nostri avversari.
     * @param enemyCards lista di carte finestra.
     */

    public void setEnemyCards(List<SideCard> enemyCards) {
        if(enemyCards != null)
            this.enemyCards = new ArrayList<>(enemyCards);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta la lista che contiene i nicknames dei nostri avversari.
     * @param enemyNicknames lista contenente i nickname da salvare.
     */
    public void setEnemyNicknames(List<String> enemyNicknames) {
        if(enemyNicknames != null)
            this.enemyNicknames = new ArrayList<>(enemyNicknames);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene i segnalini favore a disposizione del giocatore.
     * @param favours numero di segnalini favore da memorizzare.
     */
    public void setFavours(int favours) {
        if(favours >= 0)
            this.favours = favours;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene la carta finestra scelta dal giocatore.
     * @param myCard oggetto che contiene la descrzione della carta scelta.
     */
    public void setMyCard(SideCard myCard) {
        if(myCard != null)
            this.myCard = myCard;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene il nickanme del giocatore.
     * @param myNickname nickname del giocatore.
     */
    public void setMyNickname(String myNickname) {
        if(myNickname != null)
            this.myNickname = myNickname;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene la descrizione dell'obiettivo privato.
     * @param privateObjective oggetto che contiene la descrizione dell'obiettivo privato.
     */
    public void setPrivateObjective(ObjectiveCard privateObjective) {
        if(privateObjective != null)
            this.privateObjective = privateObjective;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene la descrizione degli obiettivi pubblici.
     * @param publicObjective lista degli obiettivi pubblici
     */
    public void setPublicObjective(List<ObjectiveCard> publicObjective) {
        if(publicObjective != null)
            this.publicObjective = new ArrayList<>(publicObjective);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene la descrizione della riserva.
     * @param reserve lista di informazioni dei dadi nella riserva.
     */
    public void setReserve(List<DieInfo> reserve) {
        if(reserve != null)
            this.reserve = new ArrayList<>(reserve);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene la descrizione della roundgrid.
     * Ogni round della roundgrid è identificato da una lista di informazioni sui dadi contenuti in quel round.
     * @param roundgrid descrizione della roundgrid.
     */
    public void setRoundgrid(List<ArrayList<DieInfo>> roundgrid) {
        if(roundgrid != null)
            this.roundgrid = new ArrayList<>(roundgrid);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il parametro che contiene il nickname del giocatore che sta giocando il turno.
     * @param turnOf nickname del giocatore che sta giocando.
     */
    public void setTurnOf(String turnOf) {
        if(turnOf != null)
            this.turnOf = turnOf;
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene le descrizioni delle carte utensili utilizzabili nella partita.
     * @param utensils lista contenente le informzioni di descrizione delle carte utensili.
     */
    public void setUtensils(List<UtensilCard> utensils) {
        if(utensils != null)
            this.utensils = new ArrayList<>(utensils);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo aggiorna il costo di una carta utensile.
     * @param indexOfUtensil indice dell'utensile nella collezione degli utesili.
     * @param prize nuovo prezzo della carta.
     */
    public void setUtensilPrize(int indexOfUtensil, int prize){
        this.utensils.get(indexOfUtensil).setPrize(prize);
    }

    /**
     * Il metodo restituisce il numero della carta utensile dato l'indice della sua posizione all'interno della colleizione degli utensili.
     * @param indexOfUtensil indice della carta di cui si vuole ottenere il numero.
     * @return numero della carta.
     */
    public int getUtensilNumberbyIndex(int indexOfUtensil){
        return utensils.get(indexOfUtensil).getNumber();
    }

    /**
     * Il metodo setta il campo che contiene le informazioni su i dadi posizionati sulle carte degli avversari.
     * Per ogni carta viene salvata una lista contenete le informazioni dei dadi posizionati su quella carta.
     *
     * @param diceOnEnemysCards informazioni da memorizzare.
     */
    public void setDiceOnEnemysCards(List<ArrayList<DieInfo>> diceOnEnemysCards) {
        if(diceOnEnemysCards != null)
            this.diceOnEnemysCards = new ArrayList<>(diceOnEnemysCards);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il etodo setta il campo che contiene le informazioni sui dadi posizionti sulla carta del giocatore.
     * @param diceOnMyCard lista contenete le informazioni sui dadi.
     */
    public void setDiceOnMyCard(List<DieInfo> diceOnMyCard) {
        if(diceOnMyCard != null)
            this.diceOnMyCard = new ArrayList<>(diceOnMyCard);
        else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo setta il campo che contiene le informazioni sul dado selezionato nella prima fase di giocao degli utensili 6 e 11.
     * Questa informazione viene utilizzata per giocare la seconda fase.
     * @param dieInfo informazioni sul dado selezionato.
     */
    public void setSecondPhaseDie(DieInfo dieInfo){
        if(dieInfo != null)
            this.secondPhaseDie = dieInfo; //DieInfo è immutabile.
        else
            throw new InvalidParameterException();
    }


    /**
     * Il metodo setta il campo che contiene il colore estratto dal server (e inviato al client) per giocare la seconda fase
     * della carta utensile 11.
     * @param color colore in codifica ansi.
     */
    public void setSecondPhaseColor(Ansi.Color color){
        this.secondPhaseColor = color;
    }

    /**
     * Il metodo setta il campo che contiene il valore estratto dal server (e inviato al client) per giocare la seconda fase
     * dell'utensile 6.
     * @param value valore da memorizzare.
     */
    public void setSecondPhaseValue(int value){
        this.secondPhaseValue = value;
    }

    /**
     * Il metodo restituisce i segnalini favore a disposizione del giocatore.
     * @return segnalini favore del giocatore.
     */
    public int getFavours() {
        return favours;
    }

    /**
     * Il metodo restituisce il nickname del giocatore.
     * @return nickname del giocatore.
     */
    public String getMyNickname() {
        return myNickname;
    }

    /**
     * Il metodo restituisce il nickname del giocatore che sta giocando il turno.
     * @return nickname del giocatore attivo.
     */
    public String getTurnOf() {
        return turnOf;
    }

    /**
     * Il metodo restituisce le informazioni riguardo la carta finestra del giocatore.
     * @return oggetto contenente le informazioni sulla carta finestra.
     */
    public SideCard getMyCard() {
        return myCard;
    }

    /**
     * Il metodo restituisce i nickname degli avversari.
     * @return lista contenente i nickname degli avversari.
     */
    public List<String> getEnemyNicknames() {
        return new ArrayList<>(enemyNicknames);
    }

    /**
     *  Il metodo restituisce la descrizione della riserva.
     * @return lista contenete le informazioni sui dadi che compongono la riserva.
     */
    public List<DieInfo> getReserve() {
        return new ArrayList<>(reserve);
    }

    /**
     * Il metodo restituisce tutte le informazioni sugli obiettivi associati al giocatore.
     * @return lista composta dagli oggetti che descrivono gli obiettivi del giocatore.
     */
    public List<ObjectiveCard> getAllObjectives(){
        ArrayList<ObjectiveCard> all = new ArrayList<>(this.publicObjective);
        all.add(this.privateObjective);
        return all;
    }

    /**
     * Il metodo restituisce le informazioni sulle carte scelte dagli avversari.
     * @return lista contenete gli oggetti che descrivono le carte degli avversari.
     */
    public List<SideCard> getEnemyCards() {
        return new ArrayList<>(enemyCards);
    }

    /**
     * Il metodo restituisce le descrizioni delle carte utensili utilizzabili nella partita.
     * @return lista composta da oggetti che descrivono le carte utensili.
     */
    public List<UtensilCard> getUtensils() {
        return new ArrayList<>(utensils);
    }

    /**
     * Il metodo restituisce le informazioni che descrivono la roundgrid.
     * @return lista di round della roundgrid. Ogni round è una lista che descrive i dadi contenuti nel round.
     */
    public List<ArrayList<DieInfo>> getRoundgrid() {
        return new ArrayList<>(roundgrid);
    }

    /**
     * Il metodo restituisce le informazioni sui dadi posizionati sulla carta finestra del giocatore.
     * @return lista contenente le descrizioni dei dadi posizionati sulla carta finestra del giocatore.
     */
    public List<DieInfo> getDiceOnMyCard() {
        return new ArrayList<>(diceOnMyCard);
    }

    /**
     * Il metodo restituisce le informazioni sui dadi posizionati sulle carte finestra degli avversari.
     * @return lista contenente lista di descrizione di dadi. Ogni lista di dadi è associata ad una carta.
     */
    public List<ArrayList<DieInfo>> getDiceOnEnemysCards() {
        return new ArrayList<>(diceOnEnemysCards);
    }

    /**
     * Il metodo restituisce la descrizoine del dado scelto dal giocatore per giocare la seconda fase della carta utensile 6 o 11.
     * @return descrizione del dado.
     */
    public DieInfo getSecondPhaseDie() {
        return secondPhaseDie; //Die info è immutabile.
    }

    /**
     * Il metodo restituisce il colore estratto dal server per giocare la seconda fase della carta 11.
     * @return colore estratto dal server.
     */
    public Ansi.Color getSecondPhaseColor() {
        return secondPhaseColor;
    }

    /**
     * Il metodo restituisce il valore estratto dal server per giocare la seconda fase della carta 11.
     * @return valore estratto dal server.
     */
    public int getSecondPhaseValue() {
        return secondPhaseValue;
    }

    /**
     * Il metodo dato il numero di un utensile restituisce l'indice della carta nella collezione delle carte utensili.
     * @param number numero della carta.
     * @return indice della carta nella collezione.
     */
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
