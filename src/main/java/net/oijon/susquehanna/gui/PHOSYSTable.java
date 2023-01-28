package net.oijon.susquehanna.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.oijon.susquehanna.data.Language;
import net.oijon.susquehanna.data.PhonoCategory;
import net.oijon.susquehanna.data.PhonoSystem;
import net.oijon.susquehanna.data.PhonoTable;
import net.oijon.susquehanna.data.Phonology;

//last edit: 1/6/2023 -N3

/**
 * Creates a table from a PhonoSystem, allowing easy access in the GUI
 * Based off a VBox.
 * @author alex
 *
 */
public class PHOSYSTable extends VBox {
	
	private PhonoSystem ps;
	private Phonology ph;
	private File file;

	public PHOSYSTable(PhonoSystem ps) {
		/**
		 * Creates a PHOSYS table from a PhonoSystem. The table allows values to be copied
		 * FIXME: This takes over 5 seconds to load! The program also freezes while loading...
		 */
		
		// Although these could be accessed directly, from my understanding that would take up more memory
		// In file form each PhonoSystem is only 1kb, however I have a feeling that if I didn't do this, it
		// could cause a memory leak. I'll have to look into if accessing directly would be pointing by reference
		// or pointing by value... -N3, 5:45am EST 1/2/2023
		
		this.ps = ps;
		ArrayList<PhonoTable> tables = this.ps.getTables();
		
		for (int i = 0; i < tables.size(); i++) {
			PhonoTable table = tables.get(i);
			GridPane tablePane = new GridPane();
			tablePane.setPadding(new Insets(10, 10, 10, 10));
			tablePane.setHgap(2);
			
			// Labels Row
			ArrayList<String> columnLabels = table.getColumnNames();
			for (int j = 0; j < columnLabels.size(); j++) {
				Label label = new Label();
				if (columnLabels.get(j).equals("No column names")) {
					label = new Label("");
				} else {
					label = new Label(columnLabels.get(j));
				}
				tablePane.add(label, j + 1, 0);
			}
			
			// Data
			for (int j = 0; j < table.size(); j++) {
				PhonoCategory row = table.getRow(j);
				Label rowName = new Label(row.getName());
				tablePane.add(rowName, 0, j + 1);
				int dataPerCell = table.dataPerCell();
				
				// To make sure that there are the correct amount of sounds inside a cell,
				// the current cell is instanciated outside the loop. When the modulus of 
				// the amount of sounds per cell compared to the sound index is 0, it will
				// create a new cell. Note that this is technically redundant here, and the
				// new HBox() part isn't exactly needed, however 
				HBox currentCell = new HBox();
				currentCell.setSpacing(5);
				currentCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
				currentCell.setAlignment(Pos.CENTER);
				for (int k = 0; k < row.size(); k++) {
					if (k % dataPerCell == 0 & k != 0) {
						// adds current cell to row and creates a new cell
						tablePane.add(currentCell, (k / dataPerCell), j + 1);
						currentCell = new HBox();
						currentCell.setSpacing(5);
						currentCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
						currentCell.setAlignment(Pos.CENTER);
					}
					String sound = row.getSound(k);
					TextField label = new TextField();
					label.setEditable(false);
					label.setPadding(Insets.EMPTY);
					label.setPrefWidth(20);
					if (sound.equals("#")) {
						label = new TextField(" ");
						label.setEditable(false);
						label.setPadding(Insets.EMPTY);
						label.setPrefWidth(20);
					}
					else if (sound.equals("*")) {
						label = new TextField(" ");
						label.setEditable(false);
						label.setPadding(Insets.EMPTY);
						label.setPrefWidth(20);
					} else {
						label = new TextField(row.getSound(k));
						label.setEditable(false);
						label.setPadding(Insets.EMPTY);
						label.setPrefWidth(20);
					}
					currentCell.getChildren().add(label);
				}
				// should add last column
				tablePane.add(currentCell, (row.size() / dataPerCell), j + 1);
			}
			this.getChildren().add(tablePane);
		}
		
	}
	
	/**
	 * Creates a PHOSYSTable based off a Phonology.
	 * 
	 * This displays characters that are only in a Phonology and the attached PhonoSystem.
	 * @param ph
	 */
	public PHOSYSTable(Language lang, File file) {
		this(lang, false, file); // included to support other functions
	}
	
	public PHOSYSTable(Language lang, boolean isEditable, File file) {
		this.ph = lang.getPhono();
		this.ps = ph.getPhonoSystem();
		this.file = file;
		ArrayList<PhonoTable> tables = this.ps.getTables();
		
		TextField keyboardField = new TextField();
		Button addSound = new Button("Add Sound");
		addSound.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ph.add(keyboardField.getText());
				lang.setPhono(ph);
				try {
					lang.toFile(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				keyboardField.clear();
			}
			
		});
		
		HBox addBox = new HBox();
		addBox.getChildren().addAll(keyboardField, addSound);
		
		for (int i = 0; i < tables.size(); i++) {
			PhonoTable table = tables.get(i);
			GridPane tablePane = new GridPane();
			tablePane.setPadding(new Insets(10, 10, 10, 10));
			tablePane.setHgap(2);
			
			// Labels Row
			ArrayList<String> columnLabels = table.getColumnNames();
			for (int j = 0; j < columnLabels.size(); j++) {
				Label label = new Label();
				if (columnLabels.get(j).equals("No column names")) {
					label = new Label("");
				} else {
					label = new Label(columnLabels.get(j));
				}
				tablePane.add(label, j + 1, 0);
			}
			
			// Data
			for (int j = 0; j < table.size(); j++) {
				PhonoCategory row = table.getRow(j);
				Label rowName = new Label(row.getName());
				tablePane.add(rowName, 0, j + 1);
				int dataPerCell = table.dataPerCell();
				
				// To make sure that there are the correct amount of sounds inside a cell,
				// the current cell is instanciated outside the loop. When the modulus of 
				// the amount of sounds per cell compared to the sound index is 0, it will
				// create a new cell. Note that this is technically redundant here, and the
				// new HBox() part isn't exactly needed, however its nice to have if something breaks
				HBox currentCell = new HBox();
				currentCell.setSpacing(5);
				currentCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
				currentCell.setAlignment(Pos.CENTER);
				for (int k = 0; k < row.size(); k++) {
					if (k % dataPerCell == 0 & k != 0) {
						// adds current cell to row and creates a new cell
						tablePane.add(currentCell, (k / dataPerCell), j + 1);
						currentCell = new HBox();
						currentCell.setSpacing(5);
						currentCell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
						currentCell.setAlignment(Pos.CENTER);
					}
					String sound = row.getSound(k);
					Button button = new Button();
					if (isEditable) {
						if (sound.equals("#")) {
							button = new Button(" ");
							button.setDisable(true);
						}
						else if (sound.equals("*")) {
							button = new Button(" ");
							button.setDisable(true);
						} else {
							button = new Button(sound);
							button.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {
									String currentText = keyboardField.getText();
									keyboardField.setText(currentText + sound);
								}
								
							});
						}
						currentCell.getChildren().addAll(button);
					} else {
						if (ph.isIn(sound) == false) {
							button = new Button(" ");
							button.setDisable(true);
							currentCell.getChildren().add(button);
						}
						else if (sound.equals("#")) {
							button = new Button(" ");
							button.setDisable(true);
							currentCell.getChildren().add(button);
						}
						else if (sound.equals("*")) {
							button = new Button(" ");
							button.setDisable(true);
							currentCell.getChildren().add(button);
						} else {
							for (int l = 0; l < ph.getList().size(); l++) {
								if (ph.getList().get(l).charAt(0) == row.getSound(k).charAt(0)) {
									String currentSound = ph.getList().get(l);
									final Button button2 = new Button(currentSound); // would not compile using button
									button2.setOnAction(new EventHandler<ActionEvent>() {
									File langFile = file;
										@Override
										public void handle(ActionEvent event) {
											ph.remove(currentSound);
											lang.setPhono(ph);
											try {
												lang.toFile(langFile);
											} catch (IOException e) {
												e.printStackTrace();
											}
											button2.setText(" ");
										}
										
									});
									currentCell.getChildren().add(button2);
								}	
							}
						}
						
					}
				}
				// should add last column
				tablePane.add(currentCell, (row.size() / dataPerCell), j + 1);
			}
			this.getChildren().add(tablePane);
		}
		
		GridPane diacriticTable = new GridPane();
		int rowNum = 0;
		ArrayList<String> diacriticList = ph.getPhonoSystem().getDiacritics();
		for (int j = 0; j < ph.getPhonoSystem().getDiacritics().size(); j++) {
			String diacritic = diacriticList.get(j);
			if (isEditable) {
				Button button = new Button(diacritic);
				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						String currentText = keyboardField.getText();
						keyboardField.setText(currentText + diacritic);
					}						
				});
				diacriticTable.add(button, (j) % 10, rowNum);
			} else {
				Label label = new Label(diacritic);
				label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null , null)));
				diacriticTable.add(label, (j) % 10, rowNum);
			}
			if ((j + 1) % 10 == 0) {
				rowNum++;
			}
		}
		
		// TODO: make diacritic table look good on non-editables
		if (isEditable) {
			this.getChildren().addAll(addBox, diacriticTable);
		}
	}
	
	public Phonology getPhono() {
		return ph;
	}
	
}
