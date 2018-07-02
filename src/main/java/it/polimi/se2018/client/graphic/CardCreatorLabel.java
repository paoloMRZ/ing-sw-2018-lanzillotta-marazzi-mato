package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import it.polimi.se2018.client.graphic.alert_box.AlertInfoCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.se2018.client.graphic.Utility.*;


/**
 * Classe CardCreatorLabel utilizzata per la creazione degli elementi grafici rappresentanti le carte da gioco. In particolare per la carta Obbiettivo Privata,
 * le carte Utensili e le carte Obbiettivo Pubbliche disponibili nel turno corrente.
 *
 *@author Simone Lanzillotta
 */


public class CardCreatorLabel{

    private static final String EXTENSION = ".png";
    private static final String MATCHDIRECTORY = "/cardUtensils/";
    private AnchorPane cardObjective;
    private VBox cardObjectiveLabel;
    private ArrayList<String> cardName = new ArrayList<>();
    private HashMap<String, String> dictionaryUtensils;
    private List <String> valueNumber;
    private List <String> keyName;
    private AdapterResolution adapter;


    /**
     * Costruttore della Classe
     *
     * @param nameOfCard Collezione dei nomi delle carte (Utensili o Obbiettivo Pubblico)
     * @param dictionaryUtensils Riferimento alla struttura dati che associa ad ogni eventuale carta Utensile il proprio numero
     * @param isPrivate Booleano che specifica se si intende creare l'elemento grafico rappresentante Obbiettivo Privato (TRUE) o le carte Pubbliche (FALSE)
     * @param path Stringa contenente il percorso utilizzato per accedere alle risorse
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public CardCreatorLabel(List<String> nameOfCard, Map<String, String> dictionaryUtensils, boolean isPrivate, String path, AdapterResolution adapterResolution) {

        //Configurazione attributi della Classe
        this.adapter = adapterResolution;
        cardObjective = new AnchorPane();

        //Configurazione dell'hashMap utilizzato per la ricerca del numero della carta associato alla carta Utensile eventualmente creata
        this.dictionaryUtensils = (HashMap<String, String>)dictionaryUtensils;

        //Configurazione Adapter carta Obbiettivo Privato e carte Pubbliche
        ArrayList<Integer> sizeObjectivePrivate = (ArrayList<Integer>) adapter.getObjectivePrivateSize();
        ArrayList<Integer> sizePublicCard = (ArrayList<Integer>) adapter.getPublicCardSize();

        cardObjectiveLabel = new VBox(sizeObjectivePrivate.get(0));
        cardObjectiveLabel.setAlignment(Pos.CENTER);

        if(isPrivate){
            cardObjectiveLabel.setPrefSize(sizeObjectivePrivate.get(2),sizeObjectivePrivate.get(3));

            //Intestazione Obbiettivo Privato
            Label labelName = setFontStyle(new Label(), sizeObjectivePrivate.get(1));
            labelName.setPrefSize(sizeObjectivePrivate.get(2),sizeObjectivePrivate.get(3));
            labelName.setText("Obbiettivo Privato");
            labelName.setAlignment(Pos.CENTER);

            //Configurazione carta Obbiettivo Privato
            ImageView objectivePrivate = configureImageView("/cardObjective/", nameOfCard.get(0), EXTENSION, 680, 950);
            objectivePrivate.setFitHeight(sizeObjectivePrivate.get(5));
            objectivePrivate.setFitWidth(sizeObjectivePrivate.get(4));
            cardObjectiveLabel.getChildren().addAll(labelName,objectivePrivate);
            cardObjective.getChildren().add(cardObjectiveLabel);
        }

        else{

            HBox cardSequence = configureCardSequence(path,nameOfCard, sizePublicCard);
            cardSequence.setPrefSize(sizePublicCard.get(0),sizePublicCard.get(1));
            cardSequence.setAlignment(Pos.CENTER);
            cardObjectiveLabel.getChildren().addAll(cardSequence);
            cardObjectiveLabel.setPrefSize(sizePublicCard.get(0),sizePublicCard.get(1));
            configureAnchorPane(cardObjective,cardObjectiveLabel,0d,0d,0d,0d);
        }
    }




    /**
     * Metodo utilizzato per configurare la sequenza di carte Pubbliche
     *
     * @param path Stringa contenente il percorso utilizzato per accedere alle risorse
     * @param nameOfCard Collezione dei nomi delle carte (Utensili o Obbiettivo Pubblico)
     * @param sizePublicCard Lista di dimensionamento
     * @return Riferimento all'elemento grafico contenente le carte Pubbliche
     */

    private HBox configureCardSequence(String path, List<String> nameOfCard, List<Integer> sizePublicCard){

        cardObjectiveLabel.setMaxSize(sizePublicCard.get(0),sizePublicCard.get(1));
        cardObjectiveLabel.setMinSize(sizePublicCard.get(0),sizePublicCard.get(1));

        ArrayList<String> tempCollection = new ArrayList<>(nameOfCard);
        if(nameOfCard.size()>3){
            keyName = nameOfCard.stream().filter(s -> (nameOfCard.indexOf(s) % 2 == 0)).collect(Collectors.toList());
            valueNumber = nameOfCard.stream().filter(s -> (nameOfCard.indexOf(s) % 2 != 0)).collect(Collectors.toList());
            tempCollection.clear();
            tempCollection.addAll(keyName);
        }


        HBox cardSequence = new HBox(sizePublicCard.get(2));
        for (String nameCard: tempCollection) {
            ImageView item = shadowEffect(configureImageView(path, nameCard, EXTENSION, 680, 950));
            item.setFitWidth(sizePublicCard.get(3));
            item.setFitHeight(sizePublicCard.get(4));
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
                if(nameOfCard.size()>3) AlertInfoCard.display(keyName,path);
                else AlertInfoCard.display(cardName,path);
            });
            cardSequence.getChildren().add(item);
            cardName.add(nameCard);
            if(nameOfCard.size()>3) cardName.add(valueNumber.get(tempCollection.indexOf(nameCard)));
        }

        return cardSequence;
    }


    /**
     * Metodo Getter utilizzato per restituire il Parent dell'elemento grafico
     *
     * @return Riferimento al Parent cardObjective
     */

    public AnchorPane getCardObjective() {
        return cardObjective;
    }


    /**
     * Metodo utilizzato per restituire il riferimento alla struttura dati contenente i numeri delle carti Utensili
     * associate al proprio nome
     *
     * @return Riferimento alla struttura dati dictionaryUtenssils
     */

    public Map<String, String> getDictionaryUtensils() {
        return dictionaryUtensils;
    }


    /**
     * Metodo getter che restituisce la lista di chiavi per accedere ai valori contenuti nella struttura dati
     *
     * @return Riferimento alla lista keyName
     */


    public List<String> getKeyName() {
        return keyName;
    }
}
