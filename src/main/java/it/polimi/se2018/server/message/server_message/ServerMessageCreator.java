package it.polimi.se2018.server.message.server_message;

import java.util.List;

/**
 * La classe è una raccolta di metodi per la creazione dei messaggi che il server invia al client.
 *
 * @author Marazzi Paolo
 * @author Kevin Mato
 */

public class ServerMessageCreator {

    private static final String SERVER_ID = "###";

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
        return "/"+ SERVER_ID  +"/" + addressee + "/success/put/" + index + "&" + row + "&" + col + "\n";
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
        return "/"+ SERVER_ID  +"/"  + addressee + "/error/put/" + index + "&" + row + "&" + col + "\n";
    }

    /**
     * Il messaggio restituito dal metodo indica è stata eseguita una put non autorizzata. Questo significa che il giocatore
     * ha già piazzato un dado nel suo turno.
     *
     * @param addressee destinatario del messaggio.
     * @return messaggio.
     */
    public static String getUnauthorizedMessage(String addressee){
        return "/"+ SERVER_ID  +"/"  + addressee + "/error/unauthorized" + "\n";
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
     * @param favours   favori del giocatore nel momento dell'attivazione.
     * @return messaggio.
     */
    public static String getActivateUtensilSuccessMessage(String addressee, String cardIndex, String cardNumber, String price, String favours) {
        return "/"+ SERVER_ID  +"/"  + addressee + "/success/activate/" + cardIndex + "&" + cardNumber + "&" + price + "&" + favours+"\n";
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
        return "/"+ SERVER_ID  +"/"  + addressee + "/success/use/" + index + "&" + param1 + "&" + param2 + "\n";
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
        return "/"+ SERVER_ID  +"/"  + addressee + "/error/use/" + index +"\n";
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
        return "/"+ SERVER_ID  +"/"  + addressee + "/error/activate/" + index + "&"+ cardNumber + "&"+ cardPrice + "&"+ favours+  "\n";
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
        String message = "/"+ SERVER_ID  +"/"  + addressee + "/start/chose_side/";

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
        return "/"+ SERVER_ID  +"/"  + addressee + "/utensil/end/" + index + "&"+ cardNumber + "&"+ cardPrice + "&"+ favours+  "\n";
    }

    /**
     * Il metodo restiruisce il messaggio da mendare ai client per aggiornarli riguardo la carta schema scelta da ogni giocatore.
     *
     * Attenzione: l'elenco dei nickname e l'elenco dei nomi devono essere ordinati. Il nome della carta scleta dal nickname
     * in posizione zero si deve trovare anch'essa in posizione zero nella lista dei nomi delle carte, e così via.
     *
     * @param list  stringa elenco dei nickname dei giocatori con le relative side.
     * @return messaggio.
     */
    public static String getSideListMessage(String list) {
        String message = "/"+ SERVER_ID  +"/!/start/side_list/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;

    }

    /**
     * Il metodo restiruisce il messaggio da mendare ai client per aggiornarli riguardo la carta schema scelta da ogni giocatore.
     *
     * Attenzione: l'elenco dei nickname e l'elenco dei nomi devono essere ordinati. Il nome della carta scleta dal nickname
     * in posizione zero si deve trovare anch'essa in posizione zero nella lista dei nomi delle carte, e così via.
     *
     * @param list stringa elenco dei nickname dei giocatori con le relative side.
     * @param address destibatario del messaggio.
     * @return messaggio.
     */
    public static String getSideListMessage(String list,String address) {
        String message = "/"+ SERVER_ID  +"/" +address+"/start/side_list/";
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
        String message = "/"+ SERVER_ID  +"/!/start/public_objective/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;
    }
    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali obiettivi pubblici
     * sono stati estratti.
     *
     * @param list stringa lista dei nomi degli obiettivi pubblici.
     * @param address destinatario.
     * @return messaggio.
     */
    public static String getPublicObjectiveMessage(String list,String address) {
        String message = "/"+ SERVER_ID  +"/" +address+"/start/public_objective/";
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
        String message = "/"+ SERVER_ID  +"/!/start/utensil/";
        message = message.concat(list);
        message = message.concat("\n");
        return message;

    }
    /**
     * Il metodo restituisce il messaggio da mandare ai client ad inizio partita per informarli su quali carte utensili
     * sono state estratte.
     *
     * @param list stringa lista dei nomi deelle carte utensili.
     * @param dest destinatario
     * @return messaggio.
     */
    public static String getSendUtensilsMessage(String list,String dest) {
        String message = "/"+ SERVER_ID  +"/" +dest+"/start/utensil/";
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
        return "/"+ SERVER_ID  +"/" + addressee + "/start/private_objective/" + cardName + "\n";
    }


    //Messaggi relativi all'aggiornamento della partita.


    /**
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo round.
     *
     * @param infoReserve lista dei dadi rimasti nella riserva che devono essere aggiunti alla roundgrid.
     * @return messaggio.
     */
    public static String getUpdateRoundMessage(String infoReserve) {
        return "/"+ SERVER_ID  +"/!/update/round/" + infoReserve + "\n";
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
     * Il metodo restiuisce il messaggio da mandare ai client per informarli dell'inizio di un nuovo turno.
     *
     * @param nickname nickaname del giocatore che deve giocare il turno.
     * @param dest destinatario.
     * @return messaggio.
     */
    public static String getUpdateTurnMessage(String nickname,String dest) {
        return "/"+ SERVER_ID  +"/" +dest+"/update/turn/" + nickname + "\n";
    }

    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della riserva.
     *
     * @param infoReserve rappresentazione in stringa della riserva.
     * @return messaggio.
     */
    public static String getUpdateReserveMessage(String infoReserve) {
        return "/"+ SERVER_ID  +"/!/update/reserve/" + infoReserve + "\n";
    }
    /**
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della riserva.
     *
     * @param infoReserve rappresentazione in stringa della riserva.
     * @param dest destinatario.
     * @return messaggio.
     */
    public static String getUpdateReserveMessage(String infoReserve,String dest) {
        return "/"+ SERVER_ID  +"/" +dest+"/update/reserve/" + infoReserve + "\n";
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
     * Il metodo restituisce il messaggio da inviare ai client per aggiornarli sullo stato della roundgrid.
     *
     * @param infoRoundgrid rappresentazione della roundgrid.
     * @param dest destinatario.
     * @return messaggio.
     */
    public static String getUpdeteRoundgridMessage(String infoRoundgrid,String dest) {
        return "/"+ SERVER_ID  +"/" +dest+"/update/roundgrid/" + infoRoundgrid + "\n";
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

    /**
     * Il metodo restituisce il messaggio da mandare ai client per aggiornarli sullo stato di una carta schema.
     *
     * @param infoSide rapressentazione della side.
     * @param dest destinatario
     * @return messaggio.
     */
    public static String getUpdateSideMessage(String infoSide,String dest) {
        return "/"+ SERVER_ID  +"/" +dest+"/update/side/" +  infoSide + "\n";
    }


    //Messaggio relativo alla rete.

    /**
     * Il metodo restiutisce il messaggio da mandare al gestore delle connessioni per richiedere il congelamento di un client.
     *
     * @param addressee nickname del destinatario del messaggio, cioè il client da congelare
     * @return messaggio.
     */
    public static String getTimeoutMessage(String addressee) {
        return "/"+ SERVER_ID  +"/"  + addressee + "/network_message/timeout/?\n";
    }

    /**
     * Il metodo restituisce un messaggio che indica che un giocatore è stato congelato.
     * @param nickname nickname del giocatore che è stato congelato.
     * @return messaggio.
     */
    public static String getFreezeMessage(String nickname){
        return "/###/###/network_message/freeze/"+ nickname  +"\n";
    }

    /**
     * Il metodo restituisce un messaggio che il controller utilizza per richiedere alla lobby di disconnettere un giocatore.
     * ATTENZIONE: Si tratta di una disconnessione definitiva, non di un congelamento.
     *
     * @param nickname nickname del giocatore da disconnettere.
     * @return message.
     */
    public static String getDisconnectedMessage(String nickname){return "/###/###/network_message/disconnected/"+ nickname  +"\n";}

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
        else return null;

       return message;
    }

    /**
     * Il metodo restituisce il messaggio da mandare ai client per aggiornarli sui costi delle carte utensile.
     * @param costs stringa con i costi delle utensili
     * @return messaggio.
     */
    public static String getUpdatePriceMessage(String costs){
        if(costs!=null) {
            return "/###/!/update/price/" + costs;
        }
        else return  null;
    }
    /**
     * Il metodo restituisce il messaggio da mandare a un client per aggiornarlo sui costi delle carte utensile.
     * @param costs stringa con i costi delle utensili
     * @param dest destinatario
     * @return messaggio.
     */
    public static String getUpdatePriceMessage(String costs,String dest){
        if(costs!=null && dest!=null) {
            return "/###/"+dest+"/update/price/" + costs;
        }
        else return  null;
    }

    /**
     * Il metodo si occupa di inviare n messaggio privato a un giocatore aggiornandolo sui suoi favori.
     * @param favs numero favori
     * @param dest destinatario
     * @return messaggio privato
     */
    public static String getUpdateFavsMessage(String favs,String dest){
        if(favs!=null && dest!=null) {
            return "/###/"+dest+"/update/favours/" + favs;
        }
        else return  null;
    }

}