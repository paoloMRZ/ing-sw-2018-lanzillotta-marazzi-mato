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

import static it.polimi.se2018.client.graphic.Utility.*;


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
        group = new Group(rect);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(2d);


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

        HBox hBox = new HBox(5);
        HBox hBoxHistory = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        hBoxHistory.setAlignment(Pos.CENTER);
        for (String aDieInfo : dieInfo) {
            String lowerDieInfo = aDieInfo.toLowerCase(Locale.ENGLISH);
            ImageView imageDie = configureImageView("/diePack/die-", lowerDieInfo, ".bmp", sizeProceed.get(2), sizeProceed.get(2));
            imageDie.setFitWidth(sizeProceed.get(2));
            imageDie.setFitHeight(sizeProceed.get(2));
            hBox.getChildren().add(imageDie);

            ImageView elementHistory = shadowEffect(configureImageView("/diePack/die-", lowerDieInfo, ".bmp", 177, 177));
            elementHistory.setFitWidth(sizeProceed.get(3));
            elementHistory.setFitHeight(sizeProceed.get(3));
            StackPane button = new StackPane(elementHistory);
            cell.put(button, false);
            elementHistory.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if(!cell.get(button)){
                            button.getChildren().add(group);
                            cell.replace(button, false, true);
                        }
                        else {
                            button.getChildren().remove(group);
                            cell.replace(button, true, false);
                        }

                        dieFromRoundSelected = String.valueOf(dieInfo.indexOf(aDieInfo));

            });
            hBoxHistory.getChildren().addAll(button);
        }

        roundHistory.add(hBoxHistory);
        roundNumber++;
        MenuItem item = new MenuItem("", hBox);
        contextMenu.getItems().add(item);
        passed.setFitHeight(60d);
        passed.setFitWidth(60d);
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

    public static int getRoundNumber() {
        return roundNumber;
    }
}
