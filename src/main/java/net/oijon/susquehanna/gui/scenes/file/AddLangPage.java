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
import net.oijon.susquehanna.gui.scenes.Book;

public class AddLangPage extends Book {

	public AddLangPage() {
		super();
		
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
		
	}

}
