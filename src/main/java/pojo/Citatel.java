package pojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public Citatel(String meno, String priezvisko, int cisloPreukazu) {
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.cisloPreukazu = cisloPreukazu;
		aktualnePozicaneKnihy = new SplayTree<>();
		knihyPozicaneVMinulosti = new SplayTree<>();
		oneskoreneVrateneKnihy = new SplayTree<>();
		aktualnePozicaneKnihyPodlaID = new SplayTree<>();
	}
	/**
	 * pridá tomuto èitate¾ovi novú vıpoièku
	 * @param vypozicka objekt reprezentujúci vıpoièku knihy èitate¾om
	 */
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
	/**
	 * získa knihy ktoré ma aktuálne poièané tento èitate¾
	 * @return list kníh ktoré ma aktuálne poièané tento èitate¾
	 */
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
	/**
	 * získa aktuálne vıpoièky tohto èitate¾a
	 * @return list vıpoièiek ktoré má aktuálne tento èitate¾
	 */
	public List<Vypozicka> getAktualneVypozicky() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypozicieksRovnakymNazvom = 
				aktualnePozicaneKnihy.toListLevelOrder();
		List<Vypozicka> zoznamVypoziciek = new ArrayList<>();
		for(SplayTree<Integer, Vypozicka> zoznam : zoznamyVypozicieksRovnakymNazvom) {
			zoznamVypoziciek.addAll(zoznam.toListLevelOrder());
		}
		return zoznamVypoziciek;
		
	}
	public int getCisloPreukazu() {
		return this.cisloPreukazu;
	}
	/**
	 * získa vıpoièku tohto uívate¾a pod¾a ID vıtlaèku knihy
	 * @param id id vıtlaèku knihy
	 * @return vıpoièka obshaujúca knihu so zadanım id
	 */
	public Vypozicka getVypozickuPodlaID(int id){
		return aktualnePozicaneKnihyPodlaID.find(id);
	}
	/**
	 * vymae aktuálnu vıpoièku a pridá ju ku histórií èitate¾a. Ak bola
	 * odovzdaná neskôr pridá ju aj do zoznamu oneskorenıch vrátení kníh
	 * @param vypozicka objekt reprezentujúci vıpoièku knihy èitate¾om
	 */
	public void vymazVypozicku(Vypozicka vypozicka) {
		SplayTree<Integer, Vypozicka> vypozicky = aktualnePozicaneKnihy.find(vypozicka.getNazovKnihy());
		if(vypozicky!=null) {
			vypozicky.delete(vypozicka.getIDKnihy());
		}
		aktualnePozicaneKnihyPodlaID.delete(vypozicka.getIDKnihy());
		
		SplayTree<Integer, Vypozicka> knihyVminulosti = knihyPozicaneVMinulosti.find(vypozicka.toID());
		if(knihyVminulosti == null) {
			knihyVminulosti = new SplayTree<>();
			knihyPozicaneVMinulosti.insert(vypozicka.toID(), knihyVminulosti);
		}
		knihyVminulosti.insert(vypozicka.getIDKnihy(), vypozicka);
		
		if(vypozicka.bolaVratenaNeskor()) {
			SplayTree<Integer, Vypozicka> knihyNeskorsieOdovzdane = oneskoreneVrateneKnihy.find(vypozicka.toID());
			if(knihyNeskorsieOdovzdane == null) {
				knihyNeskorsieOdovzdane = new SplayTree<>();
				oneskoreneVrateneKnihy.insert(vypozicka.toID(), knihyNeskorsieOdovzdane);
			}
			knihyNeskorsieOdovzdane.insert(vypozicka.getIDKnihy(), vypozicka);
		}
	}
	/**
	 * získa objekty reprezentujúce oneskorené vrátenia kníh
	 * @param od dátum od ktorého chceme získa oneskorené vıpoièky(sledujeme dátum vrátenia)
	 * @param doo dátum do ktorého chceme získa oneskorené vıpoièky(sledujeme dátum vrátenia)
	 * @return list objektov reprezentujúcich onekorene vratenia knih
	 */
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
	/**
	 * získa knihy ktoré boli poièané v minulosti
	 * @return list kníh ktoré boli poièané v minulosti
	 */
	public List<Vypozicka> getKnihyPozicaneVMinulosti() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypozicieksRovnakymNazvom = knihyPozicaneVMinulosti.toList();
		List<Vypozicka> zoznamVypoziciek = new ArrayList<>();
		for(SplayTree<Integer, Vypozicka> zoznam : zoznamyVypozicieksRovnakymNazvom) {
			zoznamVypoziciek.addAll(zoznam.toList());
		}
		
		return zoznamVypoziciek;
	}
	/**
	 * získa vıpoièky ktoré u boli vrátené
	 * @return list vıpoièiek ktoré u boli vrátené
	 */
	public List<Vypozicka> getVypozickyVMinulosti() {
		List<SplayTree<Integer,Vypozicka>> zoznamyVypozicieksRovnakymNazvom = knihyPozicaneVMinulosti.toListLevelOrder();
		List<Vypozicka> zoznamVypoziciek = new ArrayList<>();
		for(SplayTree<Integer, Vypozicka> zoznam : zoznamyVypozicieksRovnakymNazvom) {
			zoznamVypoziciek.addAll(zoznam.toListLevelOrder());
		}
		return zoznamVypoziciek;
	}
	/**
	 * zistí èi má èitate¾ poièané knihy
	 * @return true ak èitate¾ má poièané knihy, inak false
	 */
	public boolean maPozicaneKnihy() {
		if(aktualnePozicaneKnihyPodlaID.getSize()==0){
			return false;
		}
		return true;
	}
	/**
	 * zistí èi èitate¾ meška s vratenim nejakej knihy
	 * @param aktualnyDatum dátum ku ktorému chceme zisti meškanie
	 * @return true ak èitate¾ mešká s vrátením aspoò jednej knihy
	 */
	public boolean meskaSvratenim(LocalDate aktualnyDatum) {
		for(Vypozicka vypozicka : aktualnePozicaneKnihyPodlaID.toList()) {
			if(vypozicka.meskaSVratenim(aktualnyDatum)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * nastavı dátum odblokovnia vıpoièiek pre tohto èitate¾a
	 * @param datum do kedy bude blokovanı tento èitate¾
	 */
	public void setDatumOdblokovania(LocalDate datum) {
		this.datumOdblokovania = datum;
		
	}
	/**
	 * získa dátum do kedy je blokovanı èitate¾
	 * @return string reprezentujúci dátum do kedy bude blokovanı èitate¾
	 */
	public String getDatumOdblokovania() {
		if (datumOdblokovania==null) return "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return datumOdblokovania.format(formatter);
		
	}
	/**
	 * zistí èi je moné tomuto pouívate¾ovi poièa knihu
	 * @param datum datum ku ktorému chceme zisti monos poièania knihy
	 * @return true ak je moné poièa knihu èitate¾ovi vo zvolenı dátum, inak false
	 */
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
	/**
	 * získa údaje potrebné pre zápis tohto èitate¾a do súboru
	 * @return string reprezentujúci dáta tohto èitate¾a
	 */
	public String getSuboroveUdaje() {
		return meno+","+priezvisko+","+cisloPreukazu;
	}
	public static int getNextCisloPreukazu() {
		return nextCisloPreukazu;
	}
	public static void setNextCisloPreukazu(int nextCisloPreukazu) {
		Citatel.nextCisloPreukazu = nextCisloPreukazu;
	}
	
	
}
