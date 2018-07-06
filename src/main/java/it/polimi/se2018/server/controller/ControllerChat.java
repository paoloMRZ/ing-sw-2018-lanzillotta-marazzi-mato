package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.*;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.*;
import it.polimi.se2018.server.exceptions.InvalidValueException;

/**
 * Classe che si occupa della comunicazione degli eventi del mvc che partono dal controller alla/dalla fakeview.
 * @author Kevin Mato
 */
public class ControllerChat implements ControllerAsObserver,ControllerAsObservable {

    private final Controller controller;
    private ViewAsObserver view;

    /**
     * Metdo costruttore della classe.
     * @param controller controller "principale".
     */
    public ControllerChat(Controller controller){
        this.controller=controller;
    }

    /**
     * Metodo dell'observer grazie al quale registra un osservatore
     * @param v reference dell'unico observer del controller.
     */
    public void register(ViewAsObserver v){
        view=v;
    }

    /**
     * Metodo attraverso cui riceve un evento di congelamento di un utente.
     * @param mex evento di congelamento utente
     */
    public void update(Freeze mex){
        try {
            controller.freezer(mex);
        } catch (Exception e) {
            notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

    /**
     * Metodo attraverso cui riceve un evento di scongelamento di un utente.
     * @param mex evento di scongelamento utente
     */
    public void update(Unfreeze mex){
        controller.unfreeze(mex);
    }

    /**
     * Metodo per la ricezione di un evento di piazzamento di un dado nella carta schema.
     * Il metodo controlla che l'evento sia valido e dopo averlo contrllato lancia il compito.
     * Dopo averlo fatto cntrolla che il giocatore non abbia già fatto tutte le sue mosse disponibili, se è così fa scattare il turno.
     * @param mex evento di piazzamento
     */
    public void update(SimpleMove mex){
        if(checker(mex)) {
            try {
                controller.simpleMove(mex);
            } catch (InvalidValueException e) {
               notifyObserver(new ErrorSomethingNotGood(e));
            }
        }
    }

    /**
     * Metodo che permette l'agganciamento del model con la fakeview passando il messaggio con la reference della fakeview
     * al tavolo.
     * @param m evento contente la ref della fakeview.
     */
    public void update(HookMessage m){
        register(m.getObserver());
        controller.getcAction().hook(m);
    }

    /**
     * Metodo che passa il messagio che richiede per il giocatore il passaggio del turno.
     * @param m evento di passaggio del turno.
     */
    public void update(PassTurn m){
        controller.passTurn(m);
    }

    /**
     * Metodo che viene attraversato dalla risposta/scelta della carta schema che il giocatore vuole utilizzare.
     * @param m evento scelta del giocatore.
     */
    public void update(Choice m){
        try {
            controller.chosen(m);
        } catch (InvalidValueException e) {
            notifyObserver(new ErrorSomethingNotGood(e));
        }
    }

/* NEL CASO SI VOGLIAMO LASCIARE INTERAGIRE GLI UTENTI QUANDO NON è IL LORO TURNO
    è UNA SOLUZIONE INIZIALE MODIFICABILE IN TUTTI I MODI. SI DISPONE UNA PRIMA SOLZIONE PRONTA ALL'UTILIZZO.
    Sigillo di garanzia che la richiesta è del turnante così da utilizzare direttamente il turante
    per il fill dei campi destinatario per le risposte
*/

    /**
     * Metodo lanciato per il controllo della validità dell'evento/richiesta fatta da un utente controllando prima che gli
     * sia il turnante e che sia il momento giusto in cui farla.
     * @param mex evento genrico
     * @return esito dell'analisi dell'evento.
     */
    private boolean checker(EventMVC mex){
        return mex.getPlayer().equals(controller.getTurn().getName()) && controller.messageComingChecking(mex);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo con evento  di successo di carta multifase.
     * @param mex evento di successo contente un colore del dado estratto.
     */
    public void notifyObserver(SuccessColor mex){
    view.update(mex);
    }
    /**
     * Metodo con evento di successo di carta multifase.
     * @param mex evento di successo contente un valore del dado rilanciato.
     */
    public void notifyObserver(SuccessValue mex){
        view.update(mex);
    }
    /**
     * Metodo con evento  di successo.
     * @param mex evento di successo generico per l'attivazion della carta.
     */
    public void notifyObserver(SuccessActivation mex){
        view.update(mex);
    }
    /**
     * Metodo con evento  di successo.
     * @param mex evento di successo generico per l'attivazione della carta.
     */
    public void notifyObserver(SuccessActivationFinalized mex){
        view.update(mex);
    }

    /**
     * Metodo con evento di errore piazzamento.
     * @param mex  errore di selezione delle coordinate nella side.
     */
    public void notifyObserver(ErrorSelection mex){
        view.update(mex);
    }
    /**
     * Metodo con evento  di errore.
     * @param mex evento di errore di selezione dopo l'attivazione della carta.
     */
    public void notifyObserver(ErrorSelectionUtensil mex){
        view.update(mex);
    }

    /**
     * Metodo con evento  di errore.
     * @param mex evento di errore lanciato quando le condizioni di attivazione non sono buone.
     */
    public void notifyObserver(ErrorActivation mex){
        view.update(mex);
    }

    /**
     *Logger delle eccezioni lanciate.
     * @param mex evento che contiene le eccezioni.
     */
    public void notifyObserver(ErrorSomethingNotGood mex){
        view.update(mex);
    }

    /**
     * Metodo che segnala la fine del tempo a dispozione per qualsiasi azione.
     * @param mex evento che segnala al giocatore interessato che il suo tempo è finito.
     */
    public void notifyObserver( TimeIsUp mex){
        view.update(mex);
    }

    /**
     * Metodo che traporta eventi di tipo aggiornamento.
     * @param mex l'evento che contiene lìaggiornamento di una istanza del model.
     */
    public void notifyObserver( UpdateM mex){
        view.update(mex);
    }

    /**
     * Metodo che trasporta un messaggio di tipo freeze.
     * @param mex evento di congelamento.
     */
    public void notifyObserver( Freeze mex){
        view.update(mex);
    }

    /**
     * Metodo che richiede la disconnessione di un determinato utente.
     * @param mex evento di disoonessione.
     */
    public void notifyObserver( DisconnectPlayer mex){
        view.update(mex);
    }

    /**
     * Metodo che trasporta un messaggio di ignorazione di una azione già compiuta.
     * @param mex evento che segnala l'ignorazione di un azione.
     */
    public void notifyObserver(IgnoreMex mex){view.update(mex);}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo che richiede l'attivazione di una carta utensile controllando prima che l'evento sia valido,
     * poi controllando che prima non vada controllato il costo della carta e nel caso facendo la vera e propria attivazione
     * @param mex evento genrico di attivazone dell'utensile.
     */
    public void update(Activate mex){
        try{
            if(checker(mex)) {
                if(controller.getPlayerByName(mex.getPlayer()).getDidPlayCard()) notifyObserver(new IgnoreMex(mex.getPlayer()));
                else if (!controller.getUtensils(mex.getCard()).getPriceHasBeenChecked()) {
                    controller.getUtensils(mex.getCard()).firstActivation(controller, mex);
                }
                else{
                    controller.getUtensils(mex.getCard()).accept(controller.getcCard(), mex);
                }
            }
        }
        catch(Exception e){
                notifyObserver(new ErrorSomethingNotGood(e));
        }
    }
}
