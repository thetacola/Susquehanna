package net.oijon.susquehanna.gui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.PhonoCategory;
import net.oijon.oling.datatypes.PhonoSystem;
import net.oijon.oling.datatypes.PhonoTable;
import net.oijon.oling.datatypes.Phonology;

public class PhonemeTable extends Parent {
	
	private Phonology p;
	private VBox container;
	private boolean isEditable = true;
	// TODO: make this a bit more efficient! This is so much better than the previous iteration, but still a bit slow...
	
	public PhonemeTable(Phonology p) {
		this.p = p;
		build();
	}
	
	public PhonemeTable(Phonology p, boolean isEditable) {
		this.p = p;
		this.isEditable = isEditable;
		build();
	}
	
	public void refresh() {
		this.getChildren().clear();
		build();
	}
	
	private void build() {
		container = new VBox();
		generateFromPhonosys();
	}
	
	private void generateFromPhonosys() {
		PhonoSystem ps = p.getPhonoSystem();
		container = new VBox();
		for (int i = 0; i < ps.getTables().size(); i++) {
			container.getChildren().add(generateTable(ps.getTables().get(i), p));
		}
		this.getChildren().add(container);
	}
	
	private GridPane generateTable(PhonoTable pt, Phonology p) {
		GridPane gp = new GridPane();
		// top labels
		for (int i = 0; i < pt.getColumnNames().size(); i++) {
			Label l = new Label(pt.getColumnNames().get(i));
			// TODO: this text does not align correctly
			l.setAlignment(Pos.CENTER);
			GridPane.setRowIndex(l, 0);
			GridPane.setColumnIndex(l, i + 1);
			gp.getChildren().add(l);
		}
		// generate diacritic regex match string
		String diacriticRegex = "[";
		for (int i = 0; i < p.getPhonoSystem().getDiacritics().size(); i++) {
			diacriticRegex += p.getPhonoSystem().getDiacritics().get(i);
		}
		diacriticRegex += "]*";
		
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
						if (phoneme.equals(sound)) {
							if (pb.isInPhono()) {
								PhonemeButton newPb = new PhonemeButton(pb);
								addPerSound.add(newPb);
							} else {
								pb.setInPhono(true);
							}
						} else if (Pattern.matches(diacriticRegex + sound + diacriticRegex, phoneme)) {
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
						cell.getChildren().add(thingsToAdd.poll());
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
