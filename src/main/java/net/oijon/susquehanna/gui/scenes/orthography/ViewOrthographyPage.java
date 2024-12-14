package net.oijon.susquehanna.gui.scenes.orthography;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.components.OrthoList;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.OrthographyTools;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.orthography.Guesser;

public class ViewOrthographyPage extends Book {

	public ViewOrthographyPage() {
		super();
		id = "ortho.view";
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
		return !App.getSelectedLang().equals(Language.NULL);
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
		this.rightPage.setAlignment(Pos.CENTER);
		
		// ortho guess
		Label orthoGuessLabel = new Label("Convert to Orthography");
		orthoGuessLabel.setFont(Fonts.OPENSANS_BOLD);
		
		VBox orthoGuessInputContainer = new VBox();
		Label orthoGuessInputLabel = new Label("Insert Phonemes Here");
		TextField orthoGuessInputField = new TextField();
		orthoGuessInputContainer.getChildren().addAll(orthoGuessInputLabel, orthoGuessInputField);
		
		VBox orthoGuessOutputContainer = new VBox();
		Label orthoGuessOutputLabel = new Label("Results will appear here");
		TextField orthoGuessOutputField = new TextField();
		orthoGuessOutputField.setEditable(false);
		orthoGuessOutputContainer.getChildren().addAll(orthoGuessOutputLabel, orthoGuessOutputField);
		
		HBox orthoGuessFields = new HBox();
		orthoGuessFields.setAlignment(Pos.CENTER);
		orthoGuessFields.getChildren().addAll(orthoGuessInputContainer, orthoGuessOutputContainer);
		
		VBox orthoGuessContainer = new VBox();
		orthoGuessContainer.setAlignment(Pos.CENTER);
		Button orthoGuessButton = new Button("Guess Orthography");
		orthoGuessButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Language selectedLang = App.getSelectedLang();
				String output = Guesser.orthoGuess(orthoGuessInputField.getText(), selectedLang.getOrtho());
				orthoGuessOutputField.setText(output);
			}
			
		});
		orthoGuessContainer.setPadding(new Insets(10, 10, 10, 10));
		orthoGuessContainer.getChildren().addAll(orthoGuessLabel, orthoGuessFields, orthoGuessButton);
		
		//phono guess
		Label phonoGuessLabel = new Label("Convert to Phonology");
		phonoGuessLabel.setFont(Fonts.OPENSANS_BOLD);
		
		VBox phonoGuessInputContainer = new VBox();
		Label phonoGuessInputLabel = new Label("Insert Text Here");
		TextField phonoGuessInputField = new TextField();
		phonoGuessInputContainer.getChildren().addAll(phonoGuessInputLabel, phonoGuessInputField);
		
		VBox phonoGuessOutputContainer = new VBox();
		Label phonoGuessOutputLabel = new Label("Results will appear here");
		TextField phonoGuessOutputField = new TextField();
		phonoGuessOutputField.setEditable(false);
		phonoGuessOutputContainer.getChildren().addAll(phonoGuessOutputLabel, phonoGuessOutputField);
		
		HBox phonoGuessFields = new HBox();
		phonoGuessFields.setAlignment(Pos.CENTER);
		phonoGuessFields.getChildren().addAll(phonoGuessInputContainer, phonoGuessOutputContainer);
		
		VBox phonoGuessContainer = new VBox();
		phonoGuessContainer.setAlignment(Pos.CENTER);
		Button phonoGuessButton = new Button("Guess Phonemes");
		phonoGuessButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Language selectedLang = App.getSelectedLang();
				String output = Guesser.phonoGuess(phonoGuessInputField.getText(), selectedLang.getOrtho());
				phonoGuessOutputField.setText(output);
			}
			
		});
		phonoGuessContainer.setPadding(new Insets(10, 10, 10, 10));
		phonoGuessContainer.getChildren().addAll(phonoGuessLabel, phonoGuessFields, phonoGuessButton);
		
		OrthoList orthoList = new OrthoList(App.getSelectedLang().getOrtho());
		
		addToLeft(orthoGuessContainer);
		addToLeft(phonoGuessContainer);
		
		addToRight(orthoList);
	}
	
}
