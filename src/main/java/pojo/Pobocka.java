package pojo;

import java.time.LocalDate;
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
	public List<Kniha> getVsetkyKnihyNaPobocke() {
		List<SplayTree<Integer,Kniha>> zoznamyKnihSrovnakymNazvom = knihyPodlaNazvu.toList();
		List<Kniha> vsetkyKnihy = new ArrayList<>();
		for(SplayTree<Integer,Kniha> zoznam : zoznamyKnihSrovnakymNazvom) {
			for(Kniha kniha : zoznam.toList()) {
					vsetkyKnihy.add(kniha);
			}
		}
		return vsetkyKnihy;
	}
	public List<Vypozicka> getVypozicky() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypoziciekSrovnakymNazvom = vypozickyPodlaNazvu.toList();
		List<Vypozicka> vsetkyVypozicky = new ArrayList<>();
		for(SplayTree<Integer,Vypozicka> zoznam : zoznamyVypoziciekSrovnakymNazvom) {
			for(Vypozicka vypozicka : zoznam.toList()) {
				vsetkyVypozicky.add(vypozicka);
			}
		}
		return vsetkyVypozicky;
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
	public void vymazVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky = vypozickyPodlaNazvu.find(vypozicka.getNazovKnihy());
		vypozicky.delete(vypozicka.getIDKnihy());
	}
	public void vymazKnihu(Kniha kniha) {
		SplayTree<Integer,Kniha> knihy = knihyPodlaNazvu.find(kniha.getNazov());
		knihy.delete(kniha.getID());
		knihyPodlaID.delete(kniha.getID());
	}
	public String vymazKnihu(String idKnihy) {
		Kniha kniha = knihyPodlaID.find(Integer.parseInt(idKnihy));
		if(kniha == null) {
			return "kniha s ID "+idKnihy+" na tejto poboèke nie je";
		}
		if(kniha.jePozicana()) {
			return "táto kniha je pozicana";
		}
		knihyPodlaID.delete(Integer.parseInt(idKnihy));
		SplayTree<Integer,Kniha> knihy = knihyPodlaNazvu.find(kniha.getNazov());
		knihy.delete(kniha.getID());
		return "kniha bola vymazana";
		
		
	}
	public List<Vypozicka> getVypozickyKtoreMeskaju(LocalDate aktualnyDatum) {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypoziciekSrovnakymNazvom = vypozickyPodlaNazvu.toList();
		List<Vypozicka> vsetkyVypozicky = new ArrayList<>();
		for(SplayTree<Integer,Vypozicka> zoznam : zoznamyVypoziciekSrovnakymNazvom) {
			for(Vypozicka vypozicka : zoznam.toList()) {
				if(vypozicka.meskaSVratenim(aktualnyDatum)){
					vsetkyVypozicky.add(vypozicka);
				}
				
			}
		}
		return vsetkyVypozicky;
	}
	
}
