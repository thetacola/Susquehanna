package net.oijon.susquehanna.gui.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	
	private Phonology p;
	private VBox container;
	private boolean isEditable = true;
	private ArrayList<GridPane> tableList = new ArrayList<GridPane>();
	// TODO: make this a bit more efficient! This is so much better than the previous iteration, but still a bit slow...
	// TODO: make refresh happen at end of build; don't rely on build to mark what's in/out
	
	public PhonemeTable(Phonology p) {
		this.p = p;
		build();
	}
	
	public PhonemeTable(Phonology p, boolean isEditable) {
		this.p = p;
		this.isEditable = isEditable;
		build();
		createContainer();
	}
	
	
	
	private void build() {
		container = new VBox();
		generateFromPhonosys();
	}
	
	public void refresh() {
		ArrayList<HBox> cells = generateCellList();
		
		for (int i = 0; i < cells.size(); i++) {
			ArrayList<PhonemeButton> cell = getButtonsInCell(cells.get(i));
			ArrayList<String> phonemesInCell = getPhonemesFromButtons(cell);
			
			checkPhonemesInCell(cells.get(i), cell, phonemesInCell);
			findDuplicates(cells.get(i), cell, phonemesInCell);
		}
	}
	
	private ArrayList<HBox> generateCellList() {
		ArrayList<HBox> cells = new ArrayList<HBox>();
		
		// loop through each table
		for (int i = 0; i < tableList.size(); i++) {
			GridPane gp = tableList.get(i);
			ObservableList<Node> children = gp.getChildren();
			// loop through each cell in table
			for (int j = 0; j < children.size(); j++) {
				Node node = children.get(j);
				if (node instanceof HBox) {
					HBox cell = (HBox) node;
					cells.add(cell);
				}
			}
		}
		
		return cells;
	}
	
	private ArrayList<PhonemeButton> getButtonsInCell(HBox cell) {
		ArrayList<PhonemeButton> buttonList = new ArrayList<PhonemeButton>();
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
	
	private ArrayList<String> getPhonemesFromButtons(ArrayList<PhonemeButton> buttons) {
		ArrayList<String> phonemes = new ArrayList<String>();
		
		for (int i = 0; i < buttons.size(); i++) {
			phonemes.add(buttons.get(i).getPhoneme());
		}
		
		return phonemes;
	}
	
	private void checkPhonemesInCell(HBox cell, ArrayList<PhonemeButton> buttons, ArrayList<String> phonemes) {
		for (int i = 0; i < buttons.size(); i++) {
			boolean inPhono = p.getList().contains(buttons.get(i).getPhoneme());
			buttons.get(i).setInPhono(inPhono);
			ArrayList<String> diacritics = getListOfDiacritisizedPhonemes(buttons.get(i).getPhoneme());
			
			for (int j = 0; j < diacritics.size(); j++) {
				if (!phonemes.contains(diacritics.get(j))) {
					PhonemeButton pb = new PhonemeButton(diacritics.get(j), this, isEditable);
					pb.setInPhono(true);
					cell.getChildren().add(pb);
					phonemes.add(diacritics.get(j));
				}
			}
		}
	}
	
	private void findDuplicates(HBox cell, ArrayList<PhonemeButton> buttons, ArrayList<String> phonemes) {
		for (int i = 0; i < buttons.size(); i++) {
			
			// used to find duplicates that might still be marked as in the phonology
			int count = 0;
			for (int j = 0; j < p.getList().size(); j++) {
				String p1 = buttons.get(i).getPhoneme();
				String p2 = p.getList().get(j);
				if (p1.equals(p2)) {
					count++;
				}
			}
			// subtract one for when i == j above
			// counted when i == j & j == i, so count will be off by one
			count--;
			
			for (int j = 0; j < buttons.size(); j++) {
				PhonemeButton p1 = buttons.get(i);
				PhonemeButton p2 = buttons.get(j);
				if (p1.getPhoneme().equals(p2.getPhoneme()) &
						cell.getChildren().contains(buttons.get(i)) &
						cell.getChildren().contains(buttons.get(j)) &
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
	
	private ArrayList<String> getListOfDiacritisizedPhonemes(String phoneme) {
		ArrayList<String> list = new ArrayList<String>();
		List<String> phonemes = p.getList();
		
		String diacriticRegex = generateDiacriticRegex();
		for (int i = 0; i < phonemes.size(); i++) {
			if (Pattern.matches(diacriticRegex + phoneme + diacriticRegex, phonemes.get(i))) {
				list.add(phonemes.get(i));
			}
		}		
		
		return list;
	}
	
	private String generateDiacriticRegex() {
		String diacriticRegex = "[";
		for (int i = 0; i < p.getPhonoSystem().getDiacritics().size(); i++) {
			diacriticRegex += p.getPhonoSystem().getDiacritics().get(i);
		}
		diacriticRegex += "]*";
		return diacriticRegex;
	}
	
	private void generateFromPhonosys() {
		PhonoSystem ps = p.getPhonoSystem();
		for (int i = 0; i < ps.getTables().size(); i++) {
			tableList.add(generateTable(ps.getTables().get(i), p));
		}
	}
	
	private void createContainer() {
		container = new VBox();
		for (int i = 0; i < tableList.size(); i++) {
			container.getChildren().add(tableList.get(i));
		}
		this.getChildren().add(container);
	}
	
	private GridPane generateTable(PhonoTable pt, Phonology p) {
		GridPane gp = new GridPane();
		// top labels
		for (int i = 0; i < pt.getColumnNames().size(); i++) {
			Label l = new Label(pt.getColumnNames().get(i));
			if (l.getText().equals("No column names")) {
				l.setText("");
			}
			// TODO: this text does not align correctly
			l.setAlignment(Pos.CENTER);
			GridPane.setRowIndex(l, 0);
			GridPane.setColumnIndex(l, i + 1);
			gp.getChildren().add(l);
		}
		String diacriticRegex = generateDiacriticRegex();
		
		for (int i = 0; i < pt.size(); i++) {
			Label label = new Label(pt.getRow(i).getName());
			// TODO: this text also does not align correctly
			label.setAlignment(Pos.CENTER_RIGHT);
			GridPane.setRowIndex(label, i + 1);
			GridPane.setColumnIndex(label, 0);
			PhonoCategory row = pt.getRow(i);
			gp.getChildren().add(label);
			// cell is needed as, although phonosystems have a predictable amount of sounds per category, phonologies do not
			Queue<PhonemeButton> thingsToAdd = new LinkedList<PhonemeButton>();
			int colIndicator = 1;
			for (int j = 0; j < row.size(); j++) {
				String sound = row.getSound(j);
				if (!sound.equals("*") & !sound.equals("#")) {
					PhonemeButton pb = new PhonemeButton(row.getSound(j), this, isEditable);
					// search through phonology, mark as in phono if found, and add as many as
					// appear in phono
					Queue<PhonemeButton> addPerSound = new LinkedList<PhonemeButton>();
					for (int k = 0; k < p.getList().size(); k++) {
						String phoneme = p.getList().get(k);
						String firstNonDiacriticChar = Character.toString(phoneme.replace(diacriticRegex, "").charAt(0));
						if (phoneme.equals(sound)) {
							if (pb.isInPhono()) {
								PhonemeButton newPb = new PhonemeButton(pb);
								addPerSound.add(newPb);
							} else {
								pb.setInPhono(true);
							}
						} else if (Pattern.matches(diacriticRegex + sound + diacriticRegex, phoneme)  || firstNonDiacriticChar.equals(sound)) {
							PhonemeButton newPb = new PhonemeButton(phoneme, this, isEditable);
							newPb.setInPhono(true);
							addPerSound.add(newPb);
						}
					}
					thingsToAdd.add(pb);
					while (addPerSound.size() > 0) {
						thingsToAdd.add(addPerSound.poll());
					}
				} else {
					PhonemeButton pb = new PhonemeButton("");
					pb.getMainButton().setDisable(true);
					thingsToAdd.add(pb);
				}
				if ((j + 1) % pt.dataPerCell() == 0) {
					HBox cell = new HBox();
					while (thingsToAdd.size() > 0) {
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