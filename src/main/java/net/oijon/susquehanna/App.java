package net.oijon.susquehanna;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.PreloaderNotification;
import javafx.application.Preloader.ProgressNotification;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.oijon.oling.datatypes.Language;
import net.oijon.olog.Log;
import net.oijon.oling.datatypes.PhonoSystem;
import net.oijon.susquehanna.gui.BinderTab;
import net.oijon.susquehanna.gui.ToolButton;
import net.oijon.susquehanna.gui.Toolbox;
import net.oijon.susquehanna.gui.resources.Backgrounds;
import net.oijon.susquehanna.gui.resources.Indicator;
import net.oijon.susquehanna.gui.scenes.BlankPage;
import net.oijon.susquehanna.gui.scenes.file.AddLangPage;
import net.oijon.susquehanna.gui.scenes.file.InfoPage;
import net.oijon.susquehanna.gui.scenes.file.OpenLangPage;
import net.oijon.susquehanna.gui.scenes.file.ReportBugPage;
import net.oijon.susquehanna.gui.scenes.lexicon.EditWordsPage;
import net.oijon.susquehanna.gui.scenes.lexicon.ViewWordsPage;
import net.oijon.susquehanna.gui.scenes.orthography.EditOrthographyPage;
import net.oijon.susquehanna.gui.scenes.orthography.ViewOrthographyPage;
import net.oijon.susquehanna.gui.scenes.phonology.EditPhonoPage;
import net.oijon.susquehanna.gui.scenes.phonology.ViewPhonoPage;

import java.io.File;
import java.io.IOException;

//last edit: 1/2/24 -N3


/**
 * JavaFX App
 * @author alex
 *
 */
public class App extends Application {
	
	static Log log = new Log(System.getProperty("user.home") + "/Susquehanna");
	static VBox languageSelect = new VBox();
	//static TextArea languageList = new TextArea();    
    static Language selectedLanguage = Language.NULL;
    static File currentFile;
    static ImageView BINDING = new ImageView(new Image(App.class.getResourceAsStream("/img/page-binding.png")));
	static ImageView RIGHTWOOD = new ImageView(new Image(App.class.getResourceAsStream("/img/right-wood.png")));
    
	public static Stage stage;
	
    @SuppressWarnings("static-access") // Eclipse does not like how you make specific HBoxes fix the screen.
	@Override
    public void start(Stage stage) {
    	this.stage = stage;
    	
    	log.info("Starting application...");
    	
    	//Verify IPA is intact
    	PhonoSystem IPA = PhonoSystem.IPA;
    	IPA.toFile();
    	PhonoSystem IPAFile = new PhonoSystem(new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems/IPA.phosys"));
    	if (IPAFile.toString().equals(IPA.toString())) {
    		log.debug("IPA phonology system successfully verified!");
    	} else {
    		log.err("IPA phonology system could not be verified!");
    	}
    	
        //Navbox
    	
    	BinderTab fileButton = new BinderTab("file");
    	BinderTab phonologyButton = new BinderTab("phonology");
    	BinderTab orthographyButton = new BinderTab("orthography");
    	BinderTab grammarButton = new BinderTab("grammar");
    	BinderTab lexiconButton = new BinderTab("lexicon");
    	BinderTab settingsButton = new BinderTab("settings");
        
        VBox navVBox = new VBox(fileButton, phonologyButton, orthographyButton, grammarButton, lexiconButton, settingsButton);
        //navVBox.setPrefHeight(screenBounds.getHeight());
        navVBox.setBackground(Backgrounds.WOOD);
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navVBox);
        navBox.setBorder(null);
        navBox.setPannable(true);
        navBox.setVbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setHbarPolicy(ScrollBarPolicy.NEVER);
        navBox.setBackground(Backgrounds.WOOD);
        navBox.setFitToHeight(true);
        navBox.setFitToWidth(true);
        navBox.setPadding(new Insets(0, 0, 0, 10));
        
       
    	//File
        
        // final because it won't compile otherwise :(
    	// shouldn't affect the scuffed way I'm editing it though...
    	final HBox mainBook = new HBox(new InfoPage());        
    	mainBook.getChildren().clear();
		InfoPage infoPage = new InfoPage();
		mainBook.getChildren().add(infoPage);
		mainBook.setHgrow(infoPage, Priority.ALWAYS);

        
        HBox mainToolbox = new HBox();
        
        // define toolboxes
        Toolbox fileTools = new Toolbox(Backgrounds.FILETOOLS);
        Toolbox phonologyTools = new Toolbox(Backgrounds.PHONOLOGYTOOLS);
        Toolbox orthographyTools = new Toolbox(Backgrounds.ORTHOGRAPHYTOOLS);
        Toolbox grammarTools = new Toolbox(Backgrounds.GRAMMARTOOLS);
        Toolbox lexiconTools = new Toolbox(Backgrounds.LEXICONTOOLS);
        Toolbox settingsTools = new Toolbox(Backgrounds.SETTINGSTOOLS);
        
        
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
    	
    	
        ImageView indicator = Indicator.FILE;
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(Backgrounds.FILETOOLS);
        
        VBox rightWoodVBox = new VBox(RIGHTWOOD);
        rightWoodVBox.setBackground(Backgrounds.RIGHTWOOD);
        
        
       
        
        mainToolbox.getChildren().add(fileTools);
        
        
        //Root
        HBox rootHBox = new HBox(navBox, mainToolbox, mainBook, rightIndicator, rightWoodVBox);
        
        rootHBox.setAlignment(Pos.TOP_LEFT);
        rootHBox.setHgrow(mainBook, Priority.ALWAYS);
        
        rootHBox.setBackground(Backgrounds.WOOD);
        Scene root = new Scene(rootHBox);
        
        
        //Lexicon
        

        // create toolbuttons
        ToolButton addLanguage = new ToolButton("New\nLanguage");
        ToolButton openLanguage = new ToolButton("Open\nLanguage");
        ToolButton reportBug = new ToolButton("Report Bug");
        ToolButton info = new ToolButton("Info");
        
        ToolButton viewPhonology = new ToolButton("View\nPhonology");
        ToolButton editPhonemes = new ToolButton("Edit\nPhonology");
        ToolButton phonotactics = new ToolButton("Phonotactics");
        
        ToolButton viewOrthography = new ToolButton("View\nOrthography");
        ToolButton editOrthography = new ToolButton("Edit\nOrthography");
        ToolButton script = new ToolButton("Script");
        
        ToolButton viewWords = new ToolButton("View Words");
        ToolButton editWords = new ToolButton("Edit Words");
        
        // create button actions
        addLanguage.createTransferAction(mainBook, new AddLangPage());
        openLanguage.createTransferAction(mainBook, new OpenLangPage());
        reportBug.createTransferAction(mainBook, new ReportBugPage());
        info.createTransferAction(mainBook, new InfoPage());
        
        viewPhonology.createTransferAction(mainBook, new ViewPhonoPage());
        editPhonemes.createTransferAction(mainBook, new EditPhonoPage());
        phonotactics.createTransferAction(mainBook, new BlankPage());
        
        viewOrthography.createTransferAction(mainBook, new ViewOrthographyPage());
        editOrthography.createTransferAction(mainBook, new EditOrthographyPage());
        script.createTransferAction(mainBook, new BlankPage());
        
        viewWords.createTransferAction(mainBook, new ViewWordsPage());
        editWords.createTransferAction(mainBook, new EditWordsPage());
        
        //add to toolbars
        
        fileTools.getChildren().addAll(addLanguage, openLanguage, info, reportBug);
        phonologyTools.getChildren().addAll(viewPhonology, editPhonemes, phonotactics);
        orthographyTools.getChildren().addAll(viewOrthography, editOrthography, script);
        lexiconTools.getChildren().addAll(viewWords, editWords);
        
        
        //Navbox actions
        
        fileButton.createTransferAction(mainBook, new InfoPage(), mainToolbox, fileTools, indicator, rightIndicator);
        phonologyButton.createTransferAction(mainBook, new ViewPhonoPage(), mainToolbox, phonologyTools, indicator, rightIndicator);
        orthographyButton.createTransferAction(mainBook, new ViewOrthographyPage(), mainToolbox, orthographyTools, indicator, rightIndicator);
        grammarButton.createTransferAction(mainBook, new BlankPage(), mainToolbox, grammarTools, indicator, rightIndicator);
        lexiconButton.createTransferAction(mainBook, new ViewWordsPage(), mainToolbox, lexiconTools, indicator, rightIndicator);
        settingsButton.createTransferAction(mainBook, new BlankPage(), mainToolbox, settingsTools, indicator, rightIndicator);
        
        
        stage.setScene(root);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/img/icon.png")));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				log.critical("Closing...");
				stage.close();
			}
        	
        });
        stage.show();
        log.info("Started!");
    }

    /**
     * Sets the currently selected language to Language.NULL
     */
    public static void setSelectedLangNull() {
    	selectedLanguage = Language.NULL;
    	currentFile = null;
    }
    
    /**
     * Sets a new selected language
     * @param l The language to be selected
     * @param f The file of the newly-selected language
     */
    public static void setSelectedLang(Language l, File f) {
    	selectedLanguage = l;
    	currentFile = f;
    } 
    
    /**
     * Gets the currently selected language
     * @return The currently selected language
     */
    public static Language getSelectedLang() {
    	return selectedLanguage;
    }
    
    /**
     * Gets the file connected to the selected language
     * @return The file connected to the selected language
     */
    public static File getCurrentFile() {
    	return currentFile;
    }
    
    /**
     * Gets the currently used log.
     * @return The log being used
     */
    public static Log getLog() {
    	return log;
    }
    
    /**
     * Gets the current stage. Useful for popup windows
     * @return The current stage
     */
    public static Stage getStage() {
    	return stage;
    }
    
    /**
     * Writes the current contents of the selected language to the file.
     */
    public static void writeToSelectedLang() {
		Language lang = getSelectedLang();
		File file = getCurrentFile();
		try {
			lang.toFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
    	System.setProperty("javafx.preloader", LoadingScreen.class.getName());
        launch();
    }

}