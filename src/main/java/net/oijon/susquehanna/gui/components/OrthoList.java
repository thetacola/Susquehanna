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
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.oling.datatypes.orthography.Orthography;
import net.oijon.olog.Log;

public class OrthoList extends ScrollPane {

	private Orthography o;
	int sortedPairIDs[];
	private int mode;
	Log log = App.getLog();
	
	/**
	 * Creates an OrthoList from a given orthography
	 * @param o The orthography to display data from
	 */
	public OrthoList(Orthography o) {
		this.o = o;
		
		log.debug("Creating phoneme-grapheme pair pointer array...");
		// initiate pointers to pairs
		sortedPairIDs = new int[o.size()];
		for (int i = 0; i < sortedPairIDs.length; i++) {
			sortedPairIDs[i] = i;
		}
		sortOrtho();
		
		build();
	}
	
	/**
	 * Sorts the orthography, either by phoneme or by grapheme.
	 * To change the mode, use the private int 'mode'.
	 */
	public void sortOrtho() {
		/**
		 * 0 - via phoneme
		 * 1 - via grapheme
		 */
		
		boolean sorted = false;
		
		if (log.isDebug()) {
			log.debug("Starting ortho sort on mode " + mode);
		}
		while (sorted == false) {
			if (sortedPairIDs.length == 0 | sortedPairIDs.length == 1) {
				// any array of length 0 or 1 will be sorted no matter what
				sorted = true;
			}
			for (int i = 0; i < sortedPairIDs.length - 1; i++) {
				String compare1 = "";
				String compare2 = "";
				if (mode == 0) {
					compare1 = o.getPair(sortedPairIDs[i]).getPhonemes();
					compare2 = o.getPair(sortedPairIDs[i + 1]).getPhonemes();
				} else if (mode == 1) {
					compare1 = o.getPair(sortedPairIDs[i]).getGraphemes();
					compare2 = o.getPair(sortedPairIDs[i + 1]).getGraphemes();
				}
				
				if (compare1.compareTo(compare2) > 0) {
					// unsorted pair, sort them and restart check
					int tempInt = sortedPairIDs[i];
					sortedPairIDs[i] = sortedPairIDs[i + 1];
					sortedPairIDs[i + 1] = tempInt;
					break;
				}
				
				if (i == sortedPairIDs.length - 2) {
					// arriving here means that everything below the top has been sorted
					sorted = true;
				}
			}
		}
		if (log.isDebug()) {
			log.debug("Finished ortho sort on mode " + mode);
		}
	}
	
	/**
	 * Builds the OrthoList and allows it to be displayed.
	 */
	private void build() {
		Label sortByLabel = new Label("Sort by: ");
		Button byPhonemes = new Button("Phonemes");
		byPhonemes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mode = 0;
				sortOrtho();
				build();
			}
			
		});
		Button byGraphemes = new Button("Graphemes");
		byGraphemes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mode = 1;
				sortOrtho();
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
		for (int id : sortedPairIDs) {
			HBox cell = new HBox();
			Label phoneme = new Label(o.getPair(id).getPhonemes());
			Label grapheme = new Label(o.getPair(id).getGraphemes());
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
