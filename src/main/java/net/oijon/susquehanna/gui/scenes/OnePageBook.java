package net.oijon.susquehanna.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public abstract class OnePageBook extends Book {

	/**
	 * Creates a book with one main page, stretching over where the binding usually would be
	 */
	public OnePageBook() {
		BackgroundImage paperImage = new BackgroundImage(new Image(Book.class.getResourceAsStream("/img/paper-texture.png")),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				Backgrounds.STRETCH_TO_FIT_SIZE);
	    Background paperBackground = new Background(paperImage);
	
		leftPage.setBackground(paperBackground);
		
		leftPage.setAlignment(Pos.CENTER);
	    
	    leftPage.setPrefWidth(400);
	    rightPage.setPrefWidth(0);
		
	    leftScroll.setContent(leftPage);
	    
	    leftScroll.setFitToHeight(true);
	    leftScroll.setFitToWidth(true);
	    
		build();
	}
	
	public void add(Node node) {
		addToLeft(node);
	}
	
	// prevent the non-existant right page from being added to
	@Override
	public void addToRight(Node node) {
		addToLeft(node);
	}
	
	@Override
	protected void build() {
		bookProper.getChildren().clear();
		
		bookProper.getChildren().addAll(makeLeftPages(), leftScroll, makeRightPages());
        HBox.setHgrow(leftScroll, Priority.ALWAYS);
        HBox.setHgrow(rightScroll, Priority.ALWAYS);
        
        HBox.setHgrow(bookProper, Priority.ALWAYS);
        
        indicator.setBackground(toolbox.getBackground());
        
        root.getChildren().clear();
        root.getChildren().addAll(navbox, toolbox, bookProper, indicator, rightWood);
		
	}

}
