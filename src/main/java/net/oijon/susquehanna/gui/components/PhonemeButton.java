package net.oijon.susquehanna.gui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.susquehanna.App;

public class PhonemeButton extends Parent {

	private Button phonemeButton;
	private ToolButton add;
	private ToolButton edit;
	private ToolButton trash;
	private String phoneme = "";
	private boolean isEditable = false;
	private boolean inPhono = false;
	private VBox actionButtons = new VBox();
	private PhonemeTable pt;
	
	/**
	 * Creates a PhonemeButton, with a toolbar of actions on the right side
	 * @param phoneme The phoneme to display on the main button
	 */
	public PhonemeButton(String phoneme) {
		this.phoneme = phoneme;
		build();
	}
	
	public PhonemeButton (PhonemeButton pb) {
		this.phoneme = pb.phoneme;
		this.isEditable = pb.isEditable;
		this.inPhono = pb.inPhono;
		build();
	}
	
	public PhonemeButton(String phoneme, boolean isEditable) {
		this.phoneme = phoneme;
		this.isEditable = isEditable;
		build();
	}
	
	public PhonemeButton(String phoneme, PhonemeTable pt, boolean isEditable) {
		this.phoneme = phoneme;
		this.pt = pt;
		this.isEditable = isEditable;
		build();
	}
	
	private void build() {
		phonemeButton = new Button(phoneme);
		
		buildAdd();
		buildEdit();
		buildTrash();
		
		actionButtons.setAlignment(Pos.CENTER);
		// FIXME: javafx randomly adds spacing (???)
		actionButtons.setSpacing(-5);
		
		actionButtons.getChildren().add(add);
		if (inPhono) {
			actionButtons.getChildren().addAll(edit, trash);
			phonemeButton.setStyle("-fx-base: #00AA00;");
		} else {
			phonemeButton.setStyle("");
		}
		
		//TODO: make buttons grow with their table
		
		phonemeButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		phonemeButton.setMinSize(45, 45);
		
		HBox mainHBox = new HBox();
		HBox.setMargin(phonemeButton, new Insets(0));
		HBox.setHgrow(phonemeButton, Priority.ALWAYS);
		mainHBox.getChildren().add(phonemeButton);
		
		phonemeButton.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				if (isEditable) {
					if (mainHBox.getChildren().contains(actionButtons)) {
						HBox.setMargin(phonemeButton, new Insets(0));
						mainHBox.getChildren().remove(actionButtons);
					} else {
						HBox.setMargin(phonemeButton, new Insets(0, -15, 0, 0));
						mainHBox.getChildren().add(actionButtons);
					}
				}
			}
			
		});
		if (!isEditable) {
			phonemeButton.setDisable(true);
		}
		
		this.getChildren().add(mainHBox);
		HBox.setHgrow(mainHBox, Priority.ALWAYS);
	}
	
	/**
	 * Gets the left, main button
	 * @return The main button out of the four
	 */
	public Button getMainButton() {
		return phonemeButton;
	}
	
	/**
	 * Gets the add button
	 * @return The add button
	 */
	public Button getAddButton() {
		return add;
	}
	
	/**
	 * Gets the edit button
	 * @return The edit button
	 */
	public Button getEditButton() {
		return edit;
	}
	
	/**
	 * Gets the trash button
	 * @return The trash button
	 */
	public Button getTrashButton() {
		return trash;
	}
	
	/**
	 * Gets the phoneme displayed on the button
	 * @return The phoneme used on this button
	 */
	public String getPhoneme() {
		return phoneme;
	}
	
	/**
	 * Sets the phoneme displayed on the button
	 * @param phoneme The new phoneme to be used
	 */
	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
		phonemeButton.setText(phoneme);
	}
	
	public PhonemeTable getPhonemeTable() {
		return pt;
	}
	
	public void setPhonemeTable(PhonemeTable pt) {
		this.pt = pt;
	}
	
	/**
	 * Gets if the phoneme shown is editable or not
	 * @return The editability status of the phoneme this button represents
	 */
	public boolean isEditable() {
		return isEditable;
	}
	
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	public boolean isInPhono() {
		return inPhono;
	}
	
	public void setInPhono(boolean inPhono) {
		this.inPhono = inPhono;
		if (inPhono == true) {
			phonemeButton.setStyle("-fx-base: #00AA00;");
			actionButtons.getChildren().addAll(edit, trash);
		} else {
			phonemeButton.setStyle("");
			actionButtons.getChildren().removeAll(edit, trash);
		}
	}
	
	private void buildAdd() {
		add = new ToolButton("add");		
		add.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		PhonemeButton me = this;
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (inPhono) {
					Parent parent = me.getParent();
					if (parent != null) {
						if (parent instanceof Pane) {
							// needed to add to parent
							PhonemeButton pb = new PhonemeButton(me);
							Pane p = (Pane) parent;
							p.getChildren().add(pb);
						}						
					}
				} else {
					inPhono = true;
					actionButtons.getChildren().addAll(edit, trash);
					phonemeButton.setStyle("-fx-base: #00AA00;");
				}
				// assuming that the currently selected language is always the one being edited
				// might break in the future...
				Language lang = App.getSelectedLang();
				lang.getPhono().add(phoneme);
				App.writeToSelectedLang();
				refreshTable();
			}
		});
		VBox.setMargin(add, Insets.EMPTY);
	}
	
	private void buildEdit() {
		edit = new ToolButton("edit");
		edit.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final PhonemeEditPopup popup = new PhonemeEditPopup(phoneme, pt);
                popup.show();
                App.writeToSelectedLang();
        		refreshTable();
			}
		});
		VBox.setMargin(edit, Insets.EMPTY);
	}
	
	private void buildTrash() {
		trash = new ToolButton("trash");
		trash.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		PhonemeButton me = this;
		trash.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				me.setInPhono(false);
				Language lang = App.getSelectedLang();
				lang.getPhono().getList().remove(phoneme);
				App.writeToSelectedLang();
				refreshTable();
			}			
		});
		VBox.setMargin(trash, Insets.EMPTY);
	}
	
	private void refreshTable() {
		if (pt != null) {
			pt.refresh();
		}
	}
	
	
}
