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
import net.oijon.oling.LegacyParser;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.phonology.table.PhonoSystem;
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
import net.oijon.susquehanna.gui.scenes.file.WelcomePage;
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
import net.oijon.susquehanna.language.SusquehannaLanguage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
    static SusquehannaLanguage selectedLanguage = new SusquehannaLanguage(null, null);
    static ImageView BINDING = new ImageView(new Image(App.class.getResourceAsStream("/img/page-binding.png")));
	static ImageView RIGHTWOOD = new ImageView(new Image(App.class.getResourceAsStream("/img/right-wood.png")));
    public static Locale l;
    public static Properties settings = new Properties();
    public static LocaleBundle lb;
	
	public static Stage stage;
	
	@SuppressWarnings("deprecation")
	private void convertLegacy() {
		log.info("Checking for legacy files to convert...");
		File homeDir = new File(System.getProperty("user.home") + "/Susquehanna/");
		File[] languages = homeDir.listFiles(new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".language");
    		}
    	});
		
		if (languages.length > 0) {
			log.warn("Found " + languages.length + " to convert!");
			for (int i = 0; i < languages.length; i++) {
				LegacyParser lp = new LegacyParser(languages[i]);
				File newFile = new File(languages[i].toString().replace(".language", ".language.bak"));
				log.info("Moving legacy file to " + newFile);
				languages[i].renameTo(newFile);
			}
		}
	}
	
	private void verifyMetadata() {
		File homeDir = new File(System.getProperty("user.home") + "/Susquehanna/");
		File[] languages = homeDir.listFiles(new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".xml");
    		}
    	});
		File[] metadatas = new File[languages.length];
		for (int i = 0; i < metadatas.length; i++) {
			metadatas[i] = new File(languages[i].toString().replace(".xml", ".meta"));
		}
		
		for (int i = 0; i < metadatas.length; i++) {
			if (!metadatas[i].exists()) {
				log.warn("Found language with missing metadata! Generating...");
				SusquehannaLanguage sl = new SusquehannaLanguage(languages[i], metadatas[i]);
				sl.write();
				log.info("Generated metadata at " + metadatas[i].toString());
			}
		}
		
	}
	
	private void loadSettings() throws URISyntaxException, IOException {
		// copy over everything in localizationPacks folder
		// may or may not have lifted code from Onahsa
		URI uri = Launcher.class.getResource("/localizationPacks").toURI();
        Path myPath = null;
        FileSystem fileSystem = null;
        if (uri.getScheme().equals("jar")) {
	        fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
	        myPath = fileSystem.getPath("/localizationPacks");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        Iterator<Path> it = walk.iterator();
        it.next();
        while (it.hasNext()){
	        try {
	        	Path filePath = it.next();
	        	String idStr = filePath.getFileName().toString();
	        	File packdir = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/");
	        	
	        	if (!packdir.exists()) {
	        		packdir.mkdir();
	        	}
	        	
	        	File newFile = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/" + idStr);
	        	Files.copy(filePath, new FileOutputStream(newFile));
	        	log.info("Copying over localization pack " + newFile.getName());
	        } catch (NoSuchElementException e) {
	        	e.printStackTrace();
	        }
        }
        walk.close();
        if (fileSystem != null) {
        	fileSystem.close();
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
		
		// may not exist in case of an IOException, but if this happens the user has larger problems than missing settings
		if (f.exists()) {
			log.info("Adding any new settings to the config...");
			InputStream defaultStream = this.getClass().getResourceAsStream("/config.properties");
			Properties p = new Properties();
			p.load(defaultStream);
			
			InputStream currentSettings = new FileInputStream(f);
			p.load(currentSettings);
			
			p.store(new FileOutputStream(f), "");
		} else {
			log.err("Unable to check for missing settings, as previous copy attempt failed!");
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
		
		log.info("Checking for legacy files...");
		convertLegacy();
		
		log.info("Verifying language metadata...");
    	verifyMetadata();
		
    	// Verifying IPA used to be here, but is now handled by OLing
        
    	log.info("Loading books...");
    	
    	try {
			loadSettings();
		} catch (URISyntaxException e) {
			log.critical("Unable to load built-in localizations; URI given is invalid‽");
			e.printStackTrace();
		} catch (IOException e) {
			log.critical("Unable to load built-in localizations!!");
			e.printStackTrace();
		}
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
    	books.add(new WelcomePage());
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
    	
        Book book = new WelcomePage();
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
    	setSelectedLang(null);
    }
    
    /**
     * Sets a new selected language
     * @param f The file of the language to be selected
     */
    public static void setSelectedLang(File f) {
    	String metaPath = f.toString().replace(".xml", ".meta");
    	File meta = new File(metaPath);
    	
    	selectedLanguage = new SusquehannaLanguage(f, meta);
    	selectedLanguage.read();
    	
    	for (Book book : books) {
    		book.updateOnLanguageChange();
    	}
    } 
    
    /**
     * Gets the currently selected language
     * @return The currently selected language
     */
    public static Language getSelectedLang() {
    	if (selectedLanguage.getLanguage() == null) {
    		return Language.NULL;
    	}
    	return selectedLanguage.getLanguage();
    }
    
    /**
     * Gets the file connected to the selected language
     * @return The file connected to the selected language
     */
    public static File getCurrentFile() {
    	return selectedLanguage.getFile();
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
		selectedLanguage.write();
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