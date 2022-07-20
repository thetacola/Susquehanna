package net.oijon.susquehanna.data;

import java.util.List;

public class Phonology {

	private List<String> phonoList;
	
	public static final Phonology EMPTY = new Phonology(null);
	
	public Phonology(String[] phonoArray) {
		for (int i = 0; i < phonoArray.length; i++) {
			phonoList.add(phonoArray[i]);
		}
	}
	
	public List getList() {
		return phonoList;
	}
}
