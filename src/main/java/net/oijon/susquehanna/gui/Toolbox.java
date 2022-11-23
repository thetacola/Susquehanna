package net.oijon.susquehanna.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Creates a VBox with the parameters of all Toolboxes
 * @author alex
 *
 */
public class Toolbox extends VBox {
	/**
	 * Creates a Toolbox
	 * @param background The image for the Toolbox, should be named something like {name}-bar.png
	 */
	private Label selectLabel = new Label("No language selected");
	
	public Toolbox(Background background) {
		this.setBackground(background);
        this.setPrefWidth(100);
        this.setAlignment(Pos.TOP_CENTER);
        
        selectLabel.setTextFill(Color.WHITE);
    	selectLabel.setWrapText(true);
    	selectLabel.setTextAlignment(TextAlignment.CENTER);
    	
    	this.getChildren().addAll(selectLabel);
	}
	
	public void setSelected(String name) {
		selectLabel.setText(name);
	}
	
	public String getSelected() {
		return selectLabel.getText();
	}
	
}
