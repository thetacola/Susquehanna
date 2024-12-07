package net.oijon.susquehanna.gui.scenes.lexicon;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.components.WordDisplay;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.LexiconTools;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.lexicon.Word;
import net.oijon.oling.datatypes.lexicon.WordProperty;

public class EditWordsPage extends Book {

	public EditWordsPage() {
		super();
		id = "lexicon.edit";
		toolbox = new LexiconTools();
		refresh();
		Label wordLabel = new Label("Word: ");
		TextField wordInput = new TextField();
		Label meaningLabel = new Label("Meaning: ");
		TextField meaningInput = new TextField();
		//TODO: automatically get pronunciation from orthography
		Label pronounciationLabel = new Label("Pronounciation: ");
		TextField pronounciationInput = new TextField();
		// Button guessPronounciation = new Button("Guess Pronounciation");
		Label etymologyLabel = new Label("Etymology: ");
		TextField etymologyInput = new TextField();
		Label sourceLanguageLabel = new Label("Source Language: ");		
		ComboBox<String> sourceLanguageInput = new ComboBox<String>();
		
		ObservableList<String> options = FXCollections.observableArrayList();
		options.add(" ");
		// TODO: make this get from selected language dir
		File[] langs = Language.getLanguageFiles(
				new File(System.getProperty("user.home") + "/Susquehanna/"));
		for (File langFile : langs) {
			options.add(langFile.getName().replace(".language", ""));
		}
		sourceLanguageInput.setItems(options);
		
		Button addWord = new Button("Add Word");
		addWord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!wordInput.getText().isBlank()) {
					if (!meaningInput.getText().isBlank()) {
						// not in the constructor for testing
						Word word = new Word(wordInput.getText(), meaningInput.getText());
						word.getProperties().setProperty(
								WordProperty.PRONOUNCIATION,
								pronounciationInput.getText());
						word.getProperties().setProperty(
								WordProperty.ETYMOLOGY,
								etymologyInput.getText());
						
						// get source language from selected
						
						
						App.getSelectedLang().getLexicon().addWord(word);
						wordInput.clear();
						meaningInput.clear();
						pronounciationInput.clear();
						etymologyInput.clear();
						// TODO: make source language dropdown go to normal
						try {
							App.getSelectedLang().toFile(App.getCurrentFile());
						} catch (IOException e) {
							log.err(e.toString() + " - Could not add word " +
									word.getProperties().getProperty(WordProperty.NAME));
							e.printStackTrace();
						}
						refresh();
					}
				}
			}
			
		});
		
		addToLeft(wordLabel);
		addToLeft(wordInput);
		addToLeft(meaningLabel);
		addToLeft(meaningInput);
		addToLeft(pronounciationLabel);
		addToLeft(pronounciationInput);
		addToLeft(etymologyLabel);
		addToLeft(etymologyInput);
		addToLeft(sourceLanguageLabel);
		addToLeft(sourceLanguageInput);
		addToLeft(addWord);
	}
	
	@Override
	public void updateOnLanguageChange() {
		super.updateOnLanguageChange();
		refresh();
	}
	
	@Override
	public void refresh() {
		rightPage.getChildren().clear();
		for (int i = 0; i < App.getSelectedLang().getLexicon().size(); i++) {
			WordDisplay wd = new WordDisplay(App.getSelectedLang().getLexicon().getWord(i), false);
			addToRight(wd);
		}
	}
	
	
}
