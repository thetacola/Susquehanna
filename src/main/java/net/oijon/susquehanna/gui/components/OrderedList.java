package net.oijon.susquehanna.gui.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class OrderedList extends ScrollPane {

	private String[] labels = new String[0];
	private VBox mainBox = new VBox();
	
	public OrderedList() {
		super();
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setFitToWidth(true);
		this.setContent(mainBox);
	}
	
	public void refresh() {
		mainBox.getChildren().clear();
		for (int i = 0; i < labels.length; i++) {
			OrderedListItem oli = new OrderedListItem(i, this);
			mainBox.getChildren().add(oli);
		}
	}
	
	/**
	 * Adds a string to the bottom of the list
	 * @param string The string to add to the list
	 */
	public void add(String string) {
		String[] newLabels = new String[labels.length + 1];
		for (int i = 0; i < labels.length; i++) {
			newLabels[i] = labels[i];
		}
		newLabels[labels.length] = string;
		labels = newLabels;
		refresh();
	}
	
	/**
	 * Removes the string at the given index
	 * @param i The index of the item to remove
	 */
	public void remove(int i) {
		String[] newLabels = new String[labels.length - 1];
		// from 0 to i, the array is the exact same
		for (int j = 0; j < i; j++) {
			newLabels[j] = labels[j];
		}
		// remove i by skipping over it
		for (int j = i + 1; j < labels.length; j++) {
			newLabels[j - 1] = labels[j];
		}
		labels = newLabels;
		refresh();
	}
	
	/**
	 * Moves an item up by one, if able
	 * @param i The index of the item to move up
	 */
	public void moveUp(int i) {
		if (i <= 0) {
			return;
		} else {
			String tempStr = labels[i];
			labels[i] = labels[i - 1];
			labels[i - 1] = tempStr;
			refresh();
		}
	}
	
	/**
	 * Moves an item down by one, if able
	 * @param i The index of the item to move down
	 */
	public void moveDown(int i) {
		if (i >= labels.length - 1) {
			return;
		} else {
			String tempStr = labels[i];
			labels[i] = labels[i + 1];
			labels[i + 1] = tempStr;
			refresh();
		}
	}
	
	/**
	 * Gets the string at index i
	 * @param i The index of the string to get
	 * @return The string at the given index
	 */
	public String get(int i) throws IndexOutOfBoundsException {
		return labels[i];
	}
	
	public int size() {
		return labels.length;
	}
	
}
