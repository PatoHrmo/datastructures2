package pojo;

import structures.splaytree.SplayTree;

public class Pobocka {
	SplayTree<Integer, Kniha> knihyPodlaID;
	SplayTree<String,SplayTree<Integer, Kniha>> knihyPodlaNazvu;
	SplayTree<String,SplayTree<Integer, Vypozicka>> vypozickyPodlaNazvu;
	String nazov;
	public Pobocka(String nazov) {
		this.nazov = nazov;
		knihyPodlaID = new SplayTree<>();
		knihyPodlaNazvu = new SplayTree<>();
		vypozickyPodlaNazvu = new SplayTree<>();
	}
	public Kniha najdiKnihuPodlaID(int ID)  {
		return knihyPodlaID.find(ID);
	}
	public SplayTree<Integer, Kniha> najdiKnihyPodlaNazvu(String NazovKnihy) {
		return knihyPodlaNazvu.find(NazovKnihy);
	}
	public Kniha[] dajDalsieKnihyPodlaAbecedy(int pocetKnih) {
		@SuppressWarnings("unchecked")
		SplayTree<Integer, Kniha>[] zoznamyKnih = (SplayTree<Integer, Kniha>[]) new Object[pocetKnih];
		int pocetDalsichKnih = zoznamyKnih.length;
		Kniha knihy[];
		knihyPodlaNazvu.getSuccessorsOfRoot(zoznamyKnih);
		
		for(int i = 0;i<zoznamyKnih.length;i++) {
			if(zoznamyKnih[i]==null) {
				pocetDalsichKnih = i;
				break;
			}
		}
		knihy = new Kniha[pocetDalsichKnih];
		for(int i = 0; i<knihy.length;i++) {
			knihy[i] = zoznamyKnih[i].getDataZRootu();
		}
		return knihy;
	}
	public String getNazov(){
		return nazov;
	}
	public void pridajNovuVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky  = vypozickyPodlaNazvu.find(vypozicka.getNazovKnihy());
		if(vypozicky==null) {
			vypozicky = new SplayTree<Integer, Vypozicka>();
			vypozickyPodlaNazvu.insert(vypozicka.getNazovKnihy(), vypozicky);
		}
		vypozicky.insert(vypozicka.getIDKnihy(), vypozicka);
		
	}
	
}
