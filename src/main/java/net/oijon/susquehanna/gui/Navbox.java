package net.oijon.susquehanna.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import net.oijon.susquehanna.gui.components.BinderTab;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class Navbox extends ScrollPane {

	BinderTab fileButton = new BinderTab("file");
	BinderTab phonologyButton = new BinderTab("phonology");
	BinderTab orthographyButton = new BinderTab("orthography");
	BinderTab grammarButton = new BinderTab("grammar");
	BinderTab lexiconButton = new BinderTab("lexicon");
	BinderTab settingsButton = new BinderTab("settings");
	
	public Navbox() {
		super();
    	
        VBox navVBox = new VBox(fileButton, phonologyButton, orthographyButton, grammarButton, lexiconButton, settingsButton);
        //navVBox.setPrefHeight(screenBounds.getHeight());
        navVBox.setBackground(Backgrounds.WOOD);
        this.setContent(navVBox);
        this.setBorder(null);
        this.setPannable(true);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setBackground(Backgrounds.WOOD);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        this.setPadding(new Insets(0, 0, 0, 10));
        
        createTransferActions();
	}
	
	public void createTransferActions() {
		// transfer actions
    	fileButton.createTransferAction("file.info");
    	phonologyButton.createTransferAction("phono.view");
    	orthographyButton.createTransferAction("ortho.view");
    	grammarButton.createTransferAction("grammar.null");
    	lexiconButton.createTransferAction("lexicon.view");
    	settingsButton.createTransferAction("settings.null");
	}
	
}
