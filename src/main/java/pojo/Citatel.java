package pojo;

import structures.splaytree.SplayTree;

public class Citatel {
	private String meno;
	private String priezvisko;
	private int cisloPreukazu;
	private SplayTree<Integer, Vypozicka> aktualnePozicaneKnihy;
	private SplayTree<Integer, Vypozicka> knihyPozicaneVMinulosti;
	private SplayTree<String, Vypozicka> oneskoreneVrateneKnihy;
	public Citatel(String meno, String priezvisko, int cisloPreukazu) {
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.cisloPreukazu = cisloPreukazu;
		aktualnePozicaneKnihy = new SplayTree<>();
		knihyPozicaneVMinulosti = new SplayTree<>();
		oneskoreneVrateneKnihy = new SplayTree<>();
	}
	
}
