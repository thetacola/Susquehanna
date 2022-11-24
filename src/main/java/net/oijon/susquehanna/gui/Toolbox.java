package net.oijon.susquehanna.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import net.oijon.susquehanna.App;

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
	
	ImageView divLine = new ImageView(new Image(App.class.getResourceAsStream("/img/div-line.png")));
	
	public Toolbox(Background background) {
		this.setBackground(background);
        this.setPrefWidth(100);
        this.setAlignment(Pos.TOP_CENTER);
        
        selectLabel.setTextFill(Color.WHITE);
    	selectLabel.setWrapText(true);
    	selectLabel.setTextAlignment(TextAlignment.CENTER);
    	selectLabel.setStyle("-fx-font: 16 arial; -fx-font-weight: bold;");
    	
    	this.getChildren().addAll(selectLabel, divLine);
	}
	
	public void setSelected(String name) {
		selectLabel.setText(name);
	}
	
	public String getSelected() {
		return selectLabel.getText();
	}
	
}
