package net.oijon.susquehanna.gui.resources;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Backgrounds {
	public static BackgroundSize STRETCH_TO_FIT_SIZE = new BackgroundSize(100, 100, true, true, true, true);
	
	// private attributes needed for backgrounds to work
	private static BackgroundFill DEFAULT_FILL = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
	
	// repeats
	// array allows for future expansion where x and y repeat are different
	private static BackgroundRepeat[] ALL_REPEAT = {BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT};
	private static BackgroundRepeat[] NONE_REPEAT = {BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT};
	
	private static Image loadImage(String name) {
		return new Image(Indicator.class.getResourceAsStream("/img/" + name));
	}
	private static BackgroundImage loadBgImage(String name, BackgroundRepeat[] repeats, BackgroundSize size) {
		Image i = loadImage(name);
		return new BackgroundImage(i, repeats[0], repeats[1], BackgroundPosition.DEFAULT, size);
	}
	
    // public backgrounds
    public static Background WOOD = new Background(loadBgImage("wood-texture.png", ALL_REPEAT, STRETCH_TO_FIT_SIZE));
    public static Background DEFAULT = new Background(DEFAULT_FILL);
    public static Background BINDING = new Background(loadBgImage("page-binding.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background RIGHTWOOD = new Background(loadBgImage("right-wood.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background PAPER = new Background(loadBgImage("paper-texture.png", NONE_REPEAT, STRETCH_TO_FIT_SIZE));
    public static Background PAD = new Background(loadBgImage("pad-texture.png", NONE_REPEAT, STRETCH_TO_FIT_SIZE));
    public static Background EMPTYTOOLS = new Background(loadBgImage("empty-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background FILETOOLS = new Background(loadBgImage("file-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background PHONOLOGYTOOLS = new Background(loadBgImage("phonology-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background ORTHOGRAPHYTOOLS = new Background(loadBgImage("orthography-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background GRAMMARTOOLS = new Background(loadBgImage("grammar-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background LEXICONTOOLS = new Background(loadBgImage("lexicon-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background SETTINGSTOOLS = new Background(loadBgImage("settings-bar.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background RIGHTPAD = new Background(loadBgImage("right-pad.png", ALL_REPEAT, BackgroundSize.DEFAULT));
    public static Background BRUSHEDMETAL = new Background(loadBgImage("brushed-metal.png", ALL_REPEAT, BackgroundSize.DEFAULT));
}
