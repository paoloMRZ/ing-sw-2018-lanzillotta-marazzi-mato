package it.polimi.se2018.server.model.card.card_objective;

import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.UpdateM;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.StrategyAlgorithm;


/**
 * La classe rappresenta una carta Obbiettivo generica che, a seconda del valore assunto dall'attributo ImPrivate, identifica una carta
 * Obbiettivo Privata (se TRUE) o carta Obbiettivo Pubblica (se FALSE)
 *
 * @author Simone Lanzillotta
 */



public class Objective {

    private String name;
    private String description;
    private int favours;
    private StrategyAlgorithm myObjective;
    private boolean imPrivate;


    /**
     * Costruttore della classe: crea una carta Obbiettivo Privata o Pubblica a seconda del valore di verità assunto dal
     * parametro imPrivate
     *
     * @param name nome della carta
     * @param description descrizione della carta
     * @param favours numero segnalini favore della carta
     * @param myObjective riferimento all'interfaccia che dinamicamente seleziona l'algoritmo associato alalc arta creata
     * @param imPrivate booleano che identifica una carta Obbiettivo Provata da una Pubblica
     */

    public Objective(String name, String description, int favours, StrategyAlgorithm myObjective, boolean imPrivate) {
        this.name = name;
        this.description = description;
        this.favours = favours;
        this.myObjective = myObjective;
        this.imPrivate = imPrivate;
    }


    /**
     * Metodo che restitutisce il nome della carta
     *
     * @return descrizione della carta
     */
    public String getName(){
        return name;
    }


    /**
     * Metodo che restitutisce la descrizione della carta
     *
     * @return descrizione della carta
     */

    public String getDescription(){
        return description;
    }


    /**
     * Metodo che restituisce i segnalini favore della carta
     *
     * @return numero segnalini della carta
     */

    public int getFavours(){
        return favours;
    }


    /**
     * Metodo che richiama al suo interno il metodo use dell'interfaccia myobjective che andrà a richiamare dinamicamente
     * l'algoritmo associata alla carta
     *
     * @param player riferimento al player (quindi alla sua Side)
     * @return invocazione del metodo use(Player player)
     * @throws Exception lanciata quando il riferimento sia null
     */

    public int useAlgorithm(Player player) throws Exception {
        return myObjective.use(player);
    }
///////////////////////////////////////////////////////////////////////////////
private UpdateM createResponse(){
    String who = this.getClass().getName();
    String content = this.toString();

    return new UpdateM(null,who, content);
}
    //todo toString da fare

    public UpdateM updateForcer(UpdateReq m){
        if(m.getWhat().contains(this.getClass().getName())){
            return createResponse();}
        return null;
    }

    public UpdateM setUpdate(){
        return createResponse();
    }
}
