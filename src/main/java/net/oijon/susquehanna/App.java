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
import net.oijon.susquehanna.gui.ToolButton;
import net.oijon.susquehanna.gui.Toolbox;
import net.oijon.susquehanna.gui.scenes.BlankPage;
import net.oijon.susquehanna.gui.scenes.file.AddLangPage;
import net.oijon.susquehanna.gui.scenes.file.InfoPage;
import net.oijon.susquehanna.gui.scenes.file.OpenLangPage;
import net.oijon.susquehanna.gui.scenes.lexicon.EditWordsPage;
import net.oijon.susquehanna.gui.scenes.lexicon.ViewWordsPage;
import net.oijon.susquehanna.gui.scenes.phonology.EditPhonoPage;
import net.oijon.susquehanna.gui.scenes.phonology.ViewPhonoPage;

import java.io.File;
import java.io.InputStream;

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
    
    Image rightPad = new Image(App.class.getResourceAsStream("/img/right-pad.png"));
    BackgroundImage rightPadBI = new BackgroundImage(rightPad, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
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
    	//File
                
        ToolButton addLanguage = new ToolButton("New\nLanguage");
        
        ToolButton openLanguage = new ToolButton("Open\nLanguage");
        
        ToolButton info = new ToolButton("Info");
        
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
    	
    	// final because it won't compile otherwise :(
    	// shouldn't affect the scuffed way I'm editing it though...
    	final HBox mainBook = new HBox(new InfoPage());        
    	mainBook.getChildren().clear();
		InfoPage infoPage = new InfoPage();
		mainBook.getChildren().add(infoPage);
		mainBook.setHgrow(infoPage, Priority.ALWAYS);
    	
        ImageView indicator = new ImageView(fileIndicator);
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(fileToolsBackground);
        
        VBox rightWoodVBox = new VBox(rightWood);
        rightWoodVBox.setBackground(rightWoodBackground);
        
        
        addLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		AddLangPage addLangPage = new AddLangPage();
        		mainBook.getChildren().add(addLangPage);
        		mainBook.setHgrow(addLangPage, Priority.ALWAYS);
        	}
        });
        
        openLanguage.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		OpenLangPage openLangPage = new OpenLangPage();
        		mainBook.getChildren().add(openLangPage);
        		mainBook.setHgrow(openLangPage, Priority.ALWAYS);
        	}
        });
        
        info.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		InfoPage infoPage = new InfoPage();
        		mainBook.getChildren().add(infoPage);
        		mainBook.setHgrow(infoPage, Priority.ALWAYS);
        	}
        });
        
        fileTools.getChildren().addAll(addLanguage, openLanguage, info);
        
        loadingText.setText("Loading root...");
        loadingBar.setProgress(5/6);
        //Root
        HBox rootHBox = new HBox(navBox, fileTools, mainBook, rightIndicator, rightWoodVBox);
        
        rootHBox.setAlignment(Pos.TOP_LEFT);
        rootHBox.setHgrow(mainBook, Priority.ALWAYS);
        
        rootHBox.setBackground(woodBackground);
        Scene root = new Scene(rootHBox);
        
        loadingText.setText("Loading Phonology...");
        loadingBar.setProgress(1);
        //Phonology
        
        ToolButton viewPhonology = new ToolButton("View\nPhonology");
        viewPhonology.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		ViewPhonoPage viewPhonoPage = new ViewPhonoPage();
        		mainBook.getChildren().add(viewPhonoPage);
        		mainBook.setHgrow(viewPhonoPage, Priority.ALWAYS);
        	}
        });
        
        
        ToolButton editPhonemes = new ToolButton("Edit\nPhonology");
        editPhonemes.setOnAction(new EventHandler<ActionEvent>() {

        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		EditPhonoPage editPhonoPage = new EditPhonoPage();
        		mainBook.getChildren().add(editPhonoPage);
        		mainBook.setHgrow(editPhonoPage, Priority.ALWAYS);
        	}
        	
        });
        ToolButton phonotactics = new ToolButton("Phonotactics");
        phonologyTools.getChildren().addAll(viewPhonology, editPhonemes, phonotactics);
        
        //Lexicon
        ToolButton viewWords = new ToolButton("View Words");
        viewWords.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		mainBook.getChildren().clear();
        		ViewWordsPage viewWordsPage = new ViewWordsPage();
        		mainBook.getChildren().add(viewWordsPage);
        		mainBook.setHgrow(viewWordsPage, Priority.ALWAYS);
        	}
        });
        
        ToolButton editWords = new ToolButton("Edit Words");
        editWords.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainBook.getChildren().clear();
        		EditWordsPage editWordsPage = new EditWordsPage();
        		mainBook.getChildren().add(editWordsPage);
        		mainBook.setHgrow(editWordsPage, Priority.ALWAYS);
			}
        	
        });

        
        lexiconTools.getChildren().addAll(viewWords, editWords);
        
        //TODO: Enable/disable debug logging in settings
        
        //Navbox actions
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rootHBox.getChildren().clear();
				mainBook.getChildren().clear();
        		InfoPage infoPage = new InfoPage();
        		mainBook.getChildren().add(infoPage);
        		mainBook.setHgrow(infoPage, Priority.ALWAYS);
        		indicator.setImage(fileIndicator);
        		rightIndicator.setBackground(fileToolsBackground);
        		rootHBox.getChildren().addAll(navBox, fileTools, mainBook, rightIndicator, rightWoodVBox);
			}
        });
        
        phonologyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
        		mainBook.getChildren().clear();
        		ViewPhonoPage viewPhonoPage = new ViewPhonoPage();
        		mainBook.getChildren().add(viewPhonoPage);
        		mainBook.setHgrow(viewPhonoPage, Priority.ALWAYS);
        		
        		indicator.setImage(phonologyIndicator);
        		rightIndicator.setBackground(phonologyToolsBackground);
        		rootHBox.getChildren().clear();
				rootHBox.getChildren().addAll(navBox, phonologyTools, mainBook, rightIndicator, rightWoodVBox);
			}
        });
        orthographyButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
        		mainBook.getChildren().clear();
        		BlankPage blankPage = new BlankPage();
        		mainBook.getChildren().add(blankPage);
        		mainBook.setHgrow(blankPage, Priority.ALWAYS);
        		
        		indicator.setImage(orthographyIndicator);
        		rightIndicator.setBackground(orthographyToolsBackground);
        		rootHBox.getChildren().addAll(navBox, orthographyTools, mainBook, rightIndicator, rightWoodVBox);
			}
        });
        grammarButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
        		mainBook.getChildren().clear();
        		BlankPage blankPage = new BlankPage();
        		mainBook.getChildren().add(blankPage);
        		mainBook.setHgrow(blankPage, Priority.ALWAYS);
        		
        		indicator.setImage(grammarIndicator);
        		rightIndicator.setBackground(grammarToolsBackground);
        		rootHBox.getChildren().addAll(navBox, grammarTools, mainBook, rightIndicator, rightWoodVBox);
			}
        });
        lexiconButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
        		mainBook.getChildren().clear();
        		ViewWordsPage viewWordsPage = new ViewWordsPage();
        		mainBook.getChildren().add(viewWordsPage);
        		mainBook.setHgrow(viewWordsPage, Priority.ALWAYS);

        		indicator.setImage(lexiconIndicator);
        		rightIndicator.setBackground(lexiconToolsBackground);
        		rootHBox.getChildren().addAll(navBox, lexiconTools, mainBook, rightIndicator, rightWoodVBox);
        	}
        });
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
			public void handle(ActionEvent event) {
        		rootHBox.getChildren().clear();
        		mainBook.getChildren().clear();
        		BlankPage blankPage = new BlankPage();
        		mainBook.getChildren().add(blankPage);
        		mainBook.setHgrow(blankPage, Priority.ALWAYS);
        		
        		indicator.setImage(settingsIndicator);
        		rightIndicator.setBackground(settingsToolsBackground);
        		rootHBox.getChildren().addAll(navBox, settingsTools, mainBook, rightIndicator, rightWoodVBox);
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

    public static void setSelectedLangNull() {
    	selectedLanguage = Language.NULL;
    	currentFile = null;
    }
    
    public static void setSelectedLang(Language l, File f) {
    	selectedLanguage = l;
    	currentFile = f;
    } 
    
    public static Language getSelectedLang() {
    	return selectedLanguage;
    }
    
    public static File getCurrentFile() {
    	return currentFile;
    }
    
    public static void main(String[] args) {
        launch();
    }

}