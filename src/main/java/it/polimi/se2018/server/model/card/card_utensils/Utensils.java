package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.responses.ErrorActivation;
import it.polimi.se2018.server.events.responses.ErrorSomethingNotGood;
import it.polimi.se2018.server.events.responses.SuccessActivation;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;

/**
 * Classe del tipo generico delle utensili, contenete gli attributi principali e i metodi comuni a tutte
 * le carte.
 * Le carte utensili dovevano avere un caricamento degli attributi da json,  ma la manca di tempo non ci
 * ha permesso di apportare tale modifica non molto comune se fatta su classi figlie che estendono un padre.
 * La classe in questo caso non è astratta poichè si è pensato che rendere il metodo accept o il metodo
 * della funzione della carta astratto
 * non avrebbe avuto che il solo compito di essere una ridondanza poichè l'implementazione del metodo funzione
 * o il metodo accept, usando il patter visitor come nel nostro caso,
 * è un obbligo logico se si vuole dare una funzionalità alla
 * classe dato che altrimenti senza l'algoritmo, o la chiamata di accept che ne porta l'attivazione
 * la classe non avrebbe nessuna utilità. Oltretutto il metodo funzione se fatto astratto avrebbe
 * implicato fare un prototipo comune a tutte le carte cosa che non ci aggradava anche pensado
 * al caso in cui avremmo riscirtto le carte non con un solo metodo funzione ma come aggolmerato
 * di sottoclassi effetto.
 * @author Kevin Mato
 */
public class Utensils implements Visitable{

    private Color squareColor;
    private String myType;
    private int number;
    private String description;
    private int cost;
    private boolean isFirstTime;
    private boolean isBusy=false;
    private boolean priceHasBeenChecked=false;

    private int previousCost;

    /**
     * Costruttore che viene usato nelle classi più specifiche per definire le loro qualità principali.
     * @param numb numero della carta.
     * @param typo tipo o nom della carta.
     * @param color colore del quadratino della carta.
     * @param desc descrizione della carta.
     */
    public Utensils(int numb, String typo, Color color, String desc){
        this.number=numb;
        this.myType=typo;
        this.squareColor=color;
        this.description=desc;
        this.cost=1;
        this.previousCost=cost;
        this.isFirstTime=true;
    }

    /**
     * Metodo che imposta un attributo sulla carta che indicherà se la carta è in uso o no.
     */
    public void setTheUse(){
        isBusy= !isBusy;
        togglePriceHasBeenChecked();
    }

    /**
     * Metodo che ritorna se la carta è in uso
     * @return true se occupata in un compito, false altrimenti.
     */
    public boolean getIsBusy(){
        return isBusy;
    }

    /**
     * Ritorna tipo della carta.
     * @return stringa tipo della carta.
     */
    public String getMyType(){
        return myType;
    }

    /**
     * Ritorna il numero della carta.
     * @return numero intero della carta.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Metodo che ritorna il costo attuale della carta.
     * @return costo della carta.
     */
    public int getCost(){
        return cost;
    }

    /**
     * Ritorna il costo precedente a un tentativo di uso,
     * cioè il costo prima della prima attivazione.
     * @return vecchio costo.
     */
    public int getPreviousCost() {
        return previousCost;
    }

    /**
     * Resetta il costo della carta al suo precendete, poichè una attivazione può
     * non essere andata a buon fine.
     */
    public void undoCostUpdate(){
        cost=getPreviousCost();
    }

    /**
     * getter del colore della carta.
     * @return colore.
     */
    public Color getSquareColor(){
        return squareColor;
    }

    /**
     * Ritorna la descrizione della carta utensile.
     * @return stringa con la descrizione.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Ritorna un booleano che dice se il prezzo della carta è già stato controllato.
     * @return true se è vero false altrimenti.
     */
    public boolean getPriceHasBeenChecked(){
        return  priceHasBeenChecked;
    }
    /**
     * Inverte il booleano che dice che il prezzo della carta è stato controllato.
     */
    private void togglePriceHasBeenChecked(){
        priceHasBeenChecked=!priceHasBeenChecked;
    }

    /**
     * Metodo che aggiunge uno al costo della carta se invocato la prima volta
     */
    private void addToCost(){
       if(isFirstTime){
           cost=cost+1;
           isFirstTime=false;
       }
       else previousCost=cost;
    }

    /**
     * Metodoc he controlla se i favori del player che intende attivare la carta
     * siano abbastanza per l'attivazione.
     * @param controller reference del controller del gioco
     * @param m evento che richiede l'attivazione di una carta.
     * @return true se è possibile.
     * @throws InvalidValueException lanciato se il player che richiede la carta non esiste.
     */
    private boolean checkerPrice(Controller controller,Activate m) throws InvalidValueException {
        if(cost<=controller.getPlayerByName(m.getPlayer()).getFavours()){
            addToCost();
            setTheUse();
            return true;
        }
        return false;
    }

    /**
     * Metodo lanciato per una prima attivazione di una carta.
     * Può lanciare un evento di successo o di errore.
     * @param controller reference del controller del gioco
     * @param m evento di richiesta di attivazione.
     */
    public void firstActivation(Controller controller,Activate m){
        try {
            if (checkerPrice(controller, m)) {
                controller.getcChat().notifyObserver(new SuccessActivation(number, m.getCard(),
                        controller.getTurn().getFavours(),
                        cost,
                        controller.getTurn().getName()));
            } else
                controller.getcChat().notifyObserver(
                        new ErrorActivation(number, m.getCard(),
                                controller.getTurn().getFavours(),
                                cost,
                                controller.getTurn().getName()));
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(
                    new ErrorSomethingNotGood(e));
        }

    }

    /**
     * Metodo che deve implementare ogni oggetto visitabile. Implementato dalla classe utensils
     * generica per avere un override nelle classi più specifiche.
     * il metodo è vuoto poichè la classe utensils generica
     * definische solo le caratteristiche principlai che deve avere un utensile, e i
     * suoi metodi principali ma non ha un algoritmo specifico da eseguire
     * @param visitor reference del visitatore.
     * @param m evento che richiede l'uso di una carta.
     */
    @Override
    public void accept(Visitor visitor, Activate m) {
        /*il metodo è vuoto poichè la classe utensils generica
        definische solo le caratteristiche principlai che deve avere un utensile, e i
        suoi metodi principali ma non ha un algoritmo specifico da eseguire*/
    }
}
