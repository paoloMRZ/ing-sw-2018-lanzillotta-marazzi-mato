package it.polimi.se2018.server.timer;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Questa classe implementa un timer che effettua il conto alla rovescia partendo dal valore (in secondi) impostato
 * al momento della creazione dell'oggetto.
 * Quando scade l'interevallo di tempo impostato tramite il costruttore della classe tutti gli oggetti che osservano
 * questo timer vengono notificati.
 *
 * Attenzione: quando il timer scade il ciclo di conteggio ricomincia da capo in modo automatico!
 *
 * Per utilizzare questo timer, dopo aver costruito un nuovo oggetto, bisogna aggiungere tramite il metodo 'add' tutti gli oggetti che deveono
 * essere notificati allo scadere del tempo prestabilito. Questi oggetti devono implementare l'interfaccia 'ObserverTimer' contenuta in questo
 * package.
 *
 * Il timer deve essere avviato tramite il metodo 'start' e deve essere fermato tramite il metodo 'stop'.
 * Il metodo 'reset' ha effetto soltanto se il timer è stato avviato.
 *
 * Si noti che dopo aver stoppato il timer, se lo si riavvia, il conteggio riparte al valore impostato alla creazione dell'oggetto.
 *
 * @author Marazzi Paolo.
 */

public class SagradaTimer {
    private int lifeTime; //Valore a cui è stato impostato il timer.
    private int counter; //Contatore dei secondi.
    private Timer timer; //Gestore del TimerTask "Clock"
    private boolean isStarted; //Indica lo stato del timer.
    private ArrayList<ObserverTimer> osservatori; //Lista degli osservatori.

    /**
     * Costruttore della classe.
     * Nel caso venga passato un valore non valido (cioè <= 0) per il parametro tick il timer
     * viene settato automaticamente a 30 sec.
     * @param tick intervallo di tempo (in secondi) dopo cui gli osservatori ricevono la notifyFromFakeView.
     */
    public SagradaTimer(int tick){
        if(tick > 0){
            this.lifeTime = tick;
            this.counter = tick;
        }else {
            this.lifeTime = 30;
            this.counter = 30;
        }

        isStarted = false;
        osservatori = new ArrayList<>();
    }

    /**
     * Il metodo notifica tutti gli osservatori di questa classe richiamando il metodo timerUpdate di ognuno.
     */
    private void timerNotify(){
        for(ObserverTimer osservatore : osservatori)
            osservatore.timerUpdate();
    }

    /**
     * Il metodo aggiunge un nuovo osservatore alla lista degli osservatori.
     * Se l'osservatore è già presente non viene effettuato nessun inserimento.
     *
     * @param observerTimer osservatore da inserire.
     */
    public void add(ObserverTimer observerTimer){
        if(observerTimer != null && !osservatori.contains(observerTimer))
            osservatori.add(observerTimer);
    }

    /**
     * Il metodo rimuove l'osservatore dalla lista degli osservatori.
     * Se l'osservatore non è nella lista non viene effettuata nessuna rimozione.
     *
     * @param observerTimer osservatore da rimuovere.
     */
    public void remove(ObserverTimer observerTimer){
        if(observerTimer != null && osservatori.contains(observerTimer))
            osservatori.remove(observerTimer);
    }

    /**
     * Il metodo avvia il conto alla rovescia.
     * Nel caso fosse già stato avviato il metodo non effettuta nessuna azione.
     */
    public void start() {
        if(!isStarted) {
            //E' necessario creare un nuovo oggetto Timer percchè se quello esistente fosse già stato fermato
            //non sarebbe possibile riavviarlo (vedi documentazione java).
            timer = new Timer();

            counter = lifeTime; //Resetto il contatore al valore iniziale.

            //Avvio il timer in modo che esegua il metodo run dell'oggetto Clock (TimerTask) ogni secondo.
            timer.scheduleAtFixedRate(new Clock(this), 0, 1000);
            isStarted = true;
        }
    }

    /**
     * Il metodo ferma il conto alla rovescia.
     * Nel caso fosse già stato fermato il metodo non effettuta nessuna azione.
     */
    public void stop() {
        if(isStarted) {
            timer.cancel();
            isStarted = false;
        }
    }

    /**
     * Il metodo resetta il timer, cioè fa ripartire il conteggio dal valore iniziale.
     * Ha effetto solo se il timer è in esecuzione.
     */
    public void reset(){
        if(isStarted) {
            stop();
            start();
        }
    }

    /**
     * Il metodo restituisce lo stato del timer.
     * @return stato del timer (started/stopped)
     */
    public boolean isStarted(){
        return isStarted;
    }

    /**
     * Il metodo ritorna lo stato di conteggio.
     * @return contatore del timer.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Metodo di notifica che viene richiamato ogni secondo dal metodo run della classe Clock (TimerTask).
     *
     * Si noti che il metodo ha livello di protezione package!
     */
    void clockNotify(){
        System.out.print(counter); //TODO da rimuovere!
        counter--;

        if(counter== -1){
            //Quando il contarore è arrivato a zero lo resetto al valore impostato dall'utente.
            counter = lifeTime;

            //notifico gli osseravtori per avvisarli che il tempo è scaduto.
            timerNotify();
        }
    }
}
