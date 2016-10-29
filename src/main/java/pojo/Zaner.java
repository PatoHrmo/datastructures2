package pojo;

public enum Zaner {
	DETI("Knihy pre deti a ml�de�"),
	ROMANTIKA("Romantick�"),
	KLASIKA("Klasick�"),
	KRIMI("Krimi"),
	SCIFI("Sci-fi"),
	HISTORIA("Historick�"),
	THRILLER("Thriller"),
	FILOZOFIA("Filozofick�");
	private final String popis;
	
	Zaner(String popis) {
		this.popis = popis;
	}
	public String getPopis() {
		return this.popis;
	}
	
}
