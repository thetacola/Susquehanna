package net.oijon.susquehanna.gui.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.oijon.olog.Log;
import net.oijon.susquehanna.App;

public class PressableButton extends Button {
	
	protected Log log = App.getLog();
	public String id;

	/**
	 * Constructs a button.
	 * @param id The (non-unique) ID of the button. Used for textures.
	 */
	public PressableButton(String id) {
		this.id = id;
		String fileName = "/img/";
		fileName += id;
		fileName += ".png";
		try {
			Image img = new Image(PressableButton.class.getResourceAsStream(fileName));
			ImageView imgV = new ImageView(img);
			this.setGraphic(imgV);
			this.setPadding(Insets.EMPTY);
	        this.setContentDisplay(ContentDisplay.TOP);
	        this.setBackground(null);
	        this.setTextAlignment(TextAlignment.CENTER);
	        this.setTextFill(Color.WHITE);
	        createActions();
		} catch (NullPointerException e) {
			log.err("Unable to load button image for " + id
					+ " at " + fileName + "! Defaulting to text-only button...");
			this.setText(id);
		}
	}
	
	/**
	 * Creates the button images for when pressed
	 */
	private void createActions() {
		// only works because of pass-by-reference
		// have to do this because otherwise the eventhandlers go
		//
		//  🥺  is for me?
		// 👉👈
		Button button = this;
		final String fileName = "/img/" + id;
		
		button.setOnMousePressed(new EventHandler<MouseEvent>() {
	       	@Override
	       	public void handle(MouseEvent event) {
	       		button.setGraphic(new ImageView(new Image(PressableButton.class.getResourceAsStream(fileName + "-pressed.png"))));
	       	}
	       });
		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
	       	@Override
	       	public void handle(MouseEvent event) {
	       		button.setGraphic(new ImageView(new Image(PressableButton.class.getResourceAsStream(fileName + ".png"))));
	     	}
	    });
	}
	
}
