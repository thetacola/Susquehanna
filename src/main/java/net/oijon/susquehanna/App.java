package net.oijon.susquehanna;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.oijon.susquehanna.data.Language;
import net.oijon.susquehanna.data.LanguageFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * JavaFX App
 */
public class App extends Application {
	
	// Shared resources, mainly backgrounds
	static InputStream is = App.class.getResourceAsStream("/font/Denyut.ttf");
	static Font denyut20 = Font.loadFont(is, 20);
	static VBox languageSelect = new VBox();
	static TextArea languageList = new TextArea();
	
	BackgroundFill backgroundFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
	BackgroundImage plankImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/wood-texture.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);
	Background woodBackground = new Background(plankImage);
    Background background = new Background(backgroundFill);
    
    BackgroundImage bindingBackgroundImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/page-binding.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);
    Background bindingBackground = new Background(bindingBackgroundImage);
    ImageView bindingImage = new ImageView(new Image(App.class.getResourceAsStream("/img/page-binding.png")));
    
    BackgroundImage rightPlankImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/right-wood.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);
	Background rightWoodBackground = new Background(rightPlankImage);
	ImageView rightWood = new ImageView(new Image(App.class.getResourceAsStream("/img/right-wood.png")));
    
    BackgroundImage paperImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/paper-texture.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);
    Background paperBackground = new Background(paperImage);
    
    BackgroundImage fileBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/file-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background fileToolsBackground = new Background(fileBarImage);
    
    BackgroundImage phonologyBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/phonology-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background phonologyToolsBackground = new Background(phonologyBarImage);
    
    BackgroundImage orthographyBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/orthography-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background orthographyToolsBackground = new Background(orthographyBarImage);
    
    BackgroundImage grammarBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/grammar-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background grammarToolsBackground = new Background(grammarBarImage);
    
    BackgroundImage lexiconBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/lexicon-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background lexiconToolsBackground = new Background(lexiconBarImage);
    
    BackgroundImage settingsBarImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/settings-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background settingsToolsBackground = new Background(settingsBarImage);
    
    Image leftPapers = new Image(App.class.getResourceAsStream("/img/left-papers.png"));
    BackgroundImage leftPapersBI = new BackgroundImage(leftPapers, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
    Background leftPapersBG = new Background(leftPapersBI);
    
    Image rightPapers = new Image(App.class.getResourceAsStream("/img/right-papers.png"));
    BackgroundImage rightPapersBI = new BackgroundImage(rightPapers, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
    Background rightPapersBG = new Background(rightPapersBI);
    
    BackgroundImage brushedMetalImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/brushed-metal.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background brushedMetal = new Background(brushedMetalImage);

    @SuppressWarnings("static-access") //Eclipse does not like how you make specific HBoxes fix the screen.
	@Override
    public void start(Stage stage) {
    	
        //Navbox        
    	Button fileButton = new Button();
        ImageView fileButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/file-tab.png")));
        fileButton.setGraphic(fileButtonImage);
        fileButton.setPadding(Insets.EMPTY);
        fileButton.setBackground(null);
        
        Button phonologyButton = new Button();
        ImageView phonologyButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/phonology-tab.png")));
        phonologyButton.setGraphic(phonologyButtonImage);
        phonologyButton.setPadding(Insets.EMPTY);
        phonologyButton.setBackground(null);
        
        Button orthographyButton = new Button();
        ImageView orthographyButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/orthography-tab.png")));
        orthographyButton.setGraphic(orthographyButtonImage);
        orthographyButton.setPadding(Insets.EMPTY);
        orthographyButton.setBackground(null);
        
        Button grammarButton = new Button();
        ImageView grammarButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/grammar-tab.png")));
        grammarButton.setGraphic(grammarButtonImage);
        grammarButton.setPadding(Insets.EMPTY);
        grammarButton.setBackground(null);
        
        Button lexiconButton = new Button();
        ImageView lexiconButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/lexicon-tab.png")));
        lexiconButton.setGraphic(lexiconButtonImage);
        lexiconButton.setPadding(Insets.EMPTY);
        lexiconButton.setBackground(null);
        
        Button settingsButton = new Button();
        ImageView settingsButtonImage = new ImageView(new Image(App.class.getResourceAsStream("/img/settings-tab.png")));
        settingsButton.setGraphic(settingsButtonImage);
        settingsButton.setPadding(Insets.EMPTY);
        settingsButton.setBackground(null);
        
        VBox navVBox = new VBox(fileButton, phonologyButton, orthographyButton, grammarButton, lexiconButton, settingsButton);
        //navVBox.setPrefHeight(screenBounds.getHeight());
        navVBox.setBackground(woodBackground);
        StackPane navStackPane = new StackPane(navVBox);
        navStackPane.setPadding(new Insets(0, 10, 0, 0));
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navStackPane);
        navBox.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        navBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setBackground(woodBackground);
        navBox.setFitToHeight(true);
        navBox.setPadding(new Insets(0, -20, 0, 0));
        
        VBox leftPapersVBox = new VBox(new ImageView(leftPapers));
        leftPapersVBox.setBackground(leftPapersBG);
        VBox rightPapersVBox = new VBox(new ImageView(rightPapers));
        rightPapersVBox.setBackground(rightPapersBG);
    	//File
        Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        javaVersionLabel.setFont(denyut20);
        Label javaFXVersionLabel = new Label("Bundled with JavaFX SDK 18.0.1.");
        javaFXVersionLabel.setFont(denyut20);
        Label algonquinVersionLabel = new Label("Bundled with AlgonquinTTS 0.3.1, non-release hotfix #1.");
        algonquinVersionLabel.setFont(denyut20);
        Label versionLabel = new Label("Version 0.0.1 \"Otsego\", build 22w41a ***SNAPSHOT VERSION***");
        versionLabel.setFont(denyut20);
        Image bannerLogo = new Image(App.class.getResourceAsStream("/img/logo.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        
        Label madeByOijon = new Label("Brought to you by Oijon - oijon.net");
        madeByOijon.setFont(denyut20);
        Image oijonLogo = new Image(App.class.getResourceAsStream("/img/oijon.png"));
        ImageView oijonView = new ImageView(oijonLogo);
        
        
        
        Button addLanguage = new Button("New\nLanguage");
        addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/new-language.png"))));
        addLanguage.setPadding(Insets.EMPTY);
        addLanguage.setContentDisplay(ContentDisplay.TOP);
        addLanguage.setBackground(null);
        addLanguage.setTextAlignment(TextAlignment.CENTER);
        
        Button makeTestLanguage = new Button("Make Test Language");
        makeTestLanguage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LanguageFile.createTestLanguage();
			}
        	
        });
        Label languageNameLabel = new Label("Language Name (NOTE: cannot be changed)");
        languageNameLabel.setFont(denyut20);
        TextField languageName = new TextField();
        Label languageAutonymLabel = new Label("Language Autonym");
        languageAutonymLabel.setFont(denyut20);
        TextField languageAutonym = new TextField();
        Button createLanguage = new Button("Create!");
        
        createLanguage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Language newLang = new Language(languageName.getText());
				newLang.setAutonym(languageAutonym.getText());
				LanguageFile.createLanguage(newLang);
			}
        	
        });
        
        Button openLanguage = new Button("Open\nLanguage");
        openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/open-language.png"))));
        openLanguage.setPadding(Insets.EMPTY);
        openLanguage.setContentDisplay(ContentDisplay.TOP);
        openLanguage.setBackground(null);
        openLanguage.setTextAlignment(TextAlignment.CENTER);
        
        refreshLanguages();
        
        Button refreshLanguageList = new Button("Refresh");
                
        refreshLanguageList.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		refreshLanguages();
        	}
        });
        
        Button info = new Button("Info");
        info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/info-button.png"))));
        info.setPadding(Insets.EMPTY);
        info.setContentDisplay(ContentDisplay.TOP);
        info.setBackground(null);
        info.setTextAlignment(TextAlignment.CENTER);
        info.setAlignment(Pos.CENTER);
        
        VBox fileTools = new VBox(addLanguage, openLanguage, info);
        fileTools.setBackground(fileToolsBackground);
        
        VBox phonologyTools = new VBox();
        phonologyTools.setBackground(phonologyToolsBackground);
        phonologyTools.setAlignment(Pos.CENTER);
        
        VBox orthographyTools = new VBox();
        orthographyTools.setBackground(orthographyToolsBackground);
        
        VBox grammarTools = new VBox();
        grammarTools.setBackground(grammarToolsBackground);
        
        VBox lexiconTools = new VBox();
        lexiconTools.setBackground(lexiconToolsBackground);
        
        VBox settingsTools = new VBox();
        settingsTools.setBackground(settingsToolsBackground);
        
        VBox leftPage = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        leftPage.setBackground(paperBackground);
        
        VBox binding = new VBox(bindingImage);
        binding.setBackground(bindingBackground);
        
        VBox rightPage = new VBox(oijonView, madeByOijon);
        rightPage.setBackground(paperBackground);
        
        Image fileIndicator = new Image(App.class.getResourceAsStream("/img/file-bar.png"));
        ImageView indicator = new ImageView(fileIndicator);
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(fileToolsBackground);
        
        VBox rightWoodVBox = new VBox(rightWood);
        rightWoodVBox.setBackground(rightWoodBackground);
        
        addLanguage.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/new-language-pressed.png"))));
        	}
        });
        addLanguage.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		addLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/new-language.png"))));
        	}
        });
        addLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		leftPage.getChildren().addAll(languageNameLabel, languageName, languageAutonymLabel, languageAutonym, createLanguage, makeTestLanguage);
        		rightPage.getChildren().clear();
        	}
        });
        openLanguage.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/open-language-pressed.png"))));
        	}
        });
        openLanguage.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		openLanguage.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/open-language.png"))));
        	}
        });
        openLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		leftPage.getChildren().addAll(languageSelect);
        		rightPage.getChildren().clear();
        		rightPage.getChildren().addAll(languageList, refreshLanguageList);
        	}
        });
        info.setOnMousePressed(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/info-button-pressed.png"))));
        	}
        });
        info.setOnMouseReleased(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		info.setGraphic(new ImageView(new Image(App.class.getResourceAsStream("/img/info-button.png"))));
        	}
        });
        info.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		leftPage.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        		rightPage.getChildren().clear();
        		rightPage.getChildren().addAll(oijonView, madeByOijon);
        	}
        });
        
        //Root
        HBox rootHBox = new HBox(navBox, fileTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
        leftPage.setAlignment(Pos.CENTER);
        rightPage.setAlignment(Pos.CENTER);
        rootHBox.setAlignment(Pos.TOP_LEFT);
        leftPage.setPrefWidth(400);
        rightPage.setPrefWidth(400);
        rootHBox.setHgrow(leftPage, Priority.ALWAYS);
        rootHBox.setHgrow(rightPage, Priority.ALWAYS);
        
        rootHBox.setBackground(background);
        Scene root = new Scene(rootHBox);
        
        //Phonology
        
        
        //Navbox actions
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, fileTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        
        phonologyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, phonologyTools);
			}
        });
        orthographyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, orthographyTools);
			}
        });
        grammarButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, grammarTools);
			}
        });
        lexiconButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, lexiconTools);
			}
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, settingsTools);	
			}
        });
        
        
        stage.setScene(root);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/img/icon.png")));
        stage.show();
    }

    public static void refreshLanguages() {
    	
    	File[] files = LanguageFile.getLanguageFiles();
    	languageSelect.getChildren().clear();
    	
    	if (files != null) {
	        for (int i = 0; i < files.length; i++) {
	        	String fileNames = "";
                fileNames += files[i].getName() + "\n";
	        	Label nameLabel = new Label();
	        	nameLabel.setFont(denyut20);
	        	Label timeCreatedLabel = new Label();
	        	timeCreatedLabel.setFont(denyut20);
	        	Label lastAccessedLabel = new Label();
	        	lastAccessedLabel.setFont(denyut20);
	        	Image icon = new Image(App.class.getResourceAsStream("/img/no-image.png"));
	        	ImageView iconView = new ImageView(icon);
	        	HBox box = new HBox();
	        	
	        	try (InputStream input = new FileInputStream(files[i])) {
	
	                Properties prop = new Properties();
	
	                prop.load(input);
	                String name = prop.getProperty("name");
	                nameLabel.setText(name);
	                Long timeCreated = Long.valueOf(prop.getProperty("timeCreated"));
	                Date timeCreatedDate = new Date(timeCreated);
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	                String timeCreatedString = sdf.format(timeCreatedDate);
	                timeCreatedLabel.setText(" Created on: " + timeCreatedString);
	                
	                Long lastAccessed = Long.valueOf(prop.getProperty("lastEdited"));
	                Date lastAccessedDate = new Date(lastAccessed);
	                String lastAccessedString = sdf.format(lastAccessedDate);
	                lastAccessedLabel.setText(" Last modified: " + lastAccessedString);
	
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	
	        	box.getChildren().addAll(iconView, nameLabel, timeCreatedLabel, lastAccessedLabel);
	        	languageSelect.getChildren().add(box);
	        	languageList.setText(fileNames);
	        }
        }
    }
    
    public static void main(String[] args) {
        launch();
    }

}