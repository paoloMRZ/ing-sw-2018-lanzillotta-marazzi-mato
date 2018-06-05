package it.polimi.se2018.server.timer;

import java.util.TimerTask;

/**
 * La classe rappresenta il task che viene eseguito in modo ciclico quando il timer "Sagrada" viene avviato.
 * Questa classe estende java.util.TimerTask in modo da poter essere avviata tramite la classe java.util.Timer.
 *
 * La classe ha livello di accessibilità package.
 * @author Marazzi Paolo
 */
    class Clock extends TimerTask {

    private SagradaTimer sagradaTimer; //Timer "Sagrada" che viene notificato ad ogni esecuzione del metodo run.

    /**
     * Costruttore della classe.
     * @param sagradaTimer timer "Sagrada" che viene notificato ad ogni esecuzione del metodo run.
     */
    Clock(SagradaTimer sagradaTimer){
        if(sagradaTimer != null) {
            this.sagradaTimer = sagradaTimer;
        }
    }

    /**
     * Metodo che viene eseguito dall'oggetto dalla classe java.util.Timer contenuto nella classe SagradaTimer.
     * Il metodo receiveNotify l'oggetto SagradaTimer che è stato passato come parametro al costruttore.
     */
    @Override
    public void run() {
        sagradaTimer.clockNotify();
    }
}
