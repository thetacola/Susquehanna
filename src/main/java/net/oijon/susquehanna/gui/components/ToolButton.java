package net.oijon.susquehanna.gui.components;

import java.util.List;

import javafx.event.ActionEvent;
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
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.gui.scenes.Book;

/**
 * Creates a button designed to work with the Toolbox
 * @author alex
 *
 */
public class ToolButton extends Button {
	
	LocaleBundle lb = App.lb;
	Log log = App.getLog();
	private String id;

	/**
	 * Constructs a button. 
	 * Before ToolButton.createActions(new ToolButton()) instead!
	 * @param name The text under the button
	 */
	public ToolButton(String name) {
		id = name;
		String fileName = "/img/";
		fileName += name;
		fileName += ".png";
		this.setText(lb.get("tool." + name));
		try {
			Image img = new Image(ToolButton.class.getResourceAsStream(fileName));
			ImageView imgV = new ImageView(img);
			this.setGraphic(imgV);
			this.setPadding(Insets.EMPTY);
	        this.setContentDisplay(ContentDisplay.TOP);
	        this.setBackground(null);
	        this.setTextAlignment(TextAlignment.CENTER);
	        this.setTextFill(Color.WHITE);
	        createActions();
		} catch (NullPointerException e) {
			log.err("Unable to load button image for " + name
					+ " at " + fileName + "! Defaulting to text-only button...");
		}
		createTransferAction();
	}
	/**
	 * Sets the book that this button should transfer the main stage to
	 * @param book The book to be changed to when this button is pressed
	 * @deprecated as of 0.2.0, as the id should now be the name.
	 */
	public void createTransferAction(String id) {
		this.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		List<Book> books = App.getSceneList();
        		for (Book book : books) {
        			if (book.getID().equals(id)) {
        				App.setScene(book);
        				break;
        			}
        		}
        	}
        });
	}
	
	/**
	 * Creates the action to transfer to the book with the same ID as the button.
	 */
	private void createTransferAction() {
		createTransferAction(id);
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
	       		button.setGraphic(new ImageView(new Image(ToolButton.class.getResourceAsStream(fileName + "-pressed.png"))));
	       	}
	       });
		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
	       	@Override
	       	public void handle(MouseEvent event) {
	       		button.setGraphic(new ImageView(new Image(ToolButton.class.getResourceAsStream(fileName + ".png"))));
	     	}
	    });
	}
	
}
