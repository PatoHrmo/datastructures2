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
	 * prid� tomuto �itate�ovi nov� v�po�i�ku
	 * @param vypozicka objekt reprezentuj�ci v�po�i�ku knihy �itate�om
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
	 * z�ska knihy ktor� ma aktu�lne po�i�an� tento �itate�
	 * @return list kn�h ktor� ma aktu�lne po�i�an� tento �itate�
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
	 * z�ska aktu�lne v�po�i�ky tohto �itate�a
	 * @return list v�po�i�iek ktor� m� aktu�lne tento �itate�
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
	 * z�ska v�po�i�ku tohto u��vate�a pod�a ID v�tla�ku knihy
	 * @param id id v�tla�ku knihy
	 * @return v�po�i�ka obshauj�ca knihu so zadan�m id
	 */
	public Vypozicka getVypozickuPodlaID(int id){
		return aktualnePozicaneKnihyPodlaID.find(id);
	}
	/**
	 * vyma�e aktu�lnu v�po�i�ku a prid� ju ku hist�ri� �itate�a. Ak bola
	 * odovzdan� nesk�r prid� ju aj do zoznamu oneskoren�ch vr�ten� kn�h
	 * @param vypozicka objekt reprezentuj�ci v�po�i�ku knihy �itate�om
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
	 * z�ska objekty reprezentuj�ce oneskoren� vr�tenia kn�h
	 * @param od d�tum od ktor�ho chceme z�ska� oneskoren� v�po�i�ky(sledujeme d�tum vr�tenia)
	 * @param doo d�tum do ktor�ho chceme z�ska� oneskoren� v�po�i�ky(sledujeme d�tum vr�tenia)
	 * @return list objektov reprezentuj�cich onekorene vratenia knih
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
	 * z�ska knihy ktor� boli po�i�an� v minulosti
	 * @return list kn�h ktor� boli po�i�an� v minulosti
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
	 * z�ska v�po�i�ky ktor� u� boli vr�ten�
	 * @return list v�po�i�iek ktor� u� boli vr�ten�
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
	 * zist� �i m� �itate� po�i�an� knihy
	 * @return true ak �itate� m� po�i�an� knihy, inak false
	 */
	public boolean maPozicaneKnihy() {
		if(aktualnePozicaneKnihyPodlaID.getSize()==0){
			return false;
		}
		return true;
	}
	/**
	 * zist� �i �itate� me�ka s vratenim nejakej knihy
	 * @param aktualnyDatum d�tum ku ktor�mu chceme zisti� me�kanie
	 * @return true ak �itate� me�k� s vr�ten�m aspo� jednej knihy
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
	 * nastav� d�tum odblokovnia v�po�i�iek pre tohto �itate�a
	 * @param datum do kedy bude blokovan� tento �itate�
	 */
	public void setDatumOdblokovania(LocalDate datum) {
		this.datumOdblokovania = datum;
		
	}
	/**
	 * z�ska d�tum do kedy je blokovan� �itate�
	 * @return string reprezentuj�ci d�tum do kedy bude blokovan� �itate�
	 */
	public String getDatumOdblokovania() {
		if (datumOdblokovania==null) return "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy");
		return datumOdblokovania.format(formatter);
		
	}
	/**
	 * zist� �i je mo�n� tomuto pou��vate�ovi po�i�a� knihu
	 * @param datum datum ku ktor�mu chceme zisti� mo�nos� po�i�ania knihy
	 * @return true ak je mo�n� po�i�a� knihu �itate�ovi vo zvolen� d�tum, inak false
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
	 * z�ska �daje potrebn� pre z�pis tohto �itate�a do s�boru
	 * @return string reprezentuj�ci d�ta tohto �itate�a
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
