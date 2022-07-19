package net.oijon.susquehanna.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @SuppressWarnings("static-access") //Eclipse does not like how you make specific HBoxes fix the screen.
	@Override
    public void start(Stage stage) {
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        //Navbox
    	BackgroundFill backgroundFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
    	
    	Button fileButton = new Button();
        ImageView fileButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/file-tab.png")));
        fileButton.setGraphic(fileButtonImage);
        fileButton.setPadding(Insets.EMPTY);
        fileButton.setBackground(null);
        
        Button phonologyButton = new Button();
        ImageView phonologyButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/phonology-tab.png")));
        phonologyButton.setGraphic(phonologyButtonImage);
        phonologyButton.setPadding(Insets.EMPTY);
        phonologyButton.setBackground(null);
        
        Button orthographyButton = new Button();
        ImageView orthographyButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/orthography-tab.png")));
        orthographyButton.setGraphic(orthographyButtonImage);
        orthographyButton.setPadding(Insets.EMPTY);
        orthographyButton.setBackground(null);
        
        Button grammarButton = new Button();
        ImageView grammarButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/grammar-tab.png")));
        grammarButton.setGraphic(grammarButtonImage);
        grammarButton.setPadding(Insets.EMPTY);
        grammarButton.setBackground(null);
        
        Button lexiconButton = new Button();
        ImageView lexiconButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/lexicon-tab.png")));
        lexiconButton.setGraphic(lexiconButtonImage);
        lexiconButton.setPadding(Insets.EMPTY);
        lexiconButton.setBackground(null);
        
        Button settingsButton = new Button();
        ImageView settingsButtonImage = new ImageView(new Image(App.class.getResourceAsStream("img/settings-tab.png")));
        settingsButton.setGraphic(settingsButtonImage);
        settingsButton.setPadding(Insets.EMPTY);
        settingsButton.setBackground(null);
        
        VBox navVBox = new VBox(fileButton, phonologyButton, orthographyButton, grammarButton, lexiconButton, settingsButton);
        navVBox.setPrefHeight(screenBounds.getHeight());
        navVBox.setBackground(background);
        StackPane navStackPane = new StackPane(navVBox);
        navStackPane.setPadding(new Insets(0, 10, 0, 0));
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navStackPane);
        navBox.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        navBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setBackground(background);
        
    	//File
        Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        Label javaFXVersionLabel = new Label("Bundled with JavaFX SDK 18.0.1.");
        Label algonquinVersionLabel = new Label("Bundled with AlgonquinTTS 0.2.2.");
        Label versionLabel = new Label("Version 0.0.1 \"Otsego\", build 22w29a ***SNAPSHOT VERSION***");
        javaVersionLabel.setTextFill(Color.WHITE);
        javaFXVersionLabel.setTextFill(Color.WHITE);
        algonquinVersionLabel.setTextFill(Color.WHITE);
        versionLabel.setTextFill(Color.WHITE);
        Image bannerLogo = new Image(App.class.getResourceAsStream("img/bannerlogo.png"));
        Image hoveredBannerLogo = new Image(App.class.getResourceAsStream("img/bannerlogo-hover.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        bannerLogoView.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouse) {
				bannerLogoView.setImage(hoveredBannerLogo);
			}
        	
        });
        bannerLogoView.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouse) {
				bannerLogoView.setImage(bannerLogo);
			}
        	
        });
        
        BackgroundFill fileToolsFill = new BackgroundFill(Color.web("#D17A88"), CornerRadii.EMPTY, Insets.EMPTY);
        Background fileToolsBackground = new Background(fileToolsFill);
        
        Button addLanguage = new Button("New Language");
        addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/new-language.png"))));
        addLanguage.setPadding(Insets.EMPTY);
        addLanguage.setContentDisplay(ContentDisplay.TOP);
        addLanguage.setBackground(null);
        
        Button openLanguage = new Button("Open Language");
        openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/open-language.png"))));
        openLanguage.setPadding(Insets.EMPTY);
        openLanguage.setContentDisplay(ContentDisplay.TOP);
        openLanguage.setBackground(null);
        
        VBox fileTools = new VBox(addLanguage, openLanguage);
        fileTools.setBackground(fileToolsBackground);
        
        VBox fileVBox = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        
        HBox fileHBox = new HBox(navBox, fileTools, fileVBox);
        fileVBox.setAlignment(Pos.CENTER);
        fileHBox.setAlignment(Pos.TOP_LEFT);
        fileHBox.setHgrow(fileVBox, Priority.ALWAYS);
        
        fileHBox.setBackground(background);
        Scene file = new Scene(fileHBox);
        
        //Phonology
        
        //Navbox actions
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(file);			
			}
        });
        
        
        stage.setScene(file);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}