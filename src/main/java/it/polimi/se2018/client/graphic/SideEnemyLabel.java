package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import static it.polimi.se2018.client.graphic.Utility.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Classe SideEnemyLabel utilizzata per la configurazione dell'elemento grafico contenente le carte Side degli eventuali avversari del giocatore.
 *
 * @author Simone Lanzillotta
 */



public class SideEnemyLabel{

    private HBox labelSideEnemy;
    private ArrayList<SideCardLabel> sideEnemy;
    private AdapterResolution adapter;


    /**
     * Costruttore della Classe
     *
     * @param nameOfPlayers Lista dei nome dei giocatori avversari
     * @param sideName Lista delle carte Side associate ad ogni giocatore avversario
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    SideEnemyLabel(List<String> nameOfPlayers, List<String> sideName, AdapterResolution adapterResolution) {
        this.adapter = adapterResolution;
        sideEnemy = new ArrayList<>();
        setSideEnemy(nameOfPlayers,sideName);
    }



    /**
     * Metodo utilizzato per impostare la selezione di carte Side avversarie, munendole di intestazione con il nome del proprietario
     *
     * @param nameOfPlayers Lista dei nome dei giocatori avversari
     * @param sideName Lista delle carte Side associate ad ogni giocatore avversario
     */

    private void setSideEnemy(List<String> nameOfPlayers, List<String> sideName){
        labelSideEnemy = new HBox(0);
        labelSideEnemy.setAlignment(Pos.CENTER);
        for(int i=0; i<nameOfPlayers.size();i++){

            //Configurazione Parent
            VBox cardEnemy = new VBox(5);
            cardEnemy.setPrefSize(391,395);

            //Configurazione intestazione
            Label labelName = setFontStyle(new Label("Player: " + nameOfPlayers.get(i)), (Integer)adapter.getSideEnemyLabelSize().get(3).get(0));
            labelName.setAlignment(Pos.CENTER);

            //Configurazione carta Side
            sideEnemy.add(new SideCardLabel(sideName.get(i), nameOfPlayers.get(i),false,adapter));

            //Settaggio Finale
            cardEnemy.getChildren().addAll(labelName, sideEnemy.get(i).getAnchorPane());
            cardEnemy.setAlignment(Pos.CENTER);
            labelSideEnemy.getChildren().add(cardEnemy);
        }


        //Nel caso in cui non si stia disputando una partita con 4 giocatori totali, aggiungo un Segna-Posto
        if(nameOfPlayers.size()<3){
            for(int j=0;j<3-nameOfPlayers.size();j++) {

                //Configurazione dell'immagine Segna-Posto
                ImageView noPlayer = configureImageView("/iconPack/","icon-player", ".png", 400,400);
                noPlayer.setFitWidth((Integer)adapter.getSideEnemyLabelSize().get(4).get(0));
                noPlayer.setFitHeight((Integer)adapter.getSideEnemyLabelSize().get(4).get(1));
                noPlayer.setOpacity(0.2);
                labelSideEnemy.getChildren().add(noPlayer);
            }
        }

    }



    /**
     * Metodo Getter cge restituisce il riferimento al Parent dell'elemento grafico configurato
     *
     * @return Riferimento al Parent labelSideEnemy
     */

    public HBox getLabelSideEnemy() {
        return labelSideEnemy;
    }



    /**
     * Metodo utilizzato per aggiornare la disposizione dei dadi nelle carte Side avversarie.
     *
     * @param infoSide Lista di informazioni relative ai dadi posizioanti sulla Side avversaria
     */

    public void updateSideEnemies(List<String> infoSide){
        String nameOfEnemy = infoSide.get(0);
        for (SideCardLabel side: sideEnemy) {
            if(side.getNickName().equals(nameOfEnemy)) side.updateSide(infoSide);
        }
    }
}
