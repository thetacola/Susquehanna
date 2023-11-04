package net.oijon.susquehanna.gui.scenes.lexicon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.SimpleWordList;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.oling.datatypes.Word;

public class EditWordsPage extends Book {

	public EditWordsPage() {
		super();
		
		Label wordLabel = new Label("Word: ");
		TextField wordInput = new TextField();
		Label meaningLabel = new Label("Meaning: ");
		TextField meaningInput = new TextField();
		//TODO: automatically get pronunciation from orthography
		//TODO: let other parts of the word be editable
		Button addWord = new Button("Add Word");
		addWord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!wordInput.getText().isBlank()) {
					if (!meaningInput.getText().isBlank()) {
						Word word = new Word(wordInput.getText(), meaningInput.getText());
						App.getSelectedLang().getLexicon().addWord(word);
						wordInput.clear();
						meaningInput.clear();
						try {
							App.getSelectedLang().toFile(App.getCurrentFile());
						} catch (IOException e) {
							log.err(e.toString() + " - Could not add word " + word.getName());
							e.printStackTrace();
						}
						rightPage.getChildren().clear();
						SimpleWordList wordScroll = new SimpleWordList(App.getSelectedLang(), App.getCurrentFile());
		        		rightPage.getChildren().add(wordScroll);
					}
				}
			}
			
		});
		
		SimpleWordList wordScroll = new SimpleWordList(App.getSelectedLang(), App.getCurrentFile());
		
		addToLeft(wordLabel);
		addToLeft(wordInput);
		addToLeft(meaningLabel);
		addToLeft(meaningInput);
		addToLeft(addWord);
		
		addToRight(wordScroll);
	}
	
	@Override
	public void refresh() {
		
	}
	
	
}
