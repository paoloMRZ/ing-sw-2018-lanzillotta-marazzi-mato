package it.polimi.se2018.client.graphic.adapter_gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.configureAnchorPane;

public class MediumAdapter implements AdapterResolution {


    @Override
    public List<Integer> getSettingLabelSize(){

        //Dimensione Icona, Testo, Distanza fra gli elementi
        return new ArrayList<>(Arrays.asList(35,22,20));
    }

    @Override
    public List<List<? extends Number>> getSidePlayerSize() {
        List<List<? extends Number>> sizeSidePlayer = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(67, 66)));

        //Dimensioni della carta
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(340, 290)));

        //Posizionamento della griglia sulla Side
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(4d, 0d, 3d, 21d)));

        //Dimensioni della cornice per l'effetto Focused
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(65d, 65d)));

        //Dimensioni del dado da inserire nella mainSide
        sizeSidePlayer.add(new ArrayList<>(Collections.singletonList(55)));

        //Dimensioni del dado da inserire nelle Side avversarie
        sizeSidePlayer.add(new ArrayList<>(Collections.singletonList(33)));



        return sizeSidePlayer;
    }

    @Override
    public List<Integer> getButtonLabelSize() {

        //Larghezza Bottone, Altezza Bottone, Spazio Verticale, Spazio Orizzontale
        return new ArrayList<>(Arrays.asList(180,60,10,30));
    }

    @Override
    public List<Integer> getReserveLabelSize() {
        return new ArrayList<>(Collections.singletonList(48));
    }

    @Override
    public List<Integer> getReserveLabelUtensilSize() {
        return new ArrayList<>(Collections.singletonList(60));
    }

    @Override
    public List<List<? extends Number>> getSideEnemyLabelSize() {
        ArrayList<List<? extends Number>> sizeSideEnemy = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(39,39)));

        //Dimensioni della carta
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(200,170)));

        //Posizionamento della griglia sulla Side (Right, Top, Left, Bottom)
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(0d,0d,4d,12d)));

        //Dimensioni dell'intestazione con il nome dell'avversario
        sizeSideEnemy.add(new ArrayList<>(Collections.singletonList(18)));

        //Dimensioni dell'immagine Segna-Posto in assenza di giocatori
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(250,220)));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getObjectivePrivateSize() {

        //Spazio Verticale (Intestazione - Obbiettivo Privato),
        //Dimensione Intestazione Obbiettivo Privato
        //Dimensione Parent Obbiettivo Privato
        //Dimensioni carta Obbiettivo Privato

        return new ArrayList<>(Arrays.asList(0,15,200,330,200,280));
    }

    @Override
    public List<Integer> getPublicCardSize(){

        //Dimensioni Parent carte Pubbliche
        //Spacing Orizzontale
        //Dimensioni carte Pubbliche
        return new ArrayList<>(Arrays.asList(400,270,5,150,225));

    }

    @Override
    public List<Integer> getTabCardLabelSize() {

        //Dimensione dell'icona inserite nelle varie TAB
        //Dimensione del testo inserito nella TAB
        //Spacing Orizzontale
        //Dimensione del Parent TAB

        return new ArrayList<>(Arrays.asList(20,19,10,220,40));

    }

    @Override
    public List<List<? extends Number>> getRoundLabelSize() {

        //Dimensione rettangolo per l'effetto Focus
        //Dimensioni immagine della RoungGrid
        //Dimensioni Cella della Grid

        List<List<? extends Number>> sizeRoundLabel = new ArrayList<>();
        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(55,700,70)));

        //Posizione della Grid sull'AnchorPane

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(20d, 0d, 15d, 0d)));

        //Dimensioni immagine "Divieto" per il procedere dei round
        //Dimensioni Dado da posizionare sulla roungGrid
        //Dimensioni Dado per la visualizzazione dei dadi posizionati sulla roungGrid

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(120, 70, 40, 55)));

        return sizeRoundLabel;
    }

    @Override
    public List<List<Integer>> getSideChoiceLabelSize() {
        List<List<Integer>> sizeSideEnemy = new ArrayList<>();

        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(360, 303)));

        //Dimensione intestazione della Finestra
        //Dimensioni (Larghezza, Altezza) del bottone per interagire con la Finestra
        //Dimensioni (Larghezza, Altezza) delle Immagini relative alle carte Side
        //Spacing Verticale tra gli elementi

        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(35,220,90,30)));

        //Dimensioni della finestra
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(1100,900)));

        //Spacing Orizzontale tra le coppie di carte Side visualizzate
        //Dimensioni (Larghezza,Altezza) delle carte Side visualizzate

        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(70,360,303)));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getPrimaryStageSize(){
        return new ArrayList<>(Arrays.asList(1400,900));
    }

    @Override
    public List<Double> getSidePlayerUtensilSize() {

        //Posizone Grid della Side visualizzata nelle schermate dedicate alle carte Utensile (Right, Top, Left, Bottom)
        return new ArrayList<>(Arrays.asList(0d, 0d, 5d, 21d));
    }

    @Override
    public void putSettingLabel(AnchorPane anchorGame, HBox settingLabel) {
        configureAnchorPane(anchorGame, settingLabel, 835d, 80d, 70d, 800d);

    }

    @Override
    public void putSideLabel(AnchorPane anchorGame, AnchorPane sidePlayer) {
        configureAnchorPane(anchorGame, sidePlayer, 835d, 190d, 50d, 370d);
    }

    @Override
    public void putSideEnemyLabel(AnchorPane anchorGame, HBox hBoxEnemies) {
        configureAnchorPane(anchorGame, hBoxEnemies, 20d, 60d, 610d, 585d);
    }

    @Override
    public void putButtonLabel(AnchorPane anchorGame, VBox buttonLabel) {
        configureAnchorPane(anchorGame, buttonLabel, 880d, 500d, 100d, 200d);
    }

    @Override
    public void putReserveLabel(AnchorPane anchorGame, HBox reserveLabel) {
        configureAnchorPane(anchorGame, reserveLabel, 790d, 760d, 0d, 150d);
    }

    @Override
    public void putTabPaneLabel(AnchorPane anchorGame, BorderPane publicCard) {
        configureAnchorPane(anchorGame, publicCard, 40d, 330d, 880d, 210d);

    }

    @Override
    public void putRoundGridLabel(AnchorPane anchorGame, AnchorPane roundGrid) {
        configureAnchorPane(anchorGame, roundGrid, 20d, 670d, 610d, 60d);
    }

    @Override
    public void putPrivateObjectiveLabel(AnchorPane anchorGame, AnchorPane privateObjective) {
        configureAnchorPane(anchorGame, privateObjective, 0d, 310d, 635d, 300d);
    }


}
