package it.polimi.se2018.server.message.network_message;

/**
 * La classe raccoglie i metodi che permettono di creare dei messaggi utilizzati per la gestione delle connessioni.
 *
 * @author Marazzi Paolo
 */
public class NetworkMessageCreator {

    private NetworkMessageCreator(){}

    /**
     * Viene restituito il messaggio che indica la connessione di un giocatore.
     * @param nickname giocatore connesso.
     * @return messaggio
     */
    public static String getConnectMessage(String nickname){
        return "/###/!/network_message/connected/" + nickname + "\n";
    }

    /**
     * Viene restituito il messaggio che indica la disconnessione di un giocatore.
     * @param nickname giocatore disconnesso.
     * @return messaggio
     */
    public static String getDisconnectMessage(String nickname){
        return "/###/!/network_message/disconnected/" + nickname + "\n";
    }

    /**
     * Viene restituito il messaggio che indica che la connessione non è andata a buon fine perchè la partita è già iniziata.
     * @param addressee destinatario del messaggio.
     * @return messaggio.
     */
    public static String getConnectionErrorGameStartedMessage(String addressee){
        return "/###/" + addressee + "/network_message/ko_gamestarted/?\n";
    }

    /**
     * Viene restituito il messaggio che indica che la connessione non è andata a buon fine perchè il nickname scelto è giaà utilizzato.
     * @param addressee destinatario del messaggio.
     * @return messaggio.
     */

    public static String getConnectionErrorInvalidNickanmeMessage(String addressee){
        return "/###/" + addressee + "/network_message/ko_nickname/?\n";
    }


    /**
     * Viene restiruito il messaggio con cui la lobby notifica al controller il congelamento di un giocatore.
     * @param nickname giocatore congelato.
     * @return messaggio.
     */
    public static String getFreezeMessage(String nickname){
        return "/###/###/network_message/freeze/"+ nickname  +"\n";
    }


    /**
     * Viene restiruito il messaggio con cui la lobby notifica al controller lo scongelamento di un giocatore.
     * @param nickname giocatore scongelato.
     * @return messaggio.
     */
    public static String getUnfreezeMessage(String nickname){
        return "/###/###/network_message/unfreeze/"+ nickname  +"\n";
    }


    /**
     * Il metodo costruisce il messaggio utilizzato dal client per notificare al server la propria disconnessione.
     * Questo messaggio viene usato dal fake client per richiedere la disconnessione quando si solleva un'eccezone di rete.
     *
     * @param sender nickname del client che ha sollevato l'eccezione.
     * @return messaggio di disconnessione.
     */

    public static String getClientDisconnectMessage(String sender){
        return "/" + sender + "/###/network_message/disconnected/?\n";
    }

}
