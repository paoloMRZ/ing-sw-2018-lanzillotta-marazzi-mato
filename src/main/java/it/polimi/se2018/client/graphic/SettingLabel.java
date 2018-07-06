package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.fusesource.jansi.AnsiConsole;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static java.lang.Integer.parseInt;


public class SettingLabel implements ChangeListener<String> {


    private static final String STYLELABEL = "-fx-background-color: transparent;";
    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/iconPack/";
    private static final int SPACE = 10;
    private HBox settingHBox;
    private HBox nickNameLabel;
    private HBox favoursLabel;
    private HBox actionLabel;
    private HBox turnOfLabel;
    private TextField turnOf = new TextField();
    private TextField favours = new TextField();
    private TextField action = new TextField();
    private int imageSize;
    private int textSize;
    private int spacing;

    public SettingLabel(String nickName, String action, String favours, String turnOf, AdapterResolution adaptee){

        this.action.setText(action);
        this.favours.setText(favours);
        this.turnOf.setText(turnOf);

        imageSize = adaptee.getSettingLabelSize().get(0);
        textSize = adaptee.getSettingLabelSize().get(1);
        spacing = adaptee.getSettingLabelSize().get(2);

        nickNameLabel = configureNode(SPACE,"icon-sagrada",imageSize,imageSize,nickName,textSize);
        favoursLabel = configureNode(SPACE,"icon-favours",imageSize,imageSize, favours,textSize);
        actionLabel = configureNode(SPACE,"icon-mosse",imageSize,imageSize, action,textSize);
        turnOfLabel = configureNode(SPACE,"icon-player",imageSize,imageSize,"Turn of: " + turnOf,textSize);

        settingHBox = new HBox(spacing);
        settingHBox.setStyle(STYLELABEL);
        settingHBox.getChildren().addAll(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);

        }


    public void changed(ObservableValue observableValue, String oldValue, String newValue){
        try{
            StringProperty textProperty = (StringProperty) observableValue;
            TextField textField = (TextField) textProperty.getBean();

            if(textField == turnOf){
                turnOfLabel = configureNode(SPACE,"icon-player",imageSize,imageSize,"Turn of: " + newValue,textSize);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(spacing);
                settingHBox.setStyle(STYLELABEL);
            }

            else if(textField == favours){
                favoursLabel = configureNode(SPACE,"icon-favours",imageSize,imageSize,newValue,textSize);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(spacing);
                settingHBox.setStyle(STYLELABEL);
            }

            else if(textField == action){
                actionLabel = configureNode(SPACE,"icon-mosse",imageSize,imageSize,newValue,textSize);
                settingHBox = new HBox(nickNameLabel,favoursLabel,actionLabel,turnOfLabel);
                settingHBox.setSpacing(spacing);
                settingHBox.setStyle(STYLELABEL);
            }
        }catch (Exception e){
            AnsiConsole.out().println(e.toString());
        }
    }

    private HBox configureNode(int spaceHBox, String nameResources, int requestedWidth, int requestedHeight, String text, int sizeText){
        HBox node = new HBox(spaceHBox);
        ImageView iconPlayer = configureImageView(SUBDIRECTORY,nameResources,EXTENSION,55,55);
        iconPlayer.setFitWidth(requestedWidth);
        iconPlayer.setFitHeight(requestedHeight);
        Label labelName = setFontStyle(new Label(text),sizeText);
        node.getChildren().addAll(iconPlayer,labelName);
        node.setAlignment(Pos.CENTER);
        return node;
    }

    public HBox getSettingLabel() {
        return settingHBox;
    }

    public void updateTurn(String turnOf){
        changed(this.turnOf.textProperty(),this.turnOf.getText(),turnOf);
    }

    public void updateAction(String actionUpdate){
        changed(action.textProperty(),this.action.getText(),actionUpdate);
    }

    public void updateFavours(String updateFavours){
        changed(favours.textProperty(),this.favours.getText(),String.valueOf(parseInt(updateFavours)));
    }

}