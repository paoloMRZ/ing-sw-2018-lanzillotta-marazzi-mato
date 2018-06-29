package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

import static it.polimi.se2018.client.graphic.Utility.*;

/**
 * Classe TabCardLabel rappresenta l'elemento grafico che permette la visualizzazione delle carte Utensili e carte Obbiettivo pubbliche estratte per la sessione di gioco
 *
 * @author Simone Lanzillotta
 */


public class TabCardLabel {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/iconPack/";

    private TabPane tabPane;
    private Tab utensilsTab;
    private Tab objectiveTab;
    private BorderPane borderPane;
    private AdapterResolution adapter;


    /**
     * Costruttore della classe. Inizializza il TabPane che ospiterà le varie finestra con le carte Utensili e Obbiettivo Pubblico disponibili
     * per la partita in corso
     *
     */


    public TabCardLabel(AdapterResolution adapterResolution) {
        tabPane = new TabPane();
        borderPane = new BorderPane();
        utensilsTab = new Tab();
        objectiveTab = new Tab();
        this.adapter = adapterResolution;
        borderPane.setCenter(tabPane);
    }


    /**
     * Metodo utilizzato per configurare la TAB relativa alle carte Utensili
     *
     * @param cardUtensils Riferimento all'elemento grafico che in cui sono contenute le carte Utensili
     */

    public void configureTabUtensils(AnchorPane cardUtensils){
        configureTab(utensilsTab, "Carte Utensili", SUBDIRECTORY, "icon-utensils", EXTENSION, cardUtensils);

    }


    /**
     * Metodo utilizzato per configurare la TAB relativa alle carte Obbiettivo Pubbliche
     *
     * @param cardObjective Riferimento all'elemento grafico che in cui sono contenute le carte Obbiettivo Pubbliche
     */

    public void configureTabObjective(AnchorPane cardObjective){
        configureTab(objectiveTab, "Obbiettvi Pubblici", SUBDIRECTORY,"icon-objective", EXTENSION, cardObjective);
    }


    /**
     * Metodo utilizzato per configurare la TAB del tipo richiesto (se per le carte Utensili o per le carte Obbiettivo). La fase di creazione è stata volutamente divisa
     * in due fasi per evitare che la MainClass (InitWindow) debba richiamare il metodo inserendo solo dati di formattazione che repito debbano rimanere invisibili all
     * utente finale.
     *
     * @param tab Riferimento alla TAB che si intende formattare e cinfigurare
     * @param title Titolo della TAB
     * @param subDirectory SubDirectory della risorsa utilizzata come icona per la TAB
     * @param nameResources Nome della risorsa utilizzata come icona
     * @param extension Estensione della risorsa richiesta per l'utilizzo
     * @param content Riferimento al contenuto che si intende aggiugnere al TabPane
     */

    private void configureTab(Tab tab, String title, String subDirectory, String nameResources, String extension, AnchorPane content) {


        //Lista di dimensionamento
        ArrayList<Integer> tabSize = (ArrayList<Integer>)adapter.getTabCardLabelSize();
        int imageSize = tabSize.get(0);

        //Configurazione dell'icona della TAB
        ImageView imageView = configureImageView(subDirectory, nameResources, extension, imageSize,imageSize);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);

        //Configurazione del testo della TAB
        Label label = setFontStyle(new Label(title),tabSize.get(1));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: transparent;");

        //Unione dell'icona e dele testo della TAB
        HBox contentTab = new HBox(tabSize.get(2));
        contentTab.setAlignment(Pos.CENTER);
        contentTab.getChildren().addAll(imageView, label);
        contentTab.setStyle("-fx-background-color: transparent;");

        //Impostazione finale della TAB
        tab.setGraphic(contentTab);
        tab.setStyle("-fx-background-color: white; -fx-pref-width: " + tabSize.get(3) + "; -fx-pref-height: " + tabSize.get(4) + "; -fx-alignment: center; -fx-focus-color: gray; -fx-faint-focus-color: transparent;");
        tab.setClosable(false);
        tab.setContent(content);
        tabPane.getTabs().add(tab);

    }

    
    /**
     * Metodo getter che restituisce l'oggetto Group root
     *
     * @return Riferimento all'elemento grafico configurato
     */

    public BorderPane getGroupPane() {
        return borderPane;
    }
}
