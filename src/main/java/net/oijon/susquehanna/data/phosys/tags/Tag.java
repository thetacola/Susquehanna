package net.oijon.susquehanna.data.phosys.tags;

public class Tag {
	
	static Tag NULL = new Tag("", "");
	private String name = "";
	private String data = "";

	public Tag(String name, String data) {
		this.name = name;
		this.data = data;
	}
	public Tag(String tag) {
		String[] split = tag.split(":");
		if (split.length == 2) {
			this.name = split[0];
			this.data = split[1];
		} else if (split[0].charAt(split[0].length() - 1) == ':') {
			this.name = split[0].substring(0, split[0].length() - 1);
		} else if (split.length == 1) {
			this.name = split[0];
		}
	}
	
	public String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	public String value() {
		return data;
	}
	public void set(String data) {
		this.data = data;
	}
	public String toString() {
		String returnString = this.name + ":" + this.data;
		return returnString;
	}
	public boolean isSameTag(Tag tag) {
		if (this.name.equals(tag.getName())) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isEqual(Tag tag) {		
		if (this.name.equals(tag.getName())) {
			if (this.data.equals(tag.value())) {
				return true;
			}
		}
		return false;
	}
	
}
