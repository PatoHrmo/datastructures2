package pojo;

import structures.splaytree.SplayTree;

public class Citatel {
	private String meno;
	private String priezvisko;
	private int cisloPreukazu;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> aktualnePozicaneKnihy;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> knihyPozicaneVMinulosti;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> oneskoreneVrateneKnihy;
	public Citatel(String meno, String priezvisko, int cisloPreukazu) {
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.cisloPreukazu = cisloPreukazu;
		aktualnePozicaneKnihy = new SplayTree<>();
		knihyPozicaneVMinulosti = new SplayTree<>();
		oneskoreneVrateneKnihy = new SplayTree<>();
	}
	public void pridajNovuVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> pozicane = aktualnePozicaneKnihy.find(vypozicka.getNazovKnihy());
		if(pozicane == null) {
			pozicane  = new SplayTree<>();
			aktualnePozicaneKnihy.insert(vypozicka.getNazovKnihy(), pozicane);
		}
		pozicane.insert(vypozicka.getIDKnihy(), vypozicka);
	}
	
}
