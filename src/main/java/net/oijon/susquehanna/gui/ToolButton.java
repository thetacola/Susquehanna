package net.oijon.susquehanna.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.oijon.susquehanna.App;

/**
 * Creates a button designed to work with the Toolbox
 * @author alex
 *
 */
public class ToolButton extends Button {

	/**
	 * Constructs a button. Please use ToolButton.createActions(new ToolButton()) instead!
	 * @param name The text under the button
	 */
	public ToolButton(String name) {
		String fileName = "/img/";
		fileName += getFileName(name);
		fileName += ".png";
		this.setText(name);
		this.setGraphic(new ImageView(new Image(App.class.getResourceAsStream(fileName))));
		this.setPadding(Insets.EMPTY);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setBackground(null);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setTextFill(Color.WHITE);
	}
	
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
	
	public static ToolButton createActions(ToolButton button) {
		final String fileName = "/img/" + getFileName(button.getText());
		
		button.setOnMousePressed(new EventHandler<MouseEvent>() {
	       	@Override
	       	public void handle(MouseEvent event) {
	       		button.setGraphic(new ImageView(new Image(App.class.getResourceAsStream(fileName + "-pressed.png"))));
	       	}
	       });
		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
	       	@Override
	       	public void handle(MouseEvent event) {
	       		button.setGraphic(new ImageView(new Image(App.class.getResourceAsStream(fileName + ".png"))));
	     	}
	    });
		return button;
	}
	
}
