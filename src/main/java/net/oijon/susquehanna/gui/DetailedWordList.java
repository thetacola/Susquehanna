package net.oijon.susquehanna.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.oijon.oling.datatypes.Language;
import net.oijon.oling.datatypes.Word;

public class DetailedWordList extends ScrollPane {
	
	Language lang;

	public DetailedWordList(Language selectedLanguage) {
		this.lang = selectedLanguage;
		makeMainBox();
	}
	
	private void makeMainBox() {
		VBox wordsBox = new VBox();
    	for (int i = 0; i < lang.getLexicon().size(); i++) {
    		Word word = lang.getLexicon().getWord(i);
    		HBox wordBox = new HBox();
    		VBox leftInfo = new VBox();
    		Label nameLabel = new Label(word.getName());
    		Label meaningLabel = new Label(word.getMeaning());
    		leftInfo.getChildren().addAll(nameLabel, meaningLabel);
    		VBox rightInfo = new VBox();
    		Label etymologyLabel = new Label(word.getEtymology());
    		Label creationDateLabel = new Label("Created on " + word.getCreationDate().toString());
    		Label editDateLabel = new Label("Last edited " + word.getEditDate().toString());
    		rightInfo.getChildren().addAll(etymologyLabel, creationDateLabel, editDateLabel);
    		rightInfo.setAlignment(Pos.TOP_RIGHT);
    		wordBox.getChildren().addAll(leftInfo, rightInfo);
    		wordBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
    		wordsBox.getChildren().add(wordBox);
    	}
    	this.setContent(wordsBox);
		this.setBorder(null);
		this.setPannable(true);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setFitToHeight(true);
		this.setFitToWidth(true);
        this.setPadding(new Insets(0, 0, 0, 10));
	}
	
}
