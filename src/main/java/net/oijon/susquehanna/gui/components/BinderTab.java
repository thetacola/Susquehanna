package net.oijon.susquehanna.gui.components;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.Toolbox;

public class BinderTab extends Button {

	String name = "";
	
	public BinderTab(String name) {
		this.name = name;
		String fileName = "/img/";
		fileName += getFileName();
		fileName += "-tab.png";
		this.setGraphic(new ImageView(new Image(ToolButton.class.getResourceAsStream(fileName))));
        this.setPadding(Insets.EMPTY);
        this.setBackground(null);
	}
	
	public void createTransferAction(String id) {
		this.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		ArrayList<Book> books = App.getSceneList();
        		for (int i = 0; i < books.size(); i++) {
        			String bookID = books.get(i).getID();
        			if (bookID.equals(id)) {
        				App.setScene(books.get(i));
        				break;
        			}
        		}
        	}
        });
	}
	
	/**
	 * Gets the file name of the image from a tab name
	 * @param name The name of the tab
	 * @return The file name for the tab
	 */
	private String getFileName() {
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
}
