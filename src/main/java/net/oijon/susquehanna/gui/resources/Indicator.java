package net.oijon.susquehanna.gui.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Indicator {

	private static Image fileIndicator = new Image(Indicator.class.getResourceAsStream("/img/file-bar.png"));
	private static Image phonologyIndicator = new Image(Indicator.class.getResourceAsStream("/img/phonology-bar.png"));
	private static Image orthographyIndicator = new Image(Indicator.class.getResourceAsStream("/img/orthography-bar.png"));
	private static Image grammarIndicator = new Image(Indicator.class.getResourceAsStream("/img/grammar-bar.png"));
	private static Image lexiconIndicator = new Image(Indicator.class.getResourceAsStream("/img/lexicon-bar.png"));
	private static Image settingsIndicator = new Image(Indicator.class.getResourceAsStream("/img/settings-bar.png"));
	
	public static ImageView FILE = new ImageView(fileIndicator);
	public static ImageView PHONOLOGY = new ImageView(phonologyIndicator);
	public static ImageView ORTHOGRAPHY = new ImageView(orthographyIndicator);
	public static ImageView GRAMMAR = new ImageView(grammarIndicator);
	public static ImageView LEXICON = new ImageView(lexiconIndicator);
	public static ImageView SETTINGS = new ImageView(settingsIndicator);
}
