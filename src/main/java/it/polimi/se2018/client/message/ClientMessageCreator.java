package it.polimi.se2018.client.message;

import java.util.List;

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


    public static String getActivateUtensilMessage(String sender, String cardIndex){
        return "/" + sender + "/###/utensil/activate/" + cardIndex + "\n";
    }


    public static String getSideReplyMessage(String addressee, String cardIndex){
        return "/" + addressee + "/###/start/side_reply/" + cardIndex + "\n";
    }

    public static String getPassTurnMessage(String sender){
        return "/" + sender + "/###/update/turn/?\n";
    }

    public static String getUseUtensilMessage(String sender, String cardIndex, String numberCard, List<String> paramList){
        String message = "/" + sender + "/###/utensil/use/" + cardIndex + "&" + numberCard;

        for(String param: paramList)
            message = message.concat("&" + param);

        return message.concat("\n");
    }


}
