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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.oijon.susquehanna.data.Phonology;

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
        //navVBox.setPrefHeight(screenBounds.getHeight());
        navVBox.setBackground(background);
        StackPane navStackPane = new StackPane(navVBox);
        navStackPane.setPadding(new Insets(0, 10, 0, 0));
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navStackPane);
        navBox.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        navBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setBackground(background);
        navBox.setFitToHeight(true);
        
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
        
        Button addLanguage = new Button("New\nLanguage");
        addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/new-language.png"))));
        addLanguage.setPadding(Insets.EMPTY);
        addLanguage.setContentDisplay(ContentDisplay.TOP);
        addLanguage.setBackground(null);
        addLanguage.setTextAlignment(TextAlignment.CENTER);
        
        Label languageNameLabel = new Label("Language Name (NOTE: cannot be changed)");
        TextField languageName = new TextField();
        Label languageAutonymLabel = new Label("Language Autonym");
        TextField languageAutonym = new TextField();
        Button createLanguage = new Button("Create!");
        
        Button openLanguage = new Button("Open\nLanguage");
        openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/open-language.png"))));
        openLanguage.setPadding(Insets.EMPTY);
        openLanguage.setContentDisplay(ContentDisplay.TOP);
        openLanguage.setBackground(null);
        openLanguage.setTextAlignment(TextAlignment.CENTER);
        
        Button info = new Button("Info");
        info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/info-button.png"))));
        info.setPadding(Insets.EMPTY);
        info.setContentDisplay(ContentDisplay.TOP);
        info.setBackground(null);
        info.setTextAlignment(TextAlignment.CENTER);
        
        VBox fileTools = new VBox(addLanguage, openLanguage, info);
        fileTools.setBackground(fileToolsBackground);
        
        VBox contentVBox = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        
        addLanguage.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/new-language-pressed.png"))));
        	}
        });
        addLanguage.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/new-language.png"))));
        	}
        });
        addLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		contentVBox.getChildren().clear();
        		contentVBox.getChildren().addAll(languageNameLabel, languageName, languageAutonymLabel, languageAutonym, createLanguage);
        	}
        });
        openLanguage.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/open-language-pressed.png"))));
        	}
        });
        openLanguage.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/open-language.png"))));
        	}
        });
        openLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		contentVBox.getChildren().clear();
        	}
        });
        info.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/info-button-pressed.png"))));
        	}
        });
        info.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("img/info-button.png"))));
        	}
        });
        info.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		contentVBox.getChildren().clear();
        		contentVBox.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        	}
        });
        
        //Root
        HBox rootHBox = new HBox(navBox, fileTools, contentVBox);
        contentVBox.setAlignment(Pos.CENTER);
        rootHBox.setAlignment(Pos.TOP_LEFT);
        rootHBox.setHgrow(contentVBox, Priority.ALWAYS);
        
        rootHBox.setBackground(background);
        Scene root = new Scene(rootHBox);
        
        //Phonology
        
        
        //Navbox actions
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, fileTools, contentVBox);
			}
        });
        
        phonologyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox);		
			}
        });
        orthographyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox);		
			}
        });
        grammarButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox);		
			}
        });
        lexiconButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox);		
			}
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox);		
			}
        });
        
        
        stage.setScene(root);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}