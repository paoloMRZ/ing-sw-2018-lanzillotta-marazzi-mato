package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.setFocusStyle;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.shadowEffect;


/**
 * Classe ReserveLabel utilizzata per la configurazione della Riserva disponibile ai giocatori nel turno corrente.
 *
 * @author  Simone Lanzillotta
 */



public class ReserveLabel{

    private String pos;
    private String dieName;
    private TextField listener = new TextField();
    private HBox reserve;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private ArrayList<String> diceInfo = new ArrayList<>();
    private AdapterResolution adapter;


    /**
     * Costruttore della classe
     *
     * @param diceInfo Riferimento alla collezione contenente le informazioni sui dadi contenuti nella Riserva
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public ReserveLabel(List<String> diceInfo, AdapterResolution adapterResolution){
        this.adapter = adapterResolution;
        this.diceInfo.addAll(diceInfo);
        int imageSize = adapter.getReserveLabelSize().get(0);
        setReserve(diceInfo,imageSize);
    }


    /**
     * Metodo setter utilizzato per la configurazione dell'elemento grafico
     *
     * @param diceInfo Riferimento alla collezione contenente le informazioni sui dadi contenuti nella Riserva
     * @param imageSize Dimensioni dell'immagine del dado da visualizzare (ed eventualmente dell'effetto "Focus" sopra applicato)
     * @return
     */

    private HBox setReserve(List<String> diceInfo, int imageSize) {

        //Configurazione degli elementi per l'effetto Focus
        Rectangle rect = new Rectangle(20, 20, imageSize, imageSize);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3d);
        Group group = new Group();
        group.getChildren().add(rect);
        reserve = new HBox(5);
        reserve.setAlignment(Pos.CENTER);
        int num = diceInfo.size();

        //Controllo che la riserva rivecuta non sia vuota, quindi creo la riserva (Altrimenti lalascio vuota)
        if(!(num==1 && diceInfo.get(0).equals("white0"))) {
            for (int i = 0; i < num; i++) {
                String lowerDieInfo = diceInfo.get(i).toLowerCase(Locale.ENGLISH);
                StackPane button = new StackPane();
                button.setPrefSize(imageSize, imageSize);
                button.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-background-radius: 0; -fx-background-color: transparent;");

                ImageView die = shadowEffect(new ImageView(new Image("/diePack/die-" + lowerDieInfo + ".bmp", imageSize, imageSize, false, true)));
                button.getChildren().add(die);

                int finalI = i;
                cell.put(button, false);
                die.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    setFocusStyle(cell, button, group);
                    pos = String.valueOf(finalI);
                    dieName = diceInfo.get(finalI).toLowerCase();
                    listener.setText(dieName);
                });

                reserve.getChildren().add(button);
            }
        }
        return reserve;
    }



    /**
     * Metodo utilizzato per restituire una copia della Riserva disponibile nel turno corrente. Viene visualizzata qualora si
     * interagisse con le carte Utensili interessate ad una modifica della stessa.
     *
     * @return Riferimento all'elemento grafico contenente la Riserva corrente
     */

    public HBox callReserve(){
        ArrayList<Integer> sizeReserve = (ArrayList<Integer>) adapter.getReserveLabelUtensilSize();
        return setReserve(this.diceInfo, sizeReserve.get(0));
    }



    /**
     * Metodo Getter utilizzato per restituire la posizione del dado selezionato dalla Riserva
     *
     * @return Riferimento alla posizione del dado scelto
     */

    public String getPos() {
        return pos;
    }



    /**
     * Metodo utilizzato per restituire il riferimento alla Riserva disponibile nel turno corrente.
     *
     * @return  Riferimento all'elemento grafico contenente la Riserva corrente
     */

    public HBox getHBox() {
        return reserve;
    }






    //TODO non mi ricordo cosa fa

    public TextField getTextField() {
        return listener;
    }

    public String getDieName() {
        return dieName;
    }
}
