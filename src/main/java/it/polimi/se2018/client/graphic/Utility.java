package it.polimi.se2018.client.graphic;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * Classe Utility adibita a raccogliere metodi static per la cinfigurazione dei vari componenti delle scene da visualizzare
 *
 * @author Simone Lanzillotta
 */


public class Utility {

    private static DropShadow shadow = new DropShadow();
    private static final String FONT = "Matura MT Script Capitals";


    /**
     * Metodo che applica l'effetto shadow al nodo in ingresso
     *
     * @param button Riferimento al nodo su cui impostare l'effetto
     * @return Riferimento all'immagine modificata
     */

    public static ImageView shadowEffect(ImageView button) {
        //Effetto ombra
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        return button;
    }



    /**
     * Metodo che crea un elemento ImageView
     *
     * @param nameResources Nome della risorsa
     * @param requestedWidth Larghezza richiesta
     * @param requestedHeight Altezza richiesta
     * @return Riferimento all'oggetto ImageView contenente l'immagine desiderata
     */

    public static ImageView configureImageView(String subDirectory, String nameResources, String extension, int requestedWidth, int requestedHeight){
        return new ImageView(new Image(subDirectory + nameResources + extension,requestedWidth,requestedHeight,false,true));
    }


    /**
     * Metodo che crea un elemento Background ristretto solo alle immagini native di formato png
     *
     * @param path Percorso della risorsa
     * @param requestedWidth Larghezza richiesta
     * @param requestedHeight Altezza richiesta
     * @return Riferimento all'oggetto Background contenente l'immagine desiderata
     */

    public static Background configureBackground(String path, int requestedWidth, int requestedHeight){
        return new Background(new BackgroundImage(new Image(path + ".png",requestedWidth,requestedHeight,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
    }



    /**
     * Metodo che ancora l'elemento richiesto in una specifica posizione della finestra
     *
     * @param anchorPane Riferimento al nodo su cui si vuole ancorare l'elemento
     * @param node Riferimento al nodo che si intende ancorare
     * @param right Gap dal lato DX
     * @param top Gap dall'estremità superiore
     * @param left Gap dal lato SX
     * @param bottom Gap dall'estremità inferiore
     */

    public static void configureAnchorPane(AnchorPane anchorPane, Node node, Double right, Double top, Double left, Double bottom){
        anchorPane.getChildren().add(node);
        AnchorPane.setRightAnchor(node,right);
        AnchorPane.setTopAnchor(node,top);
        AnchorPane.setLeftAnchor(node,left);
        AnchorPane.setBottomAnchor(node,bottom);
    }


    /**
     * Metodo che configura una composizione di elementi grafici specifici
     *
     * @param header Riferimento all'intestazione del blocco di componenti
     * @param body Riferimento al corpo del blocco di componenti
     * @param spacingVBox Gap tra un elemento ed il successivo
     * @return Riferimento alla configurazione risultante
     */

    public static VBox configureVBox(Label header, Node body, int spacingVBox){
        VBox vBox = new VBox(spacingVBox);
        vBox.getChildren().addAll(header,body);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }


    /**
     * Metodo che configura il font delle varie intestazioni
     *
     * @param header Riferimento all'intestazione del blocco di componenti
     * @param size Dimensione del testo d'intestazione
     * @return Riferimento all'header formattato
     */

    public static Label setFontStyle(Label header, int size){
        header.setFont(Font.font(FONT, size));
        header.setAlignment(Pos.CENTER);
        return header;
    }


    /**
     * Metodo che imposta l'effetto Focus sul nodo in ingresso
     *
     * @param hashMap Collezione di oggetti che verifica la presenza o meno dell'effetto sul nodo
     * @param button Riferimento al Parent che del nodo su cui applicare
     * @param group Riferimento al nodo su sui applicare l'effetto
     */

    public static void setFocusStyle(Map<StackPane,Boolean> hashMap, StackPane button, Group group){

        if(!hashMap.get(button)){
            button.getChildren().add(group);
            hashMap.replace(button, false, true);
        }
        else {
            button.getChildren().remove(group);
            hashMap.replace(button, true, false);
        }
    }


    /**
     * Metodo che crea una collezione di coppie di valori String
     *
     * @param selectedInfo Riferimento alla collezione da cui prelevare gli elementi per creare il dizionario
     * @return Riferimento alla collezione di coppie di valori
     */

    public static Map<String,String> createDictionary(List<String> selectedInfo){

        HashMap<String, String> dictionary = new HashMap<>();
        List<String> keyName = selectedInfo.stream().filter(s -> (selectedInfo.indexOf(s) % 2 == 0)).collect(Collectors.toList());
        List<String> valueCost = selectedInfo.stream().filter(s -> (selectedInfo.indexOf(s) % 2 != 0)).collect(Collectors.toList());
        for (String key: keyName) {
            dictionary.put(key,valueCost.get(keyName.indexOf(key)));
        }

        return  dictionary;
    }
}
