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
		String info =  "n�zov: " + nazov + " autor: " + autor + " ISBN: " + ISBN + " EAN: " + EAN+System.lineSeparator()
		        + "��ner: " + zaner+ " pobo�ka: " + aktualnePriradenaPobocka.getNazov() + " poplatok za ome�kanie: "
				+ poplatokZaDenOmeskania + " eur"+System.lineSeparator()
				+" ID v�tla�ku:" + IDvytlacku + " v�po�i�n� doba" + vypozicnaDoba.getDays()+ " dn�";
		if(jePozicana()) {
			info+= " je po�i�an�";
		} else {
			info+=" nie je po�i�an�";
		}
		return info;
	}
	/**
	 * zist� �i je t�to kniha moment�lne po�i�an�
	 * @return true ak je t�to kniha momen�lne po�i�an�, inak false
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
	 * zist� �i t�to kniha bola vyraden� zo syst�mu
	 * @return true ak t�to kniha bola vymazan� zo syst�mu, inak false
	 */
	public boolean bolaVymazana() {
		if(aktualnePriradenaPobocka.knihyPodlaID.find(IDvytlacku)==null){
			return true;
		}
		return false;
	}
	/**
	 * z�ska �daje potrebn� pre z�pis tejto knihy do s�boru
	 * @return string reprezentuj�ci d�ta tejto knihy
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
