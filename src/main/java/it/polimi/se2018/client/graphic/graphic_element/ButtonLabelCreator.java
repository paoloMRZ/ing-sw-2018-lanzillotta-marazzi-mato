package it.polimi.se2018.client.graphic.graphic_element;


import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;

/**
 * Classe utilizzata per la creazione della Griglia di bottoni di interazioni "Continue" e "Back" utilizzati nelle varie finestre visualizzate durante la partita.
 * Rende disponibile anche l'aggiunta di azioni specifiche da associare ai due bottoni e la configurazione di elementi grafici che li contenga.
 *
 * @author Simone Lanzillotta
 */


public class ButtonLabelCreator {

    private ImageView continueButton;
    private ImageView backButton;


    /**
     * Costruttore della classe. Vengono inizializzati gli attributi continueButton e backButton con le rispettive risorse.
     *
     * @param continueWidth Larghezza del bottone "Continue"
     * @param continueHeight Altezza del bottone "Continue"
     * @param backWidth Larghezza del bottone "Back"
     * @param backHeight Altezza del bottone "Back"
     */

    public ButtonLabelCreator(int continueWidth, int continueHeight, int backWidth, int backHeight){
        backButton = shadowEffect(configureImageView("","button-back",".png",backWidth,backHeight));
        continueButton = shadowEffect(configureImageView("","button-continue",".png",continueWidth,continueHeight));
    }


    /**
     * Costruttore della classe utilizzato per la specifica del solo backButton.
     *
     * @param backWidth Larghezza del bottone "Back"
     * @param backHeight Altezza del bottone "Back"
     */

    public ButtonLabelCreator(int backWidth, int backHeight){
        backButton = shadowEffect(configureImageView("","button-back",".png",backWidth,backHeight));
    }


    /**
     * Metodo utilizzato per la creazione del Label utilizzato nelle varie finestre visualizzate durante la partita.
     *
     * @param spacing Spacing orizzontale fra i due bottoni
     * @return Riferimento all'elemento grafico che contiene i due bottoni configurati
     */

    public HBox setInteractLabel(int spacing){
        HBox labelButton = new HBox(spacing);
        labelButton.setAlignment(Pos.CENTER);
        labelButton.getChildren().addAll(continueButton, backButton);
        return labelButton;
    }



    /**
     * Metodo getter utilizzato per accedere al ContinueButton
     *
     * @return Riferimento all'elemento grafico continueButton
     */

    public ImageView getContinueButton() {
        return continueButton;
    }



    /**
     * Metodo getter utilizzato per accedere al BackButton
     *
     * @return Riferimento all'elemento grafico backButton
     */

    public ImageView getBackButton() {
        return backButton;
    }
}
