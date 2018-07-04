package it.polimi.se2018.client.message;

import java.util.List;

/**
 * La classe raccoglie i metodi che permettono di creare i messaggi che devono essere inviati dal client.
 *
 * @author Marazzi Paolo
 */

public class ClientMessageCreator {

    private ClientMessageCreator(){}


    /**
     * Il metodo costruisce un messaggio per notificare al server la propria disconnessione.
     *
     * @param sender nickname del client che invia il messaggio.
     * @return messaggio di disconnessione.
     */

    public static String getDisconnectMessage(String sender){
        return "/" + sender + "/###/network_message/disconnected/?\n";
    }

    /**
     * Il metodo restituisce il messaggio mandato dal server per disconnettere un client.
     *
     * @param nickname client che è stato disconnesso.
     * @return messaggio.
     */
    public static String getServerDisconnectMessage(String nickname){
        return "/###/network_message/disconnected/" + nickname + "\n";
    }

    /**
     * Il metodo costruisce un messaggio per notificare al server l'intenzione di eseguire un piazzamento sulla
     * prorpria griglia di gioco.
     *
     * @param sender nickname del client che invia il messaggio.
     * @param dieIndex indice del dado scelto dalla riserva.
     * @param row riga in cui piazzare il dado.
     * @param col colonna in cui piazzare il dado.
     * @return messaggio di piazzamento.
     */

    public static String getPutDiceMessage(String sender, String dieIndex, String row, String col){
        return "/" + sender + "/###/put/?/" + dieIndex + "&" + row + "&" + col + "\n";
    }


    /**
     * Il metodo restituisce il messaggio che notifica la volontà di utilizzare una carta utensile.
     * @param sender mittente del messaggio.
     * @param cardIndex indice della carta.
     * @return messaggio.
     */
    public static String getActivateUtensilMessage(String sender, String cardIndex){
        return "/" + sender + "/###/utensil/activate/" + cardIndex + "\n";
    }


    /**
     * Il metodo restituisce il messaggio che indica la carta schema scelta dall'utente per giocare la partita.
     * @param sender mittente
     * @param cardIndex indice della carta scelta
     * @return messagio
     */
    public static String getSideReplyMessage(String sender, String cardIndex){
        return "/" + sender + "/###/start/side_reply/" + cardIndex + "\n";
    }

    /**
     * Il metodo restituisce il messaggio che notifica il passaggio del turno da parte del giocatore.
     * @param sender mittente
     * @return messaggio.
     */
    public static String getPassTurnMessage(String sender){
        return "/" + sender + "/###/update/turn/?\n";
    }

    /**
     * Il metodo restituisce il messaggio che specifica l'uso di un utensile da parte di un giocatore.
     * @param sender mittente.
     * @param cardIndex indice della carta utensile.
     * @param numberCard numero della carta utensile.
     * @param paramList lista dei parametri generati dall'uso della carta.
     * @return messaggio.
     */
    public static String getUseUtensilMessage(String sender, String cardIndex, String numberCard, List<String> paramList){
        String message = "/" + sender + "/###/utensil/use/" + cardIndex + "&" + numberCard;

        for(String param: paramList)
            message = message.concat("&" + param);

        return message.concat("\n");
    }


}
