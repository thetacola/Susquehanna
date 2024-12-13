package net.oijon.susquehanna.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.phonology.Phonology;
import net.oijon.susquehanna.App;

public class PhonemeEditPopup extends Stage {

	private String phoneme = "";
	
	/**
	 * Creates the popup, with a given phoneme to edit and a linked PhonoTable
	 * The phoneme is changed by the popup, and the PhonoTable is told what to change
	 * @param phoneme The phoneme the popup is to edit
	 * @param pt The linked PhonoTable
	 */
	public PhonemeEditPopup(String phoneme, PhonemeTable pt) {
		this.phoneme = phoneme;
		initPopup(pt);
	}
	
	/**
	 * Gets the phoneme the popup is to edit
	 * @return The phoneme the popup is to edit
	 */
	public String getPhoneme() {
		return phoneme;
	}
	
	/**
	 * Sets the phoneme the popup is to edit
	 * @param phoneme The phoneme the popup is to edit
	 */
	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}
	
	/**
	 * Initialized the popup window and displays it
	 * @param pt The linked PhonoTable
	 */
	private void initPopup(PhonemeTable pt) {
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
		PhonemeEditPopup popup = this;
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		Language lang = App.getSelectedLang();
        		Phonology p = lang.getPhono();
        		String newPhoneme = submitArea.getText();
        		p.getList().remove(phoneme);
    			p.add(newPhoneme);
        		App.writeToSelectedLang();
        		popup.close();
        		App.refreshType("phono");
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
