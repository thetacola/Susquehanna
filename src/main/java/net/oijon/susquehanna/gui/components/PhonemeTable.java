package net.oijon.susquehanna.gui.components;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.oijon.oling.datatypes.phonology.PhonoCategory;
import net.oijon.oling.datatypes.phonology.PhonoSystem;
import net.oijon.oling.datatypes.phonology.PhonoTable;
import net.oijon.oling.datatypes.phonology.Phonology;

public class PhonemeTable extends Parent implements Runnable {
	
	private Phonology p;
	private VBox container;
	private boolean isEditable = true;
	private ArrayList<GridPane> tableList = new ArrayList<GridPane>();
	// TODO: make this a bit more efficient! This is so much better than the previous iteration, but still a bit slow...
	
	public PhonemeTable(Phonology p) {
		this.p = p;
		run();
	}
	
	public PhonemeTable(Phonology p, boolean isEditable) {
		this.p = p;
		this.isEditable = isEditable;
		build();
		createContainer();
	}
	
	public void refresh() {
		for (int i = 0; i < tableList.size(); i++) {
			tableList.set(i, generateTable(p.getPhonoSystem().getTables().get(i)));
		}
	}
	
	private void build() {
		container = new VBox();
		generateFromPhonosys();
	}
	
	private void generateFromPhonosys() {
		PhonoSystem ps = p.getPhonoSystem();
		for (int i = 0; i < ps.getTables().size(); i++) {
			tableList.add(generateTable(ps.getTables().get(i)));
		}
	}
	
	private void createContainer() {
		container = new VBox();
		for (int i = 0; i < tableList.size(); i++) {
			container.getChildren().add(tableList.get(i));
		}
		this.getChildren().add(container);
	}
	
	private GridPane generateLabels(PhonoTable pt, GridPane gp) {
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
		return gp;
	}
	
	private String generateDiacriticRegex() {
		String diacriticRegex = "[";
		for (int i = 0; i < p.getPhonoSystem().getDiacritics().size(); i++) {
			diacriticRegex += p.getPhonoSystem().getDiacritics().get(i);
		}
		diacriticRegex += "]*";
		return diacriticRegex;
	}
	
	private ArrayList<String> findPhonemes(String query) {		
		ArrayList<String> foundPhonemes = new ArrayList<String>();
		String diacriticRegex = generateDiacriticRegex();
		
		for (int i = 0; i < p.getList().size(); i++) {
			if (Pattern.matches(diacriticRegex + query + diacriticRegex, p.getList().get(i)) ) {
				foundPhonemes.add(p.getList().get(i));
			}
		}
		return foundPhonemes;
	}
	
	private GridPane generateRow(PhonoCategory pw, GridPane gp) {
		int rowIndex = gp.getRowCount();
		
		// label
		Label label = new Label(pw.getName());
		// TODO: this text also does not align correctly
		label.setAlignment(Pos.CENTER_RIGHT);
		GridPane.setRowIndex(label, rowIndex);
		GridPane.setColumnIndex(label, 0);
		gp.getChildren().add(label);
		
		for (int i = 0; i < pw.size(); i++) {
			HBox cell = new HBox();
			// filter out blanks
			if (!pw.getSound(i).equals("#") & !pw.getSound(i).equals("*")) {
				// find phonemes in phonology, and mark them
				ArrayList<String> phonemesInCell = findPhonemes(pw.getSound(i));
				if (phonemesInCell.size() > 0) {
					for (int j = 0; j < phonemesInCell.size(); j++) {
						PhonemeButton pb = new PhonemeButton(phonemesInCell.get(j), this, isEditable);
						pb.setInPhono(true);
						cell.getChildren().add(pb);
					}
				} else {
					PhonemeButton pb = new PhonemeButton(pw.getSound(i), this, isEditable);
					pb.getMainButton().setDisable(true);
					cell.getChildren().add(pb);
				}
			} else {
				PhonemeButton pb = new PhonemeButton("", this, false);
				cell.getChildren().add(pb);
			}
			GridPane.setRowIndex(cell, rowIndex);
			GridPane.setColumnIndex(cell, i + 1);
			gp.getChildren().add(cell);
		}
		
		
		return gp;
	}
	
	private GridPane generateTable(PhonoTable pt) {
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);

		gp = generateLabels(pt, gp);
		
		String[] phonoList = new String[p.getList().size()];
		for (int i = 0; i < p.getList().size(); i++) {
			phonoList[i] = new String(p.getList().get(i));
		}
		
		for (int i = 0; i < pt.size(); i++) {
			gp = generateRow(pt.getRow(i), gp);
		}
		
		return gp;
	}

	@Override
	public void run() {
		build();
	}

}
