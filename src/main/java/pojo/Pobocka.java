package pojo;

import java.util.ArrayList;
import java.util.List;

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
	public List<Kniha> najdiKnihyPodlaNazvu(String NazovKnihy) {
		SplayTree<Integer, Kniha> knihy= knihyPodlaNazvu.find(NazovKnihy);
		if(knihy == null) {
			return new ArrayList<Kniha>();
		}
		return knihy.toList();
	}
	public List<Kniha> dajDalsieKnihyPodlaAbecedy(int pocetKnih) {
		List<SplayTree<Integer, Kniha>> zoznamyKnih = knihyPodlaNazvu.getSuccessorsOfRootinList(pocetKnih);
		List<Kniha> knihy = new ArrayList<>();
		for(SplayTree<Integer, Kniha> zoznam : zoznamyKnih) {
			knihy.add(zoznam.getDataZRootu());
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
	public List<Kniha> getKnihyMomentalneNaPobocke() {
		List<SplayTree<Integer,Kniha>> zoznamyKnihSrovnakymNazvom = knihyPodlaNazvu.toList();
		List<Kniha> vsetkyKnihy = new ArrayList<>();
		for(SplayTree<Integer,Kniha> zoznam : zoznamyKnihSrovnakymNazvom) {
			for(Kniha kniha : zoznam.toList()) {
				if(!kniha.jePozicana()) {
					vsetkyKnihy.add(kniha);
				}
			}
		}
		return vsetkyKnihy;
	}
	public void pridajKnihu (Kniha kniha) {
		knihyPodlaID.insert(kniha.getID(), kniha);
		SplayTree<Integer, Kniha> knihySrovnakymNazvom = knihyPodlaNazvu.find(kniha.getNazov());
		if(knihySrovnakymNazvom == null) {
			knihySrovnakymNazvom = new SplayTree<>();
			knihyPodlaNazvu.insert(kniha.getNazov(), knihySrovnakymNazvom);
		}
		knihySrovnakymNazvom.insert(kniha.getID(), kniha);
	}
	
}
