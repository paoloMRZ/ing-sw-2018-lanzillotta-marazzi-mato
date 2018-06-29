package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.Choice;
import it.polimi.se2018.server.events.responses.PassTurn;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.NotifyModel;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.*;
import it.polimi.se2018.server.model.card.card_utensils.*;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe adibita al controllo della logica di gioco. Si noti che al controller viene demandata la creazione del table e delle relative classi Player
 * (rappresentanti del giocatore nel model) che gli vengono passate.

 *
 * @author Kevin Mato
 * @author Simone Lanzillotta
 */



public class Controller{

    private final Table lobby;
    private final ControllerCard cCard;
    private final ControllerAction cAction;
    private final ControllerPoints cPoints;
    private final ControllerTurn cTurn;
    private final ControllerChat cChat;


    /**
     * Costruttore della classe. Riceve in ingresso la lista di "nickname" fornitagli dal server per creare le istanze della classe Player.
     *
     * @param nameOfPlayers riferimento ad un array di stringhe contenente i nickname dei giocatori partecipanti alla partita
     */

    public Controller(List<String> nameOfPlayers) {
        if (!nameOfPlayers.isEmpty()) {
            NotifyModel notifier= new NotifyModel();
            this.lobby = new Table(setListOfUtensils(), setListOfObjectivePublic(), setListOfPlayers(nameOfPlayers,notifier),notifier);
            this.cCard = new ControllerCard(this);
            this.cAction = new ControllerAction(lobby,this);
            this.cPoints = new ControllerPoints(lobby, this);
            this.cTurn = new ControllerTurn(lobby,this);
            this.cChat= new ControllerChat(this);
        }
        else throw new NullPointerException();
    }

    /**
     *
     * Metodo utilizzato per la creazione delle istanze della classe Player partendo dalla lista dei propri nickname
     * @param nameOfPlayers lista dei nomi dei giocatori della partita
     * @param notifier notificatore del model di cui ogni giocatore deve conoscere la reference per comunicare nel mvc.
     * @return Lista di istanze di giocatori.
     */
    private List<Player> setListOfPlayers(List<String> nameOfPlayers,NotifyModel notifier) {

        if (!nameOfPlayers.isEmpty()) {
            ArrayList<Player> listOfPlayers = new ArrayList<>();
            ArrayList<Objective> objectives = (ArrayList<Objective>) setObjectivePrivate();

            //Creo uno stream di interi da 0 a 23:
            // -> Creo lo stream con IntStream
            // -> Imposto l'intervallo chiuso da cui creare lo stream di interi rangeClosed
            // -> trasformo l'IntStream in una box di Integer (boxed())
            // -> trasformo il box Collector in una collezione (in particolare un ArrayList)

            List<Integer> range = IntStream.rangeClosed(0, 4).boxed().collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(range);

            for (String s : nameOfPlayers) {
                listOfPlayers.add(new Player(randomCard(objectives, range), s,notifier));
            }

            return listOfPlayers;
        }
        else throw new NullPointerException();
    }


    /**
     * Metodo utilizzato per la creazione della collezione di carte Obbiettivo Pubbliche da disporre sul tavolo di gioco
     *
     * @return riferimento alla collezione di carte Obbiettivo Pubbliche
     */

    private List<Objective> setListOfObjectivePublic(){

        ArrayList<Objective> objectives = (ArrayList<Objective>)setObjectivePublic();
        ArrayList<Objective> selection = new ArrayList<>();

        List<Integer> range = IntStream.rangeClosed(0, 9).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        for(int i=0; i<3; i++){
            selection.add(randomCard(objectives, range));
        }

        return selection;
    }


    /**
     * Metodo utilizzato per la creazione della collezione di carte Utensili da disporre sul tavolo di gioco
     *
     * @return riferimento alla collezione di carte Utensili
     */

    private List<Utensils> setListOfUtensils(){

        ArrayList<Utensils> utensils = (ArrayList<Utensils>)setUtensils();
        ArrayList<Utensils> selection = new ArrayList<>();

        List<Integer> range = IntStream.rangeClosed(0, 9).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);

        for(int i=0; i<3; i++){
            selection.add(randomCard(utensils, range));
        }

        return selection;

    }



    /**
     * Metodo che simula la distribuzione randomica delle carte Obbiettivo (Private e Pubbliche) e delle carte Utensili. Il metodo viene chiamato un numero di
     * volte pari al numero di giocagtori partecipanti alla sessione di gioco, associando ad ognuno una crata Obbiettivo (dovrà quindi contentere all'interno un contatore
     * che fa in modo di non assegnare ad un giocatore una carta Obbiettivo gia assegnata in precedenza.
     *
     * @param cards riferimento alla collezione di carte da cui dover estrarre
     * @param randomSet riferimento ad un set di Integer da cui pescare l'indice dell'elemento da pescare
     * @return riferiemtno alla carta Obbiettivo Privata pescata
     */

    private <T> T randomCard(ArrayList<T> cards, List<Integer> randomSet) {

        if (!cards.isEmpty() && !randomSet.isEmpty()) {
            T selection;

            //Genero un numero randomico prendendo come dato la lunghezza dell'array di Interi
            Random random = new Random();
            int casualNum = random.nextInt(randomSet.size() - 1);

            int extract = randomSet.get(casualNum).intValue();
            selection = cards.get(extract);
            randomSet.remove(casualNum);

            return selection;
        }
        else throw new NullPointerException();
    }


    /**
     * Metodo utilizzato per la creazione della collezione di carte Obbietivo Private.
     *
     * @return riferimento alla collezione di carte Obbiettivo Private
     */

    private List<Objective> setObjectivePrivate(){

        ArrayList<Objective> objectives = new ArrayList<>();

        for (Color c: Color.values()) {
            switch(c){
                case RED: objectives.add(new Objective("sfumature-rosse-privata", "Somma dei valori su tutti i dadi rossi", 0, new ShadesOfCard(Color.RED),true)); break;
                case BLUE: objectives.add(new Objective("sfumature-blu-privata", "Somma dei valori su tutti i dadi blu", 0, new ShadesOfCard(Color.BLUE),true)); break;
                case GREEN: objectives.add(new Objective("sfumature-verdi-privata", "Somma dei valori su tutti i dadi verdi", 0, new ShadesOfCard(Color.GREEN),true)); break;
                case YELLOW: objectives.add(new Objective("sfumature-gialle-privata", "Somma dei valori su tutti i dadi gialli", 0, new ShadesOfCard(Color.YELLOW),true)); break;
                case PURPLE: objectives.add(new Objective("sfumature-viola-privata", "Somma dei valori su tutti i dadi viola", 0, new ShadesOfCard(Color.PURPLE),true)); break;
                default: break;
            }
        }
        return objectives;
    }


    /**
     * Metodo utilizzato per la creazione della collezione di carte Obbietivo Pubbliche.
     *
     * @return riferimento alla collezione di carte Obbiettivo Pubbliche
     */

    private List<Objective> setObjectivePublic(){

        ArrayList<Objective> objectives = new ArrayList<>();

        objectives.add(new Objective("diagonali-colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti", 0, new ColorDiagonals(), false));
        objectives.add(new Objective("varietà-di-colori", "Set di dadi di ogni colore ovunque", 4, new ColorVariety(),false));
        objectives.add(new Objective("sfumature-diverse",  "Set di dadi di ogni valore ovunque", 5, new ShadeVariety(), false));

        objectives.add(new Objective("sfumature-chiare", "Set di 1 & 2 ovunque",2, new CoupleOfShades(1,2), false));
        objectives.add(new Objective("sfumature-medie","Set di 3 & 4 ovunque",2, new CoupleOfShades(3,4), false));
        objectives.add(new Objective("sfumature-scure", "Set di 5 & 6 ovunque",2, new CoupleOfShades(5,6), false));

        objectives.add(new Objective("sfumature-diverse-riga", "Righe senza sfumature ripetute", 5, new SpecificShadeVariety(4,5), false));
        objectives.add(new Objective("sfumature-diverse-colonna", "Colonne senza sfumature ripetute", 5, new SpecificShadeVariety(5,4), false));

        objectives.add(new Objective("colori-diversi-riga", "Righe senza colori ripetuti", 6, new SpecificShadeVariety(4,5), false));
        objectives.add(new Objective("colori-diversi-colonna", "Colonne senza colori ripetuti", 5, new SpecificShadeVariety(5,4), false));

        return objectives;
    }


    /**
     * Metodo utilizzato per la creazione della collezione di carte Utensili.
     *
     * @return riferimento alla collezione di carte Utensili
     */

    private List<Utensils> setUtensils() {

        ArrayList<Utensils> utensils = new ArrayList<>();

        utensils.add(new AlesatorePerLaminaDiRame());
        utensils.add(new DiluentePerPastaSalda());
        utensils.add(new Lathekin());
        utensils.add(new Martelletto());
        utensils.add(new PennelloPerEglomise());
        utensils.add(new PennelloPerPastaSalda());
        utensils.add(new PinzaSgrossatrice());
        utensils.add(new RigaInSughero());
        utensils.add(new TaglierinaCircolare());
        utensils.add(new TaglierinaManuale());
        utensils.add(new TamponeDiamantato());
        utensils.add(new TenagliaARotelle());

        return utensils;
    }


    /**
     * Metodo che restituisce il riferimento al Player tramite una ricerca per nome
     *
     * @return riferimento a Table
     */

    public Player getPlayerByName(String name) throws InvalidValueException {
        return lobby.callPlayerByName(name);
    }


    /**
     * Metodo che restituisce il riferimento alla collezione di carte Obbiettivo Pubblico del Table
     *
     * @return alla collezione di carte Obbiettivo Pubblico
     */

    public List<Objective> getTableObjective(){
        return new ArrayList<>(lobby.getDeckObjective());
    }



    /**
     * Metodo che restituisce il riferimento alla collezione di carte Utensili del Table
     *
     * @return alla collezione di carte Utensili
     */

    public List<Utensils> getTableUtensils(){
        return new ArrayList<>(lobby.getDeckUtensils());
    }


    /**
     * Metodo che restituisce il riferimento all'attributo di tipo ControllerAction contenuto in Controller
     *
     * @return riferimento a ControllerAction
     */

    public ControllerAction getcAction(){
        return cAction;
    }


    /**
     *  Metodo che Il player turnante
     *
     * @return riferimento a player turnanate
     */

    public Player getTurn(){
        return cTurn.getTurn();
    }

    /**
     * Metodo che ritorna i noi dei giocatori in ordine di turnazione nel round appena passato
     * @return lista di nomi in ordine di comparizione in un round.
     */
    public List<String> getOrderOfTurning(){ return cTurn.getOrderOfTurning();}

    /**
     * Metodo che si occupa di indicare il vincitore della partota e di comunicarlo ai giocatori.
     * @throws Exception
     */
    protected void finalizeTheGame() throws Exception{
        cPoints.updateScoreOfPlayer();
        cPoints.nameOfWinner();
        lobby.setUpdateScoreGrid();
    }
/*

    public void callThrough(Activate mex) throws InvalidValueException {
        if(lobby.callPlayerByName(mex.getPlayer()).getDidPlayCard()) cChat.notifyObserver(new IgnoreMex(mex.getPlayer()));
        else {
            if (lobby.getUtensils(mex.getCard()).getPriceHasBeenChecked()){
                 lobby.getUtensils(mex.getCard()).accept(controller.getc, mex);
                 messageComingChecking(mex);
            }
            else{
                lobby.getUtensils(mex.getCard()).firstActivation(this,mex);
            }
        }
    }*/

    /**
     *Metodo tunnel per l'attuazione di un piazzamento.
     *  Controlla inoltre che le mosse a disposizione nel turno siano state fatte tutte.
     * @param mex messaggio di piazzamento
     */
    public void simpleMove(SimpleMove mex) throws InvalidValueException {
        if(lobby.callPlayerByName(mex.getPlayer()).getDidPlayDie()) cChat.notifyObserver(new IgnoreMex(mex.getPlayer()));
        else{
            cAction.simpleMove(mex);
            messageComingChecking(mex);
        }
    }

    /**
     * Metodo che ritorna il riferimento del comunicatore del controller.
     * @return
     */
    public ControllerChat getcChat() {
        return cChat;
    }

    /**
     * Metodo che ritorna il controller adibito agli algoritmi delle carte
     * @return
     */
    public ControllerCard getcCard() {
        return cCard;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che setta la scelta del giocatore della side.
     * @param m messaggio contenente il nome del giocatore e l'indice della side scelta.
     * @throws InvalidValueException
     */
    public void chosen(Choice m) throws InvalidValueException {
        lobby.callPlayerByName(m.getPlayer()).setMySide(m.getIndex());
        cTurn.actualPlayerIsDone();
    }

    /**
     * Metodo che segnala il volere di passare il turno del giocatore.
     * @param m
     */
    public void passTurn(PassTurn m){
        cTurn.passTurn(m);
    }

    /**
     * Metodo che congela un giocaotre a richiesta della Lobby.
     * @param m messaggio contenente il nome del giocatore richiesto.
     */
    public void freezer(Freeze m){
        try {
            lobby.callPlayerByName(m.getPlayer()).forget();
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metdo che scongela un giocatore a richiest della Lobby.
     * @param m messaggio contenente il nome del giocatore richiesto.
     */
    public void unfreeze(Unfreeze m){
        try {
            lobby.callPlayerByName(m.getPlayer()).remember();
        } catch (InvalidValueException e) {
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Metodo che mette tiene in sospeso un dado nel model, dovuto ad uso a più fasi di una carta utensile.
     * @param d riferimento del dado in questione.
     */
    public void setHoldingADiceMoveInProgress(Dice d){
        lobby.setHoldingADiceMoveInProgress(d);
    }

    /**
     * Metodo che mette prende in sospeso un dado nel model, dovuto ad uso a più fasi di una carta utensile.
     * @return dado tenuto in sospeso all'interno del model.
     */
    public Dice getHoldingADiceMoveInProgress(){
        return lobby.getHoldingADiceMoveInProgress();
    }
    public void cleanHoldingADiceMoveInProgress(){
       lobby.cleanHoldingADiceMoveInProgress();
    }
    public void setHoldingResDie(Dice d){
        lobby.setHoldingResDie(d);
    }
    public Dice getHoldingResDie(){
        return lobby.getHoldingResDie();
    }
    public void cleanHoldingResDie(){
       lobby.cleanHoldingResDie();
    }
    public void setHoldingRoundGDie(Dice d){
        lobby.setHoldingRoundGDie(d);
    }
    public Dice getHoldingRoundGDie(){
        return lobby.getHoldingRoundGDie();
    }
    public void cleanHoldingRoundGDie(){
        lobby.cleanHoldingRoundGDie();
    }
    public void cleanAll(){
        cleanHoldingADiceMoveInProgress();
        cleanHoldingResDie();
        cleanHoldingRoundGDie();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che controlla la validità del messaggio in ingresso.
     * @param m messaggio da controllare.
     * @return
     */
    public boolean messageComingChecking(EventMVC m){
        return cTurn.messageComingChecking(m);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che fa partire l'intero gioco.
     * @throws Exception
     */
    public void START() throws Exception {
        cTurn.setThePlayers();
    }

    public Utensils getUtensils(int card) {
        return lobby.getUtensils(card);
    }
}
