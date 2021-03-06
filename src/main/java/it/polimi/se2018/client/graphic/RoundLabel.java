package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


/**
 * Classe RoundLabel utilizzata per la creazione dell'elemento grafico contenente la Griglia di conteggio dei round e i dadi
 * residue da ogni turni della partita.
 *
 * @author Simone Lanzillotta
 */


public class RoundLabel{

    private int column = 0;
    private AnchorPane anchorRound;
    private GridPane labelRoundGrid;

    private static ArrayList<HBox> roundHistory= new ArrayList<>();
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;
    private static String dieFromRoundSelected;
    private static String roundFromPicked;
    private static int roundNumber = 0;
    private AdapterResolution adapter;


    /**
     * Costruttore della Classe
     *
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    @SuppressWarnings("unchecked")
    RoundLabel(AdapterResolution adapterResolution){

        //Configurazione adapter per il dimensionamento
        this.adapter = adapterResolution;
        ArrayList<Integer> sizeRect = (ArrayList<Integer>) adapter.getRoundLabelSize().get(0);

        //Configurazione dell'elemento grafico
        configureRoundGrid();

        //Configurazione effetto Focus
        Rectangle rect = new Rectangle(20, 20, sizeRect.get(0), sizeRect.get(0));
        group = new Group();
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2d);
        group.getChildren().add(rect);

    }



    /**
     * Metodo utilizzato per la configurazione dell'elemento grafico contenente la RoundGrid
     *
     */

    @SuppressWarnings("unchecked")
    private void configureRoundGrid() {

        //Lista di dimensionamento
        ArrayList<Integer> roundSize = (ArrayList<Integer>)adapter.getRoundLabelSize().get(0);
        ArrayList<Double> positionGrid = (ArrayList<Double>)adapter.getRoundLabelSize().get(1);


        //Creo l'anchorPane che accoglierà background (l'immagine del pannello del round) e la griglia posta su di esso
        anchorRound = new AnchorPane();
        anchorRound.setStyle("-fx-background-size: " + String.valueOf(roundSize.get(1)) + "; -fx-background-image: url(roundgrid.png); -fx-background-position: center; -fx-background-repeat: no-repeat;");

        //Configurazione della Griglia per la segnatira dei Round passati
        labelRoundGrid = new GridPane();
        labelRoundGrid.setAlignment(Pos.CENTER_RIGHT);
        labelRoundGrid.setHgap(0d);
        labelRoundGrid.setVgap(0d);

        for (int i = 0; i < 10; i++) {
            StackPane button = new StackPane();
            button.setPrefSize(roundSize.get(2), roundSize.get(2));
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
            labelRoundGrid.add(button, i, 0);
        }

        configureAnchorPane(anchorRound,labelRoundGrid,positionGrid.get(0),positionGrid.get(1),positionGrid.get(2),positionGrid.get(3));
    }



    /**
     * Metodo richiamato per aggiungere un segnaposto sul round appena concluso.
     *
     * @param dieInfo Ruferimento alla lista di dadi residui da aggiungere alla RoundGrid
     */

    @SuppressWarnings("unchecked")
    public void proceedRound(List<String> dieInfo){

        //Lista dimensionamento
        ArrayList<Integer> sizeProceed = (ArrayList<Integer>) adapter.getRoundLabelSize().get(2);

        //Configurazione immagine da inserire nella Round
        ImageView passed = shadowEffect(configureImageView("/iconPack/", "icon-divieto",".png",256,256));
        passed.setFitWidth(sizeProceed.get(0));
        passed.setFitHeight(sizeProceed.get(1));

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
        passed.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> contextMenu.show(passed, Side.BOTTOM, e.getScreenX(),e.getScreenY()));

        //Creo le collezioni di elementi che andranno nella RoundGrid e nelle carte Utensili che operano sulla roundGrid
        HBox hBox = new HBox(5);
        HBox hBoxHistory = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBoxHistory.setAlignment(Pos.CENTER);

        for (String aDieInfo : dieInfo) {

            //Estraggo l'informazione del dado da aggiungere nella roundGrid
            String lowerDieInfo = aDieInfo.toLowerCase(Locale.ENGLISH);

            //Controllo che il contenuto residuo della riserva da aggiungere alla RoundGrid non sia vuoto
            ImageView imageDie;
            ImageView elementHistory;
            if (!lowerDieInfo.equals("white0")) {
                imageDie = configureImageView("/diePack/die-", lowerDieInfo, ".bmp", sizeProceed.get(2), sizeProceed.get(2));
                elementHistory = shadowEffect(configureImageView("/diePack/die-", lowerDieInfo, ".bmp", sizeProceed.get(3), sizeProceed.get(3)));
            }
            else {
                imageDie = configureImageView("/iconPack/", "icon-empty", ".png", sizeProceed.get(2), sizeProceed.get(2));
                elementHistory = configureImageView("/iconPack/", "icon-empty", ".png", sizeProceed.get(3), sizeProceed.get(3));
            }

            //Aggiungo il dado all'elemento grafico da porre nella roundGrid
            hBox.getChildren().add(imageDie);

            StackPane button = new StackPane(elementHistory);
            button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
            cell.put(button, false);
            elementHistory.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    setFocusStyle(cell,button,group);
                    dieFromRoundSelected = String.valueOf(dieInfo.indexOf(aDieInfo));
                    roundFromPicked = String.valueOf(roundNumber-1);
            });

            //Aggiungo il dado formattato nell'elemento grafico da porre nella finestra di utilizzo delle carte Utensili
            hBoxHistory.getChildren().add(button);
        }

        //Aggiungo l'elemento graficoalla collezione di ripristino
        roundHistory.add(hBoxHistory);

        //Incremento il contatore di Round
        roundNumber++;

        MenuItem item = new MenuItem("", hBox);
        contextMenu.getItems().add(item);
        labelRoundGrid.add(passed, 9 - column, 0);
        column++;
    }




    /**
     * Metodo Getter che restituisce un riferimento al Parent dell'elemento grafico
     *
     * @return Riferimento al Parent anchorRound
     */

    public AnchorPane getAnchorRound() {
        return anchorRound;
    }




    /**
     *Metodo utilizzato per restituire una collezione di elementi grafici contenenti le informazioni sui dadi collocati sulla RoundGrid
     *
     * @return Riferimento alla collezione roundHistory
     */

    public static List<HBox> callRoundLable(){
        return roundHistory;
    }




    /**
     * Metodo utilizzato per prelevare l'informazione sul dado selezionato tra quelli posizionati sulla roundGrid
     *
     * @return String contenente la posizione del dado scelto
     */

    public static String getDieFromRoundSelected() {
        return dieFromRoundSelected;
    }




    /**
     * Metodo utilizzato per prelevare l'informazione del Round da cui si è prelevato un eventuale dado
     *
     * @return String contenente il numero del Round selezionato
     */

    public static String getRoundNumber() {
        return roundFromPicked;
    }
}
