package it.polimi.se2018.server.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;
import it.polimi.se2018.server.model.reserve.Reserve;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Rappresenta il tavolo di gioco ideato come una composizione di elementi, già impostati per la sessione di gioco (ovvero con le Carte Obbiettivo Private e
 * con le quattro Side per giocatore già distribuite.
 *
 * Si noti che il Player dovrà in un secondo momento scegliere una carta Side tra le quattro assegnate e quindi scartare le altre.
 *
 * @author Simone Lanzillotta
 * @author Kevin Mato
 */




public class Table {

    private ArrayList<Utensils> utensilsDeck;
    private final  ArrayList<Objective> objectiveDeck;
    private final ArrayList<Player> playersList;
    private final DiceSachet diceSachet;
    private Reserve reserve;
    private final ScoreGrid scoreGrid;
    private final RoundGrid roundGrid;
    private final NotifyModel chat;
    private Dice holdingADiceMoveInProgress;
    private Dice holdingResDie;
    private Dice holdingRoundGDie;

    /**
     * costruttore della classe Table che imposta tutti i componenti della sessione di gioco
     *
     * @param utensilsDeck  carte Utensili selezionate per la sessione di gioco
     * @param objectiveDeck carte Obbiettivo della sessione di gioco
     * @param playersList   lista dei giocatori della sessione di gioco
     * @param notifyBack    istanza della classe comunicatore dl model
     */

    public Table(List<Utensils> utensilsDeck, List<Objective> objectiveDeck, List<Player> playersList,NotifyModel notifyBack) {
        this.utensilsDeck = (ArrayList<Utensils>)utensilsDeck;
        this.objectiveDeck = (ArrayList<Objective>)objectiveDeck;
        this.playersList = (ArrayList<Player>)playersList;
        this.diceSachet = new DiceSachet();
        this.scoreGrid = new ScoreGrid(playersList.size());
        this.roundGrid = new RoundGrid();
        this.reserve = createReserve();
        this.chat= notifyBack;

    }


    /**
     * Metodo che restituisce una copia della riserva
     *
     * @return una copia della riserva della sessione di gioco
     */

    public Reserve getReserve() {
        return new Reserve(reserve.getDices());
    }


    /**
     * Metodo che restituisce una copia della collezione delle carte Utensili
     *
     * @return una copia delle carte Utensili utilizzate nella sessione di gioco
     */

    public List<Utensils> getDeckUtensils() {
        return new ArrayList<>(utensilsDeck);
    }


    /**
     * Metodo che restituisce una copia della collezione delle carte Obbiettivo
     *
     * @return una copia delle carte Obbiettivo nella sessione di gioco
     */
    public List<Objective> getDeckObjective() {
        return new ArrayList<>(objectiveDeck);
    }


    /**
     * Metodo che restituisce una determinata carta Utensile
     *
     * @param cardPosition indice della carta Utensile da restituire
     * @return la carta Utensile alla posizione indicata
     */

    public Utensils getUtensils(int cardPosition) {
        return utensilsDeck.get(cardPosition);
    }


    /**
     * Metodo che restituisce una determinata carta Obbiettivo
     *
     * @param cardPosition indice della carta Obbiettivo da restituire
     * @return la carta Obbiettivo alla posizione indicata
     */

    public Objective getObjective(int cardPosition) {
        return objectiveDeck.get(cardPosition);
    }


    /**
     * Metodo che restituisce il riferimento all'attributo scoreGrid
     *
     * @return riferimento all'attributo scoreGrid
     */

    public ScoreGrid getScoreGrid() {
        return scoreGrid;
    }


    /**
     * Metodo che simula il pescaggio dalla riserva della sessione di gioco
     *
     * @param position indice corrispondente al dado da estrarre
     * @return il dado alla posizione indicata
     * @throws ArrayIndexOutOfBoundsException lanciata quando si indica un indice al di fuori delle dimensioni massime della collezione (in questo caso Reserve)
     * @throws NullPointerException           lanciata quando viene generato un NullPointer
     */

    public Dice pickFromReserve(int position){
        Dice dadao= reserve.pick(position);
        setUpdateReserve();
        return dadao;
    }


    /**
     * Metodo che serve a cambiare la riserva se ci sono stati cambiamenti al suo interno (fondamentale per le carte Utensile)
     *
     * @param toStore riserva a cui si volgiono applicare i cambiamenti
     */

    public void setReserve(Reserve toStore) {
        ArrayList<Dice> preStored = toStore.getDices();
        this.reserve = new Reserve(preStored);
        setUpdateReserve();
    }


    /**
     * Metodo che simula l'estrazione della riserva. Per ogni giocatore, vanno estratti 2 dadi +1 al risultato complessivo. Quindi alla collezione contenuta in riserva
     * devo aggiungere (put) un dado estratto dal sacchetto dei dadi (getDiceFromSachet)
     *
     * @return riserva creata simulando un'estrazione ripetuta (e casuale) di dadi
     */

    private Reserve createReserve() {
        ArrayList<Dice> giveTo = new ArrayList<>();
        for (int i = (playersList.size() * 2 + 1); i > 0; i--) {
            giveTo.add(diceSachet.getDiceFromSachet());
        }
        return new Reserve(giveTo);
    }


    /**
     * Metodo che restituisce il giocatore selezionandolo tramite il proprio nome associato
     *
     * @param name nome del giocatore da prelevare
     * @return giocatore con il nome corrispondente
     * @throws InvalidValueException lanciata quando il  valore in ingresso (nome in questo caso) non è valido ai fini della ricerca
     */

    public Player callPlayerByName(String name) throws InvalidValueException {
        if(name!=null){
            for (Player p : playersList) {
                if (p.getName().equals(name)) return p;
            }
            throw new InvalidValueException();
        }
        throw new NullPointerException();
    }


    /**
     * Metodo che simula l'estrazione delle carte Side da assegnare al Player che, in un secondo momento, dovrà sceglierne una per giocare
     *
     * Si noti che per estrazione si intende una composizione di due fasi:
     *      -> una prima estrazione randomica di una carta Side (quindi di un lato della carta completa)
     *      -> una seconda estrazione vincolata a quella precedente (per completare la carta e simularne il corretto pescaggio)
     *
     * All'intenro del metodo si utilizza anche la libreria Jackson per l'acquisizione della mappatura delle carte Side dal file myJSON.json
     *
     * @param playersList lista dei giocatori partecipanti alla sessione di gioco
     * @throws IOException lanciato nel caso ci fossero errori di acquisizione del file JSon
     */

    public void setCardPlayer(List<Player> playersList) throws IOException {

        //Set di operazioni per l'acquisizione da file

        InputStream is = getClass().getClassLoader().getResourceAsStream("myJSON.json");
        ObjectMapper mapper = new ObjectMapper();
        Side[] sideCollection;
        sideCollection = mapper.readValue(is, Side[].class);


        //Creo uno stream di interi da 0 a 23:
        // -> Creo lo stream con IntStream
        // -> Imposto l'intervallo chiuso da cui creare lo stream di interi rangeClosed
        // -> trasformo l'IntStream in una box di Integer (boxed())
        // -> trasformo il box Collector in una collezione (in particolare un ArrayList)

        List<Integer> range = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(range);


        //Quindi per ogni giocatore nella lista
        for (Player p : playersList) {
            int i = 0;

            //Creo la collezione che andranno a contenerele carte estratte per ogni Player
            ArrayList<Side> sideSelected = new ArrayList<>();

            //Per goni giocatore devo fare due estrazioni (composte a loro volta da una estrazione vincolata) di Side
            while(i<2) {

                //Genero un numero randomico prendendo come dato la lunghezza dell'array di Interi
                Random random = new Random();
                int casualNum = random.nextInt(range.size() - 1);

                //Estraggo l'indice della carta che dovrò pescare dalla collezione di carte Side
                int extract = range.get(casualNum).intValue();
                sideSelected.add(sideCollection[extract]);

                //Ora devo assegnare l'altro lato della carta side estratta in modo da renderle univocamente accoppiate
                if (extract == 0 || extract % 2 == 0) {
                    sideSelected.add(sideCollection[extract + 1]);
                    for (Integer in : range) {
                        if (in.intValue() == extract + 1) {
                            range.remove(in);
                            break;
                        }
                    }
                    i++;

                } else if (extract % 2 != 0) {
                    sideSelected.add(sideCollection[extract - 1]);
                    for (Integer in : range) {
                        if (in.intValue() == extract - 1) {
                            range.remove(in);
                            break;
                        }
                    }
                    i++;
                }

                range.remove(casualNum);
            }

            //Una volta che ho estratto tutte le side, assegno la collezione alla classe Player
            p.setSideSelection(sideSelected);

        }
    }

    /**
     * Metodo che ritorna la roundgrid del gioco.
     * @return roundgrid del gioco.
     */
    public RoundGrid getRoundGrid(){
        return roundGrid;
    }

    /**
     * Metdo che ritorna il sacchetto del gioco.
     * @return sacchetto del gioco.
     */
    public DiceSachet getDiceSachet(){
        return diceSachet;
    }

    /**
     * Ricerca di un giocatore in base alla sua posiione nell'elenco.
     * @param x il numero cosrrispondente alla su apozine nell'lelenco.
     * @return reference del giocatore ricercato.
     */
    public Player callPlayerByNumber(int x){
        return playersList.get(x);
    }

    /**
     * Metodo che ritorna il nome del player turnante.
     * @return nome del player turnante.
     */
    private String playerTurnante(){
        for(Player p: playersList){
            if(p.getIsMyTurn()) return p.getName();
        }
        return "?";
    }
    /**
     * Metodo che ritorna il numero di giocatori della partita.
     * @return numero di giocaori.
     */
    public int peopleCounter(){
        return playersList.size();
    }

    /**
     * Metodo che ritorna istanza del comuicator del model.
     * @return cominucatore del model.
     */
    public NotifyModel responder(){
        return chat;
    }

    /**
     * Metodo che prepara i giocatori alla partita mostrandogli gli elementi pubblici e le side a dispoizione.
     * @throws IOException non lanciabile realmente.
     */
    public void preparePlayers() throws IOException {
        setCardPlayer(playersList);
        setStartMexObjs();
        setStartMexUtensils();

    }

    /**
     * Metodo che mette l'attributo is my turn del giocatore turnante a true e degli altri a false.
     * resetta anche lo stato delle utensili.
     * @param call numero del giocatore turnante.
     */
    public void rotatingPlayerTurn(int call){
        for (Player p :playersList){
            if(p.getIsMyTurn()) p.setIsMyTurner();
        }
        callPlayerByNumber(call).setIsMyTurner();
        toggleToResetUtensils();
    }

    /**
     * Metodo che cambia lo stato delle carte utensili.
     * Ed elimina il dado tenuto in sospeso nel model.
     */
    private void toggleToResetUtensils(){
        for (Utensils u : utensilsDeck){
            if(u.getIsBusy()) u.setTheUse();
        }
        cleanHoldingADiceMoveInProgress();
    }

    /**
     * Metodo che resetta gli attributi di tutti i giocatori.
     */
    public void newRoundForPlayers(){
        for (Player p :playersList){
            p.restoreValues();
        }
    }
////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che lancia un evento di aggiornamento del model.
     * @param m messaggio contenente i nuovi dati.
     */
    private void launchCommunication(UpdateM m){
        if(m!=null) chat.notifyObserver(m);
    }

    /**
     * Metodo che fa scattare l'aggiornamento della riserva.
     */
    private void setUpdateReserve(){
        launchCommunication(reserve.setUpdate());
    }
    /**
     * Metodo che fa scattare l'aggiornamento della roundgrid.
     */
    public void setUpdateRoundGrid(){
        launchCommunication(roundGrid.setUpdate());
    }
    /**
     * Metodo che fa scattare il messaggio finale della scoregrid.
     */
    public void setUpdateScoreGrid(){
        launchCommunication(scoreGrid.setUpdate());
    }

    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte obiettivo pubbliche chiamando il metodo creatore relativo al topic del messaggio.
     * Il metodo è da considerarsi un toString del deck obiettivi pubblici e segue la convenzione del protocollo
     * di gioco. Inseguito lancia il messaggio.
     */
    private void setStartMexObjs(){
        String message = startMexObjs();

        launchCommunication(new UpdateM(null,"public_objective",message));
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte utensile chiamando il metodo creatore relativo al topic del messaggio.
     * Il metodo è da considerarsi un toString del deck utensili e segue la convenzione del protocollo
     * di gioco. Inseguito lancia il messaggio.
     */
    private void setStartMexUtensils(){
        String message = startMexUtensils();
        launchCommunication(new UpdateM(null,"utensils",message));
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte schema scelte da tutti i giocatori chiamando il metodo creatore relativo al topic del messaggio.
     * Inseguito lancia il messaggio.
     */
    public void setShowEnemiesChoice(){
        String message = showEnemiesChoice();
        launchCommunication(new UpdateM(null,"side_list",message));
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carta obiettivo privata chiamando il metodo creatore relativo al topic del messaggio.
     */
    public void setShowPrivate(String turner) throws InvalidValueException {
        if(turner!=null){
            launchCommunication(new UpdateM(
                    turner,"private_objective",showPrivate(turner)));
        }
    }

    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio contente
     * i dadi rismasti e aggiunti alla roundgrid chiamando il metodo creatore relativo al topic del messaggio.
     * Inseguito lancia il messaggio.
     */
    public void setUpdateDiceToRoundGrid() {
        String message=updateDiceToRoundGrid();
        launchCommunication(new UpdateM(null,"round",message));
    }
    /**
     * Metodo che fa scattare l'aggiornamento dei costi delle utensili.
     */
    public void setUpdateCostUtensils(){
        String message= updateCostUtensils();
        launchCommunication(new UpdateM(null,"price",message));
    }

    /**
     * Metodo che lancia una serie di messaggi che permette al giocatore che si riconnette di essere aggiornato sullo
     * status della partita.
     * @param player nome del player che si riconnette.
     * @throws InvalidValueException lanciato dalle call a metodi privati.
     */
    public void setReconnect(String player) throws InvalidValueException {

        launchCommunication(new UpdateM(player,"utensils",startMexUtensils(),false));
        launchCommunication(new UpdateM(player,"public_objective",startMexObjs(),false));
        setShowPrivate(player);
        String message = showEnemiesChoice();
        launchCommunication(new UpdateM(player,"side_list",message,false));
        sendOthersSideStatus(player);
        launchCommunication(new UpdateM(player,"reserve",reserve.toString(),false));
        launchCommunication(new UpdateM(player,"RoundGrid",roundGrid.toString(),false));
        launchCommunication(new UpdateM(player,"turn",playerTurnante(),false));

    }

    /**
     * Metodo che si occupa di aggiornare il player il cui nome viene passato, dello status delle side di tutti gli
     * altri giocatori.
     * @param player nome del giocatore riconnesso.
     */
    private void sendOthersSideStatus(String player){
        if(player!=null) {
            for (Player p : playersList) {
                if(!player.equals(p.getName())){
                    launchCommunication(new UpdateM(player,"Side",p.getMySide().toString(),false));
                }
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////
    /**
     * Metodo che mette tiene in sospeso un dado nel model, dovuto ad uso a più fasi di una carta utensile.
     * @param d riferimento del dado in questione.
     */
    public void setHoldingADiceMoveInProgress(Dice d){
        holdingADiceMoveInProgress=d;
    }
    /**
     * Metodo che mette prende in sospeso un dado nel model, dovuto ad uso a più fasi di una carta utensile.
     * @return dado tenuto in sospeso all'interno del model.
     */

    public Dice getHoldingADiceMoveInProgress(){
        return holdingADiceMoveInProgress;
    }
    /**Metodo che pulisce il riferimento del dado tenuto in sospeso nel model
     */
    public void cleanHoldingADiceMoveInProgress(){
        holdingADiceMoveInProgress=null;
    }
    /**
     * Metodo che tiene in sospeso un dado della riserva all'interno del model.
     * @param d dado della riservs da tenere in sospeso
     */

    public void setHoldingResDie(Dice d){
        holdingResDie=d;
    }
    /**
     * Metodo che ritorna il dado della riserva tenuto in sospeso.
     * @return reference del dado tenuto in sospeso.
     */

    public Dice getHoldingResDie(){
        return holdingResDie;
    }
    /**
     * Metodo che resetta referece del dado della riserva tenuto in sospeso.
     */

    public void cleanHoldingResDie(){
        holdingResDie=null;
    }
    /**
     * Metodo che tiene in sospeso un dado della roundgrid all'interno del model.
     * @param d dado della roundgrid da tenere in sospeso
     */
    public void setHoldingRoundGDie(Dice d){
        holdingRoundGDie=d;
    }
    /**
     * Metodo che ritorna il dado della roundgrid tenuto in sospeso.
     * @return reference del dado tenuto in sospeso.
     */
    public Dice getHoldingRoundGDie(){
        return holdingRoundGDie;
    }
    /**
     * Metodo che resetta referece del dado della roundgrid tenuto in sospeso.
     */
    public void cleanHoldingRoundGDie(){
        holdingRoundGDie=null;
    }

/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo utilizzato per risettare la lista di utensili presenti nel gioco utile per un possibile modifica
     * e reprogettazione del sistema con cui vengono impostate le carte utensile.
     * @param newDeck lista di carte utensile.
     */
    public void resetUtensilsDeck(List<Utensils> newDeck){
        utensilsDeck= new ArrayList<>(newDeck);
    }
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte obiettivo pubbliche.
     * Il metodo è da considerarsi un toString del deck obiettivi pubblici e segue la convenzione del protocollo
     * di gioco.
     */
    private String startMexObjs(){
        String message ="";
        for (int i = 0; i < 3; i++) {
            if (i == 0)
                message = message.concat(objectiveDeck.get(i).getName());
            else
                message = message.concat("&" + objectiveDeck.get(i).getName());
        }
        message= message.concat("\n");

        return message;
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte utensile.
     * Il metodo è da considerarsi un toString del deck utensili e segue la convenzione del protocollo
     * di gioco.
     */
    private String startMexUtensils(){
        String message ="";
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                message = message.concat(utensilsDeck.get(i).getMyType());
                message= message.concat("&");
                message = message.concat(String.valueOf(utensilsDeck.get(i).getNumber()));
            }
            else{
                message = message.concat("&" + utensilsDeck.get(i).getMyType());
                message= message.concat("&");
                message = message.concat(String.valueOf(utensilsDeck.get(i).getNumber()));
            }
        }
        return message.concat("\n");
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carte schema scelte da tutti i giocatori.
     */
    private String showEnemiesChoice(){
        String content= "";
        for(int i=0;i<playersList.size();i++){
            content=content.concat(playersList.get(i).getName());
            content = content.concat("&");
            content=content.concat(playersList.get(i).getMySide().getName());
            if(i!=playersList.size()-1) content = content.concat("&");
            else content = content.concat("\n");
        }
        return content;
    }
    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio di start contente
     * le carta obiettivo privata.
     */
    private String showPrivate(String turner) throws InvalidValueException {
        if(turner!=null){
            return callPlayerByName(turner).getMyObjective().getName();
        }
        return null;
    }

    /**
     * Metodo che crea il contenuto del messaggio di aggiornamento che diverrà un messaggio contente
     * i dadi rismasti e aggiunti alla roundgrid.
     */
    private String updateDiceToRoundGrid() {
        roundGrid.putAtFinishedRound(reserve.getDices());
        return reserve.toString();
    }
    /**
     * Metodo che fa scattare l'aggiornamento dei costi delle utensili.
     */
    private String updateCostUtensils(){
        String message="";
        for(int i=0;i<utensilsDeck.size();i++){
            message=message.concat(String.valueOf(utensilsDeck.get(i).getCost()));
            if(i!=utensilsDeck.size()-1) message= message.concat("&");
        }
        return message.concat("\n");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che crea una nuova riserva allo scattare di un nuovo round.
     */
    public void newRiserva4Game() {
        setReserve(createReserve());
    }
}
