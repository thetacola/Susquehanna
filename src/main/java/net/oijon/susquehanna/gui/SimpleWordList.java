package net.oijon.susquehanna.gui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.oijon.utils.parser.data.Language;
import net.oijon.utils.logger.Log;
import net.oijon.utils.parser.data.Word;

public class SimpleWordList extends ScrollPane {

	public SimpleWordList(Language selectedLanguage, File currentFile) {
		Log log = new Log(System.getProperty("user.home") + "/Susquehanna", true);
		VBox wordList = new VBox();
		for (int i = 0; i < selectedLanguage.getLexicon().size(); i++) {
			Word listword = selectedLanguage.getLexicon().getWord(i);
			Label wordText = new Label(listword.getName());
			Label wordMeaning = new Label(" - " + listword.getMeaning());
			Button removeWord = new Button("Remove Word");
			removeWord.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					selectedLanguage.getLexicon().removeWord(listword);
					try {
						selectedLanguage.toFile(currentFile);
					} catch (IOException e) {
						log.err(e.toString() + " - Could not remove word " + listword.getName());
						e.printStackTrace();
					}
				}
				
			});
			HBox wordHBox = new HBox();
			wordHBox.getChildren().addAll(removeWord, wordText, wordMeaning);
			wordHBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
			wordList.getChildren().add(wordHBox);
		}
		this.setContent(wordList);
		this.setBorder(null);
		this.setPannable(true);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setFitToHeight(true);
		this.setFitToWidth(true);
		this.setPadding(new Insets(0, 0, 0, 10));
	}
	
}
