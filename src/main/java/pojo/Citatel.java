package pojo;

import java.util.ArrayList;
import java.util.List;

import structures.splaytree.SplayTree;

public class Citatel {
	private String meno;
	private String priezvisko;
	private int cisloPreukazu;
	private static int nextCisloPreukazu = 1;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> aktualnePozicaneKnihy;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> knihyPozicaneVMinulosti;
	private SplayTree<String, SplayTree<Integer, Vypozicka>> oneskoreneVrateneKnihy;
	public Citatel(String meno, String priezvisko) {
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.cisloPreukazu = nextCisloPreukazu++;
		aktualnePozicaneKnihy = new SplayTree<>();
		knihyPozicaneVMinulosti = new SplayTree<>();
		oneskoreneVrateneKnihy = new SplayTree<>();
	}
	public void pridajNovuVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> pozicane = aktualnePozicaneKnihy.find(vypozicka.getNazovKnihy());
		if(pozicane == null) {
			pozicane  = new SplayTree<Integer, Vypozicka>();
			aktualnePozicaneKnihy.insert(vypozicka.getNazovKnihy(), pozicane);
		}
		pozicane.insert(vypozicka.getIDKnihy(), vypozicka);
	}
	
	public String getMeno() {
		return meno;
	}
	
	public String getPriezvisko() {
		return priezvisko;
	}
	public List<Kniha> getPozicaneKnihy() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypozicieksRovnakymNazvom = aktualnePozicaneKnihy.toList();
		List<Vypozicka> zoznamVypoziciek = new ArrayList<>();
		List<Kniha> zoznamKnih = new ArrayList<>();
		for(SplayTree<Integer, Vypozicka> zoznam : zoznamyVypozicieksRovnakymNazvom) {
			zoznamVypoziciek.addAll(zoznam.toList());
		}
		for(Vypozicka vypozicka : zoznamVypoziciek) {
			zoznamKnih.add(vypozicka.getKniha());
		}
		return zoznamKnih;
		
	}
	public int getCisloPreukazu() {
		return this.cisloPreukazu;
	}
	
	
}
