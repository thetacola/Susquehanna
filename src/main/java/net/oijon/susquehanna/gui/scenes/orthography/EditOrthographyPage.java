package net.oijon.susquehanna.gui.scenes.orthography;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.components.OrthoList;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.OrthographyTools;
import net.oijon.oling.datatypes.language.Language;

public class EditOrthographyPage extends Book {

	public EditOrthographyPage() {
		super();
		id = "ortho.edit";
		toolbox = new OrthographyTools();
		refresh();
	}
	
	@Override
	public void refresh() {
		clear();
		if (hasViewableOrtho()) {
			buildVisible();
		} else {
			buildNonVisible();
		}
	}
	
	@Override
	public void updateOnLanguageChange() {
		super.updateOnLanguageChange();
		refresh();
	}

	private boolean hasViewableOrtho() {
		if (App.getSelectedLang().equals(Language.NULL)) {
			return false;
		} else {
			return true;
		}
	}
	
	private void buildNonVisible() {
		clear();
		
		Label noLangViewOrtho = new Label("Could not display orthography."
				+ " Either no language is selected, or the orthography is invalid.");
		
		addToLeft(noLangViewOrtho);
	}
	
	private void buildVisible() {
		clear();
		this.leftPage.setAlignment(Pos.CENTER);
		
		Label phonemeLabel = new Label("Phoneme(s)");
		Label goesTo = new Label(" --Goes to--> ");
		Label graphemeLabel = new Label("Grapheme(s)");
		
		TextField phonemes = new TextField();
		TextField graphemes = new TextField();
		
		VBox phonemeBox = new VBox(phonemeLabel, phonemes);
		VBox graphemeBox = new VBox(graphemeLabel, graphemes);
		
		HBox fieldContainer = new HBox(phonemeBox, goesTo, graphemeBox);
		
		Button submit = new Button("Add pair");
		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.getSelectedLang().getOrtho().add(phonemes.getText(), graphemes.getText());
				try {
					App.getSelectedLang().toFile(App.getCurrentFile());
				} catch (IOException e) {
					log.err("Could not save orthography!");
				}
				phonemes.clear();
				graphemes.clear();
				App.refreshType("ortho");
			}
			
		});
		
		addToLeft(fieldContainer);
		addToLeft(submit);
		
		OrthoList orthoList = new OrthoList(App.getSelectedLang().getOrtho());
		//PHOSYSTable phonemeTable = new PHOSYSTable(App.getSelectedLang().getPhono().getPhonoSystem());
		
		addToRight(orthoList);
		//addToRight(phonemeTable);
	}

}
