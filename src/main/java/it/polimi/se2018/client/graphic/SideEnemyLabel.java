package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
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
    private ArrayList<String> nameOfEnemies;
    private ArrayList<String> cardOfEnemies;


    /**
     * Costruttore della Classe
     *
     * @param nameOfPlayers Lista dei nome dei giocatori avversari
     * @param sideName Lista delle carte Side associate ad ogni giocatore avversario
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    SideEnemyLabel(List<String> nameOfPlayers, List<String> sideName, AdapterResolution adapterResolution) {

        //Configurazione degli attributi della classe (Per il dimensionamento e per un eventuale aggiornamento delle carte)
        this.adapter = adapterResolution;
        this.cardOfEnemies = (ArrayList<String>) sideName;
        this.nameOfEnemies = (ArrayList<String>) nameOfPlayers;
        this.sideEnemy = new ArrayList<>();

        //Configurazione elemento grafico
        setSideEnemy(nameOfPlayers,sideName);
    }



    /**
     * Metodo utilizzato per impostare la selezione di carte Side avversarie, munendole di intestazione con il nome del proprietario
     *
     * @param nameOfPlayers Lista dei nome dei giocatori avversari
     * @param sideName Lista delle carte Side associate ad ogni giocatore avversario
     */

    private void setSideEnemy(List<String> nameOfPlayers, List<String> sideName){
        labelSideEnemy = new HBox((Integer)adapter.getSideEnemyLabelSize().get(5).get(0));
        labelSideEnemy.setPrefWidth(845);
        labelSideEnemy.setAlignment(Pos.CENTER);
        for(int i=0; i<nameOfPlayers.size();i++){

            //Configurazione Parent
            VBox cardEnemy = new VBox(5);
            cardEnemy.setPrefSize(391,395);

            //Configurazione intestazione
            Label labelName = setFontStyle(new Label("Player: " + nameOfPlayers.get(i)), (Integer)adapter.getSideEnemyLabelSize().get(3).get(0));
            labelName.setAlignment(Pos.CENTER);

            //Configurazione carta Side
            sideEnemy.add(new SideCardLabel(sideName.get(i), nameOfPlayers.get(i),false,false,adapter));

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

        //Estraggo il nome dell'avversario a cui aggiornare la Side
        String nameOfEnemy = infoSide.get(0);

        //Estraggo le informazioni sulla history di put dei dadi sulle carte Side. Ricerco quindi il nome dell'avversario
        //suddetto e imposto la nuova collezione per l'aggiornamento

        List<List<String>> allHistoryEnemies = new ArrayList<>();

        for (SideCardLabel side: sideEnemy) {
            if(side.getNickName().equals(nameOfEnemy)) allHistoryEnemies.add(new ArrayList<>(infoSide));
            else allHistoryEnemies.add(new ArrayList<>(side.getDicePutHistory()));
        }

        //Pulisco la collezione di side
        sideEnemy.clear();

        //Ricreo la collezione di carte avversarie
        setSideEnemy(nameOfEnemies,cardOfEnemies);

        //Imposto la cronologia di posizionamenti dei dai sulle Side avversarie e aggiorno di conseguenza
        for (SideCardLabel side: sideEnemy) {
            side.setDicePutHistory(allHistoryEnemies.get(sideEnemy.indexOf(side)));
            side.updateSide();
        }


    }
}
