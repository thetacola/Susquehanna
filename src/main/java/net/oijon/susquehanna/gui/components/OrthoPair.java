package net.oijon.susquehanna.gui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class OrthoPair extends HBox {

	/**
	 * Creates an OrthoPair, a component for displaying pairs of orthographic information.
	 * @param left The text that should go on the left
	 * @param right The text that should go on the right
	 */
	public OrthoPair(String left, String right) {
		Label leftLabel = new Label(left);
		Label colonLabel = new Label(":");
		colonLabel.setPadding(new Insets(5, 5, 5, 5));
		Label rightLabel = new Label(right);
		rightLabel.setPadding(new Insets(5, 5, 5, 5));
		this.getChildren().addAll(leftLabel, colonLabel, rightLabel);
	}
	
}
