package net.oijon.susquehanna.gui.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Indicator {

	// images
	
	private static Image loadImage(String name) {
		return new Image(Indicator.class.getResourceAsStream("/img/" + name));
	}
	
	// imageviews
	public static ImageView FILE = new ImageView(loadImage("file-bar.png"));
	public static ImageView PHONOLOGY = new ImageView(loadImage("phonology-bar.png"));
	public static ImageView ORTHOGRAPHY = new ImageView(loadImage("orthography-bar.png"));
	public static ImageView GRAMMAR = new ImageView(loadImage("grammar-bar.png"));
	public static ImageView LEXICON = new ImageView(loadImage("lexicon-bar.png"));
	public static ImageView SETTINGS = new ImageView(loadImage("settings-bar.png"));
}
