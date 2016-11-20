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
	 * z�ska knihu s dann�m id na tejto pobo�ke
	 * @param ID ��slo v�tla�ku knihy ktor� h�ad�me
	 * @return kniha s dann�m id alebo null ak tak� neexistuje
	 */
	public Kniha najdiKnihuPodlaID(int ID)  {
		return knihyPodlaID.find(ID);
	}
	/**
	 * z�ska zoznam kn�h na tejto pobo�ke ktor� maj� rovnak� n�zov
	 * @param NazovKnihy n�zov kn�h ktor� h�ad�me
	 * @return list kn�h na tejto pobo�ke ktor� maj� rovnak� n�zov
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
	 * z�ska �al�ie knihy v abecednom porad� od poslednej knihy nad ktorou sa uskuto�nila nejak� oper�cia
	 * @param pocetKnih po�et kn�h v abecednom porad� kor� chceme vyp�sa�
	 * @return list kn�h v abecednom porad� od poslednej knihy nad ktorou sa uskuto�nila nejak� oper�cia
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
	 * prid� do tejto pobocky nov� v�po�i�ku
	 * @param vypozicka objekt reprezentuj�ci nov� v�po�i�ku 
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
	 * z�ska knihy ktor� s� MOMENTALNE na pobocke
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
	 * z�ska knihy ktor� s� evidovan� na tejto pobo�ke
	 * @return list kn�h ktor� s� evidovan� na tejto pobo�ke
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
	 * z�ska v�etky v�po�i�ky na tejto pobo�ke
	 * @return list vpo�i�iek na tejto pobo�ke
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
	 * prid� do evidencie tejto pobo�ky nov� knihu
	 * @param kniha ktor� bude pridan� do evidencie tejto knihy
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
	 * vyma�e v�po�i�ku knihy na tejto pobo�ke
	 * @param vypozicka objekt reprezentuj�ci v�po�i�ku
	 */
	public void vymazVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky = vypozickyPodlaNazvu.find(vypozicka.getNazovKnihy());
		vypozicky.delete(vypozicka.getIDKnihy());
	}
	/**
	 * vyma�e knihu z evidencie na tejto pobo�ke 
	 * @param kniha kniha ktor� bude vymazan� z evidencie na tejto pobo�ke
	 */
	public void vymazKnihu(Kniha kniha) {
		SplayTree<Integer,Kniha> knihy = knihyPodlaNazvu.find(kniha.getNazov());
		knihy.delete(kniha.getID());
		knihyPodlaID.delete(kniha.getID());
	}
	/**
	 * vyma�e knihu z evidencie na tejto pobo�ke 
	 * @param idKnihy �islo v�tla�ku knihy ktor� bude vymazan� z evidencie na tejto pobo�ke
	 * @return spr�va o �spe�nosti mazania knihy z evidencie
	 */
	public String vymazKnihu(String idKnihy) {
		Kniha kniha = knihyPodlaID.find(Integer.parseInt(idKnihy));
		if(kniha == null) {
			return "kniha s ID "+idKnihy+" na tejto pobo�ke nie je";
		}
		if(kniha.jePozicana()) {
			return "t�to kniha je pozicana";
		}
		knihyPodlaID.delete(Integer.parseInt(idKnihy));
		SplayTree<Integer,Kniha> knihy = knihyPodlaNazvu.find(kniha.getNazov());
		knihy.delete(kniha.getID());
		return "kniha bola vymazana";
		
		
	}
	/**
	 * z�ska v�po�i�ky na tejto pobo�ke pri ktor�ch �itatela me�ak� s vr�ten�m
	 * @param aktualnyDatum d�tum ku ktor�mu zis�ujeme me�kanie
	 * @return list vypo�i�iek ktor� me�kaj� s vr�ten�m
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
	 * zist� �i je t�to pobo�ka pr�zdna (teda �i tu nie s� �iadne knihy alebo v�po�i�ky)
	 * @return true ak je pr�zdna, inak false
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
