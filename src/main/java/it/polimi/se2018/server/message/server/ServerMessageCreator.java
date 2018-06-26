package it.polimi.se2018.server.message.server;

import java.util.List;

/**
 * La classe è una raccolta di metodi per la creazione dei messaggi che il server invia al client.
 *
 * @author Marazzi Paolo
 */

public class ServerMessageCreator {

    private ServerMessageCreator() {
    }

    /**
     * Il messaggio restituito dal metodo indica che la put effettuata dal client è andata a buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che rappresenta il dado della riserva scelto per l'inserimento.
     * @param row       riga della cella su cui è stato effettuato l'inserimento.
     * @param col       colonna della cella su cui è stato effettuato l'inserimento.
     * @return messaggio.
     */
    public static String getPutDieSuccesMessage(String addressee, String index, String row, String col) {
        return "/###/" + addressee + "/success/put/" + index + "&" + row + "&" + col + "\n";
    }


    /**
     * Il messaggio restituito dal metodo indica che la put effettuata dal client non è andata a buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che rappresenta il dado della riserva scelto per l'inserimento.
     * @param row       riga della cella su cui è stato effettuato l'inserimento.
     * @param col       colonna della cella su cui è stato effettuato l'inserimento.
     * @return messaggio.
     */
    public static String getPutDieErrorMessage(String addressee, String index, String row, String col) {
        return "/###/" + addressee + "/error/put/" + index + "&" + row + "&" + col + "\n";
    }

    //Messaggi relativi all'uso degli utensili.

    /**
     * Il messaggio restituito dal metodo indica che l'attivazione della carta utensile scelta dal client è andata a
     * buon fine.
     *
     * @param addressee  destinatario del messaggio.
     * @param cardIndex  indice che identifica la carta utensile scelta dal client.
     * @param cardNumber numero della carta utensile.
     * @param price      nuovo prezzo della carta utensile che è stata attivata.
     * @param favours
     * @return messaggio.
     */
    public static String getActivateUtensilSuccessMessage(String addressee, String cardIndex, String cardNumber, String price, String favours) {
        return "/###/" + addressee + "/success/activate/" + cardIndex + "&" + cardNumber + "&" + price + "&" + favours+"\n";
    }

    /**
     * Il metodo restituisce un messaggio che indica che l'uso di una carta utensile è andato a buon fine.
     * Alcune carte utensili devono restituire informazioni ai giocatori, queste informazioni sono contenute nelle variabili
     * param1 e param2.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice della carta.
     * @param param1    primo parametro.
     * @param param2    secondo parametro.
     * @return messaggio.
     */
    public static String getUseUtensilSuccessMessage(String addressee, String index, String param1, String param2) {
        return "/###/" + addressee + "/success/use/" + index + "&" + param1 + "&" + param2 + "\n";
    }

    /**
     * Il messaggio restituito dal metodo indica che l'uso della carta utensile scelta dal client non è andato a
     * buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che identifica la carta utensile scelta dal client.
     * @return messaggio.
     */
    public static String getUseUtensilErrorMessage(String addressee, String index) {
        return "/###/" + addressee + "/error/use/" + index +"\n";
    }

    /**
     * Il messaggio restituito dal metodo indica che l'attivazione della carta utensile scelta dal client non è andato a
     * buon fine.
     *
     * @param addressee destinatario del messaggio.
     * @param index     indice che identifica la carta utensile scelta dal client.
     * @param cardNumber numero della carta
     * @param cardPrice prezzo della carta in una determinata fase
     * @param favours favori del giocatore
     * @return messaggio.
     */
    public static String getActivateUtensilErrorMessage(String addressee, String index, String cardNumber, String cardPrice, String favours ) {
        return "/###/" + addressee + "/error/activate/" + index + "&"+ cardNumber + "&"+ cardPrice + "&"+ favours+  "\n";
    }
    //Messaggi relativi all'avvio della partita.


    /**
     * Il metodo restituisce il messaggio da mandare al client a inizio partita per permettergli di scegliere
     * con quale carta schema giocare.
     * Le informazioni passate al client sono i nomi delle carte ed il numero di segnalini favore
     * associati ad ogni carta.
     *
     * @param addressee destinatario del messaggio.
     * @param cardList  coppie di valori nome carta, segnalini favore.
     * @return messaggio.
     */
    public static String getChoseSideMessage(String addressee, List<String> cardList) {
        String message = "/###/" + addressee + "/start/chose_side/";

        for (int i = 0; i < cardList.size(); i = i + 2) {
            if (i == 0)
                message = message.concat(cardList.get(i) + "&" + cardList.get(i + 1));
            else
                message = message.concat("&" + cardList.get(i) + "&" + cardList.get(i + 1));
        }


        message = message.concat("\n");

        return message;
    }


    /**
     * Il metodo restituisce il messaggio da mandare al client avvisarlo che la fase di gioco della carta utensile è
     * terminata.
     *
     * @param addressee destinatario del messaggio.
     * @param index indice della carta utensile.
     * @param cardNumber numero della carta
     * @param cardPrice prezzo della carta in una determinata fase
     * @param favours favori del giocatore
     * @return messaggio.
     */
    public static String getUseUtensilEndMessage(String addressee, String index, String cardNumber, String cardPrice, String favours ){
        return "/###/" + addressee + "/utensil/end/" + index + "&"+ cardNumber + "&"+ cardPrice + "&"+ favours+  "\n";
    }

    /**
     * Il metodo restiruisce il messaggio da mendare ai client per aggiornarli riguardo la carta schema scelta da ogni giocatore.
     *
     * Attenzione: l'elenco dei nickname e l'elenco dei nomi devono essere ordinati. Il nome della carta scleta dal nickname
     * in posizione zero si deve trovare anch'essa in posizione zero nella lista dei nomi delle carte, e così via.
     *
     * @param list  stringa elenco dei nickname dei giocatori.
     * @return messaggio.
     */
    public static String getSideListMessage(String list) {
        String message = "/###/!/start/side_list/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;

    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali obiettivi pubblici
     * sono stati estratti.
     *
     * @param list stringa lista dei nomi degli obiettivi pubblici.
     * @return messaggio.
     */
    public static String getPublicObjectiveMessage(String list) {
        String message = "/###/!/start/public_objective/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali carte utensili
     * sono state estratte.
     *
     * @param list stringa lista dei nomi deelle carte utensili.
     * @return messaggio.
     */
    public static String getSendUtensilsMessage(String list) {
        String message = "/###/!/start/utensil/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;

    }

    /**
     * Il metodo restituisce il messaggio da mandare al client ad inizio partita per informarlo su quale sia il suo obiettivo privato.
     *
     * @param addressee nickname del destinatario del messaggio.
     * @param cardName  nome dell'obiettivo privato.
     * @return messaggio.
     */
    public static String getPrivateObjectiveMesage(String addressee, String cardName) {
        return "/###/"+ addressee + "/start/private_objective/" + cardName + "\n";
    }


    //Messaggi relativi all'aggiornamento della partita.


    /**
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo round.
     *
     * @param nickname    nickaname del giocatore che deve giocare il primo turno del round.
     * @param infoReserve lista dei dadi rimasti nella riserva che devono essere aggiunti alla roundgrid.
     * @return messaggio.
     */
    public static String getUpdateRoundMessage(String nickname, String infoReserve) {
        return "/###/!/update/round/" + nickname + "&" + infoReserve + "\n";
    }


    /**
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo turno.
     *
     * @param nickname nickaname del giocatore che deve giocare il turno.
     * @return messaggio.
     */
    public static String getUpdateTurnMessage(String nickname) {
        return "/###/!/update/turn/" + nickname + "\n";
    }

    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della riserva.
     *
     * @param infoReserve rappresentazione in stringa della riserva.
     * @return messaggio.
     */
    public static String getUpdateReserveMessage(String infoReserve) {
        return "/###/!/update/reserve/" + infoReserve + "\n";


    }

    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della roundgrid.
     *
     * @param infoRoundgrid rappresentazione della roundgrid.
     * @return messaggio.
     */
    public static String getUpdeteRoundgridMessage(String infoRoundgrid) {
        return "/###/!/update/roundgrid/" + infoRoundgrid + "\n";
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client per aggiornarli sullo stato di una carta schema.
     *
     * @param infoSide rapressentazione della side.
     * @return messaggio.
     */
    public static String getUpdateSideMessage(String infoSide) {
        return "/###/!/update/side/" +  infoSide + "\n";
    }


    //Messaggio relativo alla rete.

    /**
     * Il metodo restiutisce il messaggio da mandare al gestore delle connessioni per richiedere il congelamento di un client.
     *
     * @param addressee nickname del destinatario del messaggio, cioè il client da congelare
     * @return messaggio.
     */
    public static String getTimeoutMessage(String addressee) {
        return "/###/" + addressee + "/network/timeout/?\n";
    }

    /**
     * Il metodo restituisce un messaggio che indica la fine della partita e contiene il punteggio associato ad ogni
     * giocatore.
     *
     * @param statistics elenco dei nickname.
     * @return messaggio.
     */
    public static String getEndGameMessage(String statistics){
        String message = "/###/!/end/winner/";
        if(statistics!=null) message = message.concat(statistics);

       /* int i = 0; //(List<String> nicknames, List<String> scores, String nameOfWinner) prototipo vecchio

        for (String nick : nicknames) {

            if (i == 0)
                message = message.concat(nick + "&" + scores.get(i));
            else
                message = message.concat("&" + nick + "&" + scores.get(i));

            i++;
        }

        return message.concat("&" + nameOfWinner + "\n");*/

       return message;
    }


}