package net.oijon.susquehanna.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.oijon.oling.datatypes.Language;
import net.oijon.oling.datatypes.Phonology;
import net.oijon.susquehanna.App;

public class PhonemeEditPopup extends Stage {

	private String phoneme = "";
	private PhonemeTable pt;
	
	public PhonemeEditPopup(String phoneme, PhonemeTable pt) {
		this.phoneme = phoneme;
		this.pt = pt;
		initPopup();
	}
	
	public String getPhoneme() {
		return phoneme;
	}
	
	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}
	
	private void initPopup() {
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(App.getStage());
		this.setTitle("Changing phoneme: " + phoneme);
		
        VBox dialogVbox = new VBox();         
        
        HBox bottomBar = new HBox();
        TextField submitArea = new TextField(phoneme);
        submitArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Button submitButton = new Button("Submit");
        submitButton.setDefaultButton(true);
        // used to make popup close properly on submit
        @SuppressWarnings("unused")
		PhonemeEditPopup popup = this;
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		Language lang = App.getSelectedLang();
        		Phonology p = lang.getPhono();
        		String newPhoneme = submitArea.getText();
        		if (p.getPhonoSystem().isIn(newPhoneme) & !newPhoneme.isBlank()) {
        			p.remove(phoneme);
        			p.add(submitArea.getText());
        		}
        		App.writeToSelectedLang();
        		pt.refresh();
        		popup.close();            
        	}
        });
        bottomBar.getChildren().addAll(submitArea, submitButton);
        
        PhonemeKeyboard keyboard = new PhonemeKeyboard(App.getSelectedLang().getPhono().getPhonoSystem(),
        		submitArea);
        
        dialogVbox.getChildren().addAll(keyboard, bottomBar);
        Scene dialogScene = new Scene(dialogVbox);
        
        this.setScene(dialogScene);
	}
	
}
