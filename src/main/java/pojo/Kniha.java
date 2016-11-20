package pojo;

import java.time.Period;

public class Kniha {
	private static int nextID = 1;
	private String autor;
	private String nazov;
	private String ISBN;
	private String EAN;
	private String zaner;
	private Pobocka aktualnePriradenaPobocka;
	private double poplatokZaDenOmeskania;
	private int IDvytlacku;
	private Period vypozicnaDoba;
	private Vypozicka vypozickaVKtorejJeMomentalneTatoKniha;
	public Kniha(String nazov, String autor, String iSBN, String eAN, String zaner, Pobocka aktualnePriradenaPobocka,
			double poplatokZaDenOmeskania, Period vypozicnaDoba) {
		this.nazov = nazov;
		this.autor = autor;
		ISBN = iSBN;
		EAN = eAN;
		this.zaner = zaner;
		this.aktualnePriradenaPobocka = aktualnePriradenaPobocka;
		this.poplatokZaDenOmeskania = poplatokZaDenOmeskania;
		this.vypozicnaDoba = vypozicnaDoba;
		IDvytlacku = nextID;
		nextID++;
	}
	public Kniha(String nazov, String autor, String iSBN, String eAN, String zaner, Pobocka aktualnePriradenaPobocka,
			double poplatokZaDenOmeskania, Period vypozicnaDoba, int ID) {
		this.nazov = nazov;
		this.autor = autor;
		ISBN = iSBN;
		EAN = eAN;
		this.zaner = zaner;
		this.aktualnePriradenaPobocka = aktualnePriradenaPobocka;
		this.poplatokZaDenOmeskania = poplatokZaDenOmeskania;
		this.vypozicnaDoba = vypozicnaDoba;
		IDvytlacku = ID;
	}
	@Override
	public String toString() {
		String info =  "názov: " + nazov + " autor: " + autor + " ISBN: " + ISBN + " EAN: " + EAN+System.lineSeparator()
		        + "žáner: " + zaner+ " poboèka: " + aktualnePriradenaPobocka.getNazov() + " poplatok za omeškanie: "
				+ poplatokZaDenOmeskania + " eur"+System.lineSeparator()
				+" ID výtlaèku:" + IDvytlacku + " výpožièná doba" + vypozicnaDoba.getDays()+ " dní";
		if(jePozicana()) {
			info+= " je požièaná";
		} else {
			info+=" nie je požièaná";
		}
		return info;
	}
	/**
	 * zistí èi je táto kniha momentálne požièaná
	 * @return true ak je táto kniha momenálne požièaná, inak false
	 */
	public boolean jePozicana() {
		if(vypozickaVKtorejJeMomentalneTatoKniha==null)
			return false;
		return true;
	}
	public Period getVypozicnaDoba() {
		return this.vypozicnaDoba;
	}
	public void setAktualnaVypozicka(Vypozicka vypozicka) {
		vypozickaVKtorejJeMomentalneTatoKniha = vypozicka;
	}
	public String getNazov() {
		return this.nazov;
	}
	public int getID() {
		return this.IDvytlacku;
	}
	public double getPoplatokZaDenOmeskania() {
		return poplatokZaDenOmeskania;
	}
	public void setPriradenaPobocka(Pobocka pobocka) {
		this.aktualnePriradenaPobocka = pobocka;
		
	}
	public Pobocka getAktualnePriradenaPobocka() {
		return aktualnePriradenaPobocka;
	}
	/**
	 * zistí èi táto kniha bola vyradená zo systému
	 * @return true ak táto kniha bola vymazaná zo systému, inak false
	 */
	public boolean bolaVymazana() {
		if(aktualnePriradenaPobocka.knihyPodlaID.find(IDvytlacku)==null){
			return true;
		}
		return false;
	}
	/**
	 * získa údaje potrebné pre zápis tejto knihy do súboru
	 * @return string reprezentujúci dáta tejto knihy
	 */
	public String getSuboroveUdaje() {
		String udaje = nazov+","+autor+","+ISBN+","+EAN+","+zaner+","+aktualnePriradenaPobocka.getNazov()
		+","+poplatokZaDenOmeskania+","+vypozicnaDoba.getDays()+","+IDvytlacku;
		return udaje;
	}
	public static int getNextID() {
		return nextID;
	}
	public static void setNextID(int nextID) {
		Kniha.nextID = nextID;
	}
		
	
}
