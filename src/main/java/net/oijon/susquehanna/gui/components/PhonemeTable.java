package net.oijon.susquehanna.gui.components;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.phonology.PhonoCategory;
import net.oijon.oling.datatypes.phonology.PhonoSystem;
import net.oijon.oling.datatypes.phonology.PhonoTable;
import net.oijon.oling.datatypes.phonology.Phonology;

public class PhonemeTable extends Parent {
	
	private Phonology phonology;
	private boolean isEditable = true;
	private List<GridPane> tableList = new ArrayList<GridPane>();
	
	/**
	 * Creates a PhonemeTable, displaying a Phonology via GUI components
	 * @param phonology The Phonology to display via the PhonemeTable
	 */
	public PhonemeTable(Phonology phonology) {
		this.phonology = phonology;
		build();
	}
	
	/**
	 * Similar to PhonemeTable(Phonology), with an extra toggle to make the table read-only
	 * @param phonology The Phonology to display via the PhonemeTable
	 * @param isEditable True if editable, false if view-only
	 */
	public PhonemeTable(Phonology phonology, boolean isEditable) {
		this.phonology = phonology;
		this.isEditable = isEditable;
		build();
		createContainer();
	}
	
	/**
	 * Refreshes the table, checking for any new changes and displaying them
	 */
	public void refresh() {
		List<HBox> cells = generateCellList();
		
		for (HBox hbox : cells) {
			List<PhonemeButton> cell = getButtonsInCell(hbox);
			
			checkPhonemesInCell(hbox);
			removeDuplicates(hbox, cell);
		}
	}
	
	/**
	 * Builds the PhonemeTable and allows it to display. Identical to generateFromPhonosys();
	 */
	private void build() {
		generateFromPhonosys();
	}
	
	/**
	 * Adds any missing phonemes with diacritics to a cell
	 * 
	 * @param cell The cell to be checked for missing phonemes
	 * @param buttons The buttons inside the cell
	 * @param phonemes The list of phonemes in the cell
	 */
	private void checkPhonemesInCell(HBox cell) {
		List<PhonemeButton> buttons = getButtonsInCell(cell);
		List<String> phonemes = getPhonemesFromButtons(buttons);
		
		for (PhonemeButton button : buttons) {
			boolean inPhono = phonology.getList().contains(button.getPhoneme());
			button.setInPhono(inPhono);
			List<String> diacritics = getListOfDiacritisizedPhonemes(button.getPhoneme());
			
			for (String diacriticPhoneme : diacritics) {
				if (!phonemes.contains(diacriticPhoneme)) {
					PhonemeButton pb = new PhonemeButton(diacriticPhoneme, this, isEditable);
					pb.setInPhono(true);
					cell.getChildren().add(pb);
					phonemes.add(diacriticPhoneme);
				}
			}
		}
	}
	
	/**
	 * Creates the container for each PhonoTable shown in the PhonemeTable
	 */
	private void createContainer() {
		this.getChildren().removeAll();
		VBox container = new VBox();
		for (GridPane grid : tableList) {
			container.getChildren().add(grid);
		}
		this.getChildren().add(container);
	}
	
	/**
	 * Generates a list of all cells in the PhonemeTable
	 * @return A list of all cells in the PhonemeTable
	 */
	private List<HBox> generateCellList() {
		List<HBox> cells = new ArrayList<HBox>();
		
		// loop through each table
		for (GridPane table : tableList) {
			ObservableList<Node> children = table.getChildren();
			// loop through each cell in table
			for (Node child : children) {
				if (child instanceof HBox) {
					HBox cell = (HBox) child;
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	/**
	 * Generates regex for filtering out diacritics in a given PhonoSystem
	 * @return The regex to check for diacritics
	 */
	private String generateDiacriticRegex() {
		String diacriticRegex = "[";
		for (int i = 0; i < phonology.getPhonoSystem().getDiacritics().size(); i++) {
			diacriticRegex += phonology.getPhonoSystem().getDiacritics().get(i);
		}
		diacriticRegex += "]*";
		return diacriticRegex;
	}
	
	/**
	 * Creates a table for each PhonoTable found in the linked PhonoSystem
	 */
	private void generateFromPhonosys() {
		PhonoSystem ps = phonology.getPhonoSystem();
		for (int i = 0; i < ps.getTables().size(); i++) {
			tableList.add(generateTable(ps.getTables().get(i)));
		}
		refresh();
	}
	
	/**
	 * Creates the labels needed for the given PhonoTable
	 * @param pt The PhonoTable to generate the labels for
	 * @return A GridPane with labels for each row and column, without any data in
	 * said rows or columns
	 */
	private GridPane generatePaneWithLabels(PhonoTable pt) {
		GridPane gp = new GridPane();
		// top labels
		for (int i = 0; i < pt.getColumnNames().size(); i++) {
			Label l = new Label(pt.getColumnNames().get(i));
			if ("No column names".equals(l.getText())) {
				l.setText("");
			}
			// TODO: this text does not align correctly
			l.setAlignment(Pos.CENTER);
			GridPane.setRowIndex(l, 0);
			GridPane.setColumnIndex(l, i + 1);
			gp.getChildren().add(l);
		}
		return gp;
	}
	
	/**
	 * Gets a list of PhonemeButtons in a cell
	 * @param cell The cell to extract PhonemeButtons from
	 * @return A list of all PhonemeButtons in the given cell
	 */
	private List<PhonemeButton> getButtonsInCell(HBox cell) {
		List<PhonemeButton> buttonList = new ArrayList<PhonemeButton>();
		ObservableList<Node> children = cell.getChildren();
		
		for (int i = 0; i < children.size(); i++) {
			Node child = children.get(i);
			if (child instanceof PhonemeButton) {
				PhonemeButton pb = (PhonemeButton) child;
				buttonList.add(pb);
			}
		}
		
		return buttonList;
	}
	
	/**
	 * Gets a list of phonemes with diacritics using a base phoneme
	 * @param phoneme The diacriticless phoneme to use as a base
	 * Note that one could include a diacritic here, however it would only give phonemes
	 * that contain that specific diacritic.
	 * @return A list of all phonemes using diacritics with the given phoneme as a base
	 */
	private List<String> getListOfDiacritisizedPhonemes(String phoneme) {
		List<String> list = new ArrayList<String>();
		List<String> phonemes = phonology.getList();
		
		String diacriticRegex = generateDiacriticRegex();
		for (String listPhoneme : phonemes) {
			if (Pattern.matches(diacriticRegex + phoneme + diacriticRegex, listPhoneme)) {
				list.add(listPhoneme);
			}
		}		
		
		return list;
	}
	
	/**
	 * Gets a list of phonemes from a list of buttons
	 * @param buttons The buttons to extract phonemes from
	 * @return A list of phonemes, extracted from buttons
	 */
	private List<String> getPhonemesFromButtons(List<PhonemeButton> buttons) {
		List<String> phonemes = new ArrayList<String>();
		
		for (PhonemeButton button : buttons) {
			phonemes.add(button.getPhoneme());
		}
		
		return phonemes;
	}
	
	/**
	 * Removes duplicate buttons in a given cell
	 * 
	 * This also feels like it could be refactored...
	 * 
	 * TODO: Refactor PhonemeTable.removeDuplicates()
	 * 
	 * @param cell The cell to check for duplicates in
	 * @param buttons The buttons in the cell
	 */
	private void removeDuplicates(HBox cell, List<PhonemeButton> buttons) {
		for (int i = 0; i < buttons.size(); i++) {
			
			// used to find duplicates that might still be marked as in the phonology
			int count = 0;
			for (int j = 0; j < phonology.getList().size(); j++) {
				String p1 = buttons.get(i).getPhoneme();
				String p2 = phonology.getList().get(j);
				if (p1.equals(p2)) {
					count++;
				}
			}
			// counts when i == j & j == i, so count will be off by one without this
			count--;
			
			for (int j = 0; j < buttons.size(); j++) {
				PhonemeButton p1 = buttons.get(i);
				PhonemeButton p2 = buttons.get(j);
				// checks that the phonemes are equal, both in the same cell,
				// not the *exact* same button, and not a blank spacer
					if (!"".equals(p1.getPhoneme()) &
						p1.getPhoneme().equals(p2.getPhoneme()) &
						cell.getChildren().contains(p1) &
						cell.getChildren().contains(p2) &
						i != j) {
					// filter out button duplicates already marked as not in phono
					if (!p2.isInPhono()) {
						cell.getChildren().remove(p2);
					} else if (!p1.isInPhono()) {
						cell.getChildren().remove(p1);
					}
					// filter out button duplicates still marked as in phono
					if (count > 0) {
						count--;
					} else {
						cell.getChildren().remove(p2);
					}
				}
			}
		}
	}
	
	/**
	 * Generates a GridPane from a given PhonoTable
	 * @param pt The PhonoTable to use for data and layout
	 * @return a GridPane displaying all data in the PhonoTable
	 */
	private GridPane generateTable(PhonoTable pt) {
		GridPane gp = generatePaneWithLabels(pt);
		
		for (int i = 0; i < pt.size(); i++) {
			
			// generate row label
			Label label = new Label(pt.getRow(i).getName());
			// TODO: this text also does not align correctly
			label.setAlignment(Pos.CENTER_RIGHT);
			GridPane.setRowIndex(label, i + 1);
			GridPane.setColumnIndex(label, 0);
			PhonoCategory row = pt.getRow(i);
			gp.getChildren().add(label);
			
			// queue prevents duplicate HBoxes from being added
			Queue<PhonemeButton> thingsToAdd = new LinkedList<PhonemeButton>();
			// 1 instead of 0 as to give room for side labels
			int colIndicator = 1;
			for (int j = 0; j < row.size(); j++) {
				String sound = row.getSound(j);
				if (!"*".equals(sound) & !"#".equals(sound)) {
					PhonemeButton pb = new PhonemeButton(row.getSound(j), this, isEditable);
					thingsToAdd.add(pb);					
				} else {
					PhonemeButton pb = new PhonemeButton("");
					pb.getMainButton().setDisable(true);
					thingsToAdd.add(pb);
				}
				if ((j + 1) % pt.dataPerCell() == 0) {
					// cell is needed as, although phonosystems have a predictable amount of sounds per category, phonologies do not
					HBox cell = new HBox();
					while (!thingsToAdd.isEmpty()) {
						PhonemeButton pb = thingsToAdd.poll();
						HBox.setHgrow(pb, Priority.ALWAYS);
						cell.getChildren().add(pb);
					}
					GridPane.setRowIndex(cell, i + 1);
					GridPane.setColumnIndex(cell, colIndicator);
					gp.getChildren().add(cell);
					colIndicator++;
				}
			}
		}
		return gp;
	}

}