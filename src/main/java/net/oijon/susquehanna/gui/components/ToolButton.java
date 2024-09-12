package net.oijon.susquehanna.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.oijon.susquehanna.gui.scenes.Book;

//last edit: 6/25/23 -N3

/**
 * Creates a button designed to work with the Toolbox
 * @author alex
 *
 */
public class ToolButton extends Button {

	/**
	 * Constructs a button. 
	 * Before ToolButton.createActions(new ToolButton()) instead!
	 * @param name The text under the button
	 */
	public ToolButton(String name) {
		String fileName = "/img/";
		fileName += getFileName(name);
		fileName += ".png";
		this.setText(name);
		this.setGraphic(new ImageView(new Image(ToolButton.class.getResourceAsStream(fileName))));
		this.setPadding(Insets.EMPTY);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setBackground(null);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setTextFill(Color.WHITE);
        createActions();
	}
	/**
	 * Crea
	 * @param container
	 * @param book
	 */
	@SuppressWarnings("static-access")
	public void createTransferAction(HBox container, Book book) {
		this.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		container.getChildren().clear();
        		container.getChildren().add(book);
        		container.setHgrow(book, Priority.ALWAYS);
        		book.refresh();
        	}
        });
	}
	
	/**
	 * Gets the file name of the images from a button name
	 * @param name The name of the button
	 * @return The file name for the button
	 */
	private static String getFileName(String name) {
		String fileName = "";
		for (int i = 0; i < name.length(); i++) {
			char currentChar = name.charAt(i);
			if (currentChar == ' ') {
				currentChar = '-';
			}
			else if (currentChar == '\n') {
				currentChar = '-';
			}
			currentChar = Character.toLowerCase(currentChar);
			fileName += currentChar;
		}
		return fileName;
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
		final String fileName = "/img/" + getFileName(button.getText());
		
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
