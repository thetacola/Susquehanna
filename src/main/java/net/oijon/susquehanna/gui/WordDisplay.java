package net.oijon.susquehanna.gui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.oijon.oling.datatypes.Word;

public class WordDisplay extends Parent {
	
	private Word word;
	private boolean detailed = false;
	
	private GridPane gp = new GridPane();
	
	private Label wordLabel = new Label();
	private Label meaningLabel = new Label();
	private Label pronounciationLabel = new Label();
	private Label etymologyLabel = new Label();
	private Label sourceLangLabel = new Label();
	private Label createdLabel = new Label();
	private Label editedLabel = new Label();
	
	public WordDisplay(Word w) {
		this.word = w;
		build();
		refresh();
	}
	
	public WordDisplay(Word w, boolean detailed) {
		this.word = w;
		this.detailed = detailed;
		build();
		refresh();
	}
	
	public void refresh() {
		wordLabel.setText(word.getName());
		meaningLabel.setText(word.getMeaning());
		pronounciationLabel.setText("/" + word.getPronounciation() + "/");
		etymologyLabel.setText("Etymology: " + word.getEtymology());
		// FIXME: show source language!
		sourceLangLabel.setText("");
		createdLabel.setText("Created: " + word.getCreationDate());
		editedLabel.setText("Last Edited: " + word.getEditDate());
	}
	
	private void build() {
		gp.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		gp.setHgap(20);
		gp.setVgap(5);
		gp.setPadding(new Insets(5, 5, 5, 5));
		GridPane.setConstraints(wordLabel, 0, 0);
		GridPane.setConstraints(meaningLabel, 1, 0);
		GridPane.setConstraints(pronounciationLabel, 0, 1);
		GridPane.setConstraints(etymologyLabel, 1, 1);
		GridPane.setConstraints(sourceLangLabel, 1, 2);
		GridPane.setConstraints(createdLabel, 1, 3);
		GridPane.setConstraints(editedLabel, 1, 4);
		
		if (detailed) {
			gp.getChildren().addAll(wordLabel, meaningLabel, pronounciationLabel, etymologyLabel,
					sourceLangLabel, createdLabel, editedLabel);
		} else {
			gp.getChildren().addAll(wordLabel, meaningLabel);
		}
		this.getChildren().add(gp);
	}

}
