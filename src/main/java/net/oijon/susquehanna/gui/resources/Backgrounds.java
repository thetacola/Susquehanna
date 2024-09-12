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
	private static BackgroundFill defaultFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
	private static BackgroundImage plankImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/wood-texture.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
			STRETCH_TO_FIT_SIZE);
    
	private static BackgroundImage bindingBackgroundImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/page-binding.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);

	private static BackgroundImage rightPlankImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/right-wood.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	        BackgroundSize.DEFAULT);
	
	private static BackgroundImage paperImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/paper-texture.png")),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			STRETCH_TO_FIT_SIZE);
    
	private static BackgroundImage padImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/pad-texture.png")),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			STRETCH_TO_FIT_SIZE);
    
	private static BackgroundImage emptyBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/empty-bar.png")),
			BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);
	
	private static BackgroundImage fileBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/file-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
	private static BackgroundImage phonologyBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/phonology-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
    
	private static BackgroundImage orthographyBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/orthography-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
	private static BackgroundImage grammarBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/grammar-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
    
	private static BackgroundImage lexiconBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/lexicon-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
	private static BackgroundImage settingsBarImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/settings-bar.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
    
	private static Image rightPad = new Image(Backgrounds.class.getResourceAsStream("/img/right-pad.png"));
	private static BackgroundImage rightPadBI = new BackgroundImage(rightPad, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
    
	private static BackgroundImage brushedMetalImage = new BackgroundImage(new Image(Backgrounds.class.getResourceAsStream("/img/brushed-metal.png")),
    		BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          	BackgroundSize.DEFAULT);
    
    
    // public backgrounds
    public static Background WOOD = new Background(plankImage);
    public static Background DEFAULT = new Background(defaultFill);
    public static Background BINDING = new Background(bindingBackgroundImage);
    public static Background RIGHTWOOD = new Background(rightPlankImage);
    public static Background PAPER = new Background(paperImage);
    public static Background PAD = new Background(padImage);
    public static Background EMPTYTOOLS = new Background(emptyBarImage);
    public static Background FILETOOLS = new Background(fileBarImage);
    public static Background PHONOLOGYTOOLS = new Background(phonologyBarImage);
    public static Background ORTHOGRAPHYTOOLS = new Background(orthographyBarImage);
    public static Background GRAMMARTOOLS = new Background(grammarBarImage);
    public static Background LEXICONTOOLS = new Background(lexiconBarImage);
    public static Background SETTINGSTOOLS = new Background(settingsBarImage);
    public static Background RIGHTPAD = new Background(rightPadBI);
    public static Background BRUSHEDMETAL = new Background(brushedMetalImage);
}
