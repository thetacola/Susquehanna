package net.oijon.susquehanna.gui.components;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.gui.scenes.Book;

/**
 * Creates a button designed to work with the Toolbox
 * @author alex
 *
 */
public class ToolButton extends PressableButton {
	
	protected LocaleBundle lb = App.lb;

	/**
	 * Constructs a button. 
	 * Before ToolButton.createActions(new ToolButton()) instead!
	 * @param name The text under the button
	 */
	public ToolButton(String id) {
		super(id);
		this.setText(lb.get("tool." + id));
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
	
}
