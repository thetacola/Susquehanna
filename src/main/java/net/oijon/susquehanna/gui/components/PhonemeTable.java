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
	
	public void refresh() {
		// FIXME: REFACTOR THIS!!! This is spaghetti right now
		ArrayList<String> phonoList = new ArrayList<String>();
		for (int i = 0; i < p.getList().size(); i++) {
			phonoList.add(new String(p.getList().get(i)));
		}
		for (int i = 0; i < tableList.size(); i++) {
			GridPane gp = tableList.get(i);
			ObservableList<Node> children = gp.getChildren();
			for (int j = 0; j < children.size(); j++) {
				Node c1 = children.get(j);
				if (c1 instanceof HBox) {
					ArrayList<PhonemeButton> cell = new ArrayList<PhonemeButton>();
					ArrayList<String> phonemesInCell = new ArrayList<String>();
					HBox hbox = (HBox) c1;
					
					// generate list of phonemes and phonemebuttons in cell
					for (int k = 0; k < hbox.getChildren().size(); k++) {
						Node c2 = hbox.getChildren().get(k);
						if (c2 instanceof PhonemeButton) {
							PhonemeButton pb = (PhonemeButton) c2;
							cell.add(pb);
							phonemesInCell.add(pb.getPhoneme());
						}
					}
					
					// a bit of a design flaw in the phono tables makes it so that multiple
					// phonemes are in the same cell... as such, *every* phoneme needs
					// their diacritics checked
					for (int k = 0; k < cell.size(); k++) {
						cell.get(k).setInPhono(p.getList().contains(cell.get(k).getPhoneme()));
						ArrayList<String> diacritics = getListOfDiacritisizedPhonemes(cell.get(k).getPhoneme());
						
						for (int l = 0; l < diacritics.size(); l++) {
							if (!phonemesInCell.contains(diacritics.get(l))) {
								PhonemeButton pb = new PhonemeButton(diacritics.get(l), this, isEditable);
								pb.setInPhono(true);
								hbox.getChildren().add(pb);
								cell.add(pb);
								phonemesInCell.add(diacritics.get(l));
							}
						}
					}
					
					// find duplicates not in phono, remove them
					for (int k = 0; k < cell.size(); k++) {
						for (int l = 0; l < cell.size(); l++) {
							if (k != l) {
								if (cell.get(k).getPhoneme().equals(cell.get(l).getPhoneme()) &
										hbox.getChildren().contains(cell.get(k)) &
										hbox.getChildren().contains(cell.get(l))) {
									if (!cell.get(l).isInPhono()) {
										hbox.getChildren().remove(cell.get(l));
									} else if (!cell.get(k).isInPhono()) {
										hbox.getChildren().remove(cell.get(k));
									}
								}
							}
						}
					}
					// find duplicates that are marked in phono but not, remove them
					// this state happens when phonemes are edited
					for (int k = 0; k < cell.size(); k++) {
						int count = -1;
						for (int l = 0; l < p.getList().size(); l++) {
							String phonemeK = cell.get(k).getPhoneme();
							String phonemeL = p.getList().get(l);
							if (phonemeK.equals(phonemeL)) {
								count++;
							}
						}
						
						for (int l = 0; l < cell.size(); l++) {
							if (k != l) {
								if (cell.get(k).getPhoneme().equals(cell.get(l).getPhoneme()) &
										hbox.getChildren().contains(cell.get(k)) &
										hbox.getChildren().contains(cell.get(l))) {
									if (count > 0) {
										count--;
									} else {
										hbox.getChildren().remove(cell.get(l));
									}
								}
							}
						}
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
	
	private void build() {
		container = new VBox();
		generateFromPhonosys();
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