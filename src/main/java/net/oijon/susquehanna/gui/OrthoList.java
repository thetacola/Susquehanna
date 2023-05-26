package net.oijon.susquehanna.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import net.oijon.utils.parser.data.Orthography;

public class OrthoList extends ScrollPane {

	private String[] sortedOrtho;
	
	public OrthoList(Orthography o) {
		Label sortByLabel = new Label("Sort by: ");
		Button byPhonemes = new Button("Phonemes");
		Button byGraphemes = new Button("Graphemes");
		HBox sortByBox = new HBox();
		sortByBox.getChildren().addAll(sortByLabel, byPhonemes, byGraphemes);
	}	
}
