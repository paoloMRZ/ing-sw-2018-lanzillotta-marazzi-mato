package it.polimi.se2018.client.graphic.graphic_element;


import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;

/**
 * Classe utilizzata per la configurazione dei vari bottoni delle finestre tramite i quali il giocatore può interagire con le stesse. Permettq quindi la personalizzazione
 * sulle azioni associate ai bottoni e la costruzione di elementi grafici contenenti i bottoni oppurtunatamente configurati
 *
 * @author Simone Lanzillotta
 */


public class ButtonLabelCreator {

    private static ImageView continueButton;
    private static ImageView backButton;
    private static ImageView startButton;


    /**
     * Metodo Creator per il bottone "Continue"
     *
     * @param continueWidth Larghezza del bottone "Continue"
     * @param continueHeight Altezza del bottone "Continue"
     */

    private static void continueButtonCreator(int continueWidth, int continueHeight){
        continueButton = shadowEffect(configureImageView("","button-continue",".png",continueWidth,continueHeight));
    }


    /**
     * Metoto Creator per il bottone "Back"
     *
     * @param backWidth Larghezza del bottone "Back"
     * @param backHeight Altezza del bottone "Back"
     */

    private static void backButtonCreator(int backWidth, int backHeight){
        backButton = shadowEffect(configureImageView("","button-back",".png",backWidth,backHeight));
    }



    /**
     * Metoto Creator per il bottone "Start"
     *
     * @param startWidth Larghezza del bottone "Back"
     * @param startHeight Altezza del bottone "Back"
     */

    private static void startButtonCreator(int startWidth, int startHeight){
        startButton = shadowEffect(configureImageView("", "button-start-game", ".png", 190, 90));
    }


    /**
     * Metodo utilizzato per la creazione del Label utilizzato nelle varie finestre visualizzate durante la partita.
     *
     * @param continueButton Riferimento all'elemento grafico del "continueButton"
     * @param backButton Riferimento all'elemento grafico del "backButton"
     * @param spacing Spacing orizzontale fra i due bottoni
     * @return Riferimento all'elemento grafico che contiene i due bottoni configurati
     */

    public static HBox setInteractLabel(ImageView continueButton, ImageView backButton, int spacing){
        HBox labelButton = new HBox(spacing);
        labelButton.setAlignment(Pos.CENTER);
        labelButton.getChildren().addAll(continueButton, backButton);
        return labelButton;
    }


    /**
     * /**
     * Metodo Getter utilizzato per accedere al ContinueButton
     *
     * @param continueWidth Larghezza del bottone "Continue"
     * @param continueHeight Altezza del bottone "Continue"
     * @return Riferimento all'elemento grafico continueButton
     */

    public static ImageView getContinueButton(int continueWidth, int continueHeight) {
        continueButtonCreator(continueWidth, continueHeight);
        return continueButton;
    }



    /**
     * Metodo Getter utilizzato per accedere al BackButton
     *
     * @param backWidth Larghezza del bottone "Back"
     * @param backHeight Altezza del bottone "Back"
     * @return Riferimento all'elemento grafico backButton
     */

    public static ImageView getBackButton(int backWidth, int backHeight) {
        backButtonCreator(backWidth, backHeight);
        return backButton;
    }




    /**
     * Metodo Getter utilizzato per accedere al ContinueButton
     *
     * @param startWidth Larghezza del bottone "Back"
     * @param startHeight Altezza del bottone "Back"
     * @return Riferimento all'elemento grafico continueButton
     */

    public static ImageView getStartButton(int startWidth, int startHeight) {
        startButtonCreator(startWidth, startHeight);
        return startButton;
    }

}
