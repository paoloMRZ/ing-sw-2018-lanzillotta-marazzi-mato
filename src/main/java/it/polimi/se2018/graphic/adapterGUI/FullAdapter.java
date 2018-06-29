package it.polimi.se2018.graphic.adapterGUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullAdapter implements AdapterResolution{
    @Override
    public List<Integer> getSettingLabelSize(){

        //Dimensione Icona, Testo, Distanza fra gli elementi
        return new ArrayList<>(Arrays.asList(35,22,20));
    }

    @Override
    public List<List<? extends Number>> getSidePlayerSize() {
        List<List<? extends Number>> sizeSidePlayer = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSidePlayer.add(Arrays.asList(67, 66));

        //Dimensioni della carta
        sizeSidePlayer.add(Arrays.asList(340, 290));

        //Posizionamento della griglia sulla Side
        sizeSidePlayer.add(Arrays.asList(4d, 0d, 3d, 21d));

        //Dimensioni della cornice per l'effetto Focused
        sizeSidePlayer.add(Arrays.asList(65d, 65d));

        //Dimensioni del dado da inserire nella mainSide
        sizeSidePlayer.add(Arrays.asList(55));

        //Dimensioni del dado da inserire nelle Side avversarie
        sizeSidePlayer.add(Arrays.asList(33));



        return sizeSidePlayer;
    }

    @Override
    public List<Integer> getButtonLabelSize() {

        //Larghezza Bottone, Altezza Bottone, Spazio Verticale, Spazio Orizzontale
        return new ArrayList<>(Arrays.asList(180,60,10,30));
    }

    @Override
    public List<Integer> getReserveLabelSize() {
        return new ArrayList<>(Arrays.asList(48));
    }

    @Override
    public List<Integer> getReserveLabelUtensilSize() {
        return new ArrayList<>(Arrays.asList(60));
    }

    @Override
    public List<List<? extends Number>> getSideEnemyLabelSize() {
        List<List<? extends Number>> sizeSideEnemy = new ArrayList<>();

        //Dimensioni della Griglia posta sopra la Side
        sizeSideEnemy.add(Arrays.asList(39,39));

        //Dimensioni della carta
        sizeSideEnemy.add(Arrays.asList(200,170));

        //Posizionamento della griglia sulla Side
        sizeSideEnemy.add(Arrays.asList(0d,0d,4d,12d));

        //Dimensioni del dado da inserire nelle Side avversarie
        sizeSideEnemy.add(Arrays.asList(33));

        return sizeSideEnemy;
    }

    @Override
    public List<Integer> getObjectivePrivateSize() {

        //Spazio Verticale (Intestazione - Obbiettivo Privato),
        //Dimensione Intestazione Obbiettivo Privato
        //Dimensione Parent Obbiettivo Privato
        //Dimensioni carta Obbiettivo Privato

        return new ArrayList<>(Arrays.asList(0,15,200,350,200,280));
    }

    @Override
    public List<Integer> getPublicCardSize(){

        //Dimensioni Parent carte Pubbliche
        //Spacing Orizzontale
        //Dimensioni carte Pubbliche
        return new ArrayList<>(Arrays.asList(600,350,5,150,225));

    }

    @Override
    public List<Integer> getTabCardLabelSize() {

        //Dimensione dell'icona inserite nelle varie TAB
        //Dimensione del testo inserito nella TAB
        //Spacing Orizzontale

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

        sizeRoundLabel.add(new ArrayList<>(Arrays.asList(70d, 0d, 15d, 0d)));

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
        sizeSideEnemy.add(new ArrayList<>(Arrays.asList(1000,820)));

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
    public void putSettingLabel(AnchorPane root, HBox settingLabel) {

    }

    @Override
    public void putSideLabel(AnchorPane root, AnchorPane sidePlayer) {

    }

    @Override
    public void putSideEnemyLabel(AnchorPane root, HBox enemiesCard) {

    }

    @Override
    public void putButtonLabel(AnchorPane root, VBox buttonLabel) {

    }

    @Override
    public void putReserveLabel(AnchorPane root, HBox reserveLabel) {

    }

    @Override
    public void putTabPaneLabel(AnchorPane root, BorderPane pubblicCard) {

    }

    @Override
    public void putRoundGridLabel(AnchorPane root, AnchorPane roundGrid) {

    }

    @Override
    public void putPrivateObjectiveLabel(AnchorPane root, AnchorPane privateObjective) {

    }


}
