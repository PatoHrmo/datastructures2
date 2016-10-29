package pojo;

public enum Zaner {
	DETI("Knihy pre deti a mládež"),
	ROMANTIKA("Romantické"),
	KLASIKA("Klasické"),
	KRIMI("Krimi"),
	SCIFI("Sci-fi"),
	HISTORIA("Historické"),
	THRILLER("Thriller"),
	FILOZOFIA("Filozofické");
	private final String popis;
	
	Zaner(String popis) {
		this.popis = popis;
	}
	public String getPopis() {
		return this.popis;
	}
	
}
