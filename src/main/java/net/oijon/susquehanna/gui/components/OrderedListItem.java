package net.oijon.susquehanna.gui.components;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.oijon.olog.Log;
import net.oijon.susquehanna.App;

public class OrderedListItem extends HBox {

	private int index;
	private OrderedList linkedList;
	private Log log = App.getLog();
	// put as a class-level variable as to allow extensions for dual lists
	private Button label = new Button();
	
	public OrderedListItem(int index, OrderedList linkedList) {
		super();
		
		this.index = index;
		this.linkedList = linkedList;
		
		buildButton();
		build();
	}
	
	protected void buildButton() {
		label.setMaxWidth(Double.MAX_VALUE);
		try {
			label.setText(linkedList.get(index));
		} catch (ArrayIndexOutOfBoundsException e) {
			log.err("Unable to set text of OrderedListItem for given OrderedList!");
			if (log.isDebug()) {
				log.debug("Index: " + index + "; Length: " + linkedList.size());
			}
			label.setText("Invalid list item!");
		}
		
		HBox.setHgrow(label, Priority.ALWAYS);
	}
	
	private void build() {		
		// This HBox is used for spacing buttons
		HBox buttons = new HBox();
		
		Button up = new Button("Up ↑");
		HBox.setHgrow(up, Priority.ALWAYS);
		up.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				linkedList.moveUp(index);
			}
		});
		
		Button down = new Button("Down ↓");
		HBox.setHgrow(down, Priority.ALWAYS);
		down.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				linkedList.moveDown(index);
			}
		});
		
		buttons.getChildren().addAll(up, down);
		
		this.getChildren().addAll(label, buttons);
	}
}
