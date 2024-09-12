package net.oijon.susquehanna.gui.scenes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.Navbox;
import net.oijon.susquehanna.gui.resources.Backgrounds;
import net.oijon.susquehanna.gui.toolboxes.EmptyTools;
import net.oijon.susquehanna.gui.toolboxes.Toolbox;
import net.oijon.olog.Log;

public abstract class Book extends Scene {
	protected String id = "null.null";
	protected static Log log = App.getLog();
	// root hbox
	protected HBox root = new HBox();
	
	// main components
	protected Navbox navbox = new Navbox();
	protected Toolbox toolbox = new EmptyTools();
	protected HBox bookProper = new HBox();
	protected VBox indicator = new VBox();
	protected VBox rightWood = new VBox();
	
	// non-static entities
	protected VBox leftPage = new VBox();
	protected VBox rightPage = new VBox();
	protected ScrollPane leftScroll = new ScrollPane();
	protected ScrollPane rightScroll = new ScrollPane();
	
	
	/**
	 * Creates an empty book, with default background
	 */
	public Book() {
		// silly, but java does not like instance vars in subconstructors
		super(new HBox());
		// root
		root.setBackground(Backgrounds.WOOD);
		
		// left page
		leftPage.setBackground(Backgrounds.PAPER);
		leftPage.setAlignment(Pos.CENTER);
        leftPage.setPrefWidth(400);
        leftScroll.setContent(leftPage);
        leftScroll.setFitToHeight(true);
        leftScroll.setFitToWidth(true);
        
        // right page
        rightPage.setBackground(Backgrounds.PAPER);
        rightPage.setAlignment(Pos.CENTER);
        rightPage.setPrefWidth(400);
        rightScroll.setContent(rightPage);
        rightScroll.setFitToHeight(true);
        rightScroll.setFitToWidth(true);
        
        Region spacer1 = new Region();
        spacer1.setPrefWidth(80);
        indicator.getChildren().addAll(spacer1);
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        
        Region spacer2 = new Region();
        spacer2.setPrefWidth(60);
        rightWood.getChildren().add(spacer2);
        HBox.setHgrow(spacer2, Priority.SOMETIMES);
        rightWood.setBackground(Backgrounds.RIGHTWOOD);
        
        build();
		super.setRoot(root);
	}
	
	public void updateOnLanguageChange() {
		toolbox.refreshSelected();
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
	 */
	protected void build() {
		// if left page and right page are still stored in vars, this should
		// (hopefully) prevent them from data being wiped
		// should also make sure that Java properly shows changes
		// I know that this is most likely pass-by-reference, but JavaFX is a bit funky
		bookProper.getChildren().clear();
		
		bookProper.getChildren().addAll(makeLeftPages(), leftScroll, makeBinding(),
				rightScroll, makeRightPages());
        HBox.setHgrow(leftScroll, Priority.ALWAYS);
        HBox.setHgrow(rightScroll, Priority.ALWAYS);
        
        HBox.setHgrow(bookProper, Priority.ALWAYS);
        
        indicator.setBackground(toolbox.getBackground());
        
        root.getChildren().clear();
        root.getChildren().addAll(navbox, toolbox, bookProper, indicator, rightWood);
        
		
	}
	
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
		indicator.setBackground(toolbox.getBackground());
		build();
	}
	
	public Toolbox getToolbox() {
		return toolbox;
	}
	
	public void setNavbox(Navbox navbox) {
		this.navbox = navbox;
		build();
	}
	
	public Navbox getNavbox() {
		return navbox;
	}
	
	public String getID() {
		return id;
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
	protected VBox makeLeftPages() {
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
	protected VBox makeRightPages() {
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
