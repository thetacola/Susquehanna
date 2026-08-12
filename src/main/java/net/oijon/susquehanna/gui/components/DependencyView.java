package net.oijon.susquehanna.gui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.resources.Fonts;

public class DependencyView extends HBox {

	private String fileName = "";
	private String displayName = "";
	private String version = "";
	
	public DependencyView(String fileName, String displayName, String version) {
		super();
		this.fileName = fileName;
		this.displayName = displayName;
		this.version = version;
		create();
	}
	
	public DependencyView(String fileName, String version) {
		super();
		this.fileName = fileName;
		this.displayName = fileName;
		this.version = version;
		create();
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
		create();
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		create();
	}
	
	public void setVersion(String version) {
		this.version = version;
		create();
	}
	
	private void create() {
		this.getChildren().clear();
		
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(3, 3, 3, 3));
		
		ImageView logo;
		try {
			logo = new ImageView(new Image(DependencyView.class.getResourceAsStream("/img/dependency-logos/" + fileName)));
		} catch (NullPointerException e) {
			App.getLog().warn("Unable to find file " + fileName + " in dependency logos! Defaulting to placeholder...");
			logo = new ImageView(new Image(DependencyView.class.getResourceAsStream("/img/no-image.png")));
		}
		Label versionLabel = new Label(displayName + " - v" + version);
		versionLabel.setFont(Fonts.OPENSANS);
		versionLabel.setPadding(new Insets(1, 1, 1, 20));
		versionLabel.setWrapText(true);
		
		this.setMaxWidth(300);
		
		this.getChildren().addAll(logo, versionLabel);
	}
}
