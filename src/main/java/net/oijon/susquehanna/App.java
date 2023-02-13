package net.oijon.susquehanna;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.oijon.utils.parser.data.Language;
import net.oijon.utils.logger.Log;
import net.oijon.utils.parser.data.PhonoSystem;
import net.oijon.utils.parser.data.Word;
import net.oijon.utils.parser.Parser;
import net.oijon.susquehanna.gui.DetailedWordList;
import net.oijon.susquehanna.gui.PHOSYSTable;
import net.oijon.susquehanna.gui.SimpleWordList;
import net.oijon.susquehanna.gui.ToolButton;
import net.oijon.susquehanna.gui.Toolbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

//last edit: 2/13/23 -N3


/**
 * JavaFX App
 * @author alex
 *
 */
public class App extends Application {
	
	static Log log = new Log(System.getProperty("user.home") + "/Susquehanna");
	
	// Shared resources, mainly backgrounds
	static InputStream is = App.class.getResourceAsStream("/font/Denyut.ttf");
	static Font denyut20 = Font.loadFont(is, 20);
	static InputStream is2 = App.class.getResourceAsStream("/font/OpenSans-Regular.ttf");
	static Font opensans = Font.loadFont(is2, 16);
	static VBox languageSelect = new VBox();
	//static TextArea languageList = new TextArea();
	
	BackgroundSize stretchToFit = new BackgroundSize(100, 100, true, true, true, true);
	BackgroundFill backgroundFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
	BackgroundImage plankImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/wood-texture.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        stretchToFit);
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
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			stretchToFit);
    Background paperBackground = new Background(paperImage);
    
    BackgroundImage padImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/pad-texture.png")),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			stretchToFit);
    Background padBackground = new Background(padImage);
    
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
    
    Image rightPad = new Image(App.class.getResourceAsStream("/img/right-pad.png"));
    BackgroundImage rightPadBI = new BackgroundImage(rightPapers, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
    Background rightPadBG = new Background(rightPadBI);
    
    BackgroundImage brushedMetalImage = new BackgroundImage(new Image(App.class.getResourceAsStream("/img/brushed-metal.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    Background brushedMetal = new Background(brushedMetalImage);

    Image fileIndicator = new Image(App.class.getResourceAsStream("/img/file-bar.png"));
    Image phonologyIndicator = new Image(App.class.getResourceAsStream("/img/phonology-bar.png"));
    Image orthographyIndicator = new Image(App.class.getResourceAsStream("/img/orthography-bar.png"));
    Image grammarIndicator = new Image(App.class.getResourceAsStream("/img/grammar-bar.png"));
    Image lexiconIndicator = new Image(App.class.getResourceAsStream("/img/lexicon-bar.png"));
    Image settingsIndicator = new Image(App.class.getResourceAsStream("/img/settings-bar.png"));
    
    static Language selectedLanguage = Language.NULL;
    static File currentFile;
    
    @SuppressWarnings("static-access") // Eclipse does not like how you make specific HBoxes fix the screen.
	@Override
    public void start(Stage stage) {
    	log.logSystemInfo();
    	log.info("Starting loading screen...");
    	Stage loadingStage = new Stage();
    	loadingStage.setTitle("Susquehanna Conlang Manager - Loading...");
    	loadingStage.getIcons().add(new Image(App.class.getResourceAsStream("/img/icon.png")));
    	
    	ProgressBar loadingBar = new ProgressBar();
    	loadingBar.setProgress(0);
    	Label loadingText = new Label("Starting loading screen...");
    	loadingText.setTextFill(Color.WHITE);
    	VBox loadingVBox = new VBox(loadingText, loadingBar);
    	loadingVBox.setBackground(background);
    	loadingVBox.setAlignment(Pos.CENTER);
    	Scene loadingScene = new Scene(loadingVBox);
    	loadingStage.setScene(loadingScene);
    	loadingStage.show();
    	log.info("Starting application...");
    	loadingText.setText("Starting application...");
    	loadingBar.setProgress(1/6);
    	
    	while (!loadingStage.isShowing()) {
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	
    	//Verify IPA is intact
    	PhonoSystem IPA = PhonoSystem.IPA;
    	IPA.toFile();
    	PhonoSystem IPAFile = new PhonoSystem(new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems/IPA.phosys"));
    	if (IPAFile.toString().equals(IPA.toString())) {
    		loadingText.setText("IPA phonology system successfully verified!");
    		loadingBar.setProgress(2/6);
    		log.debug("IPA phonology system successfully verified!");
    	} else {
    		log.err("IPA phonology system could not be verified!");
    	}
    	
    	loadingText.setText("Loading Navbox...");
    	loadingBar.setProgress(3/6);
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
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navVBox);
        navBox.setBorder(null);
        navBox.setPannable(true);
        navBox.setVbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setBackground(woodBackground);
        navBox.setFitToHeight(true);
        navBox.setFitToWidth(true);
        navBox.setPadding(new Insets(0, 0, 0, 10));
        
        loadingText.setText("Loading File...");
        loadingBar.setProgress(4/6);
        VBox leftPapersVBox = new VBox(new ImageView(leftPapers));
        leftPapersVBox.setBackground(leftPapersBG);
        VBox rightPapersVBox = new VBox(new ImageView(rightPapers));
        rightPapersVBox.setBackground(rightPapersBG);
    	//File
        Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        javaVersionLabel.setFont(opensans);
        Label javaFXVersionLabel = new Label("Bundled with JavaFX " + System.getProperty("javafx.runtime.version") + ".");
        javaFXVersionLabel.setFont(opensans);
        Label utilsVersionLabel = new Label("Powered by " + SystemInfo.utilsVersion());
        utilsVersionLabel.setFont(opensans);
        Label versionLabel = new Label("Version " + SystemInfo.susquehannaVersion());
        versionLabel.setFont(opensans);
        Image bannerLogo = new Image(App.class.getResourceAsStream("/img/icon.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        
        Label madeByOijon = new Label("Brought to you by Oijon - oijon.net");
        madeByOijon.setFont(opensans);
        Image oijonLogo = new Image(App.class.getResourceAsStream("/img/oijon.png"));
        ImageView oijonView = new ImageView(oijonLogo);
        
        ToolButton addLanguage = ToolButton.createActions(new ToolButton("New\nLanguage"));
        
        
        Label languageNameLabel = new Label("Language Name (NOTE: cannot be changed)");
        languageNameLabel.setFont(opensans);
        TextField languageName = new TextField();
        Label languageAutonymLabel = new Label("Language Autonym");
        languageAutonymLabel.setFont(opensans);
        TextField languageAutonym = new TextField();
        Button createLanguage = new Button("Create!");
        
        createLanguage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				log.info("Creating new language...");
				Language newLang = new Language(languageName.getText());
				newLang.setAutonym(languageAutonym.getText());
				try {
					newLang.toFile(new File(System.getProperty("user.home") + "/Susquehanna/" + languageName.getText() + ".language"));
				} catch (IOException e) {
					log.err(e.toString() + " - Could not write new language to file!");
					e.printStackTrace();
				}
				log.info(newLang.getName() + " has been created!");
			}
        	
        });
        
        ToolButton openLanguage = ToolButton.createActions(new ToolButton("Open\nLanguage"));
        refreshLanguages();
        
        Button refreshLanguageList = new Button("Refresh");
                
        refreshLanguageList.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		refreshLanguages();
        	}
        });
        
        ToolButton info = ToolButton.createActions(new ToolButton("Info"));
        
        Toolbox fileTools = new Toolbox(fileToolsBackground);
        
        Toolbox phonologyTools = new Toolbox(phonologyToolsBackground);
        
        Toolbox orthographyTools = new Toolbox(orthographyToolsBackground);
        
        Toolbox grammarTools = new Toolbox(grammarToolsBackground);
        
        Toolbox lexiconTools = new Toolbox(lexiconToolsBackground);
        
        Toolbox settingsTools = new Toolbox(settingsToolsBackground);
        
      //Selection thread
    	Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					if (selectedLanguage != Language.NULL & selectedLanguage.getName().equals(fileTools.getSelected()) == false) {
						Platform.runLater(new Runnable() {
							//This is very silly, but JavaFX cannot be edited from other threads, which is annoying
						    @Override
						    public void run() {
						    	fileTools.setSelected(selectedLanguage.getName());
						    	phonologyTools.setSelected(selectedLanguage.getName());
						    	orthographyTools.setSelected(selectedLanguage.getName());
						    	grammarTools.setSelected(selectedLanguage.getName());
						    	lexiconTools.setSelected(selectedLanguage.getName());
						    	settingsTools.setSelected(selectedLanguage.getName());
						    }
						});
					} else if (selectedLanguage == Language.NULL & fileTools.getSelected().equals("No language selected") == false) {
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	fileTools.setSelected("No language selected");
						    	phonologyTools.setSelected("No language selected");
						    	orthographyTools.setSelected("No language selected");
						    	grammarTools.setSelected("No language selected");
						    	lexiconTools.setSelected("No language selected");
						    	settingsTools.setSelected("No language selected");
						    }
						});
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						log.err(e.toString());
						e.printStackTrace();
					}
				}
				
			}
    		
    	});
    	t1.setDaemon(true);
    	t1.start();
    	
        //VBox leftPage = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
    	VBox leftPage = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, utilsVersionLabel, versionLabel);
        leftPage.setBackground(paperBackground);
        
        VBox binding = new VBox(bindingImage);
        binding.setBackground(bindingBackground);
        
        VBox rightPage = new VBox(oijonView, madeByOijon);
        rightPage.setBackground(paperBackground);
        
        ImageView indicator = new ImageView(fileIndicator);
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(fileToolsBackground);
        
        VBox rightWoodVBox = new VBox(rightWood);
        rightWoodVBox.setBackground(rightWoodBackground);
        
        
        addLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		leftPage.getChildren().addAll(languageNameLabel, languageName, languageAutonymLabel, languageAutonym, createLanguage);
        		rightPage.getChildren().clear();
        	}
        });
        
        openLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		ScrollPane scrollPane = new ScrollPane();
        		scrollPane.setContent(languageSelect);
        		leftPage.getChildren().addAll(scrollPane);
        		rightPage.getChildren().clear();
        		rightPage.getChildren().addAll(refreshLanguageList);
        		refreshLanguages();
        	}
        });
        
        info.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		//leftPage.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        		leftPage.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, versionLabel);
        		rightPage.getChildren().clear();
        		rightPage.getChildren().addAll(oijonView, madeByOijon);
        	}
        });
        
        fileTools.getChildren().addAll(addLanguage, openLanguage, info);
        
        loadingText.setText("Loading root...");
        loadingBar.setProgress(5/6);
        //Root
        HBox rootHBox = new HBox(navBox, fileTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
        leftPage.setAlignment(Pos.CENTER);
        rightPage.setAlignment(Pos.CENTER);
        rootHBox.setAlignment(Pos.TOP_LEFT);
        leftPage.setPrefWidth(400);
        rightPage.setPrefWidth(400);
        rootHBox.setHgrow(leftPage, Priority.ALWAYS);
        rootHBox.setHgrow(rightPage, Priority.ALWAYS);
        
        rootHBox.setBackground(woodBackground);
        Scene root = new Scene(rootHBox);
        
        loadingText.setText("Loading Phonology...");
        loadingBar.setProgress(1);
        //Phonology
        
        ToolButton viewPhonology = ToolButton.createActions(new ToolButton("View\nPhonology"));
        viewPhonology.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		leftPage.getChildren().clear();
        		if (selectedLanguage != Language.NULL) {
        			Label phonoLabel = new Label("Phonology");
        			PHOSYSTable testTable = new PHOSYSTable(selectedLanguage, currentFile);
        			leftPage.getChildren().addAll(phonoLabel, testTable);
        		} else {
        			Label noLangViewPhono = new Label("Could not display phonology."
        					+ " Either no language is selected, or the phonology is invalid.");
        			leftPage.getChildren().addAll(noLangViewPhono);
        		}
        		
        		rightPage.getChildren().clear();
        	}
        });
        
        
        ToolButton editPhonemes = ToolButton.createActions(new ToolButton("Edit\nPhonology"));
        editPhonemes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				leftPage.getChildren().clear();
        		if (selectedLanguage != Language.NULL) {
        			Label phonoLabel = new Label("Phonology");
        			PHOSYSTable table = new PHOSYSTable(selectedLanguage, true, currentFile);
        			leftPage.getChildren().addAll(phonoLabel, table);
        		} else {
        			Label noLangViewPhono = new Label("Could not display phonology."
        					+ " Either no language is selected, or the phonology is invalid.");
        			leftPage.getChildren().addAll(noLangViewPhono);
        		}
        		
        		rightPage.getChildren().clear();
        		if (selectedLanguage != Language.NULL) {
        			Label phonoSystemLabel = new Label("Phonology System - " + selectedLanguage.getPhono().getPhonoSystem().getName());
        			log.debug(selectedLanguage.toString());
        			PHOSYSTable testTable = new PHOSYSTable(selectedLanguage.getPhono().getPhonoSystem());
        			rightPage.getChildren().addAll(phonoSystemLabel, testTable);
        		} else {
        			Label noLangViewPhono = new Label("Could not display phonology system."
        					+ " Either no language is selected, or the phonology system is invalid.");
        			rightPage.getChildren().addAll(noLangViewPhono);
        		}
				
			}
        	
        });
        ToolButton phonotactics = ToolButton.createActions(new ToolButton("Phonotactics"));
        phonologyTools.getChildren().addAll(viewPhonology, editPhonemes, phonotactics);
        
        //Lexicon
        ToolButton viewWords = ToolButton.createActions(new ToolButton("View Words"));
        viewWords.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		if (selectedLanguage != Language.NULL) {
        			leftPage.getChildren().clear();
            		rightPage.getChildren().clear();
            		DetailedWordList wordList = new DetailedWordList(selectedLanguage);
            		leftPage.getChildren().add(wordList);
        		} else {
        			leftPage.getChildren().clear();
        			rightPage.getChildren().clear();
        			Label cantDisplay = new Label("Could not display lexicon."
        					+ " Either no language is selected, or the lexicon is invalid.");
        			leftPage.getChildren().add(cantDisplay);
        		}
        	}
        });
        
        ToolButton editWords = ToolButton.createActions(new ToolButton("Edit Words"));
        editWords.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				leftPage.getChildren().clear();
				Label wordLabel = new Label("Word: ");
				TextField wordInput = new TextField();
				Label meaningLabel = new Label("Meaning: ");
				TextField meaningInput = new TextField();
				//TODO: automatically get pronunciation from orthography
				//TODO: let other parts of the word be editable
				Button addWord = new Button("Add Word");
				addWord.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (!wordInput.getText().isBlank()) {
							if (!meaningInput.getText().isBlank()) {
								Word word = new Word(wordInput.getText(), meaningInput.getText());
								selectedLanguage.getLexicon().addWord(word);
								wordInput.clear();
								meaningInput.clear();
								try {
									selectedLanguage.toFile(currentFile);
								} catch (IOException e) {
									log.err(e.toString() + " - Could not add word " + word.getName());
									e.printStackTrace();
								}
								rightPage.getChildren().clear();
								SimpleWordList wordScroll = new SimpleWordList(selectedLanguage, currentFile);
				        		rightPage.getChildren().add(wordScroll);
							}
						}
					}
					
				});
				leftPage.getChildren().addAll(wordLabel, wordInput, meaningLabel, meaningInput, addWord);
				
				rightPage.getChildren().clear();
				SimpleWordList wordScroll = new SimpleWordList(selectedLanguage, currentFile);
        		rightPage.getChildren().add(wordScroll);
			}
        	
        });

        
        lexiconTools.getChildren().addAll(viewWords, editWords);
        
        //TODO: Enable/disable debug logging in settings
        
        //Navbox actions
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				leftPage.setBackground(paperBackground);
				rightPapersVBox.getChildren().clear();
				//rightPapersVBox.getChildren().add(rightPad);
		        rightPapersVBox.setBackground(rightPadBG);
				rootHBox.getChildren().clear();
        		leftPage.getChildren().clear();
        		rightPage.getChildren().clear();
        		indicator.setImage(fileIndicator);
        		rightIndicator.setBackground(fileToolsBackground);
        		//leftPage.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        		leftPage.getChildren().addAll(bannerLogoView, javaVersionLabel, javaFXVersionLabel, versionLabel);
        		rightPage.getChildren().addAll(oijonView, madeByOijon);
				rootHBox.getChildren().addAll(navBox, fileTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        
        phonologyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		leftPage.setBackground(padBackground);
        		ProgressBar loadingBar = new ProgressBar();
        		leftPage.getChildren().addAll(loadingBar);
        		
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, phonologyTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
        		
        		if (selectedLanguage != Language.NULL) {
        			Label phonoLabel = new Label("Phonology");
        			PHOSYSTable testTable = new PHOSYSTable(selectedLanguage, currentFile);
        			leftPage.getChildren().clear();
        			leftPage.getChildren().addAll(phonoLabel, testTable);
        		} else {
        			Label noLangViewPhono = new Label("Could not display phonology."
        					+ " Either no language is selected, or the phonology is invalid.");
        			leftPage.getChildren().clear();
        			leftPage.getChildren().addAll(noLangViewPhono);
        		}
        		
        		indicator.setImage(phonologyIndicator);
        		rightIndicator.setBackground(phonologyToolsBackground);
        		rightPapersVBox.setBackground(rightPadBG);
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, phonologyTools, leftPapersVBox, leftPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        orthographyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		leftPage.setBackground(paperBackground);
        		rootHBox.getChildren().clear();
        		leftPage.getChildren().clear();
        		rightPage.getChildren().clear();
        		indicator.setImage(orthographyIndicator);
        		rightIndicator.setBackground(orthographyToolsBackground);
        		rootHBox.getChildren().addAll(navBox, orthographyTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        grammarButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		leftPage.setBackground(paperBackground);
        		rootHBox.getChildren().clear();
        		leftPage.getChildren().clear();
        		rightPage.getChildren().clear();
        		indicator.setImage(grammarIndicator);
        		rightIndicator.setBackground(grammarToolsBackground);
        		rootHBox.getChildren().addAll(navBox, grammarTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        lexiconButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		leftPage.setBackground(paperBackground);
        		rootHBox.getChildren().clear();
        		leftPage.getChildren().clear();
        		rightPage.getChildren().clear();
        		indicator.setImage(lexiconIndicator);
        		rightIndicator.setBackground(lexiconToolsBackground);
        		if (selectedLanguage != Language.NULL) {
        			leftPage.getChildren().clear();
            		rightPage.getChildren().clear();
            		DetailedWordList wordList = new DetailedWordList(selectedLanguage);
            		leftPage.getChildren().add(wordList);
        		} else {
        			leftPage.getChildren().clear();
        			rightPage.getChildren().clear();
        			Label cantDisplay = new Label("Could not display lexicon."
        					+ " Either no language is selected, or the lexicon is invalid.");
        			leftPage.getChildren().add(cantDisplay);
        		}
        		rootHBox.getChildren().addAll(navBox, lexiconTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
        	}
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		leftPage.setBackground(paperBackground);
        		rootHBox.getChildren().clear();
        		leftPage.getChildren().clear();
        		rightPage.getChildren().clear();
        		indicator.setImage(settingsIndicator);
        		rightIndicator.setBackground(settingsToolsBackground);
        		rootHBox.getChildren().addAll(navBox, settingsTools, leftPapersVBox, leftPage, binding, rightPage, rightPapersVBox, rightIndicator, rightWoodVBox);
			}
        });
        
        
        stage.setScene(root);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/img/icon.png")));
        stage.show();
        loadingStage.close();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				log.critical("Closing...");
				stage.close();
			}
        	
        });
        log.info("Started!");
    }

    public static void refreshLanguages() {
    	
    	File[] files = Language.getLanguageFiles();
    	log.info("Found " + files.length + " language(s)");
    	log.debug("Language(s) found:");
    	for (int i = 0; i < files.length; i++) {
    		log.debug(files[i].getName());
    	}
    	languageSelect.getChildren().clear();
    	
    	if (files != null) {
	        for (int i = 0; i < files.length; i++) {
	        	Label nameLabel = new Label();
	        	nameLabel.setFont(opensans);
	        	Label timeCreatedLabel = new Label();
	        	timeCreatedLabel.setFont(opensans);
	        	Label lastAccessedLabel = new Label();
	        	lastAccessedLabel.setFont(opensans);
	        	Image icon = new Image(App.class.getResourceAsStream("/img/no-image.png"));
	        	ImageView iconView = new ImageView(icon);
	        	Button select = new Button("Select");
	        	select.setPrefSize(100, 30);
	        	select.setMinSize(100, 30);
	        	Button delete = new Button("Delete");
	        	delete.setPrefSize(100, 30);
	        	delete.setMinSize(100, 30);
	        	VBox infoBox = new VBox();
	        	HBox buttonHBox = new HBox();
	        	HBox box = new HBox();
	        	
	        	try (InputStream input = new FileInputStream(files[i])) {
	        		final File file = files[i];
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
	                
	                select.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Parser parser = new Parser(file);
							try {
								selectedLanguage = parser.parseLanguage();
								currentFile = file;
							} catch (Exception e) {
								selectedLanguage = Language.NULL;
								e.printStackTrace();
								log.err("Unable to select language!");
								log.err(e.toString());
							}
							log.info("Selected language: " + selectedLanguage.getName());
						}
	                	
	                });
	                delete.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Stage delete = new Stage();
							delete.setWidth(400);
							delete.setHeight(150);
							
							Label label = new Label("Are you sure you want to delete " + name + "?");
							Button yes = new Button("Yes");
							yes.setPrefSize(50, 25);
							yes.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									File mainFolder = new File(System.getProperty("user.home") + "/Susquehanna/" + name);
									File langFile = new File(System.getProperty("user.home") + "/Susquehanna/" + name + ".language");
									
									if (selectedLanguage.getName() == name) {
										selectedLanguage = Language.NULL;
										log.err("The currently selected language is being deleted! Unselecting " + name + "...");
									}
									
									try {
										input.close();
									} catch (IOException e) {
										log.err(e.toString());
										e.printStackTrace();
									}
									File[] allContents = mainFolder.listFiles();
								    if (allContents != null) {
								        for (File file : allContents) {
								            if (file.delete()) {
								            	log.info("Deleted " + file.toString());
								            }
								        }
								    }
									mainFolder.delete();
									if (langFile.delete()) {
										log.info("Successfully deleted language " + name + " at " + langFile.toString());
									} else {
										log.err("Unable to delete " + name + " at " + langFile.toString());
									}
									refreshLanguages();
									delete.close();
								}
								
							});
							Button no = new Button("No");
							no.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									delete.close();
								}
								
							});
							no.setPrefSize(50, 25);
							
							HBox buttons = new HBox(yes, no);
							buttons.setAlignment(Pos.CENTER);
							VBox popupBox = new VBox(label, buttons);
							popupBox.setAlignment(Pos.CENTER);
							
							Scene popup = new Scene(popupBox);
							delete.setScene(popup);
							delete.show();
						}
	                	
	                });
	
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        	
	        	buttonHBox.getChildren().addAll(select, delete);
	        	infoBox.getChildren().addAll(nameLabel, timeCreatedLabel, lastAccessedLabel, buttonHBox);
	        	box.getChildren().addAll(iconView, infoBox);
	        	languageSelect.getChildren().add(box);
	        	//languageList.setText(fileNames);
	        }
        }
    	
    }
 
    
    public static void main(String[] args) {
        launch();
    }

}