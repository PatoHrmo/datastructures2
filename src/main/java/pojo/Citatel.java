package pojo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import structures.splaytree.SplayTree;

public class Citatel {
	private String meno;
	private String priezvisko;
	private int cisloPreukazu;
	private LocalDate datumOdblokovania;
	private static int nextCisloPreukazu = 1;
	private SplayTree<Integer, Vypozicka> aktualnePozicaneKnihyPodlaID;
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
		aktualnePozicaneKnihyPodlaID = new SplayTree<>();
	}
	public void pridajNovuVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> pozicane = aktualnePozicaneKnihy.find(vypozicka.getNazovKnihy());
		if(pozicane == null) {
			pozicane  = new SplayTree<Integer, Vypozicka>();
			aktualnePozicaneKnihy.insert(vypozicka.getNazovKnihy(), pozicane);
		}
		pozicane.insert(vypozicka.getIDKnihy(), vypozicka);
		aktualnePozicaneKnihyPodlaID.insert(vypozicka.getIDKnihy(), vypozicka);
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
	public Vypozicka getVypozickuPodlaID(int id){
		return aktualnePozicaneKnihyPodlaID.find(id);
	}
	public void vymazVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky = aktualnePozicaneKnihy.find(vypozicka.getNazovKnihy());
		if(vypozicky!=null) {
			vypozicky.delete(vypozicka.getIDKnihy());
		}
		aktualnePozicaneKnihyPodlaID.delete(vypozicka.getIDKnihy());
		
		SplayTree<Integer, Vypozicka> knihyVminulosti = knihyPozicaneVMinulosti.find(vypozicka.getNazovKnihy());
		if(knihyVminulosti == null) {
			knihyVminulosti = new SplayTree<>();
			knihyPozicaneVMinulosti.insert(vypozicka.getNazovKnihy(), knihyVminulosti);
		}
		knihyVminulosti.insert(vypozicka.getIDKnihy(), vypozicka);
		
		if(vypozicka.bolaVratenaNeskor()) {
			SplayTree<Integer, Vypozicka> knihyNeskorsieOdovzdane = oneskoreneVrateneKnihy.find(vypozicka.getNazovKnihy());
			if(knihyNeskorsieOdovzdane == null) {
				knihyNeskorsieOdovzdane = new SplayTree<>();
				oneskoreneVrateneKnihy.insert(vypozicka.getNazovKnihy(), knihyNeskorsieOdovzdane);
			}
			knihyNeskorsieOdovzdane.insert(vypozicka.getIDKnihy(), vypozicka);
		}
	}
	public List<Vypozicka> oneskoreneVypozicky(LocalDate od, LocalDate doo) {
		List<Vypozicka> vypozicky = new ArrayList<>();
		List<SplayTree<Integer,Vypozicka>> zoznamyOneskorenychVypoziciek = oneskoreneVrateneKnihy.toList();
		for(SplayTree<Integer, Vypozicka> zoznamOneskorenychVypoziciek : zoznamyOneskorenychVypoziciek) {
			for(Vypozicka vypozicka : zoznamOneskorenychVypoziciek.toList()) {
				if(vypozicka.getDatumKedySaVratila().isAfter(od) &&
						vypozicka.getDatumKedySaVratila().isBefore(doo)){
					vypozicky.add(vypozicka);
				}
			}
		}
		return vypozicky;
	}
	public List<Kniha> getKnihyPozicaneVMinulosti() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypozicieksRovnakymNazvom = knihyPozicaneVMinulosti.toList();
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
	public boolean maPozicaneKnihy() {
		if(aktualnePozicaneKnihyPodlaID.getSize()==0){
			return false;
		}
		return true;
	}
	public boolean meskaSvratenim(LocalDate aktualnyDatum) {
		for(Vypozicka vypozicka : aktualnePozicaneKnihyPodlaID.toList()) {
			if(vypozicka.meskaSVratenim(aktualnyDatum)) {
				return true;
			}
		}
		return false;
	}
	public void setDatumOdblokovania(LocalDate datum) {
		this.datumOdblokovania = datum;
		
	}
	public boolean jeMoznePozicat(LocalDate datum) {
		if(meskaSvratenim(datum)) {
			return false;
		}
		if(datumOdblokovania==null) {
			return true;
		} else if(datumOdblokovania.isBefore(datum)) {
				datumOdblokovania=null;
				return true;
			} else return false;
		
	}
	
	
}
