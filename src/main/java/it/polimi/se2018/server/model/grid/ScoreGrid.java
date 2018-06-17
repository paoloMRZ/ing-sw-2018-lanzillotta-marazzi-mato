package it.polimi.se2018.server.model.grid;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.Table;

import java.util.ArrayList;

/**
 * La classe rappresenta il tracciato per il calcolo finale dei punti da associare a ciascun giocatore
 *
 * @author Simone Lanzillotta
 *
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
    public void add(String name){
        names.add(name);
    }
    public void addi(int amount){
        scorie.add(amount);
    }
    public void setNameWinner(String name){
        if(name!=null) nameWinner=name;
    }

    private UpdateM createResponse(){
        String who = this.getClass().getName();
        String content = this.toString();
        return new UpdateM(null,who, content);
    }
    //todo toString da fare
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

    public UpdateM updateForcer(UpdateReq m){
        if(m.getWhat().contains(this.getClass().getName())){
            return createResponse();}
        return null;
    }

    public UpdateM setUpdate(){
        return createResponse();
    }
}
