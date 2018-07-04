package it.polimi.se2018.client.graphic.adapter_gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.configureAnchorPane;

public class SmallAdapter implements  AdapterResolution {

    @Override
    public List<Integer> getSettingLabelSize(){

        //Dimensione Icona, Testo, Distanza fra gli elementi
        return new ArrayList<>(Arrays.asList(30,20,15));
    }

    @Override
    public List<List<? extends Number>> getSidePlayerSize() {
        List<List<? extends Number>> sizeSidePlayer = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(62, 61)));

        //Dimensioni della carta
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(315, 270)));

        //Posizionamento della griglia sulla Side
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(0d, 0d, 0d, 20d)));

        //Dimensioni della cornice per l'effetto Focused
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(60d, 60d)));

        //Dimensioni del dado da inserire nella mainSide
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(50)));

        //Dimensioni del dado da inserire nelle Side avversarie
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(28)));



        return sizeSidePlayer;
    }

    @Override
    public List<Integer> getButtonLabelSize() {

        //Larghezza Bottone, Altezza Bottone, Spazio Verticale, Spazio Orizzontale
        return new ArrayList<>(Arrays.asList(170,55,10,30));
    }

    @Override
    public List<Integer> getReserveLabelSize() {

        //Dimensione del dado posto nella Riserva
        return new ArrayList<>(Arrays.asList(45));
    }

    @Override
    public List<Integer> getReserveLabelUtensilSize() {
        return new ArrayList<>(Arrays.asList(50));
    }

    @Override
    public List<List<? extends Number>> getSideEnemyLabelSize() {
        ArrayList<List<? extends Number>> sizeSideEnemy = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(37,36)));

        //Dimensioni della carta
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(185,150)));

        //Posizionamento della griglia sulla Side (Right, Top, Left, Bottom)
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(0d,0d,8d,8d)));

        //Dimensioni dell'intestazione con il nome dell'avversario
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(17)));

        //Dimensioni dell'immagine Segna-Posto in assenza di giocatori
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(230,200)));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getObjectivePrivateSize() {

        //Spazio Verticale (Intestazione - Obbiettivo Privato),
        //Dimensione Intestazione Obbiettivo Privato
        //Dimensione Parent Obbiettivo Privato
        //Dimensioni carta Obbiettivo Privato

        return new ArrayList<>(Arrays.asList(0,15,200,300,190,260));
    }

    @Override
    public List<Integer> getPublicCardSize(){

        //Dimensioni Parent carte Pubbliche
        //Spacing Orizzontale
        //Dimensioni carte Pubbliche
        return new ArrayList<>(Arrays.asList(400,270,5,135,205));

    }

    @Override
    public List<Integer> getTabCardLabelSize() {

        //Dimensione dell'icona inserite nelle varie TAB
        //Dimensione del testo inserito nella TAB
        //Spacing Orizzontale
        //Dimensione del Parent TAB

        return new ArrayList<>(Arrays.asList(20,17,10,205,40));

    }

    @Override
    public List<List<? extends Number>> getRoundLabelSize() {

        List<List<? extends Number>> sizeRoundLabel = new ArrayList<>();

        //Dimensione rettangolo per l'effetto Focus
        //Dimensioni immagine della RoungGrid
        //Dimensioni Cella della Grid

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(55,665,68)));

        //Posizione della Grid sull'AnchorPane (Right, Top, Left, Bottom)

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(15d, 0d, 15d, 0d)));

        //Dimensioni immagine "Divieto" per il procedere dei round
        //Dimensioni Dado da posizionare sulla roungGrid
        //Dimensioni Dado per la visualizzazione dei dadi posizionati sulla roundGrid

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(115, 70, 40, 55)));

        return sizeRoundLabel;
    }

    @Override
    public List<List<Integer>> getSideChoiceLabelSize() {
        List<List<Integer>> sizeSideEnemy = new ArrayList<>();

        //Dimensione del rettangolo per l'effetto Focus
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(350, 293)));

        //Dimensione intestazione della Finestra
        //Dimensioni (Larghezza, Altezza) del bottone per interagire con la Finestra
        //Dimensioni (Larghezza, Altezza) delle Immagini relative alle carte Side
        //Spacing Verticale tra gli elementi

        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(30,200,80,20)));

        //Dimensioni della finestra
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(1000,820)));

        //Spacing Orizzontale tra le coppie di carte Side visualizzate
        //Dimensioni (Larghezza,Altezza) delle carte Side visualizzate

        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(70,350,293)));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getPrimaryStageSize(){
        return new ArrayList<>(Arrays.asList(1366,768));
    }

    @Override
    public List<Double> getSidePlayerUtensilSize() {

        //Posizone Grid della Side visualizzata nelle schermate dedicate alle carte Utensile (Right, Top, Left, Bottom)
        return new ArrayList<>(Arrays.asList(0d, 0d, 10d, 21d));
    }

    @Override
    public void putSettingLabel(AnchorPane anchorGame, HBox settingLabel) {
        configureAnchorPane(anchorGame, settingLabel, 750d, 40d, 100d, 900d);

    }

    @Override
    public void putSideLabel(AnchorPane anchorGame, AnchorPane sidePlayer) {
        configureAnchorPane(anchorGame, sidePlayer, 835d, 135d, 110d, 300d);
    }

    @Override
    public void putSideEnemyLabel(AnchorPane anchorGame, HBox hBoxEnemies) {
        configureAnchorPane(anchorGame, hBoxEnemies, 57d, 15d, 603d, 495d);
    }

    @Override
    public void putButtonLabel(AnchorPane anchorGame, VBox buttonLabel) {
        configureAnchorPane(anchorGame, buttonLabel, 875d, 500d, 150d, 220d);
    }

    @Override
    public void putReserveLabel(AnchorPane anchorGame, HBox reserveLabel) {
        configureAnchorPane(anchorGame, reserveLabel, 750d, 730d, 20d, 160d);
    }

    @Override
    public void putTabPaneLabel(AnchorPane anchorGame, BorderPane publicCard) {
        configureAnchorPane(anchorGame, publicCard, 70d, 275d, 850d, 170d);

    }

    @Override
    public void putRoundGridLabel(AnchorPane anchorGame, AnchorPane roundGrid) {
        configureAnchorPane(anchorGame, roundGrid, 45d, 570d, 590d, 0d);
    }

    @Override
    public void putPrivateObjectiveLabel(AnchorPane anchorGame, AnchorPane privateObjective) {
        configureAnchorPane(anchorGame, privateObjective, 0d, 255d, 615d, 300d);
    }


}
