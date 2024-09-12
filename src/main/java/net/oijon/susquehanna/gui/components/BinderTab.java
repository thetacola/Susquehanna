package net.oijon.susquehanna.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import net.oijon.susquehanna.gui.scenes.Book;

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
	
	@SuppressWarnings("static-access")
	public void createTransferAction(HBox bookContainer, Book book,
			HBox toolContainer, Toolbox tools, ImageView leftIndicator, VBox rightIndicator) {
		this.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {
        		bookContainer.getChildren().clear();
        		bookContainer.getChildren().add(book);
        		toolContainer.getChildren().clear();
        		toolContainer.getChildren().add(tools);
        		bookContainer.setHgrow(book, Priority.ALWAYS);
        		leftIndicator.setImage(getIndicatorImage());
        		rightIndicator.setBackground(getIndicatorBackground());
        		book.refresh();
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
	
	private Image getIndicatorImage() {
		String fileName = "/img/";
		fileName += name;
		fileName += "-bar.png";
		return new Image(BinderTab.class.getResourceAsStream(fileName));
	}
	
	private Background getIndicatorBackground() {
		BackgroundImage bgImg = new BackgroundImage(getIndicatorImage(),
	    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
		          	BackgroundSize.DEFAULT);
	    return new Background(bgImg);
	}
}
