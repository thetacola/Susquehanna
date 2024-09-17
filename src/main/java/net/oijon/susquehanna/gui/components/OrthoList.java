package net.oijon.susquehanna.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.oling.datatypes.orthography.Orthography;

public class OrthoList extends ScrollPane {

	private String[][] sortedOrtho;
	private Orthography o;
	private int mode;
	
	public OrthoList(Orthography o) {
		this.o = o;
		sortOrtho();
		build();
	}
	
	public void sortOrtho() {
		/**
		 * 0 - via phoneme
		 * 1 - via grapheme
		 */
		sortedOrtho = new String[o.size()][2];
		
		for (int i = 0; i < o.size(); i++) {
			String[] pair = new String[2];
			pair[0] = o.getPair(i).getPhonemes();
			pair[1] = o.getPair(i).getGraphemes();
			sortedOrtho[i] = pair;
		}
		
		boolean sorted = false;
		
		while (!sorted) {
			// sort
			for (int i = 0; i < o.size() - 1; i++) {
				if (sortedOrtho[i][mode].length() > sortedOrtho[i + 1][mode].length()) {
					String tempVar = sortedOrtho[i][mode];
					sortedOrtho[i][mode] = sortedOrtho[i + 1][mode];
					sortedOrtho[i + 1][mode] = tempVar;
				}
			}
			
			boolean sortedSoFar = true;
			// verify
			for (int i = 0; i < o.size() - 1; i++) {
				if (sortedOrtho[i][mode].length() > sortedOrtho[i + 1][mode].length() | !sortedSoFar) {
					sortedSoFar = false;
				}
			}
			
			if (sortedSoFar) {
				sorted = true;
			}
			
		}
		build();
	}
	
	private void build() {
		Label sortByLabel = new Label("Sort by: ");
		Button byPhonemes = new Button("Phonemes");
		byPhonemes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mode = 0;
				build();
			}
			
		});
		Button byGraphemes = new Button("Graphemes");
		byGraphemes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mode = 1;
				build();
			}
			
		});
		HBox sortByBox = new HBox();
		sortByBox.getChildren().addAll(sortByLabel, byPhonemes, byGraphemes);
		VBox orthoBox = new VBox();
		Label phonemeHeader = new Label("Phonemes");
		Label graphemeHeader = new Label("Grapheme");
		phonemeHeader.setPadding(new Insets(0, 10, 0, 10));
		graphemeHeader.setPadding(new Insets(0, 10, 0, 10));
		phonemeHeader.setFont(Fonts.OPENSANS_BOLD);
		graphemeHeader.setFont(Fonts.OPENSANS_BOLD);
		HBox chartHeaders = new HBox(phonemeHeader, graphemeHeader);
		chartHeaders.setAlignment(Pos.TOP_CENTER);
		orthoBox.getChildren().add(chartHeaders);
		orthoBox.setAlignment(Pos.CENTER);
		for (int i = 0; i < sortedOrtho.length; i++) {
			HBox cell = new HBox();
			Label phoneme = new Label(sortedOrtho[i][0]);
			Label grapheme = new Label(sortedOrtho[i][1]);
			phoneme.setPadding(new Insets(0, 10, 0, 10));
			grapheme.setPadding(new Insets(0, 10, 0, 10));
			phoneme.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
			grapheme.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
			cell.getChildren().addAll(phoneme, grapheme);
			orthoBox.getChildren().add(cell);
		}
		VBox container = new VBox(sortByBox, orthoBox);
		container.setAlignment(Pos.CENTER);
		this.setContent(container);
	}
}
