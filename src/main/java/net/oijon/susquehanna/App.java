package net.oijon.susquehanna;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;
import net.oijon.oling.datatypes.phonology.PhonoSystem;
import net.oijon.olog.Log;
import net.oijon.susquehanna.gui.Navbox;
import net.oijon.susquehanna.gui.resources.Backgrounds;
import net.oijon.susquehanna.gui.resources.Indicator;
import net.oijon.susquehanna.gui.scenes.Book;
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
import net.oijon.susquehanna.gui.toolboxes.FileTools;
import net.oijon.susquehanna.gui.toolboxes.Toolbox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//last edit: 9/12/24 -N3


/**
 * JavaFX App
 * @author alex
 *
 */
public class App extends Application {
	
	static ArrayList<Book> books = new ArrayList<Book>();
	static Log log = new Log(System.getProperty("user.home") + "/Susquehanna");
	static VBox languageSelect = new VBox();
	//static TextArea languageList = new TextArea();    
    static Language selectedLanguage = Language.NULL;
    static File currentFile;
    static ImageView BINDING = new ImageView(new Image(App.class.getResourceAsStream("/img/page-binding.png")));
	static ImageView RIGHTWOOD = new ImageView(new Image(App.class.getResourceAsStream("/img/right-wood.png")));
    
	public static Stage stage;
	
	@Override
    public void start(Stage stage) {
    	App.stage = stage;
    	
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
        
    	log.debug("Loading scenes...");
    	// Book instanciation
    	books.add(new InfoPage());
    	books.add(new AddLangPage());
    	books.add(new OpenLangPage());
    	books.add(new ReportBugPage());
    	books.add(new EditPhonoPage());
    	books.add(new ViewPhonoPage());
    	books.add(new EditOrthographyPage());
    	books.add(new ViewOrthographyPage());
    	books.add(new EditWordsPage());
    	books.add(new ViewWordsPage());
    	log.debug("Loaded " + books.size() + " scenes!");
    	
        ImageView indicator = Indicator.FILE;
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(Backgrounds.FILETOOLS);
        
        VBox rightWoodVBox = new VBox(RIGHTWOOD);
        rightWoodVBox.setBackground(Backgrounds.RIGHTWOOD);
        
        Navbox navbox = new Navbox();
        navbox.createTransferActions();
        
        Book book = new InfoPage();
        book.setNavbox(navbox);
        book.setToolbox(new FileTools());
        
        stage.setScene(book);
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

	public static ArrayList<Book> getSceneList() {
		return books;
	}
	
	public static void setScene(Scene s) {
		// TODO: change to loading scene, then change to actual scene once finished loading
		if (s instanceof Book) {
			Book b = (Book) s;
			Navbox nb = b.getNavbox();
			nb.createTransferActions();
		}
		
		stage.setScene(s);
	}
	
    /**
     * Sets the currently selected language to Language.NULL
     */
    public static void setSelectedLangNull() {
    	setSelectedLang(Language.NULL, null);
    }
    
    /**
     * Sets a new selected language
     * @param l The language to be selected
     * @param f The file of the newly-selected language
     */
    public static void setSelectedLang(Language l, File f) {
    	selectedLanguage = l;
    	currentFile = f;
    	for (int i = 0; i < books.size(); i++) {
    		books.get(i).updateOnLanguageChange();
    	}
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