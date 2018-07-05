package it.polimi.se2018.server.message.network_message;

/**
 * La classe raccoglie dei metodi che facilitano l'interpretazioe dei messaggi di gestione delle connessioni.
 *
 * @author Marazzi Paolo
 */

public class NetworkMessageParser {

    private NetworkMessageParser(){}


    /**
     * Il metodo controlla se il messaggio è un messaggio che notifica una disconnessione.
     * @param message messaggio da controllare.
     * @return true se il messaggio notifica una disconnessione.
     */
    public static boolean isDisconnectMessage(String message){
        return message.replace("\n", "").split("/")[3].equals("network_message") && message.split("/")[4].equals("disconnected");
    }

    /**
     * Il metodo controlla se il messaggio è di tipo broadcast.
     * @param message messaggio da controllare.
     * @return true se è di tipo broadcast.
     */
    public static boolean isBroadcastMessage(String message){
        return message.replace("\n", "").split("/")[2].equals("!");
    }

    /**
     * Il metodo controlla se il messaggio notifica il congelamento di un giocatore.
     * @param message messaggio da controllare.
     * @return true se notifica il congelamento.
     */
    public static boolean isFreezeMessage(String message){ return message.replace("\n", "").split("/")[4].equals("freeze");
    }

    /**
     * Il metodo restituisce il mittente del messaggio.
     * @param message messaggio da cui estrapolare il mittente.
     * @return nickanme del mittente.
     */
    public static String getMessageSender(String message){
        return message.replace("\n", "").split("/")[1];
    }

    /**
     * Il metodo restituisce il destinatario del messaggio.
     * @param message messaggio da cui estrapolare il destinatario.
     * @return nickanme del destinatario.
     */
    public static String getMessageAddressee(String message){
        return message.replace("\n", "").split("/")[2];
    }

    /**
     * Il metodo restituisce il campo informazioni del messaggio.
     * @param message messaggio da cui trarre le informazioni.
     * @return informazioni contenute nel messaggio.
     */
    public static String getMessageInfo(String message) {
        return message.replace("\n", "").split("/")[5];
    }

    /**
     * Il metodo controlla se il messaggio passato è un messaggio che notifica la fine della partita.
     *
     * @param message messaggio da conrtollare
     * @return true se il metodo notifica la fine della partita.
     */
    public static boolean isWinnerMessage(String message){
        return (message.replace("\n", "").split("/")[4]).equals("winner");
    }

}