package it.polimi.se2018.server.model.grid;


import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.Table;

import java.util.ArrayList;

/**
 * La classe rappresenta il tracciato per il calcolo finale dei punti da associare a ciascun giocatore
 *
 * @author Simone Lanzillotta
 * @author Kevin Mato (collegamento al model per gli aggiornamenti)
 */

public class ScoreGrid {

    private int[] scoreLines;
    private ArrayList<String> names= new ArrayList<>();
    private ArrayList<Integer> scorie= new ArrayList<>();
    private String nameWinner;

    /**
     * Costruttore della classe che setta la dimensione dell'array scoreLines uguale al numero di gicatori partecipanti alla
     * sessione di gioco
     *
     * @param numberOfPlayers riferimento al numero di giocatori partecipanti
     */

    public ScoreGrid(int numberOfPlayers) {
        scoreLines = new int[numberOfPlayers];

    }


    /**
     * Metodo utilizzato per l'aggiornamento del punteggio di ciascun giocatore
     *
     * @param player riferimento alla posizione del Player nell'ArrayList contenuto nel Table della sessione di gioco
     * @param howMuch riferimento alla quantità da aggungere al punteggio parziale del Player selezionato
     */

    public void updatePoints(int player, int howMuch){
        scoreLines[player] = scoreLines[player]+howMuch;
    }


    /**
     * Metodo utilizzato per accedere al punteggio parziale di ogni singolo giocatore
     *
     * @param playersnumber riferimento alla posizione del Player nell'ArrayList contenuto nell'oggetto Table della sessione di gioco
     * @return riferimento al punteggio parziale del Player selezionato
     */

    public int getPlayersPoint(int playersnumber){
        return scoreLines[playersnumber];
    }


    /**
     * Metodo che restituisce la dimensione dell'array scoreLines
     *
     * @return riferiemtno alla dimensione di scoreLines
     */

    public int getSizeScore(){
        return scoreLines.length;
    }

    /////////////Comunicazione/////

    /**
     * Metodo a sostegno della routine che si  occupa della stilazione della classifica.
     * Aggiunge il nome del giocatore in ordine di posizione nella turnazione.
     * Avrà corrispondenza diretta con l'arrayList dei scores, per un recupero dei dati facilitato.
     * @param name nome del giocatore
     */
    public void add(String name){
        names.add(name);
    }

    /**
     * Metodo a sostegno della routine che si  occupa della stilazione della classifica.
     * Aggiunge lo score del giocatore in ordine di posizione nella turnazione.
     * Avrà corrispondenza diretta con l'arrayList dei nomi, per un recupero dei dati facilitato.
     * Il nome della classe simpaticamente si riconduce all'istruzione assembly per cui si carica il numero
     * in un registro.
     * @param amount score del giocatore
     */
    public void addi(int amount){
        scorie.add(amount);
    }

    /**
     * Metodo che assegna alla variabile del nome del vincitore, il nome del player che secondo l'algoritmo merita
     * il primo posto.
     * @param name nome del vincitore.
     */
    public void setNameWinner(String name){
        if(name!=null) nameWinner=name;
    }
    /**
     * Metodo che crea l'evento di aggiornamento della ScoreGrid.
     * @return evento contenete la rappresentazione.
     */
    private UpdateM createResponse(){
        String content = this.toString();
        return new UpdateM(null,"ScoreGrid", content);
    }
    /**
     * override del toString di Object, serve a creare una rappresentazione customizzata secondo protocollo
     * della classe.
     * @return stringa di rappresentazione della classe.
     */
    public String toString(){
        String message="";
        for(int i=0;i<scorie.size();i++){
            message = message.concat(names.get(i));
            message =message.concat("&");
            message =message.concat(String.valueOf(scorie.get(i)));
            message =message.concat("&");
        }
        message =message.concat(nameWinner+"\n");
        return message;
    }


    /**
     * Metodo designato dalla classe tavolo che triggera la generazione di un aggiornamento della classe.
     * Il tavolo recuperare gli eventi di update grazie questi metodi.
     * @return l'evento di aggiornamento.
     */
    public UpdateM setUpdate(){
        return createResponse();
    }
}
