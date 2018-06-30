package it.polimi.se2018.client.graphic.adapter_gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.se2018.client.graphic.Utility.configureAnchorPane;

public class FullAdapter implements AdapterResolution{

    @Override
    public List<Integer> getSettingLabelSize(){

        //Dimensione Icona, Testo, Distanza fra gli elementi
        return new ArrayList<>(Arrays.asList(40,30,30));
    }

    @Override
    public List<List<? extends Number>> getSidePlayerSize() {
        List<List<? extends Number>> sizeSidePlayer = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(85, 86)));

        //Dimensioni della carta
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(435, 380)));

        //Posizionamento della griglia sulla Side (Top, Right, Left, Bottom)
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(0d, 0d, 0d, 24d)));

        //Dimensioni della cornice per l'effetto Focused
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(85d, 86d)));

        //Dimensioni del dado da inserire nella mainSide
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(70)));

        //Dimensioni del dado da inserire nelle Side avversarie
        sizeSidePlayer.add(new ArrayList<>(Arrays.asList(35)));



        return sizeSidePlayer;
    }

    @Override
    public List<Integer> getButtonLabelSize() {

        //Larghezza Bottone, Altezza Bottone, Spazio Verticale, Spazio Orizzontale
        return new ArrayList<>(Arrays.asList(210,75,20,40));
    }

    @Override
    public List<Integer> getReserveLabelSize() {

        //Dimensione del dado posto nella Riserva
        return new ArrayList<>(Arrays.asList(68));
    }

    @Override
    public List<Integer> getReserveLabelUtensilSize() {
        return new ArrayList<>(Arrays.asList(60));
    }

    @Override
    public List<List<? extends Number>> getSideEnemyLabelSize() {
        ArrayList<List<? extends Number>> sizeSideEnemy = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(37,36)));

        //Dimensioni della carta
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(265,220)));

        //Posizionamento della griglia sulla Side (Right, Top, Left, Bottom)
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(0d,0d,8d,8d)));

        //Dimensioni dell'intestazione con il nome dell'avversario
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(20)));

        //Dimensioni dell'immagine Segna-Posto in assenza di giocatori
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(290,250)));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getObjectivePrivateSize() {

        //Spazio Verticale (Intestazione - Obbiettivo Privato),
        //Dimensione Intestazione Obbiettivo Privato
        //Dimensione Parent Obbiettivo Privato
        //Dimensioni carta Obbiettivo Privato

        return new ArrayList<>(Arrays.asList(15,30,300,400,270,360));
    }

    @Override
    public List<Integer> getPublicCardSize(){

        //Dimensioni Parent carte Pubbliche
        //Spacing Orizzontale
        //Dimensioni carte Pubbliche
        return new ArrayList<>(Arrays.asList(400,270,10,190,275));

    }

    @Override
    public List<Integer> getTabCardLabelSize() {

        //Dimensione dell'icona inserite nelle varie TAB
        //Dimensione del testo inserito nella TAB
        //Spacing Orizzontale
        //Dimensione del Parent TAB

        return new ArrayList<>(Arrays.asList(40,22,15,275,60));

    }

    @Override
    public List<List<? extends Number>> getRoundLabelSize() {

        List<List<? extends Number>> sizeRoundLabel = new ArrayList<>();

        //Dimensione rettangolo per l'effetto Focus
        //Dimensioni immagine della RoungGrid
        //Dimensioni Cella della Grid

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(60,900,90)));

        //Posizione della Grid sull'AnchorPane (Right, Top, Left, Bottom)

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(95d, 0d, 0d, 0d)));

        //Dimensioni immagine "Divieto" per il procedere dei round
        //Dimensioni Dado da posizionare sulla roungGrid
        //Dimensioni Dado per la visualizzazione dei dadi posizionati sulla roundGrid

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(300, 200, 70, 60)));

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
        return new ArrayList<>(Arrays.asList(1920,1080));
    }

    @Override
    public void putSettingLabel(AnchorPane anchorGame, HBox settingLabel) {
        configureAnchorPane(anchorGame, settingLabel, 720d, 50d, 110d, 920d);

    }

    @Override
    public void putSideLabel(AnchorPane anchorGame, AnchorPane sidePlayer) {
        configureAnchorPane(anchorGame, sidePlayer, 1080d, 140d, 40d, 380d);
    }

    @Override
    public void putSideEnemyLabel(AnchorPane anchorGame, HBox hBoxEnemies) {
        configureAnchorPane(anchorGame, hBoxEnemies, 100d, 40d, 840d, 730d);
    }

    @Override
    public void putButtonLabel(AnchorPane anchorGame, VBox buttonLabel) {
        configureAnchorPane(anchorGame, buttonLabel, 1080d, 690d, 50d, 280d);
    }

    @Override
    public void putReserveLabel(AnchorPane anchorGame, HBox reserveLabel) {
        configureAnchorPane(anchorGame, reserveLabel, 1080d, 910d, 30d, 90d);
    }

    @Override
    public void putTabPaneLabel(AnchorPane anchorGame, BorderPane publicCard) {
        configureAnchorPane(anchorGame, publicCard, 90d, 400d, 1215d, 200d);

    }

    @Override
    public void putRoundGridLabel(AnchorPane anchorGame, AnchorPane roundGrid) {
        configureAnchorPane(anchorGame, roundGrid, 0d, 820d, 780d, 0d);
    }

    @Override
    public void putPrivateObjectiveLabel(AnchorPane anchorGame, AnchorPane privateObjective) {
        configureAnchorPane(anchorGame, privateObjective, 0d, 385d, 870d, 200d);
    }

}
