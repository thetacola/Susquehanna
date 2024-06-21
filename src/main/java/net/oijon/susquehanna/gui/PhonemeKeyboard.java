package net.oijon.susquehanna.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.phonology.PhonoSystem;
import net.oijon.oling.datatypes.phonology.PhonoTable;

public class PhonemeKeyboard extends Parent {

	TextField field = new TextField();
	
	public PhonemeKeyboard(PhonoSystem ps) {
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.TOP_CENTER);
		mainBox.setSpacing(20);
		for (int i = 0; i < ps.getTables().size(); i++) {
			mainBox.getChildren().add(buildTable(ps.getTables().get(i)));
		}
		mainBox.getChildren().add(buildDiacriticTable(ps));
		this.getChildren().add(mainBox);
	}
	
	public PhonemeKeyboard(PhonoSystem ps, TextField field) {
		this(ps);
		this.field = field;
	}
	
	private GridPane buildTable(PhonoTable pt) {
		GridPane gp = new GridPane();
		
		for (int i = 0; i < pt.size(); i++) {
			for (int j = 0; j < pt.getRow(i).size(); j++) {
				String phoneme = pt.getRow(i).getSound(j);
				Button button = new Button();
				if (!phoneme.equals("#") & !phoneme.equals("*")) {
					button = new Button(phoneme);
					
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							String currentText = field.getText();
							field.setText(currentText + phoneme);
						}			
					});
				} else {
					button.setDisable(true);
				}
				GridPane.setRowIndex(button, i);
				GridPane.setColumnIndex(button, j);
				gp.getChildren().add(button);
			}
		}
		gp.setAlignment(Pos.CENTER);
		return gp;
	}
	
	private GridPane buildDiacriticTable(PhonoSystem ps) {
		GridPane gp = new GridPane();
		
		int rowSize = ps.getTables().get(0).getRow(0).size();
		
		int totalAmount = ps.getDiacritics().size();
		int iterations = (totalAmount % rowSize) + 1;
		int count = 0;
		for (int i = 0; i < iterations; i++) {
			for (int j = 0; j < rowSize; j++) {
				if (count >= totalAmount) {
					break;
				} else {
					String diacritic = ps.getDiacritics().get(count);
					Button button = new Button(diacritic);
					
					button.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							String currentText = field.getText();
							field.setText(currentText + diacritic);
						}			
					});
					
					GridPane.setRowIndex(button, i);
					GridPane.setColumnIndex(button, j);
					gp.getChildren().add(button);
					count++;
				}
			}
		}
		gp.setAlignment(Pos.CENTER);
		return gp;
	}
	
	public TextField getTextField() {
		return field;
	}
	
	public void setTextField(TextField field) {
		this.field = field;
	}
	
	
	
}
