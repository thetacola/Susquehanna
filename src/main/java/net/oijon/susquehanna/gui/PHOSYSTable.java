package net.oijon.susquehanna.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
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
	
	private boolean editable = false;
	private PhonoSystem ps;
	private Phonology ph;
	private File file;
	private ArrayList<PHOSYSCheck> checks = new ArrayList<PHOSYSCheck>(); // will be null if isEditable == false

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
	public PHOSYSTable(Language lang) {
		this(lang, false, null); // included to support other functions
	}
	
	public PHOSYSTable(Language lang, boolean isEditable, File file) {
		this.ph = lang.getPhono();
		this.ps = ph.getPhonoSystem();
		this.file = file;
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
					Label label = new Label();
					PHOSYSCheck check = new PHOSYSCheck();
					boolean addCheck = false;
					if (isEditable) {
						if (sound.equals("#")) {
							label = new Label(" ");
							addCheck = false;
						}
						else if (sound.equals("*")) {
							label = new Label(" ");
							addCheck = false;
						}
						else if (ph.isIn(sound) == false) {
							label = new Label(sound);
							check = new PHOSYSCheck(sound);
							check.setSelected(false);
							addCheck = true;
						} else {
							label = new Label();
							String labelData = "";
							for (int l = 0; l < ph.getList().size(); l++) {
								if (ph.getList().get(l).charAt(0) == row.getSound(k).charAt(0)) {
									labelData += ph.getList().get(l) + " ";
								}	
							}
							label.setText(labelData);
							check = new PHOSYSCheck(sound);
							check.setSelected(true);
							addCheck = true;
						}
						if (addCheck) {
							this.checks.add(check);
							currentCell.getChildren().addAll(label, check);
						} else {
							currentCell.getChildren().add(label);
						}
					} else {
						if (ph.isIn(sound) == false) {
							label = new Label(" ");
						}
						else if (sound.equals("#")) {
							label = new Label(" ");
						}
						else if (sound.equals("*")) {
							label = new Label(" ");
						} else {
							String labelData = "";
							for (int l = 0; l < ph.getList().size(); l++) {
								if (ph.getList().get(l).charAt(0) == row.getSound(k).charAt(0)) {
									labelData += ph.getList().get(l) + " ";
								}	
							}
							label = new Label(labelData);
						}
						currentCell.getChildren().add(label);
					}
				}
				// should add last column
				tablePane.add(currentCell, (row.size() / dataPerCell), j + 1);
			}
			this.getChildren().add(tablePane);
		}
		if (isEditable) {
			Button applyChanges = new Button("Apply Changes");
			applyChanges.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					ph.clear();
					for (int i = 0; i < checks.size(); i++) {
						if (checks.get(i).isSelected()) {
							ph.add(checks.get(i).getSound());
						}
					}
					lang.setPhono(ph);
					try {
						lang.toFile(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        });
			this.getChildren().add(applyChanges);
		}
	}
	
	public Phonology getPhono() {
		return ph;
	}
	
}
