package net.oijon.susquehanna.gui.scenes.file;

import java.io.File;
import java.util.Properties;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.SystemInfo;
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
		
		Label javaVersionLabel = new Label("Java - " + System.getProperty("java.version"));
        javaVersionLabel.setFont(Fonts.OPENSANS);
        Label javaFXVersionLabel = new Label("JavaFX - " + System.getProperty("javafx.runtime.version"));
        javaFXVersionLabel.setFont(Fonts.OPENSANS);
        Label jcolorVersionLabel = new Label("JColor - v5.5.1");
        jcolorVersionLabel.setFont(Fonts.OPENSANS);
        Label olingVersionLabel = new Label(SystemInfo.olingVersion());
        olingVersionLabel.setFont(Fonts.OPENSANS);
        Label ologVersionLabel = new Label(SystemInfo.ologVersion());
        ologVersionLabel.setFont(Fonts.OPENSANS);
        Label opennlpVersionLabel = new Label("OpenNLP Tools - v1.8.4");
        opennlpVersionLabel.setFont(Fonts.OPENSANS);
        Label otimeVersionLabel = new Label("OTime - v0.0.2");
        otimeVersionLabel.setFont(Fonts.OPENSANS);
        Label versionLabel = new Label("Version " + SystemInfo.susquehannaVersion());
        versionLabel.setFont(Fonts.OPENSANS);
        
        ImageView susquehannalogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/icon.png")));
        ImageView oijonLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/oijon.png")));
        ImageView javaLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/java.png")));
        ImageView javafxLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/javafx.png")));
        ImageView jcolorLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/jcolor.png")));
        ImageView olingLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/oling.png")));
        ImageView ologLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/olog.png")));
        ImageView opennlpLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/opennlp.png")));
        ImageView otimeLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/dependency-logos/otime.png")));

        Label madeByOijon = new Label(lb.get("file.info.broughttoyou"));
        Label debugInfo = new Label(lb.get("file.info.debuginfo"));
        debug.setText(generateDebugInfo());
        madeByOijon.setFont(Fonts.OPENSANS);
        
        addToLeft(susquehannalogo);
        addToLeft(versionLabel);
        addToLeft(javaLogo);
        addToLeft(javaVersionLabel);
        addToLeft(javafxLogo);
        addToLeft(javaFXVersionLabel);
        addToLeft(jcolorLogo);
        addToLeft(jcolorVersionLabel);
        addToLeft(olingLogo);
        addToLeft(olingVersionLabel);
        addToLeft(ologLogo);
        addToLeft(ologVersionLabel);
        addToLeft(opennlpLogo);
        addToLeft(opennlpVersionLabel);
        addToLeft(otimeLogo);
        addToLeft(otimeVersionLabel);
        
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
		list += "=== OLing Settings ===\n";
		File[] files = Language.getLanguageFiles(
				new File(System.getProperty("user.home") + "/Susquehanna/"));
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
