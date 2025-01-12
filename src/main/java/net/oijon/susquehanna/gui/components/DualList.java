package net.oijon.susquehanna.gui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DualList extends VBox {

	private OrderedList list1;
	private OrderedList list2;
	
	public DualList(String label1, String label2) {
		Label l1 = new Label(label1);
		Label l2 = new Label(label2);
		this.getChildren().addAll(l1, list1, l2, list2);
	}
	
}
