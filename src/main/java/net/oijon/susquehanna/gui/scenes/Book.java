package net.oijon.susquehanna.gui.scenes;

import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
import net.oijon.susquehanna.App;
import net.oijon.utils.logger.Log;

public abstract class Book extends HBox {

	private static InputStream is2 = App.class.getResourceAsStream("/font/OpenSans-Regular.ttf");
	public static Font opensans = Font.loadFont(is2, 16);
	public static BackgroundSize stretchToFit = new BackgroundSize(100, 100, true, true, true, true);
	
	
	protected static Log log = new Log(System.getProperty("user.home") + "/Susquehanna");
	
	protected VBox leftPage = new VBox();
	protected VBox rightPage = new VBox();
	
	/**
	 * Creates an empty book, with default background
	 */
	@SuppressWarnings("static-access")
	public Book() {
		
		 BackgroundImage paperImage = new BackgroundImage(new Image(Book.class.getResourceAsStream("/img/paper-texture.png")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					stretchToFit);
		    Background paperBackground = new Background(paperImage);
		
		leftPage.setBackground(paperBackground);
		rightPage.setBackground(paperBackground);
		
		leftPage.setAlignment(Pos.CENTER);
        rightPage.setAlignment(Pos.CENTER);
        
        leftPage.setPrefWidth(400);
        rightPage.setPrefWidth(400);  
		
		build();
	}
	
	/**
	 * Adds any amount of JavaFX nodes to the left page
	 * @param nodes The JavaFX node to be added to the page
	 */
	public void addToLeft(Node node) {
		leftPage.getChildren().add(node);
		build();
	}
	
	/**
	 * Adds any amount of JavaFX nodes to the right page
	 * @param nodes The JavaFX node to be added to the page
	 */
	public void addToRight(Node node) {
		rightPage.getChildren().add(node);
		build();
	}
	
	/**
	 * Clears the left page
	 */
	public void clearLeft() {
		leftPage.getChildren().clear();
		build();
	}
	
	/**
	 * Clears the right page
	 */
	public void clearRight() {
		rightPage.getChildren().clear();
		build();
	}
	
	/**
	 * Clears both pages
	 */
	public void clear() {
		leftPage.getChildren().clear();
		rightPage.getChildren().clear();
		build();
	}
	
	/**
	 * Builds the book, including both pages, binding, and left/right page images
	 * 
	 * The SuppressWarnings is here because Java does not like how setting HGrows works
	 */
	@SuppressWarnings("static-access")
	protected void build() {
		// if left page and right page are still stored in vars, this should
		// (hopefully) prevent them from data being wiped
		// should also make sure that Java properly shows changes
		// I know that this is most likely pass-by-reference, but JavaFX is a bit funky
		this.getChildren().clear();
		this.getChildren().addAll(makeLeftPages(), leftPage, makeBinding(),
				rightPage, makeRightPages());
		this.setHgrow(leftPage, Priority.ALWAYS);
	    this.setHgrow(rightPage, Priority.ALWAYS);
		
	}
	
	/**
	 * Refreshes the content on both pages. Some Books do not need this.
	 */
	public abstract void refresh();
	
	/* \--------------------/
	 * |  Private Builders  |
	 * /--------------------\
	 */
	
	/**
	 * Makes the left pages decoration
	 * @return The left pages decoration
	 */
	private VBox makeLeftPages() {
		Image leftPapers = new Image(Book.class.getResourceAsStream("/img/left-papers.png"));
	    BackgroundImage leftPapersBI = new BackgroundImage(leftPapers, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
	    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
	    Background leftPapersBG = new Background(leftPapersBI);		
		
		VBox leftPapersVBox = new VBox(new ImageView(leftPapers));
        leftPapersVBox.setBackground(leftPapersBG);
        return leftPapersVBox;
	}
	
	/**
	 * Makes the right pages decoration
	 * @return The right pages decoration
	 */
	private VBox makeRightPages() {
		Image rightPapers = new Image(Book.class.getResourceAsStream("/img/right-papers.png"));
	    BackgroundImage rightPapersBI = new BackgroundImage(rightPapers, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, 
	    		BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
	    Background rightPapersBG = new Background(rightPapersBI);		
		
		VBox rightPapersVBox = new VBox(new ImageView(rightPapers));
        rightPapersVBox.setBackground(rightPapersBG);
        return rightPapersVBox;
	}
	
	/**
	 * Makes the binding decoration
	 * @return The binding decoration
	 */
	private VBox makeBinding() {
		BackgroundImage bindingBackgroundImage = new BackgroundImage(new Image(Book.class.getResourceAsStream("/img/page-binding.png")),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background bindingBackground = new Background(bindingBackgroundImage);
		ImageView bindingImage = new ImageView(new Image(Book.class.getResourceAsStream("/img/page-binding.png")));
		
		VBox binding = new VBox(bindingImage);
	    binding.setBackground(bindingBackground);
	    
	    return binding;
	}
}
