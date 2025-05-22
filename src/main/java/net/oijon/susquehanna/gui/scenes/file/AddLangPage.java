package net.oijon.susquehanna.gui.scenes.file;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.FileTools;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;

public class AddLangPage extends Book {

	LocaleBundle lb = App.lb;
	
	public AddLangPage() {
		super();
		id = "file.add";
		toolbox = new FileTools();
		
		Label languageNameLabel = new Label(lb.get("file.add.name"));
	    languageNameLabel.setFont(Fonts.OPENSANS);
	    TextField languageName = new TextField();
	    Label languageAutonymLabel = new Label(lb.get("file.add.autonym"));
	    languageAutonymLabel.setFont(Fonts.OPENSANS);
	    TextField languageAutonym = new TextField();
	    Button createLanguage = new Button(lb.get("file.add.create"));
	    
	    createLanguage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				log.info("Creating new language...");
				Language newLang = new Language(languageName.getText());
				newLang.getProperties().setProperty(LanguageProperty.AUTONYM,
						languageAutonym.getText());
				try {
					newLang.toFile(new File(System.getProperty("user.home") + "/Susquehanna/" + languageName.getText() + ".language"));
				} catch (IOException e) {
					log.err(e.toString() + " - Could not write new language to file!");
					e.printStackTrace();
				}
				log.info(newLang.getProperties().getProperty(LanguageProperty.NAME) + " has been created!");
			}
        	
        });
	    
	    addToLeft(languageNameLabel);
	    addToLeft(languageName);
	    addToLeft(languageAutonymLabel);
	    addToLeft(languageAutonym);
	    addToLeft(createLanguage);
	}
	
	@Override
	public void refresh() {
		// nothing to refresh
	}

}
