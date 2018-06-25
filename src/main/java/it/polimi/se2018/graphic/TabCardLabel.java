package it.polimi.se2018.graphic;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import static it.polimi.se2018.graphic.Utility.*;


public class TabCardLabel {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/iconPack/";

    private TabPane tabPane;
    private Tab utensilsTab;
    private Tab objectiveTab;
    private BorderPane borderPane;
    private Group root;


    public TabCardLabel() {
        tabPane = new TabPane();
        borderPane = new BorderPane();
        borderPane.setMaxWidth(1600);
        root = new Group();
        utensilsTab = new Tab();
        objectiveTab = new Tab();
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
    }

    public void configureTabUtensils(AnchorPane cardUtensils){
        configureTab(utensilsTab, "Carte Utensili", SUBDIRECTORY, "icon-utensils", EXTENSION, cardUtensils);

    }

    public void configureTabObjective(AnchorPane cardObjective){
        configureTab(objectiveTab, "Obbiettvi Pubblici", SUBDIRECTORY,"icon-objective", EXTENSION, cardObjective);
    }

    private void configureTab(Tab tab, String title, String subDirectory, String nameResources, String extension, AnchorPane content) {
        int imageSize = 20;

        ImageView imageView = configureImageView(subDirectory, nameResources, extension, imageSize,imageSize);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);

        Label label = setFontStyle(new Label(title),19);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: transparent;");

        HBox contentTab = new HBox(10);
        contentTab.setAlignment(Pos.CENTER);
        contentTab.getChildren().addAll(imageView, label);
        contentTab.setStyle("-fx-background-color: transparent;");

        tab.setGraphic(contentTab);
        tab.setStyle("-fx-background-color: white; -fx-pref-width: 220; -fx-pref-height: 40; -fx-alignment: center; -fx-focus-color: gray; -fx-faint-focus-color: transparent;");
        tab.setClosable(false);
        tab.setContent(content);
        tabPane.getTabs().add(tab);

    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
