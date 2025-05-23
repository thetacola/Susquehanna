package net.oijon.susquehanna;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.phonology.PhonoSystem;
import net.oijon.olog.Log;
import net.oijon.susquehanna.gui.Navbox;
import net.oijon.susquehanna.gui.resources.Backgrounds;
import net.oijon.susquehanna.gui.resources.Indicator;
import net.oijon.susquehanna.gui.scenes.BlankPage;
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
import net.oijon.susquehanna.gui.scenes.settings.LocalePage;
import net.oijon.susquehanna.gui.toolboxes.FileTools;
import net.oijon.susquehanna.gui.toolboxes.OrthographyTools;
import net.oijon.susquehanna.gui.toolboxes.PhonologyTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.codehaus.plexus.util.FileUtils;

//last edit: 5/22/25 -N3


/**
 * JavaFX App
 * @author alex
 *
 */
public class App extends Application {
	
	static List<Book> books = new ArrayList<Book>();
	static Log log = new Log(System.getProperty("user.home") + "/Susquehanna");
	static VBox languageSelect = new VBox();
	//static TextArea languageList = new TextArea();    
    static Language selectedLanguage = Language.NULL;
    static File currentFile;
    static ImageView BINDING = new ImageView(new Image(App.class.getResourceAsStream("/img/page-binding.png")));
	static ImageView RIGHTWOOD = new ImageView(new Image(App.class.getResourceAsStream("/img/right-wood.png")));
    public static Locale l;
    public static Properties settings = new Properties();
    public static LocaleBundle lb;
	
	public static Stage stage;
	
	private void loadSettings() {
		// copy over everything in localizationPacks folder
		File packsDir = new File(getClass().getResource("/localizationPacks").getFile());
		
		if (!packsDir.exists()) {
			packsDir.mkdir();
		}
		
		File[] packs = packsDir.listFiles();
		for (int i = 0; i < packs.length; i++) {
			File pack = packs[i];
			File dest = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/"
					+ pack.getName());
			if (!dest.exists()) {
				try {
					FileUtils.copyFile(pack, dest);
					log.info("Copied over localization pack " + pack.getName());
				} catch (IOException e) {
					log.err("Could not copy pack " + pack.getName() + "! " + e.toString());
					e.printStackTrace();
				}
			}
		}
		
		// copy over default settings if don't exist
		File f = new File(System.getProperty("user.home") + "/Susquehanna/config.properties");
    	
		if (!f.exists()) {
    		log.warn("Config file not found, copying over default...");
    		URL defaultConfig = getClass().getResource("/config.properties");
    		try {
				FileUtils.copyURLToFile(defaultConfig, f);
				log.info("Default config copied successfully!");
			} catch (IOException e) {
				log.err("Unable to copy over default config file! " + e.toString());
				e.printStackTrace();
			}
    	}
    	
		try {
			settings.load(new FileInputStream(f));
			log.info("Config successfully loaded!");
		} catch (FileNotFoundException e) {
			log.err("Cannot find config!");
			e.printStackTrace();
		} catch (IOException e) {
			log.err("Unable to load config!");
			e.printStackTrace();
		}
		
		l = new Locale(settings.getProperty("language"), settings.getProperty("country"));
	}
	
	public static void saveSettings() {
		File config = new File(System.getProperty("user.home") + "/Susquehanna/config.properties");
		
		OutputStream os;
		try {
			os = new FileOutputStream(config);
			settings.store(os, null);
		} catch (IOException e) {
			try {
				config.createNewFile();
				os = new FileOutputStream(config);
				settings.store(os, null);
			} catch (Exception e1) {
				log.err("Unable to save to config!");
				e1.printStackTrace();
			}
		}		
	}
	
	@Override
	public void init() {
		notifyPreloader(new Preloader.ProgressNotification(0));
		log.info("Initializing application...");
    	
    	//Verify IPA is intact
    	PhonoSystem ipa = PhonoSystem.IPA;
    	ipa.toFile();
    	PhonoSystem ipaFile = new PhonoSystem(new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems/IPA.phosys"));
    	if (ipaFile.toString().equals(ipa.toString())) {
    		if (log.isDebug()) {
    			log.debug("IPA phonology system successfully verified!");
    		}
    	} else {
    		log.err("IPA phonology system could not be verified!");
    	}
        
    	log.info("Loading books...");
    	
    	loadSettings();
    	File localizationDir = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/");
		lb = new LocaleBundle(localizationDir, l);
    	
    	// Create blank placeholders
    	BlankPage phonotactics = new BlankPage();
    	phonotactics.setID("phono.phonotactics");
    	phonotactics.setToolbox(new PhonologyTools());
    	
    	BlankPage script = new BlankPage();
    	script.setID("ortho.script");
    	script.setToolbox(new OrthographyTools());
    	
    	BlankPage grammar = new BlankPage();
    	grammar.setID("grammar.null");
    	
    	// Book instanciation
    	// Has the nice side effect of preloading everything, so no lag when switching scenes :D
    	// file
    	books.add(new InfoPage());
    	books.add(new AddLangPage());
    	books.add(new OpenLangPage());
    	// phono
    	books.add(new EditPhonoPage());
    	books.add(new ViewPhonoPage());
    	books.add(phonotactics);
    	// ortho
    	books.add(new EditOrthographyPage());
    	books.add(new ViewOrthographyPage());
    	books.add(script);
    	// grammar
    	books.add(grammar);
    	// lexicon
    	books.add(new EditWordsPage());
    	books.add(new ViewWordsPage());
    	// settings
    	books.add(new LocalePage());

    	
        ImageView indicator = Indicator.FILE;
        VBox rightIndicator = new VBox(indicator);
        rightIndicator.setBackground(Backgrounds.FILETOOLS);
        
        VBox rightWoodVBox = new VBox(RIGHTWOOD);
        rightWoodVBox.setBackground(Backgrounds.RIGHTWOOD);
        log.info("Initialized!");
	}
	
	@Override
    public void start(Stage stage) {
    	App.stage = stage;
    	log.info("Starting application...");
    	
    	// Oddly, webviews count as "a scene or stage" according to JavaFX
    	// Books do not, despite extending scene...
    	log.info("Loading webview books...");
    	books.add(new ReportBugPage());
    	log.info("Loaded " + books.size() + " books!");
        
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
				log.info("Application closed by user.");
				stage.close();
			}
        	
        });
        stage.show();
        log.info("Started!");
    }

	/**
	 * Lists all books registered in the application
	 * @return A list of all books registered
	 */
	public static List<Book> getSceneList() {
		return books;
	}
	
	/**
	 * Sets the current book displayed
	 * @param s The scene/book to display
	 */
	public static void setScene(Scene s) {
		// TODO: change to loading scene, then change to actual scene once finished loading
		if (s instanceof Book) {
			Book b = (Book) s;
			
			// prevents flashing between scenes
			// getting the stage size includes the window decorations, so
			// if that was used instead, each scene would grow a little bit on switch
			Scene oldScene = stage.getScene();
			HBox hbox = b.getMainHBox();
			hbox.setPrefHeight(oldScene.getHeight());
			hbox.setPrefWidth(oldScene.getWidth());
			
			Navbox nb = b.getNavbox();
			nb.createTransferActions();
		}
		
		stage.setScene(s);
	}
	
	/**
	 * Refreshes all books registered. Useful for locale changes.
	 */
	public static void refreshAll() {
		for (Book book : books) {
			book.refresh();
		}
	}
	
	/**
	 * Refreshes all books in a given category
	 * ex. if "phonology" is the given type, all books following the pattern
	 * "phonology.*" will be refreshed
	 * 
	 * @param type The category to refresh
	 */
	public static void refreshType(String type) {
		for (Book book : books) {
			if (book.getID().startsWith(type)) {
				book.refresh();
			}
		}
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
    	for (Book book : books) {
    		book.updateOnLanguageChange();
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
    
    /**
     * Creates the preloader and launches the application
     * @param args Launch args
     */
    public static void main(String[] args) {
    	System.setProperty("javafx.preloader", LoadingScreen.class.getName());
        launch();
    }

}