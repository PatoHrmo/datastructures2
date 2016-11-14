package pojo;

import java.time.Period;
import java.util.Date;

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
	public boolean bolaVymazana() {
		if(aktualnePriradenaPobocka.knihyPodlaID.find(IDvytlacku)==null){
			return true;
		}
		return false;
	}
	public String getSuboroveUdaje() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
}
