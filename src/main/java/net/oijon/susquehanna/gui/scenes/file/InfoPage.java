package net.oijon.susquehanna.gui.scenes.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Properties;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.SystemInfo;
import net.oijon.susquehanna.gui.components.DependencyView;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.FileTools;

public class InfoPage extends Book {

	LocaleBundle lb = App.lb;
    TextArea debug = new TextArea();
	
	public InfoPage() {
		super();
		id = "file.info";
		toolbox = new FileTools();
		
        Label versionLabel = new Label("Version " + SystemInfo.susquehannaVersion());
        versionLabel.setFont(Fonts.OPENSANS);
        
        ImageView susquehannalogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/icon.png")));
        ImageView oijonLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/oijon.png")));
       
        Label madeByOijon = new Label(lb.get("file.info.broughttoyou"));
        Label debugInfo = new Label(lb.get("file.info.debuginfo"));
        debug.setText(generateDebugInfo());
        madeByOijon.setFont(Fonts.OPENSANS);
        
        DependencyView javaDV = new DependencyView("java.png", "Java", System.getProperty("java.version"));
        DependencyView javafxDV = new DependencyView("javafx.png", "JavaFX", System.getProperty("javafx.runtime.version"));
        DependencyView olingDV = new DependencyView("oling.png", "OLing", net.oijon.oling.info.Info.getVersionNum());
        DependencyView ologDV = new DependencyView("olog.png", "OLog", net.oijon.olog.info.Info.getVersionNum());
        DependencyView otimeDV = new DependencyView("otime.png", "OTime", "0.0.2");
        DependencyView plexusDV = new DependencyView("plexus-utils.png", "Plexus Common Utilities", "4.0.3");
        
        VBox dependencies = new VBox();
        dependencies.setAlignment(Pos.CENTER);
        dependencies.getChildren().addAll(javaDV, javafxDV, olingDV, ologDV, otimeDV, plexusDV);
        
        addToLeft(susquehannalogo);
        addToLeft(versionLabel);
        addToLeft(dependencies);
        
        addToRight(oijonLogo);
        addToRight(madeByOijon);
        addToRight(debugInfo);
        addToRight(debug);
	}

	@Override
	public void refresh() {
		debug.setText(generateDebugInfo());
	}
	
	private String generateDebugInfo() {
		Properties properties = System.getProperties();
		String rawProperties = properties.toString();
		String[] propArray = rawProperties.substring(0, rawProperties.length() - 1).split(", ");
		
		String list = "";
		list += "=== Susquehanna Settings ===\n";
		list += "Locale=" + App.l.toString() + "\n";
		File susquehannaHome = new File(System.getProperty("user.home") + "/Susquehanna/");
		list += "Susquehanna home location=" + susquehannaHome + "\n";
		File[] files = susquehannaHome
				.listFiles(new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".xml");
    		}
    	});
		list += "№ of languages=" + files.length + "\n";
		list += "=== OLog Settings ===\n";
		list += "Log location=" + log.getLogFile() + "\n";
		list += "=== System Settings ===\n";
		for (String prop : propArray) {
			list += prop + "\n";
		}
		return list;
	}
	
}
