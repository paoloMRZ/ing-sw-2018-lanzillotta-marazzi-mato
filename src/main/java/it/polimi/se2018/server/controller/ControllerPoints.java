package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante della parte di controller adibita al calcolo del punteggio da applicare alla fine della partita. Si noti che ogni
 * giocatore può prendere punti da:
 *      • Ogni Carta Obiettivo Pubblico - I giocatori possono guadagnare punti da ciascuna carta più volte.
 *      • La propria Carta Obiettivo Privato - Si somma il valore dei propri dadi del colore specificato.
 *      • I Segnalini Favore - 1 Punto Vittoria per ciascun segnalino non utilizzato.
 *      • I giocatori perdono 1 Punto Vittoria per ogni spazio libero rimasto sulla loro vetrata.
 *
 * @author Simone Lanzillotta
 */



public class ControllerPoints {

    private final Table lobby;
    private final Controller controller;


    /**
     * Costruttore della classe Controlleroints
     *
     * @param lobby riferimento all'oggetto Table della sessione di gioco
     */

    public ControllerPoints(Table lobby, Controller controller) {
        this.lobby = lobby;
        this.controller = controller;
    }

    /**
     * Metodo di supporto che viene utilizzato per calcolare il numero di celle rimaste vuote alla fine della partita
     *
     * @param side riferimento alla carta Sude su cui si vuole applicare la ricerca
     * @return numero di celle senza un dado associato
     */

    private int searchVoidCell(Side side) throws InvalidShadeValueException, InvalidCoordinatesException {

        int counter = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (side.showCell(i, j).showDice() == null) counter++;
            }
        }
        return counter;
    }


    /**
     * Metodo utilizzato per il calcolo del punteggio finale associato a ciascun giocatore
     *
     * @throws Exception viene lanciata quando si genera un riferimento non valido
     */

    public void updateScoreOfPlayer() throws Exception {

        //Blocco che calcola il punteggo relativo alle carte Obbiettivo (sia Pubbliche che Private) e segnalini Favore residui

        //Estraggo un riferimento alla lista di carte Obbiettivo Pubblico da lobby e alla lista di Players
        ArrayList<Objective> objectives = new ArrayList<>(lobby.getDeckObjective());

        //Ricavo il numero di player dei player di cui si intende calcolare il punteggio
        int numbOfPlayers = lobby.getScoreGrid().getSizeScore();

        for (int i = 0; i < numbOfPlayers; i++) {
            Player player = lobby.callPlayerByNumber(i);
            for (Objective objective : objectives) {
                lobby.getScoreGrid().updatePoints(i, objective.useAlgorithm(player));
            }

            lobby.getScoreGrid().updatePoints(i, player.getMyObjective().useAlgorithm(player));
            lobby.getScoreGrid().updatePoints(i, player.getFavours());
            lobby.getScoreGrid().updatePoints(i, (searchVoidCell(player.getMySide())) * (-1));
        }
    }


    /**
     * Classe adibita alla creazione di un oggetto che rappresenti un associazione tra Player e un punteggio specifico riguardo le modalità di scelta di un
     * vincitore in caso di parità. Dispone quindi di un campo name e un campo score contente il relativo punteggio parziale.
     */

    private class ScoreByPlayer {

        String name;
        int score;

        public ScoreByPlayer(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getNameOfPlayer() {
            return name;
        }

        public int getScoreOfPlayer() {
            return score;
        }
    }


    /**
     * Metodo che ispeziona una collezione di oggetti ScoreByPlayer e identifica il massimo punteggio parziale tra questi
     *
     * @param partialScore collezione di oggetti ScoreByName da ispezionare
     * @return riferimento al valore massimo di punteggio parziale
     */

    private int maxValues(List<ScoreByPlayer> partialScore) {
        int head = partialScore.get(0).getScoreOfPlayer();

        for (ScoreByPlayer partial : partialScore) {
            if (head <= partial.getScoreOfPlayer()) head = partial.getScoreOfPlayer();
        }

        return head;
    }


    /**
     * Metodo che estrae una sottolista di oggetti ScoreByName con un punteggio parziale uguale.
     *
     * @param partialScore collezione di oggetti ScoreByName da ispezionare
     * @return riferimento alla collezione di oggetti ScoreByName
     */

    private List<String> equalityPlayer(List<ScoreByPlayer> partialScore) {

        int maxScore = maxValues(partialScore);
        ArrayList<String> selectedPlayers = new ArrayList<>();

        //Il metodo estrae una sottolista di elementi che hanno uno stesso punteggio
        for (ScoreByPlayer partial : partialScore) {
            if (partial.getScoreOfPlayer() == maxScore) selectedPlayers.add(partial.getNameOfPlayer());
        }

        return selectedPlayers;
    }

        /**
         * Metodo che estrae da collezione di oggetti ScoreByName una lista di ScoreByName selezionati
         *
         * @param partialScore riferimento alla collezione di ScoreByName da cui si intende estrarre oggetti
         * @param equality riferimento alla lista di nomi per identificare gli oggetti ScoreByName che si intende estrarre
         * @return riferimento alla collezioni di oggetti ScoreByName selezionati
         */

        private List<ScoreByPlayer> extractPlayer (List < ScoreByPlayer > partialScore, List < String > equality){

            ArrayList<ScoreByPlayer> selection = new ArrayList<>();
            for (ScoreByPlayer partial : partialScore) {
                for (String name : equality) {
                    if (name.equals(partial.getNameOfPlayer())) selection.add(partial);
                }

            }

            return selection;
        }


        /**
         *  Metodo utilizzato per eleggere il vincitore della partita, ovvero il gicoatore con il punteggio più alto. Richiama al suo interno il metodo per il calcolo dei
         *  punteggi finali. Devo però gestire eventiali casi di parità. Si noti che i casi possibili che si possono verificare, vanno applicati nell'ordine seguente:
         *
         *      • Vince il giocatore col maggior numero di punti dati dall’Obiettivo Personale;
         *      • Se c’è ancora parità vince chi ha più Segnalini Favore;
         *      • se ancora non c’è un vincitore, vince il giocatore, tra quelli in parità, che occupa la posizione più bassa dell’ordine dell’ultimo round.
         *
         * @return nome del vincitore
         * @throws Exception viene lanciata qualora si passassero valori non accettabili
         */

        public String nameOfWinner () throws Exception {

            ArrayList<String> compareFinalPoint;
            ArrayList<String> compareObjectivePoint;
            ArrayList<String> compareFavoursPoint;
            ArrayList<String> compareRound;

            String nameOfWinner = null;

            //Richiamo il metodo per il calcolo del punteggio
            updateScoreOfPlayer();


            //Creo le collezioni di gestione della parità, che verranno valjutate nell'ordine indicato
            int numbOfPlayers = lobby.getScoreGrid().getSizeScore();

            ArrayList<ScoreByPlayer> finalPoint = new ArrayList<>();
            ArrayList<ScoreByPlayer> favoursPoint = new ArrayList<>();
            ArrayList<ScoreByPlayer> objectivePoint = new ArrayList<>();
            ArrayList<String> roundPosition = (ArrayList<String>) controller.getOrderOfTurning();

            for (int i = 0; i < numbOfPlayers; i++) {
                Player player = lobby.callPlayerByNumber(i);
                favoursPoint.add(new ScoreByPlayer(player.getName(), player.getFavours()));
                objectivePoint.add(new ScoreByPlayer(player.getName(), player.getMyObjective().useAlgorithm(player)));
                finalPoint.add(new ScoreByPlayer(player.getName(), lobby.getScoreGrid().getPlayersPoint(i)));
            }


            //Create tutte le collezioni del caso, procedo con la scelta del vincitore

            //Esiste un giocatore che ha un punteggio finale maggiore degli altri ed è unico?
            compareFinalPoint = new ArrayList<>(equalityPlayer(finalPoint));

            if (compareFinalPoint.size() == 1) nameOfWinner = compareFinalPoint.get(0);
            else {

                //Valuta il punteggio parziale relativo alle carte Obbiettivo Privato dei giocatori a pari punteggio finale
                compareObjectivePoint = new ArrayList<>(equalityPlayer(extractPlayer(objectivePoint, compareFinalPoint)));
                if (compareObjectivePoint.size() == 1) nameOfWinner = compareObjectivePoint.get(0);
                else {

                    //Valuta il punteggio parziale relativo ai segnalini Favore dei giocatori a pari punteggio finale e pari punteggio relativo alla carta Obbiettivo Privato
                    compareFavoursPoint = new ArrayList<>(equalityPlayer(extractPlayer(favoursPoint, compareObjectivePoint)));
                    if (compareFavoursPoint.size() == 1) nameOfWinner = compareFavoursPoint.get(0);
                    else {

                        //Valuta la posizione nell'ultimo round dei giocatori a pari punteggio finale, pari punteggio relativo alla carta Obbiettivo Privato e pari segnalini Favore
                        for (String position : roundPosition) {
                            if (compareFavoursPoint.contains(position)) nameOfWinner = position;
                        }
                    }
                }
            }

            return nameOfWinner;
        }

}
