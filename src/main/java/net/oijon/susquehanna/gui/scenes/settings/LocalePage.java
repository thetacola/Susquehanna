package net.oijon.susquehanna.gui.scenes.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.SettingsTools;

public class LocalePage extends Book {

	LocaleBundle lb = App.lb;
	
	public LocalePage() {
		super();
		id = "settings.locale";
		toolbox = new SettingsTools();
		
		refresh();
	}
	
	@Override
	public void refresh() {
		clearLeft();
		
		Label setLanguage = new Label(lb.get(id + ".setlang"));
		
		addToLeft(setLanguage);
		
		Locale[] packLocales = getLocales();
		String[] packNames = getNames();
		
		for (int i = 0; i < packLocales.length; i++) {
			HBox hbox = new HBox();
			hbox.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			Label label = new Label(packNames[i]);
			
			Button sel = new Button(lb.get("gen.select"));
			
			Locale l = packLocales[i];
			File localizationDir = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/");
			sel.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					lb.load(localizationDir, l);
					App.settings.setProperty("language", l.getLanguage());
					App.settings.setProperty("country", l.getCountry());
					App.saveSettings();
					App.refreshAll();
				}
				
			});			
			hbox.getChildren().addAll(label, sel);
			
			addToLeft(hbox);
		}

	}
	
	private ArrayList<File> getFiles() {
		File localizationDir = new File(System.getProperty("user.home") + "/Susquehanna/localizationPacks/");
		File[] f = localizationDir.listFiles();
		log.debug("Found " + f.length + " localization packs...");
		ArrayList<File> matchingFiles = new ArrayList<File>();
		for (int i = 0; i < f.length; i++) {
			if (f[i].isFile()) {
				String fileName = f[i].getName();
				if (fileName.startsWith("SusquehannaBundle") && fileName.endsWith(".properties")) {
					matchingFiles.add(f[i]);
				}
			}
		}
		
		return matchingFiles;
	}
	
	private String[] getNames() {
		ArrayList<File> matchingFiles = getFiles();
		
		String[] retArr = new String[matchingFiles.size()];
		
		for (int i = 0; i < matchingFiles.size(); i++) {
			Properties p = new Properties();
			FileInputStream fis;
			
			// TODO: this seems like it'd be rather slow...
			try {
				fis = new FileInputStream(matchingFiles.get(i));
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
				p.load(br);
			} catch (Exception e) {
				log.err("Unable to load locale pack " + matchingFiles.get(i).getName()
						+ ", despite finding it. - " + e.toString());
				e.printStackTrace();
			}
			
			String language = p.getProperty("locale.lang");
			String country = p.getProperty("locale.country");
			
			String fileName = matchingFiles.get(i).getName();
			fileName.replace("SusquehannaBundle_", "");
			fileName.replace(".properties", "");
			
			retArr[i] = "";
			if (language != null) {
				retArr[i] += language;
			}
			if (country != null) {
				retArr[i] += " (" + country + ")";
			}
			
			if (language != null) {
				retArr[i] += " - ";
			}
			retArr[i] += fileName;
			
			
		}
		
		return retArr;
	}
	
	private Locale[] getLocales() {
		ArrayList<File> matchingFiles = getFiles();
		
		Locale[] retArr = new Locale[matchingFiles.size()];
		
		for (int i = 0; i < matchingFiles.size(); i++) {
			String fileName = matchingFiles.get(i).getName();
			
			String[] splitPoint = fileName.split("\\.");
			
			fileName = "";
			for (int j = 0; j < splitPoint.length - 1; j++) {
				fileName += splitPoint[j];
			}
			log.debug("Localization pack name: " + fileName);
			
			String[] splitName = fileName.split("_");
			
			if (splitName.length < 2) {
				retArr[i] = Locale.US;
			} else if (splitName.length > 2) {
				retArr[i] = new Locale(splitName[1], splitName[2]); 
			} else {
				retArr[i] = new Locale(splitName[1]);
			}
			
		}
		
		return retArr;
	}

}
