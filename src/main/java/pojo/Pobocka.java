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
	/**
	 * získa knihu s dannım id na tejto poboèke
	 * @param ID èíslo vıtlaèku knihy ktorú h¾adáme
	 * @return kniha s dannım id alebo null ak taká neexistuje
	 */
	public Kniha najdiKnihuPodlaID(int ID)  {
		return knihyPodlaID.find(ID);
	}
	/**
	 * získa zoznam kníh na tejto poboèke ktoré majú rovnakı názov
	 * @param NazovKnihy názov kníh ktoré h¾adáme
	 * @return list kníh na tejto poboèke ktoré majú rovnakı názov
	 */
	public List<Kniha> najdiKnihyPodlaNazvu(String NazovKnihy,int pocetKnih) {
		SplayTree<Integer, Kniha> knihy= knihyPodlaNazvu.find(NazovKnihy);
		if(knihy == null) {
			List<SplayTree<Integer, Kniha>> zoznamyKnih = knihyPodlaNazvu.getRootAndSuccesorsInList(pocetKnih);
			List<Kniha> dalsieKnihy = new ArrayList<>();
			for(SplayTree<Integer, Kniha> zoznam : zoznamyKnih) {
				dalsieKnihy.add(zoznam.getDataZRootu());
			}
			if(dalsieKnihy.size()!=0) {
				if (dalsieKnihy.get(0).getNazov().compareTo(NazovKnihy)<1) {
					dalsieKnihy.remove(0);
				}
			}
			return dalsieKnihy;
		}
		return knihy.toList();
	}
	/**
	 * získa ïalšie knihy v abecednom poradí od poslednej knihy nad ktorou sa uskutoènila nejaká operácia
	 * @param pocetKnih poèet kníh v abecednom poradí koré chceme vypísa
	 * @return list kníh v abecednom poradí od poslednej knihy nad ktorou sa uskutoènila nejaká operácia
	 */
	public List<Kniha> dajDalsieKnihyPodlaAbecedy(int pocetKnih) {
		List<SplayTree<Integer, Kniha>> zoznamyKnih = knihyPodlaNazvu.getRootAndSuccesorsInList(pocetKnih);
		List<Kniha> knihy = new ArrayList<>();
		for(SplayTree<Integer, Kniha> zoznam : zoznamyKnih) {
			knihy.add(zoznam.getDataZRootu());
		}
		return knihy;
		
	}
	public String getNazov(){
		return nazov;
	}
	/**
	 * pridá do tejto pobocky novú vıpoièku
	 * @param vypozicka objekt reprezentujúci novú vıpoièku 
	 */
	public void pridajNovuVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky  = vypozickyPodlaNazvu.find(vypozicka.getNazovKnihy());
		if(vypozicky==null) {
			vypozicky = new SplayTree<Integer, Vypozicka>();
			vypozickyPodlaNazvu.insert(vypozicka.getNazovKnihy(), vypozicky);
		}
		vypozicky.insert(vypozicka.getIDKnihy(), vypozicka);
		
	}
	/**
	 * získa knihy ktoré sú MOMENTALNE na pobocke
	 * @return list knih ktore sa nachadzaju na pobocke
	 */
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
	/**
	 * získa knihy ktoré sú evidované na tejto poboèke
	 * @return list kníh ktoré sú evidované na tejto poboèke
	 */
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
	/**
	 * získa všetky vıpoièky na tejto poboèke
	 * @return list vpoièiek na tejto poboèke
	 */
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
	/**
	 * pridá do evidencie tejto poboèky novú knihu
	 * @param kniha ktorá bude pridaná do evidencie tejto knihy
	 */
	public void pridajKnihu (Kniha kniha) {
		knihyPodlaID.insert(kniha.getID(), kniha);
		SplayTree<Integer, Kniha> knihySrovnakymNazvom = knihyPodlaNazvu.find(kniha.getNazov());
		if(knihySrovnakymNazvom == null) {
			knihySrovnakymNazvom = new SplayTree<>();
			knihyPodlaNazvu.insert(kniha.getNazov(), knihySrovnakymNazvom);
		}
		knihySrovnakymNazvom.insert(kniha.getID(), kniha);
	}
	/**
	 * vymae vıpoièku knihy na tejto poboèke
	 * @param vypozicka objekt reprezentujúci vıpoièku
	 */
	public void vymazVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky = vypozickyPodlaNazvu.find(vypozicka.getNazovKnihy());
		vypozicky.delete(vypozicka.getIDKnihy());
	}
	/**
	 * vymae knihu z evidencie na tejto poboèke 
	 * @param kniha kniha ktorá bude vymazaná z evidencie na tejto poboèke
	 */
	public void vymazKnihu(Kniha kniha) {
		SplayTree<Integer,Kniha> knihy = knihyPodlaNazvu.find(kniha.getNazov());
		knihy.delete(kniha.getID());
		knihyPodlaID.delete(kniha.getID());
	}
	/**
	 * vymae knihu z evidencie na tejto poboèke 
	 * @param idKnihy èislo vıtlaèku knihy ktorá bude vymazaná z evidencie na tejto poboèke
	 * @return správa o úspešnosti mazania knihy z evidencie
	 */
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
	/**
	 * získa vıpoièky na tejto poboèke pri ktorıch èitatela mešakú s vrátením
	 * @param aktualnyDatum dátum ku ktorému zisujeme meškanie
	 * @return list vypoièiek ktoré meškajú s vrátením
	 */
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
	public String getSuboroveUdaje() {
		return nazov;
	}
	/**
	 * zistí èi je táto poboèka prázdna (teda èi tu nie sú iadne knihy alebo vıpoièky)
	 * @return true ak je prázdna, inak false
	 */
	public boolean jePrazdna() {
		List<SplayTree<Integer,Vypozicka>> zoznamVypoziciekPodlaMena = vypozickyPodlaNazvu.toList();
		for(SplayTree<Integer,Vypozicka> zoznamy : zoznamVypoziciekPodlaMena){
			if (zoznamy.getSize()!=0) {
				return false;
			}
		}
		if(knihyPodlaID.getSize()!=0) {
			return false;
		}
		return true;
	}
	
}
