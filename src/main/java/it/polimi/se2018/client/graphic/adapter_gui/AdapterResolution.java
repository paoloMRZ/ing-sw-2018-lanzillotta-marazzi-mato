package it.polimi.se2018.client.graphic.adapter_gui;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Interfaccia adibita all'adattamento della schermata di gioco a risoluzioni preimpostate. La scelta di programamre il resize della schermata verte sulla possibilità di
 * riuscire ad interagire con l'interfaccia grafica in modo semplice e maggiormente comprensibile, soprattutto senza intaccara la User Experience. Le risoluzioni
 * supportate sono le seguenti:
 *
 *  - 1920 x 1080
 *  - 1400 x 900
 *  - 1366 x 768
 *
 *  L'interfaccia obbliga quindi le classi che la implementano a specificare le dimensioni dei seguenti elementi grafici:
 *
 *  - Griglia delle informazioni riguardanti la partita in corso (Nome Giocatore, Segnalini Azione, Segnalini Favore, Nome del Turnante)
 *  - Carta Side del giocatore
 *  - Griglia di bottini per l'interazione da parte dell'utente
 *  - Riserva disponibile nel turno
 *  - Griglia delle carte Side dei rispettivi avversari
 *  - Carta Obbiettivo Privata
 *  - Griglia contenente le carte Utensili e le carte Obbiettivo Pubbliche
 *  - Griglia dei Round
 *  - Finestra di selezione della carta Side
 *  - Finestra di interazione con le carte Utensili
 *
 */


public interface AdapterResolution {

    List<Integer> getSettingLabelSize();
    List<List<? extends Number>> getSidePlayerSize();
    List<Integer> getButtonLabelSize();
    List<Integer> getReserveLabelSize();
    List<Integer> getReserveLabelUtensilSize();
    List<List<? extends Number>> getSideEnemyLabelSize();
    List<Integer> getObjectivePrivateSize();
    List<Integer> getPublicCardSize();
    List<Integer> getTabCardLabelSize();
    List<List<? extends Number>> getRoundLabelSize();
    List<List<Integer>> getSideChoiceLabelSize();
    List<Integer> getPrimaryStageSize();
    List<List<? extends Number>> getSidePlayerUtensilSize();

    void putSettingLabel(AnchorPane root, HBox settingLabel);
    void putSideLabel(AnchorPane root, AnchorPane sidePlayer);
    void putSideEnemyLabel(AnchorPane root, HBox enemiesCard);
    void putButtonLabel(AnchorPane root, VBox buttonLabel);
    void putReserveLabel(AnchorPane root, HBox reserveLabel);
    void putTabPaneLabel(AnchorPane root, BorderPane pubblicCard);
    void putRoundGridLabel(AnchorPane root, AnchorPane roundGrid);
    void putPrivateObjectiveLabel(AnchorPane root, AnchorPane privateObjective);


}
