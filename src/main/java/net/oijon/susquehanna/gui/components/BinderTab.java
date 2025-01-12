package net.oijon.susquehanna.gui.components;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.oijon.olog.Log;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.scenes.Book;

public class BinderTab extends Button {

	String name = "";
	Log log = App.getLog();
	
	/**
	 * Creates a binder tab, using the name of the tab to grab the correct image file
	 * @param name The name of the tab (file, phonology, etc.)
	 */
	public BinderTab(String name) {
		this.name = name;
		String fileName = "/img/";
		fileName += getFileName();
		fileName += "-tab.png";
		this.setGraphic(new ImageView(new Image(ToolButton.class.getResourceAsStream(fileName))));
        this.setPadding(Insets.EMPTY);
        this.setBackground(null);
	}
	
	/**
	 * Creates an action that switches the current scene with a different scene.
	 * @param id The ID of the book to switch to
	 */
	public void createTransferAction(String id) {
		this.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		List<Book> books = App.getSceneList();
        		boolean found = false;
        		for (Book book : books) {
        			String bookID = book.getID();
        			if (bookID.equals(id)) {
        				App.setScene(book);
        				found = true;
        				log.debug("Successfully transfered to \"" + id + "\"!");
        				break;
        			}
        		}
        		if (!found) {
        			log.err("Unable to find book \"" + id + "\"! Is it enabled?");
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
