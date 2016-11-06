package main;

import java.time.LocalDate;
import java.util.Date;

import pojo.Citatel;
import pojo.Kniha;
import pojo.Pobocka;
import pojo.Vypozicka;
import structures.splaytree.SplayTree;

public class KniznicnySoftware {
	private LocalDate aktualnyDatum;
	private SplayTree<Integer, Citatel> citateliaPodlaID;
	private SplayTree<String, Pobocka> pobockyPodlaMena;
	private SplayTree<String, SplayTree<Integer,Citatel>> citateliaPodlaMena;
	
	public KniznicnySoftware() {
		aktualnyDatum = LocalDate.now();
		citateliaPodlaID  =new SplayTree<>();
		pobockyPodlaMena = new SplayTree<>();
		citateliaPodlaMena = new SplayTree<>();
	}
	
	public String najdiKnihuPodlaID(int IDknihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if(pobocka == null) return null;
		Kniha kniha = pobocka.najdiKnihuPodlaID(IDknihy);
		return kniha.toString();
	}
	public String[] najdiKnihyPodlaNazvu(String NazovKnihy, String NazovPobocky) {
		Pobocka pobocka = pobockyPodlaMena.find(NazovPobocky);
		if(pobocka==null) return null;
		SplayTree<Integer, Kniha> knihy = pobocka.najdiKnihyPodlaNazvu(NazovKnihy);
		if(knihy !=null) {
			int pocetKnihSTymtoNazvom = knihy.getSize();
			Kniha[] knihyStymtoNazvom = new Kniha[pocetKnihSTymtoNazvom];
			knihy.inOrder(knihyStymtoNazvom);
			String[] udaje = new String[pocetKnihSTymtoNazvom];
			for(int i = 0;i<udaje.length;i++) {
				udaje[i] = knihyStymtoNazvom[i].toString();
			}
			return udaje;
		} else {
			Kniha[] nasledovneKnihy = pobocka.dajDalsieKnihyPodlaAbecedy(5);
			String[] udaje = new String[nasledovneKnihy.length];
			for(int i = 0;i<udaje.length;i++) {
				udaje[i] = nasledovneKnihy[i].toString();
			}
			return udaje;
			
		}
		
	}
	public String zapozicajKnihu(String nazovPobocky, int IDknihy, int cisloPreukazu) {
		Pobocka pobocka = pobockyPodlaMena.find(nazovPobocky);
		if(pobocka==null) {
			return "pobocka "+nazovPobocky+" neexistuje";
		}
		Kniha kniha = pobocka.najdiKnihuPodlaID(IDknihy);
		if(kniha == null) {
			return "kniha s ID "+IDknihy+" na tejto pobocke neexistuje";
		}
		if(kniha.jePozicana()) {
			return "kniha s ID "+IDknihy+" je uz pozicana";
		}
		Citatel citatel = citateliaPodlaID.find(cisloPreukazu);
		if (citatel == null) {
			return "Citatel s ID "+cisloPreukazu+" neexistuje";
		}
		//TODO kontrola ci je citatel svedomity
		Vypozicka vypozicka = new Vypozicka(pobocka, kniha, citatel,
				aktualnyDatum.plus(kniha.getVypozicnaDoba()), aktualnyDatum);
		return "kniha bola zapožièaná";
	}
	
	
}
