package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


public class SideCardLabel{

    private static final String EMPTYCELL = "white0";

    private String nickName;
    private String posX;
    private String posY;
    private AnchorPane anchorPane;
    private GridPane gridPane;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group;
    private ArrayList<String> dicePutHistory;
    private String pathCard;
    private AdapterResolution adapter;



    /**
     * Costruttore della classe
     *
     * @param sideCard Nome della carta selezionata dal giocatore
     * @param nickName Nome del giocatore a cui è associata la carta
     * @param includeShadowGrid Booleano che indica la richiesta di aggiunta dell'effetto "Evidenzia Cella" (TRUE)
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    @SuppressWarnings("unchecked")
    SideCardLabel(String sideCard, String nickName, boolean includeShadowGrid, boolean isCallUtensil, AdapterResolution adapterResolution){

        this.nickName = nickName;
        this.pathCard = sideCard;
        this.adapter = adapterResolution;

        //Configuro la raccolta iniziale di dadi posizionati sulla Side (Al momentodella creazione composta da tutti dadi white0)
        setInitDicePutHistory();

        //Creo e dimensiono la cella della griglia posta sulla carta Side
        setGridSide(includeShadowGrid, isCallUtensil);

        //Creo l'immagine di background e Aggiungo la gridPane all'anchorPane
        setAnchorSide(sideCard,includeShadowGrid);

        //Posiziono la griglia sull'anchorPane creato
        setPositionGrid(includeShadowGrid,isCallUtensil);

        ArrayList<Double> sizeRect;
        if(!isCallUtensil) sizeRect = (ArrayList<Double>) adapter.getSidePlayerSize().get(3);
        else sizeRect = (ArrayList<Double>) adapter.getSidePlayerUtensilSize().get(2);
        if(includeShadowGrid) {
            group = new Group();
            Rectangle rect = new Rectangle(20, 20, sizeRect.get(0), sizeRect.get(1));
            rect.setFill(Color.TRANSPARENT);
            rect.setStroke(Color.RED);
            rect.setStrokeWidth(2d);
            group.getChildren().add(rect);
        }
    }



    /**
     * Metodo utilizzato per la configurazione della griglia posta sulla cara Side per il posizionamento dei dadi
     *
     * @param includeShadowGrid Booleano che indica la richiesta di aggiunta dell'effetto "Evidenzia Cella" (TRUE)
     */

    @SuppressWarnings("unchecked")
    private void setGridSide(boolean includeShadowGrid, boolean isCallUtensil){
        gridPane = new GridPane();
        ArrayList<Integer> gridSize;
        if(includeShadowGrid) {
            if(!isCallUtensil) gridSize = (ArrayList<Integer>) adapter.getSidePlayerSize().get(0);
            else gridSize = (ArrayList<Integer>) adapter.getSidePlayerUtensilSize().get(1);
        }
        else gridSize = (ArrayList<Integer>) adapter.getSideEnemyLabelSize().get(0);
        for(int i=0; i<4; i++){
            for(int j=0;j<5;j++){
                StackPane button = new StackPane();
                button.setAlignment(Pos.CENTER);
                button.setPrefSize(gridSize.get(0),gridSize.get(1));
                button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");
                if(includeShadowGrid) {
                    cell.put(button, false);
                    button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setStyle("-fx-background-color: rgba(83, 83, 83, 0.5);"));
                    button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setStyle("-fx-background-color: transparent;"));
                    button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        setFocusStyle(cell,button,group);
                        posX = String.valueOf(GridPane.getRowIndex(button));
                        posY = String.valueOf(GridPane.getColumnIndex(button));
                    });
                }
                gridPane.add(button, j, i);
            }
        }

        gridPane.setAlignment(Pos.CENTER);
    }



    /**
     * Metodo utilizzato per la configurazione dello sfondo dell'anchorPane sui cui sarà posta la griglia
     *
     * @param sideCard Riferimento al nome della carta Side da impostare come sfondo
     * @param includeShadowGrid Booleano che indica la richiesta di aggiunta dell'effetto "Evidenzia Cella" (TRUE)
     */

    @SuppressWarnings("unchecked")
    private void setAnchorSide(String sideCard, Boolean includeShadowGrid) {
        anchorPane = new AnchorPane();
        ArrayList<Integer> sideSize;
        if (includeShadowGrid) {
            sideSize = (ArrayList<Integer>) adapter.getSidePlayerSize().get(1);
            anchorPane.setStyle("-fx-background-image: url(cardPack/" + sideCard + ".png); -fx-background-size:" + String.valueOf(sideSize.get(0)) + " " + String.valueOf(sideSize.get(1)) + "; -fx-background-position: center; -fx-background-repeat: no-repeat;");
            anchorPane.setPrefSize(sideSize.get(0), sideSize.get(1));
        } else {
            sideSize = (ArrayList<Integer>) adapter.getSideEnemyLabelSize().get(1);
            anchorPane.setStyle("-fx-background-image: url(cardPack/" + sideCard + ".png); -fx-background-size:" + String.valueOf(sideSize.get(0)) + " " + String.valueOf(sideSize.get(1)) + "; -fx-background-position: center; -fx-background-repeat: no-repeat;");
            anchorPane.setPrefSize(sideSize.get(0), sideSize.get(1));
        }
    }


    /**
     * Metodo utilizzato per posizionare la griglia sull'anchorPane
     *
     * @param includeShadowGrid Booleano che indica la richiesta di aggiunta dell'effetto "Evidenzia Cella" (TRUE)
     *
     */

    @SuppressWarnings("unchecked")
    private void setPositionGrid(Boolean includeShadowGrid,Boolean isCallUtensil){
        ArrayList<Double> positionGrid;
        if(includeShadowGrid){
            if(!isCallUtensil) positionGrid = (ArrayList<Double>) adapter.getSidePlayerSize().get(2);
            else positionGrid = (ArrayList<Double>) adapter.getSidePlayerUtensilSize().get(0);
            configureAnchorPane(anchorPane,gridPane,positionGrid.get(0),positionGrid.get(1),positionGrid.get(2),positionGrid.get(3));
        }
        else{
            positionGrid = (ArrayList<Double>) adapter.getSideEnemyLabelSize().get(2);
            configureAnchorPane(anchorPane,gridPane,positionGrid.get(0),positionGrid.get(1),positionGrid.get(2),positionGrid.get(3));
        }
    }


    /**
     * Metodo utilizzato per l'update della carta Side dopo l'avvenuto inserimento da parte del giocatore
     *
     * @param cellUpdate Riferimento alla lista di informazioni sulle celle della Side
     */

    @SuppressWarnings("unchecked")
    public void updateSideAfterPut(List<String> cellUpdate){
        int imageSize = (Integer)adapter.getSidePlayerSize().get(4).get(0);
        dicePutHistory.clear();
            int k=1;
            for(int i=0; i<4; i++) {
                for (int j=0; j<5; j++) {
                    if (!cellUpdate.get(k).equals(EMPTYCELL)) {
                        ImageView passed = configureImageView("/diePack/die-",cellUpdate.get(k),".bmp",70,70);
                        dicePutHistory.add(cellUpdate.get(k));
                        passed.setFitWidth(imageSize);
                        passed.setFitHeight(imageSize);
                        gridPane.add(passed, j, i);
                        GridPane.setHalignment(passed, HPos.CENTER);
                    }
                    else dicePutHistory.add(cellUpdate.get(k));
                    k++;
                }
            }
    }




    /**
     * Metodo utilizzato per l'update delle carte Side avversarie a seguito di una modifica. Viene anche aggiornata la lista diePutHistory per la creazione
     * della carta a seguito di un aggiornamento.
     *
     */

    @SuppressWarnings("unchecked")
    public void updateSide(){
        int imageSize = (Integer)adapter.getSidePlayerSize().get(5).get(0);
        int k;
        if(dicePutHistory.size()==21) k=1;
        else k=0;

        for(int i=0; i<4; i++) {
            for (int j=0; j<5; j++) {
                if (!dicePutHistory.get(k).equals(EMPTYCELL)) {
                    ImageView passed = configureImageView("/diePack/die-", dicePutHistory.get(k),".bmp",imageSize,imageSize);
                    gridPane.add(passed,j,i);
                }
                k++;
            }
        }
    }




    /**
     * Metodo utilizzato per comporre una copia della carta Side e i relativi dadi nella schermata di interazione con le carte Utensili
     *
     * @param sideCard Nome della carta selezionata dal giocatore
     * @param nickName Nome del giocatore a cui è associata la carta
     * @param includeShadowGrid Booleano che indica la richiesta di aggiunta dell'effetto "Evidenzia Cella" (TRUE)
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     * @return Riferimento all'elemento SideCardLabel creato
     */

    public SideCardLabel callPlayerSide(String sideCard, String nickName, boolean includeShadowGrid, boolean isCallUtensil, AdapterResolution adapterResolution){
        SideCardLabel callPlayerSide = new SideCardLabel(sideCard, nickName, includeShadowGrid, isCallUtensil,adapterResolution);
        int imageSize = (Integer)adapter.getSidePlayerUtensilSize().get(3).get(0);
        callPlayerSide.setDicePutHistory(dicePutHistory);
        int k=0;
        for(int i=0; i<4; i++) {
            for (int j = 0; j < 5; j++) {
                if(!dicePutHistory.get(k).equals(EMPTYCELL)) {
                    ImageView passed = configureImageView("/diePack/die-", dicePutHistory.get(k),".bmp" ,imageSize, imageSize);
                    callPlayerSide.getGridPane().add(passed, j, i);
                    GridPane.setHalignment(passed,HPos.CENTER);
                }
                k++;
            }
        }
        return callPlayerSide;
    }





    /**
     * Metodo utilizzato per l'aggiornamento dei dadi posizionati sulla Side di un avversario. A seguito dell'utilizzo di alcune carte Utensili che permettono lo
     * spostamento dei dadi posti su di essa, è necessario impostare la nuova lista diePutHistory per il successivo update.
     * Il metodo è richiamato anche per settare allo stesso modo la carta Side del giocatore turnante per visualizzarla nelle schermate dedicate alle carte Utensili
     * interessate alla sua visualizzazione.
     *
     * @param dicePutHistory Riferimento alla lista di informazioni relative ai dadi posti sulla carta Side
     */

    public void setDicePutHistory(List<String> dicePutHistory) {
        this.dicePutHistory = (ArrayList<String>) dicePutHistory;
    }





    /**
     * Metodo utilizzato per restituire il riferimento alla lista di dadi posizionati sulla Side.
     *
     * @return Lista dei dadi posizionati sulla Side
     */

    public ArrayList<String> getDicePutHistory() {
        return dicePutHistory;
    }





    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getPosX() {
        return posX;
    }

    public String getPosY() {
        return posY;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPathCard() {
        return pathCard;
    }

    private void setInitDicePutHistory(){
        dicePutHistory = new ArrayList<>();
        for(int i=0; i<20; i++){
            dicePutHistory.add(EMPTYCELL);
        }
    }
}
